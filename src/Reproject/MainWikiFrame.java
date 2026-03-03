package Reproject;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainWikiFrame extends JFrame {
    private SearchService searchService;
    private ConceptRepository repository;

    // 필드 선언: 다른 메서드에서도 접근할 수 있도록 클래스 레벨에 선언합니다.
    private JScrollPane scrollPane;
    private JList<Concept> resultList;
    private DefaultListModel<Concept> listModel;
    private JTextField searchField;
    private String currentCategory;

    public MainWikiFrame(SearchService searchService, ConceptRepository repository) {
        this.searchService = searchService;
        this.repository = repository;

        setTitle("Java Wiki -Java UI");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initTopPanel();    // 검색바 영역
        initCenterPanel(); // 리스트 및 상세 내용 영역
        initStatusBar();   // 하단 상태바 영역

        // 초기 데이터 로딩
        updateList(repository.findAll());

        setLocationRelativeTo(null);
    }

    private void initTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        topPanel.setBackground(new Color(236, 240, 241));

        searchField = new JTextField();
        searchField.setFont(new Font("맑은 고딕", Font.PLAIN, 16));

        JButton searchButton = new JButton("검색");
        searchButton.addActionListener(e -> performSearch());
        searchField.addActionListener(e -> performSearch());

        topPanel.add(new JLabel("지식 검색: "), BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
    }

    private void initCenterPanel() {
        // 1. 왼쪽 영역을 감쌀 패널 (상단 필터 버튼 + 리스트)
        JPanel leftPanel = new JPanel(new BorderLayout());

        // 2. 필터 버튼 패널 (기본, 중급, 고급)
        JPanel filterPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        filterPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        String[] categories = {"전체", "기초", "중급", "고급"};
        for (String cat : categories) {
            JButton btn = new JButton(cat);
            btn.setFont(new Font("맑은 고딕", Font.BOLD, 12));
            btn.addActionListener(e -> {
                currentCategory = cat;
                filterList(cat); // 필터링 로직 실행
            });
            filterPanel.add(btn);
        }
        leftPanel.add(filterPanel, BorderLayout.NORTH);

        // 3. 리스트 설정
        listModel = new DefaultListModel<>();
        resultList = new JList<>(listModel);
        resultList.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        resultList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) displayDetail(resultList.getSelectedValue());
        });

        JScrollPane leftScroll = new JScrollPane(resultList);
        leftScroll.setBorder(BorderFactory.createTitledBorder("지식 인덱스"));
        leftPanel.add(leftScroll, BorderLayout.CENTER);

        // 4. 오른쪽 상세 영역 (기존과 동일)
        JPanel welcomePanel = new JPanel(new GridBagLayout());
        welcomePanel.setBackground(Color.WHITE);
        welcomePanel.add(new JLabel("카테고리를 선택하거나 검색해 주세요."));
        scrollPane = new JScrollPane(welcomePanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("상세 지식"));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, scrollPane);
        splitPane.setDividerLocation(300);
        add(splitPane, BorderLayout.CENTER);
    }

    private void initStatusBar() {
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        JLabel statusLabel = new JLabel("자바 학습 도우미가 준비되었습니다.");
        statusPanel.add(statusLabel);
        add(statusPanel, BorderLayout.SOUTH);
    }

    // [핵심] 자바 컴포넌트 동적 조립 로직
    private void displayDetail(Concept selected) {
        if (selected == null) return;

        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setBackground(Color.WHITE);
        // 전체 여백을 상단은 줄이고 좌우는 유지합니다.
        detailPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 30, 40));

        // 1. 제목 (최상단)
        JLabel titleLabel = new JLabel(selected.getTitle());
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 28));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailPanel.add(titleLabel);

        // 제목과 분류 사이 간격 (최소화)
        detailPanel.add(Box.createVerticalStrut(2));

        // 2. 카테고리 (분류: 내부동작)
        JLabel catLabel = new JLabel("분류: " + selected.getCategory());
        catLabel.setForeground(new Color(52, 152, 219));
        catLabel.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        catLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailPanel.add(catLabel);

        // 분류와 구분선 사이 간격 (바짝 붙임)
        detailPanel.add(Box.createVerticalStrut(8));

        // 3. 구분선 (Separator)
        JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1)); // 선 두께 최소화
        sep.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailPanel.add(sep);

        // [중요] 구분선 바로 아래에 본문이 오도록 간격을 아주 작게 설정 (5px)
        detailPanel.add(Box.createVerticalStrut(10));

        // 4. 본문 조립
        for (String line : selected.getDescriptionLines()) {
            JLabel label = new JLabel();
            label.setAlignmentX(Component.LEFT_ALIGNMENT);

            if (line.startsWith("[H2]")) {
                label.setText(line.replace("[H2] ", ""));
                label.setFont(new Font("맑은 고딕", Font.BOLD, 19));
                label.setForeground(new Color(44, 62, 80));
                // 섹션 제목 위아래 간격 조절
                detailPanel.add(Box.createVerticalStrut(10));
            } else if (line.startsWith("•")) {
                label.setText(line);
                label.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
                label.setBorder(BorderFactory.createEmptyBorder(0, 15, 3, 0));
            } else if (line.startsWith("[TIP]")) {
                label.setText("💡 " + line.replace("[TIP] ", ""));
                label.setFont(new Font("맑은 고딕", Font.ITALIC, 14));
                label.setForeground(new Color(127, 140, 141));
            } else {
                label.setText(line);
                label.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
            }

            detailPanel.add(label);
            // 줄 사이 간격 (기존 12 -> 7로 축소)
            detailPanel.add(Box.createVerticalStrut(7));
        }

        // 하단 빈 공간 채우기 (내용을 위로 밀어올림)
        detailPanel.add(Box.createVerticalGlue());

        scrollPane.setViewportView(detailPanel);

        // 클릭 시 항상 최상단 노출
        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(0));

        scrollPane.revalidate();
        scrollPane.repaint();
    }

    private void filterList(String category) {
        List<Concept> all = repository.findAll();
        listModel.clear();

        for (Concept c : all) {
            if (category.equals("전체") || c.getCategory().equals(category)) {
                listModel.addElement(c);
            }
        }
        // 필터링 후 첫 번째 항목 자동 선택 (선택 사항)
        if (!listModel.isEmpty()) resultList.setSelectedIndex(0);
    }

    private void performSearch() {
        String query = searchField.getText();
        List<Concept> results = searchService.search(query);
        updateList(results);

        if (results.isEmpty()) {
            Concept best = searchService.getBestMatch(query);
            if (best != null) {
                JOptionPane.showMessageDialog(this,
                        "찾으시는 결과가 없나요? 혹시 '" + best.getTitle() + "'를 찾으시나요?");
            }
        }
    }

    private void updateList(List<Concept> concepts) {
        listModel.clear();
        for (Concept c : concepts) {
            listModel.addElement(c);
        }
    }
}