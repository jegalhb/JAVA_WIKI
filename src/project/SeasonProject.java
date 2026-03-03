package project;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SeasonProject {

    // 공통 공유 데이터들 모음집
    static int globalScore = 0;
    static final int LMit = 20; // 현재 실시간 점수 기능용
    static ArrayList<Integer> scoreHistory = new ArrayList<>(); // 누적 점수 기록 기능용
    static DefaultTableModel tableModel;                 // 랭킹 테이블 기능용

    // 계절 게임과 주식게임 연동용
    static int currentSeasonShared = 0;

    private static void createAndShow() {
        JFrame frame = new JFrame("Season Flower Game: Strategic Investment Edition");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();
        SeasonGamePanel mainGame = new SeasonGamePanel();
        BoatPanel boatPanel = new BoatPanel(); //

        // 메인 탭 구성
        tabs.addTab("게임스타트", mainGame);
        tabs.addTab("꽃 운송로", boatPanel);
        tabs.addTab("스코어 보드", scoreBoardDemo());
        tabs.addTab("계절 주식", stockGamePanel());
        tabs.addTab("계절 적금" ,stockGamePanel());
        tabs.addTab("게임진행기록", scoreHistoryDemo());
        tabs.addTab("랭킹", rankingTableDemo());
        tabs.addTab("데이터", dataManageDemo());

        // 탭 변경 이벤트 처리 기능!
        tabs.addChangeListener(e -> {
            if (tabs.getSelectedIndex() == 0) {
                mainGame.requestFocusInWindow();
                if (mainGame.getIsPlaying()) mainGame.resumeTimer();
            } else if (tabs.getSelectedIndex() == 1) {
                mainGame.stopTimer();
                boatPanel.start(); // 운송 탭 진입 시 애니메이션
            } else {
                mainGame.stopTimer();
                updateAllData(); // 다른 탭으로 이동 시 데이터 최신화
            }
        });

        frame.add(tabs);
        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        mainGame.requestFocusInWindow();
    }


    // 꽃 운송로 기능

    static class BoatPanel extends JPanel {
        private int boatX = -140;        // 배 시작 위치
        private final int boatY = 400;   // 배 기준 y (중앙 하단 배치)
        private final Timer timer;

        BoatPanel() { // 꽃 보트!
            setBackground(Color.BLACK);


            timer = new Timer(30, e -> {
                boatX += 3;

                // 밖으로 나가면 다시 돌아오게
                if (boatX > getWidth() + 140) {
                    boatX = -140;
                }
                repaint(); // 상태 변경 후 다시 그리기
            });
        }

        void start() {
            timer.start();
        }

        @Override
        protected void paintComponent(Graphics g) { // 보트
            super.paintComponent(g); // 잔상 방지 오늘배운거
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            // 바다
            g.setColor(new Color(10, 40, 80));
            g.fillRect(0, h / 2, w, h / 2);

            // 물결 반원형
            g.setColor(new Color(30, 80, 140));
            for (int x = 0; x < w; x += 40) {
                g.drawArc(x, h / 2 + 40, 40, 20, 0, 180);
                g.drawArc(x + 20, h / 2 + 80, 40, 20, 0, 180);
            }

            // 배 그리기
            int x = boatX;
            int y = boatY;

            // 선체
            g.setColor(new Color(120, 70, 30));
            g.fillRoundRect(x, y, 140, 30, 10, 10);

            // 선체 아래
            g.setColor(new Color(90, 50, 20));
            g.fillPolygon(
                    new int[]{x + 10, x + 130, x + 110},
                    new int[]{y + 30, y + 30, y + 55},
                    3
            );

            // 돛대
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(x + 70, y - 80, 4, 80);

            // 돛
            g.setColor(new Color(220, 220, 220));
            g.fillPolygon(
                    new int[]{x + 72, x + 72, x + 125},
                    new int[]{y - 80, y - 20, y - 20},
                    3
            );


            // 바이브 코딩 : 현재 globalScore에 따라 배 위에 분홍색 꽃 알갱이들이 쌓입니다.
            int flowerCount = Math.min(globalScore / 5, 100); // 점수 5점당 꽃 1개 표시 (최대 100개)
            g.setColor(new Color(255, 150, 200));
            Random r = new Random(99); // 꽃 위치 고정을 위한 시드
            for (int i = 0; i < flowerCount; i++) {
                int fx = x + 10 + r.nextInt(120);
                int fy = y - 2 - r.nextInt(15);
                g.fillOval(fx, fy, 6, 6);
            }

            // 선실
            g.setColor(new Color(170, 170, 170));
            g.fillRect(x + 25, y - 25, 35, 25);

            // 창문
            g.setColor(Color.YELLOW);
            g.fillRect(x + 30, y - 20, 8, 8);
            g.fillRect(x + 45, y - 20, 8, 8);

            // 텍스트 정보 표시
            g.setColor(Color.WHITE);
            g.setFont(new Font("맑은 고딕", Font.BOLD, 20));
            g.drawString("운송중인 꽃 자산: " + globalScore + " PTS", 20, 40);
        }
    }
    // 계절 적금 미니게임 패널 기능 시작!
    private static JPanel savingsGamePanel(){
        JPanel p = new JPanel(new BorderLayout(15,15));
        p.setBorder(new TitledBorder("현재 적금 근황"));

        // 적금 데이터 설정
        String [] save = {"[봄] 벚꽃적금","[여름] 삼성에어컨적금" ,"[가을]노랑우산적금","[겨울] 대성쎌틱에너시스적금"};
        int[] savePrices = {100 , 200 , 200 , 400};
        int[] mySaveShares = {0,0,0,0};
        // 상단 적금 투자정보 레이블용
        JLabel SaveinfoLabel = new JLabel("투자가능적금 :" + globalScore + "STS" , SwingConstants.CENTER);
        SaveinfoLabel.setFont(new Font("맑은 고딕" , Font.BOLD,22));
        SaveinfoLabel.setForeground(new Color(61, 231, 47, 208));
        // 적금 리스트 테이블 기능
        DefaultTableModel saveModel = new DefaultTableModel(new String[]{"적금회사명" ,"현재가","변동폭","보유수량"},0);
        JTable saveTable = new JTable(saveModel);
        saveTable.setRowHeight(50);
        for (String s : save) saveModel.addRow(new Object[]{s,100,"0",0});

        // 적금 시즌 테이블 점수 가산점 적용중
        Timer t = new Timer(3000,e -> {
            Random s  = new Random();
            for (int i = 0; i < savePrices.length; i++) {
                int growth = new Random().nextInt(5); // 무조건 0~5 상승
                savePrices[i] += growth;
                saveModel.setValueAt(savePrices[i] + " STS", i, 1);
            }
        });
        t.start();
        return wrap(p,"적금은 원금이 보장된다 ㄷㄷ;;");
    }
    //  계절 주식 미니게임 패널 기능 시작!
    private static JPanel stockGamePanel() {
        JPanel p = new JPanel(new BorderLayout(15, 15));
        p.setBorder(new TitledBorder("실시간 계절 테마주 시장"));

        // 주식 데이터 설정
        String[] stocks = {"[봄] 벚꽃 엔딩 주식", "[여름] 삼성에어컨", "[가을] 노랑우산", "[겨울] 대성쎌틱에너시스"};
        int[] stockPrices = {100, 100, 100, 100}; // 현재가
        int[] myShares = {0, 0, 0, 0};           // 내 보유량

        // 상단 투자 정보 레이블용 기능
        JLabel infoLabel = new JLabel("투자 가능 자산: " + globalScore + " PTS", SwingConstants.CENTER);
        infoLabel.setFont(new Font("맑은 고딕", Font.BOLD, 22));
        infoLabel.setForeground(new Color(70, 130, 180));

        // 주식 리스트 테이블 구성용
        DefaultTableModel stockModel = new DefaultTableModel(new String[]{"종목명", "현재가", "변동폭", "보유수량"}, 0);
        JTable stockTable = new JTable(stockModel);
        stockTable.setRowHeight(30);
        for (String s : stocks) stockModel.addRow(new Object[]{s, 100, "0", 0});

        // [주식 로직] 1.5초마다 주가 변동 및 계절 보너스 적용
        new Timer(5000, e -> {
            Random r = new Random();
            for (int i = 0; i < stockPrices.length; i++) {
                int seasonBonus = (currentSeasonShared == i) ? r.nextInt(15) : r.nextInt(10) - 7;
                stockPrices[i] = Math.max(5, stockPrices[i] + seasonBonus);
                stockModel.setValueAt(stockPrices[i] + " PTS", i, 1);
                stockModel.setValueAt((seasonBonus >= 0 ? "▲ " : "▼ ") + Math.abs(seasonBonus), i, 2);
            }
            infoLabel.setText("투자 가능 자산: " + globalScore + " PTS (현재 계절: " + getSeasonName(currentSeasonShared) + ")");
        }).start();

        // 매수 매도 버튼 추가
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        JButton buyBtn = new JButton("선택 종목 매수 (Buy)");
        JButton sellBtn = new JButton("선택 종목 매도 (Sell)");

        buyBtn.setBackground(new Color(255, 200, 200));
        sellBtn.setBackground(new Color(200, 200, 255));

        // 내 점수를 매각해서 주식을 늘리는 기능!
        buyBtn.addActionListener(e -> {
            int row = stockTable.getSelectedRow();
            if (row != -1 && globalScore >= stockPrices[row]) {
                globalScore -= stockPrices[row];
                myShares[row]++;
                stockModel.setValueAt(myShares[row], row, 3);
            } else if (row == -1) {
                JOptionPane.showMessageDialog(null, "종목을 먼저 선택해주세요!");
            } else {
                JOptionPane.showMessageDialog(null, "점수가 부족합니다! 꽃을 더 모아오세요.");
            }
        });

        // 매도 로직: 주식을 줄이고 점수를 돌려받음
        sellBtn.addActionListener(e -> {
            int row = stockTable.getSelectedRow();
            if (row != -1 && myShares[row] > 0) {
                globalScore += stockPrices[row];
                myShares[row]--;
                stockModel.setValueAt(myShares[row], row, 3);
            }
        });

        btnPanel.add(buyBtn); btnPanel.add(sellBtn);
        p.add(infoLabel, BorderLayout.NORTH);
        p.add(new JScrollPane(stockTable), BorderLayout.CENTER);
        p.add(btnPanel, BorderLayout.SOUTH);

        return wrap(p, "Tip: 계절과 일치하는 주식은 급등할 확률이 높아요!!");
    }

    private static String getSeasonName(int s) {
        return switch(s) {
            case 0 -> "봄"; case 1 -> "여름"; case 2 -> "가을"; case 3 -> "겨울";
            default -> ""; };
    }

    private static JPanel scoreBoardDemo() {
        JPanel p = new JPanel(new BorderLayout(15, 15));
        p.setBorder(new TitledBorder("등록 대기 점수"));
        JLabel scoreLabel = new JLabel("0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Monospaced", Font.BOLD, 120));
        scoreLabel.setForeground(new Color(50, 150, 50));
        new Timer(100, e -> scoreLabel.setText(String.valueOf(globalScore))).start();
        JButton saveBtn = new JButton("기록실에 이 점수를 최종 제출합니다");
        saveBtn.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        saveBtn.setBackground(new Color(70, 130, 180));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.addActionListener(e -> {
            if (globalScore == 0) {
                JOptionPane.showMessageDialog(null, "등록할 점수가 없습니다!");
                return;
            }
            scoreHistory.add(globalScore);
            int registeredScore = globalScore;
            globalScore = 0;
            JOptionPane.showMessageDialog(null, registeredScore + "점이 등록되었습니다!");
        });
        p.add(scoreLabel, BorderLayout.CENTER);
        p.add(saveBtn, BorderLayout.SOUTH);
        return wrap(p, "현재 점수를 기록실에 영구 저장.");
    }

    private static JPanel rankingTableDemo() {
        JPanel p = new JPanel(new GridLayout(1, 1));
        tableModel = new DefaultTableModel(new String[]{"Rank", "Score", "Performance"}, 0);
        p.add(new JScrollPane(new JTable(tableModel)));
        return wrap(p, "전체 랭킹 시스템");
    }

    private static JPanel scoreHistoryDemo() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel cardContainer = new JPanel();
        cardContainer.setLayout(new BoxLayout(cardContainer, BoxLayout.Y_AXIS));
        cardContainer.setBackground(new Color(240, 240, 240));
        JScrollPane scrollPane = new JScrollPane(cardContainer);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        JButton refreshBtn = new JButton(" 기록 로그 갱신 (1등부터 순위순 정렬)");
        refreshBtn.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        refreshBtn.addActionListener(e -> {
            cardContainer.removeAll();
            ArrayList<Integer> sortedList = new ArrayList<>(scoreHistory);
            Collections.sort(sortedList, Collections.reverseOrder());
            for (int i = 0; i < sortedList.size(); i++) {
                int score = sortedList.get(i);
                int rank = i + 1;
                JPanel card = createScoreCard(rank, score);
                cardContainer.add(card);
            }
            cardContainer.revalidate(); cardContainer.repaint();
        });
        mainPanel.add(refreshBtn, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        return wrap(mainPanel, "최고 점수 순 정렬 로그");
    }

    private static JPanel createScoreCard(int rank, int score) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setMaximumSize(new Dimension(850, 80));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 10, 5, 10),
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true)
        ));
        JLabel rankLbl = new JLabel(getOrdinal(rank), SwingConstants.CENTER);
        rankLbl.setFont(new Font("Verdana", Font.ITALIC, 22));
        rankLbl.setPreferredSize(new Dimension(80, 40));
        if (rank == 1) rankLbl.setForeground(new Color(218, 165, 32));
        else if (rank == 2) rankLbl.setForeground(new Color(160, 160, 160));
        else if (rank == 3) rankLbl.setForeground(new Color(180, 115, 60));
        JLabel sLbl = new JLabel(score + " PTS", SwingConstants.CENTER);
        sLbl.setFont(new Font("Verdana", Font.BOLD, 24));
        JLabel tagLbl = new JLabel(score > 300 ? "LEGEND  " : "PLAYER  ");
        tagLbl.setForeground(Color.LIGHT_GRAY);
        card.add(rankLbl, BorderLayout.WEST);
        card.add(sLbl, BorderLayout.CENTER);
        card.add(tagLbl, BorderLayout.EAST);
        return card;
    }

    private static JPanel dataManageDemo() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        statsPanel.setBorder(new TitledBorder("실시간 통계"));
        JLabel avgLbl = new JLabel("평균 점수: 0");
        JLabel maxLbl = new JLabel("최고 점수: 0");
        JLabel cntLbl = new JLabel("총 등록 횟수: 0");
        new Timer(500, e -> {
            if (!scoreHistory.isEmpty()) {
                int sum = 0, max = 0;
                for(int s : scoreHistory) { sum += s; if(s > max) max = s; }
                avgLbl.setText("평균 점수: " + (sum / scoreHistory.size()) + " pts");
                maxLbl.setText("최고 점수: " + max + " pts");
                cntLbl.setText("총 등록 횟수: " + scoreHistory.size() + " 회");
            }
        }).start();
        statsPanel.add(cntLbl); statsPanel.add(maxLbl); statsPanel.add(avgLbl);
        JPanel controlPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        controlPanel.setBorder(new TitledBorder("시스템 관리"));
        JButton exportBtn = new JButton("내 기록 메모장으로 내보내기 (.txt)");
        JButton delBtn = new JButton("모든 데이터 초기화 (영구 삭제)");
        delBtn.setBackground(new Color(255, 100, 100));
        delBtn.setForeground(Color.WHITE);
        delBtn.addActionListener(e -> {
            if(JOptionPane.showConfirmDialog(null, "정말 초기화하시겠습니까?", "경고", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                scoreHistory.clear(); globalScore = 0;
            }
        });
        controlPanel.add(exportBtn); controlPanel.add(delBtn);
        mainPanel.add(statsPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(controlPanel);
        return wrap(mainPanel, "데이터를 분석하고 관리합니다.");
    }

    private static void updateAllData() {
        if (tableModel != null) {
            tableModel.setRowCount(0);
            ArrayList<Integer> sorted = new ArrayList<>(scoreHistory);
            Collections.sort(sorted, Collections.reverseOrder());
            for (int i = 0; i < sorted.size(); i++)
                tableModel.addRow(new Object[]{i + 1, sorted.get(i), sorted.get(i) > 30000 ? "왕부자" : "개미"});
        }
    }

    private static String getOrdinal(int n) {
        if (n <= 0) return String.valueOf(n);
        int mod100 = n % 100;
        int mod10 = n % 10;
        if (mod10 == 1 && mod100 != 11) return n + "st";
        if (mod10 == 2 && mod100 != 12) return n + "nd";
        if (mod10 == 3 && mod100 != 13) return n + "rd";
        return n + "th";
    }

    private static JPanel wrap(JComponent c, String tip) {
        JPanel p = new JPanel(new BorderLayout());
        p.add(c, BorderLayout.CENTER);
        p.add(new JLabel(" 가이드: " + tip), BorderLayout.SOUTH);
        return p;
    }

    // 실제 게임 화면에 필요한것들
    static class SeasonGamePanel extends JPanel {
        private final int COUNT = 45;
        private final Random random = new Random();
        private int currentSeason = 0;
        private float[] xs = new float[COUNT], ys = new float[COUNT], vys = new float[COUNT];
        private float[] angles = new float[COUNT], scales = new float[COUNT];
        private int px = 450, py = 500;
        private int timeLeft = 20;
        private Timer timer;
        private Timer gameTimer;
        private boolean isPlaying = false;
        private JButton startButton;

        SeasonGamePanel() {
            setLayout(null); setFocusable(true);
            startButton = new JButton("GAME START");
            startButton.setBounds(400, 350, 200, 60);
            startButton.setFont(new Font("Arial", Font.BOLD, 20));
            startButton.setBackground(new Color(50, 200, 50));
            startButton.setForeground(Color.WHITE);
            startButton.addActionListener(e -> startGame());
            add(startButton);
            initTimers(); initInputHandlers(); initFlowers();
        }

        private void initTimers() {
            timer = new Timer(30, e -> { if(isPlaying) { updateLogic(); repaint(); } });
            gameTimer = new Timer(1000, e -> {
                if (isPlaying) {
                    timeLeft--;
                    if (timeLeft <= 0) endGame();
                    repaint();
                }
            });
        }

        private void initInputHandlers() {
            addMouseMotionListener(new MouseMotionAdapter() {
                @Override public void mouseMoved(MouseEvent e) {
                    if (isPlaying && currentSeason != 1) { px = e.getX(); py = e.getY(); }
                }
            });
            boolean[] arrowArr = new boolean[4];
            int up = 0, down = 1, right = 2, left = 3;
            addKeyListener(new KeyAdapter() {
                @Override public void keyPressed(KeyEvent e) {
                    if (!isPlaying) return;
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        currentSeason = (currentSeason + 1) % 4;
                        currentSeasonShared = currentSeason;
                    }
                    if (e.getKeyCode() == KeyEvent.VK_UP) arrowArr[up] = true;
                    if (e.getKeyCode() == KeyEvent.VK_DOWN) arrowArr[down] = true;
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) arrowArr[left] = true;
                    if (e.getKeyCode() == KeyEvent.VK_RIGHT) arrowArr[right] = true;
                }
                @Override public void keyReleased(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_UP) arrowArr[up] = false;
                    if (e.getKeyCode() == KeyEvent.VK_DOWN) arrowArr[down] = false;
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) arrowArr[left] = false;
                    if (e.getKeyCode() == KeyEvent.VK_RIGHT) arrowArr[right] = false;
                }
            });
            Timer moveTimer = new Timer(1000 / 60, (e) -> { //
                if(!isPlaying) return;
                double dx = 0, dy = 0;
                if (arrowArr[up]) dy -= 1; if (arrowArr[down]) dy += 1;
                if (arrowArr[left]) dx -= 1; if (arrowArr[right]) dx += 1;
                double d = Math.sqrt(dx*dx + dy*dy);
                if (d > 0) { px += (dx/d) * 7; py += (dy/d) * 7; }
            });
            moveTimer.start();
        }

        private void startGame() {
            isPlaying = true; globalScore = 0; timeLeft = 20;
            currentSeasonShared = currentSeason; startButton.setVisible(false);
            initFlowers(); timer.start(); gameTimer.start(); requestFocusInWindow();
        }

        private void endGame() {
            isPlaying = false; timer.stop(); gameTimer.stop();
            JOptionPane.showMessageDialog(this, "시간초과!!!!!!!사망 ㅠㅠ\nFinal Score: " + globalScore);
            timeLeft = 20; startButton.setText("Retry?"); startButton.setVisible(true); repaint();
        }

        private void initFlowers() { for (int i = 0; i < COUNT; i++) { resetFlower(i); ys[i] = random.nextInt(800); } }
        private void resetFlower(int i) {
            xs[i] = random.nextInt(1000); ys[i] = -50 - random.nextInt(200);
            vys[i] = 1.5f + random.nextFloat() * 4.0f; angles[i] = random.nextInt(360);
            scales[i] = 0.5f + random.nextFloat() * 1.5f;
        }

        public boolean getIsPlaying() { return isPlaying; }
        public void stopTimer() { timer.stop(); gameTimer.stop(); }
        public void resumeTimer() { if(isPlaying) { timer.start(); gameTimer.start(); } }

        private void updateLogic() {
            for (int i = 0; i < COUNT; i++) {
                ys[i] += vys[i]; angles[i] += 2.0f;
                if (ys[i] > getHeight()) resetFlower(i);
                float hitRange = 30 * scales[i];
                if (Math.abs(px - xs[i]) < hitRange + 10 && Math.abs(py - ys[i]) < hitRange + 10) {
                    globalScore += (currentSeason == 1) ? -10 : (int)(15 * scales[i]);
                    resetFlower(i);
                }
            }
        }

        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            drawSeasonBackground(g2);
            if (!isPlaying && globalScore == 0 && timeLeft == 20) {
                drawStartScreen(g2);
            } else {
                g2.setColor(Color.YELLOW); g2.fillOval(px - 15, py - 15, 30, 30);
                for (int i = 0; i < COUNT; i++) drawSeasonFlower(g2, (int)xs[i], (int)ys[i], angles[i], scales[i]);
            }
            drawUI(g2);
        }

        private void drawUI(Graphics2D g2) {
            String[] sNames = {"SPRING", "SUMMER", "FALL", "WINTER"};
            g2.setFont(new Font("SansSerif", Font.BOLD, 22));
            g2.setColor(currentSeason == 0 ? Color.DARK_GRAY : Color.WHITE);
            g2.drawString("Season: " + sNames[currentSeason] + " | Score: " + globalScore, 20, 40);
            if (isPlaying) {
                if (timeLeft <= 10) g2.setColor(Color.RED);
                else g2.setColor(Color.WHITE);
                g2.drawString("Time Left: " + timeLeft + "s", getWidth() - 180, 40);
            }
        }

        private void drawStartScreen(Graphics2D g2) {
            g2.setColor(Color.WHITE); g2.setFont(new Font("맑은 고딕", Font.BOLD, 40));
            g2.drawString("Season Flower Collector", 260, 300);
        }

        private void drawSeasonBackground(Graphics2D g) {
            Color c = isPlaying ? switch(currentSeason) {
                case 0 -> new Color(255, 235, 245); case 1 -> new Color(34, 139, 34);
                case 2 -> new Color(160, 82, 45); case 3 -> new Color(25, 25, 35);
                default -> Color.DARK_GRAY;
            } : Color.DARK_GRAY;
            g.setColor(c); g.fillRect(0, 0, getWidth(), getHeight());
        }

        private void drawSeasonFlower(Graphics2D g, int x, int y, float angle, float scale) {
            AffineTransform old = g.getTransform();
            g.translate(x, y); g.rotate(Math.toRadians(angle)); g.scale(scale, scale);
            if (currentSeason == 0) {
                g.setColor(new Color(255, 182, 193));
                for (int i=0; i<5; i++) { g.fillOval(-4, -10, 8, 12); g.rotate(Math.toRadians(72)); }
            } else if (currentSeason == 1) {
                g.setColor(Color.YELLOW);
                for (int i=0; i<10; i++) { g.fillOval(-3, -12, 6, 15); g.rotate(Math.toRadians(36)); }
                g.setColor(new Color(101, 67, 33)); g.fillOval(-5, -5, 10, 10);
            } else if (currentSeason == 2) {
                g.setColor(new Color(218, 112, 214));
                for (int i=0; i<8; i++) { g.fillRoundRect(-2, -15, 4, 18, 4, 4); g.rotate(Math.toRadians(45)); }
            } else if (currentSeason == 3) {
                g.setColor(Color.WHITE); g.setStroke(new BasicStroke(2));
                for (int i=0; i<6; i++) { g.drawLine(0, 0, 0, -12); g.drawLine(-3, -8, 3, -8); g.rotate(Math.toRadians(60)); }
            }
            g.setTransform(old);
        }

    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SeasonProject::createAndShow);
    }
}