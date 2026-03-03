package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class SimpleVolleyGame extends JPanel implements ActionListener {
    // ====== 화면/게임 설정 ======
    private final int W = 900;
    private final int H = 500;

    private final int GROUND = 430;
    private final int NET_X = W / 2;
    private final int NET_W = 8;
    private final int NET_H = 140;

    private final int P_W = 50;
    private final int P_H = 50;
    private final int BALL_RADIUS = 23;
    private final int SPIKE_RANGE = 14;

    private final double GRAVITY = 0.6;
    private final double JUMP_SPEED = -12;
    private final double MOVE_SPEED = 7.2;

    private final double BALL_GRAVITY = 0.45;

    private final Timer timer = new Timer(16, this);

    // ====== 네트워크/모드 ======
    private enum Mode { MENU, SINGLE, HOST, JOIN }
    private Mode mode = Mode.MENU;

    private VolleyServer server;
    private VolleyClient client;

    private volatile boolean isNetworkClient = false;
    private volatile boolean isNetworkHost = false;

    private volatile double netP1x, netP1y, netP2x, netP2y, netBx, netBy;
    private volatile int netScoreL, netScoreR;
    private volatile boolean netStarted, netPaused, netGameOver, netCountdownActive, netRoundEndActive;
    private volatile int netCountdownValue;
    private volatile String netGameOverMsg = "";
    private volatile String netRoundWinnerMsg = "";
    private volatile float netFadeAlpha = 0f;

    private final InputState localInput = new InputState();

    // ====== 입력 처리 ======
    private final Set<Integer> keys = new HashSet<>();

    // ====== 플레이어 ======
    private double p1x = 200, p1y = GROUND;
    private double p1vx = 0, p1vy = 0;

    private double p2x = 700, p2y = GROUND;
    private double p2vx = 0, p2vy = 0;

    // ====== 공 ======
    private double bx = W / 2.0, by = 200;
    private double bvx = 3.2, bvy = -2;

    // ====== 점수/게임 ======
    private int scoreL = 0;
    private int scoreR = 0;
    private int winScore = 5;

    private boolean paused = false;
    private boolean gameOver = false;
    private boolean started = false;
    private String gameOverMsg = "";

    // ====== 라운드 연출 ======
    private boolean roundEndActive = false;
    private int roundEndTicks = 0;
    private final int ROUND_END_TOTAL = 120; // 약 2초
    private String roundWinnerMsg = "";

    // ====== 페이드 ======
    private float fadeAlpha = 0f;
    private boolean fadingIn = false;
    private boolean fadingOut = false;

    // ====== 카운트다운 ======
    private boolean countdownActive = false;
    private int countdownValue = 3;
    private int countdownTicks = 0;
    private final int COUNTDOWN_FRAMES = 60;
    private boolean lastServeLeft = true;

    // ====== AI ======
    private boolean aiEnabled = true;
    private int aiLevel = 2; // 1: 쉬움, 2: 보통, 3: 어려움
    private boolean aiSpike = false;

    // ====== 애니메이션 프레임 ======
    private int frame = 0;

    // ====== 충돌 이펙트 ======
    private final List<HitEffect> effects = new ArrayList<>();

    public SimpleVolleyGame() {
        setPreferredSize(new Dimension(W, H));
        setBackground(new Color(40, 120, 200));
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (mode == Mode.MENU) return;
                keys.add(e.getKeyCode());

                if (e.getKeyCode() == KeyEvent.VK_P) paused = !paused;
                if (e.getKeyCode() == KeyEvent.VK_R) resetGame();
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (!started && !gameOver) {
                        started = true;
                        startCountdown(lastServeLeft);
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_T) aiEnabled = !aiEnabled;

                if (e.getKeyCode() == KeyEvent.VK_1) { aiLevel = 1; aiEnabled = true; }
                if (e.getKeyCode() == KeyEvent.VK_2) { aiLevel = 2; aiEnabled = true; }
                if (e.getKeyCode() == KeyEvent.VK_3) { aiLevel = 3; aiEnabled = true; }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (mode == Mode.MENU) return;
                keys.remove(e.getKeyCode());
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (mode != Mode.MENU) return;
                handleMenuClick(e.getX(), e.getY());
            }
        });

        timer.start();
    }

    private void handleMenuClick(int mx, int my) {
        Rectangle singleBtn = new Rectangle(W / 2 - 140, H / 2 - 90, 280, 50);
        Rectangle hostBtn   = new Rectangle(W / 2 - 140, H / 2 - 20, 280, 50);
        Rectangle joinBtn   = new Rectangle(W / 2 - 140, H / 2 + 50, 280, 50);

        if (singleBtn.contains(mx, my)) {
            mode = Mode.SINGLE;
            aiEnabled = true;
            started = false;
            resetGame();
        } else if (hostBtn.contains(mx, my)) {
            mode = Mode.HOST;
            aiEnabled = false;
            startHost();
        } else if (joinBtn.contains(mx, my)) {
            mode = Mode.JOIN;
            aiEnabled = false;
            startJoin();
        }
    }

    private void startHost() {
        resetGame();
        isNetworkHost = true;
        isNetworkClient = false;

        server = new VolleyServer(5050, this::applyServerTick, this::snapshotState);
        new Thread(server, "VolleyServer").start();

        client = new VolleyClient("localhost", 5050, this::applyClientState);
        client.connect();
        isNetworkClient = true;
    }

    private void startJoin() {
        resetGame();
        isNetworkHost = false;
        isNetworkClient = true;

        String ip = JOptionPane.showInputDialog(this, "호스트 IP 입력", "localhost");
        if (ip == null || ip.isBlank()) {
            mode = Mode.MENU;
            return;
        }
        client = new VolleyClient(ip.trim(), 5050, this::applyClientState);
        if (!client.connect()) {
            JOptionPane.showMessageDialog(this, "서버에 연결 실패");
            mode = Mode.MENU;
        }
    }

    private void resetRound(boolean leftServe) {
        lastServeLeft = leftServe;
        bx = W / 2.0;
        by = 200;
        bvx = leftServe ? 3.2 : -3.2;
        bvy = -2;

        p1x = 200; p1y = GROUND; p1vx = 0; p1vy = 0;
        p2x = 700; p2y = GROUND; p2vx = 0; p2vy = 0;
    }

    private void startCountdown(boolean leftServe) {
        resetRound(leftServe);
        countdownActive = true;
        countdownValue = 3;
        countdownTicks = COUNTDOWN_FRAMES;
    }

    private void resetGame() {
        scoreL = 0;
        scoreR = 0;
        gameOver = false;
        paused = false;
        started = false;
        gameOverMsg = "";
        effects.clear();
        lastServeLeft = true;
        resetRound(true);
        countdownActive = false;
        roundEndActive = false;
        fadeAlpha = 0f;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame++;

        if (mode == Mode.MENU) {
            updateEffects();
            repaint();
            return;
        }

        if (isNetworkClient) {
            collectLocalInput();
            if (client != null) client.sendInput(localInput);

            updateEffects();
            repaint();
            return;
        }

        if (paused) {
            updateEffects();
            repaint();
            return;
        }

        if (gameOver) {
            updateEffects();
            repaint();
            return;
        }

        if (!started) {
            updateEffects();
            repaint();
            return;
        }

        if (roundEndActive) {
            handleRoundEnd();
            updateEffects();
            repaint();
            return;
        }

        if (countdownActive) {
            countdownTicks--;
            if (countdownTicks <= 0) {
                countdownValue--;
                if (countdownValue <= 0) {
                    countdownActive = false;
                } else {
                    countdownTicks = COUNTDOWN_FRAMES;
                }
            }
            updateEffects();
            repaint();
            return;
        }

        handleInput();
        updatePlayer();
        updateBall();
        updateEffects();

        repaint();
    }

    private void collectLocalInput() {
        localInput.left = keys.contains(KeyEvent.VK_A) || keys.contains(KeyEvent.VK_LEFT);
        localInput.right = keys.contains(KeyEvent.VK_D) || keys.contains(KeyEvent.VK_RIGHT);
        localInput.jump = keys.contains(KeyEvent.VK_W) || keys.contains(KeyEvent.VK_UP);
        localInput.spike = keys.contains(KeyEvent.VK_SPACE);
    }

    private void applyServerTick(InputState p1, InputState p2) {
        if (paused || gameOver || !started) return;

        p1vx = 0;
        if (p1.left) p1vx = -MOVE_SPEED;
        if (p1.right) p1vx = MOVE_SPEED;
        if (p1.jump && p1y >= GROUND) p1vy = JUMP_SPEED;

        p2vx = 0;
        if (p2.left) p2vx = -MOVE_SPEED;
        if (p2.right) p2vx = MOVE_SPEED;
        if (p2.jump && p2y >= GROUND) p2vy = JUMP_SPEED;

        updatePlayer();
        updateBall();
        updateEffects();
    }

    private void applyClientState(GameSnapshot s) {
        netP1x = s.p1x; netP1y = s.p1y;
        netP2x = s.p2x; netP2y = s.p2y;
        netBx = s.bx; netBy = s.by;
        netScoreL = s.scoreL; netScoreR = s.scoreR;
        netStarted = s.started;
        netPaused = s.paused;
        netGameOver = s.gameOver;
        netGameOverMsg = s.gameOverMsg;
        netCountdownActive = s.countdownActive;
        netCountdownValue = s.countdownValue;
        netRoundEndActive = s.roundEndActive;
        netRoundWinnerMsg = s.roundWinnerMsg;
        netFadeAlpha = s.fadeAlpha;
    }

    private GameSnapshot snapshotState() {
        GameSnapshot s = new GameSnapshot();
        s.p1x = p1x; s.p1y = p1y;
        s.p2x = p2x; s.p2y = p2y;
        s.bx = bx; s.by = by;
        s.scoreL = scoreL; s.scoreR = scoreR;
        s.started = started;
        s.paused = paused;
        s.gameOver = gameOver;
        s.gameOverMsg = gameOverMsg;
        s.countdownActive = countdownActive;
        s.countdownValue = countdownValue;
        s.roundEndActive = roundEndActive;
        s.roundWinnerMsg = roundWinnerMsg;
        s.fadeAlpha = fadeAlpha;
        return s;
    }

    private void handleRoundEnd() {
        roundEndTicks++;
        if (fadingOut) {
            fadeAlpha = Math.min(0.7f, fadeAlpha + 0.03f);
            if (fadeAlpha >= 0.7f) {
                fadingOut = false;
                fadingIn = true;
            }
        } else if (fadingIn) {
            fadeAlpha = Math.max(0f, fadeAlpha - 0.02f);
            if (fadeAlpha <= 0f) {
                fadingIn = false;
            }
        }

        if (roundEndTicks >= ROUND_END_TOTAL) {
            roundEndActive = false;
            startCountdown(lastServeLeft);
        }
    }

    private void handleInput() {
        // Player 1 (A,D,W,Space)
        p1vx = 0;
        if (keys.contains(KeyEvent.VK_A)) p1vx = -MOVE_SPEED;
        if (keys.contains(KeyEvent.VK_D)) p1vx = MOVE_SPEED;
        if (keys.contains(KeyEvent.VK_W) && p1y >= GROUND) p1vy = JUMP_SPEED;

        // Player 2 (←, →, ↑) 또는 AI
        p2vx = 0;
        if (aiEnabled) {
            aiControl();
        } else {
            if (keys.contains(KeyEvent.VK_LEFT)) p2vx = -MOVE_SPEED;
            if (keys.contains(KeyEvent.VK_RIGHT)) p2vx = MOVE_SPEED;
            if (keys.contains(KeyEvent.VK_UP) && p2y >= GROUND) p2vy = JUMP_SPEED;
        }
    }

    private void aiControl() {
        if (aiLevel != 1) {
            // 기존 2, 3단계 로직 유지 (비교용)
            double predict = (aiLevel == 2) ? 18.0 : 30.0;
            double speedMul = (aiLevel == 2) ? 2.15 : 1.4;
            double targetX = (bx > NET_X) ? bx + bvx * predict : NET_X + 80;
            targetX = Math.max(targetX, NET_X + 70);
            if (p2x < targetX - 2) p2vx = MOVE_SPEED * speedMul;
            if (p2x > targetX + 2) p2vx = -MOVE_SPEED * speedMul;
            boolean close = Math.abs(bx - p2x) < 80;
            if (bx > NET_X + 10 && by < GROUND - 60 && close && p2y >= GROUND) {
                p2vy = JUMP_SPEED;
            }
            return;
        }

        // ==========================================
        // 🔥 GEMINI GOD-MODE (aiLevel == 1) 🔥
        // ==========================================

        // 1. 초정밀 물리 예측 (Trajectory Prediction)
        double virtualBy = by;
        double virtualBvy = bvy;
        double virtualBx = bx;
        double virtualBvx = bvx;
        double timeToHit = 0;

        // 공이 AI 타격 범위(GROUND - 100)까지 내려올 때까지의 시간과 위치 계산
        for (int i = 0; i < 100; i++) {
            virtualBvy += BALL_GRAVITY;
            virtualBy += virtualBvy;
            virtualBx += virtualBvx;
            if (virtualBy >= GROUND - 100 || virtualBx < NET_X || virtualBx > W) break;
            timeToHit++;
        }

        // 2. 타겟 포지셔닝 (공격 각도 계산)
        // 공의 뒤쪽 15픽셀 지점을 잡아서 네트 방향으로 강하게 밀어버림
        double targetX = virtualBx + 15;

        // 수비 시에는 네트 근처에서 대기, 공격 시에는 예측 지점으로 돌진
        if (bx < NET_X) {
            targetX = NET_X + 120; // 센터 포지셔닝
        }

        targetX = clamp(targetX, NET_X + 40, W - 40);

        // 3. 초고속 이동 (관성 무시 수준의 4.0배 속도)
        double speedMode = 4.0;
        double distance = targetX - p2x;

        if (Math.abs(distance) > 1.0) {
            p2vx = (distance > 0 ? MOVE_SPEED : -MOVE_SPEED) * speedMode;
        } else {
            p2vx = 0;
            p2x = targetX; // 미세 위치 보정
        }

        // 4. 필살 스파이크 로직
        boolean ballInZone = bx > NET_X + 20;
        boolean isBallDropping = bvy > 0;

        // 플레이어와의 거리에 따른 점프 타이밍 조절
        double distToBall = Math.abs(bx - p2x);

        // 공이 머리 위에 오기 직전, 최적의 타점에서 점프
        if (ballInZone && distToBall < 60 && by < GROUND - 120 && p2y >= GROUND) {
            p2vy = JUMP_SPEED * 1.2; // 더 높은 타점 점유
        }

        // 공중 제어: 공이 사거리 안에 들어오면 즉시 스파이크 활성화
        aiSpike = (distToBall < 85 && by < p2y && p2y < GROUND - 10);
    }

    private void updatePlayer() {
        // P1
        p1x += p1vx;
        p1vy += GRAVITY;
        p1y += p1vy;
        if (p1y > GROUND) { p1y = GROUND; p1vy = 0; }

        // P2
        p2x += p2vx;
        p2vy += GRAVITY;
        p2y += p2vy;
        if (p2y > GROUND) { p2y = GROUND; p2vy = 0; }

        // 벽 제한
        p1x = clamp(p1x, P_W / 2.0, NET_X - NET_W / 2.0 - P_W / 2.0);
        p2x = clamp(p2x, NET_X + NET_W / 2.0 + P_W / 2.0, W - P_W / 2.0);
    }

    private void updateBall() {
        bvy += BALL_GRAVITY;
        bx += bvx;
        by += bvy;

        // 벽 반사
        if (bx < BALL_RADIUS) { bx = BALL_RADIUS; bvx *= -1; addEffect(bx, by); }
        if (bx > W - BALL_RADIUS) { bx = W - BALL_RADIUS; bvx *= -1; addEffect(bx, by); }
        if (by < BALL_RADIUS) { by = BALL_RADIUS; bvy *= -1; addEffect(bx, by); }

        // 바닥 (득점)
        if (by >= GROUND - BALL_RADIUS) {
            by = GROUND - BALL_RADIUS;
            bvy = 0;
            checkScore();
            return;
        }

        // 네트 충돌
        Rectangle net = new Rectangle(NET_X - NET_W / 2, GROUND - NET_H, NET_W, NET_H);
        if (circleIntersectsRect(bx, by, BALL_RADIUS, net)) {
            if (bx < NET_X) bx = NET_X - NET_W / 2.0 - BALL_RADIUS;
            else bx = NET_X + NET_W / 2.0 + BALL_RADIUS;
            bvx *= -1;
            addEffect(bx, by);
        }

        // 플레이어 충돌
        collidePlayer(p1x, p1y, true);
        collidePlayer(p2x, p2y, false);
    }

    private void collidePlayer(double px, double py, boolean left) {
        double rectLeft = px - P_W / 2.0;
        double rectTop = py - P_H;
        double rectRight = rectLeft + P_W;
        double rectBottom = rectTop + P_H;

        double closestX = clamp(bx, rectLeft, rectRight);
        double closestY = clamp(by, rectTop, rectBottom);

        double dx = bx - closestX;
        double dy = by - closestY;
        double dist2 = dx * dx + dy * dy;

        boolean spikeKey = left
                ? keys.contains(KeyEvent.VK_SPACE)
                : (aiEnabled ? aiSpike : keys.contains(KeyEvent.VK_SPACE));
        boolean inAir = (left ? p1y : p2y) < GROUND;

        double hitRadius = BALL_RADIUS;
        if (spikeKey && inAir) hitRadius += SPIKE_RANGE;

        if (dist2 < hitRadius * hitRadius) {
            double dist = Math.max(1e-6, Math.sqrt(dist2));
            double nx = dx / dist;
            double ny = dy / dist;

            double power = spikeKey && inAir ? 10.5 : 7.5;
            bvx = nx * power + (left ? 1.0 : -1.0);
            bvy = ny * power - (spikeKey && inAir ? 4.5 : 2.5);

            if (spikeKey && inAir) {
                bvx += (left ? 4.6 : -4.6);
                addSpikeEffect(bx, by);
            } else {
                addEffect(bx, by);
            }

            bx = closestX + nx * BALL_RADIUS;
            by = closestY + ny * BALL_RADIUS;
        }
    }

    private void checkScore() {
        if (gameOver) return;

        if (bx < NET_X) {
            scoreR++;
            roundWinnerMsg = "RIGHT WINS THIS ROUND!";
        } else {
            scoreL++;
            roundWinnerMsg = "LEFT WINS THIS ROUND!";
        }

        Toolkit.getDefaultToolkit().beep();

        if (scoreL >= winScore) {
            gameOverMsg = "LEFT WINS THE MATCH!";
            gameOver = true;
            return;
        }
        if (scoreR >= winScore) {
            gameOverMsg = "RIGHT WINS THE MATCH!";
            gameOver = true;
            return;
        }

        // 라운드 연출 시작
        roundEndActive = true;
        roundEndTicks = 0;
        fadingOut = true;
        fadingIn = false;
        fadeAlpha = 0f;
    }

    // ====== 이펙트 ======
    private void addEffect(double x, double y) {
        effects.add(new HitEffect(x, y, 8, 12, new Color(255, 255, 255, 180)));
    }

    private void addSpikeEffect(double x, double y) {
        effects.add(new HitEffect(x, y, 14, 18, new Color(255, 200, 60, 200)));
    }

    private void updateEffects() {
        for (int i = effects.size() - 1; i >= 0; i--) {
            HitEffect ef = effects.get(i);
            ef.life--;
            ef.radius += 1.6;
            if (ef.life <= 0) effects.remove(i);
        }
    }

    private void drawEffects(Graphics2D g2) {
        for (HitEffect ef : effects) {
            int alpha = Math.max(0, ef.life * 14);
            g2.setColor(new Color(ef.color.getRed(), ef.color.getGreen(), ef.color.getBlue(),
                    Math.min(220, alpha)));
            int r = (int) ef.radius;
            g2.setStroke(new BasicStroke(ef.stroke));
            g2.drawOval((int) (ef.x - r), (int) (ef.y - r), r * 2, r * 2);
        }
    }

    // ====== 그리기 ======
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (mode == Mode.MENU) {
            drawMenu(g);
            return;
        }

        if (isNetworkClient) {
            drawBackground(g);

            g.setColor(Color.WHITE);
            g.fillRect(NET_X - NET_W / 2, GROUND - NET_H, NET_W, NET_H);

            g.setColor(Color.WHITE);
            g.fillOval((int)(netBx - BALL_RADIUS), (int)(netBy - BALL_RADIUS),
                    BALL_RADIUS * 2, BALL_RADIUS * 2);

            drawPlayer(g, netP1x, netP1y, new Color(255, 220, 80), true);
            drawPlayer(g, netP2x, netP2y, new Color(255, 140, 100), false);

            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.setColor(Color.WHITE);
            g.drawString("P1: " + netScoreL, 30, 30);
            g.drawString("P2: " + netScoreR, W - 120, 30);

            if (netRoundEndActive) {
                drawRoundEndOverlay(g, netRoundWinnerMsg, netFadeAlpha);
            }
            if (netCountdownActive) {
                drawCountdown(g, netCountdownValue);
            }
            if (netPaused) {
                g.setFont(new Font("Arial", Font.BOLD, 36));
                g.drawString("PAUSED", W / 2 - 80, H / 2);
            }
            if (netGameOver) {
                g.setFont(new Font("Arial", Font.BOLD, 32));
                g.drawString("GAME OVER", W / 2 - 110, H / 2 - 20);
                g.setFont(new Font("Arial", Font.PLAIN, 18));
                g.drawString(netGameOverMsg, W / 2 - 130, H / 2 + 10);
            }
            return;
        }

        drawBackground(g);

        // 네트
        g.setColor(Color.WHITE);
        g.fillRect(NET_X - NET_W / 2, GROUND - NET_H, NET_W, NET_H);

        // 공
        g.setColor(Color.WHITE);
        g.fillOval((int)(bx - BALL_RADIUS), (int)(by - BALL_RADIUS),
                BALL_RADIUS * 2, BALL_RADIUS * 2);

        // 플레이어(사각형)
        drawPlayer(g, p1x, p1y, new Color(255, 220, 80), true);
        drawPlayer(g, p2x, p2y, new Color(255, 140, 100), false);

        // 충돌/스파이크 이펙트
        drawEffects((Graphics2D) g);

        // 점수
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.WHITE);
        g.drawString("P1: " + scoreL, 30, 30);
        g.drawString("P2: " + scoreR, W - 120, 30);
        g.drawString("WIN: " + winScore, W / 2 - 35, 30);

        // 안내
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.drawString("P1: A/D 이동, W 점프, Space 스파이크", 30, 55);
        g.drawString("P2: ←/→ 이동, ↑ 점프, Space 스파이크", W - 300, 55);
        g.drawString("P: 일시정지 | R: 재시작 | T: AI 토글 | 1~3: AI 레벨", W / 2 - 200, 75);

        if (!started) {
            g.setFont(new Font("Arial", Font.BOLD, 28));
            g.drawString("ENTER 키로 시작", W / 2 - 120, H / 2);
        }

        if (roundEndActive) {
            drawRoundEndOverlay(g, roundWinnerMsg, fadeAlpha);
        }

        if (countdownActive) {
            drawCountdown(g, countdownValue);
        }

        if (paused) {
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("PAUSED", W / 2 - 80, H / 2);
        }

        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 32));
            g.drawString("GAME OVER", W / 2 - 110, H / 2 - 20);
            g.setFont(new Font("Arial", Font.PLAIN, 18));
            g.drawString(gameOverMsg, W / 2 - 130, H / 2 + 10);
            g.drawString("R 키로 재시작", W / 2 - 70, H / 2 + 32);
        }
    }

    private void drawMenu(Graphics g) {
        drawBackground(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setFont(new Font("Arial", Font.BOLD, 36));
        g2.setColor(Color.WHITE);
        g2.drawString("SIMPLE VOLLEY", W / 2 - 150, H / 2 - 140);

        drawMenuButton(g2, "싱글 모드", W / 2 - 140, H / 2 - 90, 280, 50);
        drawMenuButton(g2, "멀티: 호스트", W / 2 - 140, H / 2 - 20, 280, 50);
        drawMenuButton(g2, "멀티: 참가", W / 2 - 140, H / 2 + 50, 280, 50);

        g2.setFont(new Font("Arial", Font.PLAIN, 14));
        g2.drawString("로컬 P2P / 서버 권한 방식", W / 2 - 110, H / 2 + 120);
    }

    private void drawMenuButton(Graphics2D g2, String text, int x, int y, int w, int h) {
        g2.setColor(new Color(20, 40, 60, 180));
        g2.fillRoundRect(x, y, w, h, 18, 18);
        g2.setColor(Color.WHITE);
        g2.drawRoundRect(x, y, w, h, 18, 18);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics fm = g2.getFontMetrics();
        int tx = x + (w - fm.stringWidth(text)) / 2;
        int ty = y + (h + fm.getAscent()) / 2 - 4;
        g2.drawString(text, tx, ty);
    }

    private void drawRoundEndOverlay(Graphics g, String msg, float alpha) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(new Color(0, 0, 0, (int) (alpha * 255)));
        g2.fillRect(0, 0, W, H);

        g2.setFont(new Font("Arial", Font.BOLD, 28));
        g2.setColor(Color.WHITE);
        int textW = g2.getFontMetrics().stringWidth(msg);
        g2.drawString(msg, W / 2 - textW / 2, H / 2 - 40);
    }

    private void drawCountdown(Graphics g, int value) {
        String text = String.valueOf(value);
        g.setFont(new Font("Arial", Font.BOLD, 64));

        FontMetrics fm = g.getFontMetrics();
        int textW = fm.stringWidth(text);

        int x = W / 2 - textW / 2;
        int y = H / 2 - 20;

        g.setColor(Color.BLACK);
        g.drawString(text, x - 2, y);
        g.drawString(text, x + 2, y);
        g.drawString(text, x, y - 2);
        g.drawString(text, x, y + 2);

        g.setColor(Color.WHITE);
        g.drawString(text, x, y);
    }

    private void drawBackground(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        GradientPaint sky = new GradientPaint(0, 0, new Color(60, 150, 240),
                0, GROUND, new Color(140, 210, 255));
        g2.setPaint(sky);
        g2.fillRect(0, 0, W, GROUND);

        g2.setColor(new Color(255, 240, 120));
        g2.fillOval(70, 50, 60, 60);

        g2.setColor(new Color(255, 255, 255, 220));
        drawCloud(g2, 200, 80);
        drawCloud(g2, 520, 60);
        drawCloud(g2, 720, 110);

        g2.setColor(new Color(80, 140, 120));
        Polygon m1 = new Polygon(new int[]{0, 160, 320}, new int[]{GROUND, 220, GROUND}, 3);
        Polygon m2 = new Polygon(new int[]{220, 420, 620}, new int[]{GROUND, 200, GROUND}, 3);
        g2.fillPolygon(m1);
        g2.fillPolygon(m2);

        g2.setColor(new Color(70, 180, 90));
        g2.fillRect(0, GROUND, W, H - GROUND);
    }

    private void drawCloud(Graphics2D g2, int x, int y) {
        g2.fillOval(x, y, 60, 30);
        g2.fillOval(x + 20, y - 10, 60, 40);
        g2.fillOval(x + 40, y, 60, 30);
    }

    private void drawPlayer(Graphics g, double x, double y, Color body, boolean left) {
        int drawX = (int) (x - P_W / 2.0);
        int drawY = (int) (y - P_H);

        g.setColor(body);
        g.fillRect(drawX, drawY, P_W, P_H);

        boolean blink = (frame % 60) < 3;
        g.setColor(Color.BLACK);
        int eyeY = drawY + P_H / 3;
        int eyeX1 = drawX + P_W / 3;
        int eyeX2 = drawX + (P_W * 2 / 3) - 6;

        if (blink) {
            g.drawLine(eyeX1, eyeY, eyeX1 + 6, eyeY);
            g.drawLine(eyeX2, eyeY, eyeX2 + 6, eyeY);
        } else {
            g.fillRect(eyeX1, eyeY, 6, 6);
            g.fillRect(eyeX2, eyeY, 6, 6);
        }

        g.setColor(new Color(255, 120, 120));
        g.fillRect(drawX + (left ? 6 : P_W - 14), drawY + P_H / 2, 8, 8);
    }

    private double clamp(double v, double min, double max) {
        return Math.max(min, Math.min(max, v));
    }

    private boolean circleIntersectsRect(double cx, double cy, double r, Rectangle rect) {
        double closestX = clamp(cx, rect.x, rect.x + rect.width);
        double closestY = clamp(cy, rect.y, rect.y + rect.height);
        double dx = cx - closestX;
        double dy = cy - closestY;
        return dx * dx + dy * dy <= r * r;
    }

    private static class HitEffect {
        double x, y;
        double radius;
        int life;
        int stroke;
        Color color;

        HitEffect(double x, double y, int startRadius, int life, Color color) {
            this.x = x;
            this.y = y;
            this.radius = startRadius;
            this.life = life;
            this.color = color;
            this.stroke = 2;
        }
    }

    // ====== 도우미 구조체 ======
    public static class InputState {
        boolean left, right, jump, spike;
    }

    public static class GameSnapshot {
        double p1x, p1y, p2x, p2y, bx, by;
        int scoreL, scoreR;
        boolean started, paused, gameOver, countdownActive, roundEndActive;
        int countdownValue;
        String gameOverMsg, roundWinnerMsg;
        float fadeAlpha;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("2인 배구 게임");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setContentPane(new SimpleVolleyGame());
            f.pack();
            f.setResizable(false);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}