package poolsandwich;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class E07_LayoutGallery {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(E07_LayoutGallery::createAndShow);
    }

    private static void createAndShow() {
        JFrame frame = new JFrame("Swing Layout 종류별 모음");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 탭으로 레이아웃별 예시를 분리
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("BorderLayout", borderLayoutDemo());
        tabs.addTab("FlowLayout", flowLayoutDemo());
        tabs.addTab("GridLayout", gridLayoutDemo());
        tabs.addTab("BoxLayout", boxLayoutDemo());
        tabs.addTab("CardLayout", cardLayoutDemo());
        tabs.addTab("GridBagLayout", gridBagLayoutDemo());
        tabs.addTab("null layout", nullLayoutDemo());

        frame.add(tabs);

        frame.setSize(900, 650);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // 1) BorderLayout: NORTH/SOUTH/EAST/WEST/CENTER
    private static JPanel borderLayoutDemo() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBorder(new TitledBorder("BorderLayout: 5구역 배치"));

        p.add(make("NORTH"), BorderLayout.NORTH);
        p.add(make("SOUTH"), BorderLayout.SOUTH);
        p.add(make("WEST"), BorderLayout.WEST);
        p.add(make("EAST"), BorderLayout.EAST);
        p.add(make("CENTER(남는 공간)"), BorderLayout.CENTER);

        return wrap(p, "특징: CENTER가 남는 공간을 크게 차지한다.");
    }

    // 2) FlowLayout: 왼쪽부터 흐르듯 배치, 부족하면 줄바꿈
    private static JPanel flowLayoutDemo() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        p.setBorder(new TitledBorder("FlowLayout: 흐르듯 배치"));

        for (int i = 1; i <= 20; i++) {
            p.add(new JButton("B" + i));
        }

        return wrap(p, "특징: 공간이 부족하면 자동으로 다음 줄로 넘어간다.");
    }

    // 3) GridLayout: 행/열 격자, 모든 칸 동일 크기
    private static JPanel gridLayoutDemo() {
        JPanel p = new JPanel(new GridLayout(3, 4, 8, 8));
        p.setBorder(new TitledBorder("GridLayout: 격자 배치(동일 크기)"));

        for (int i = 1; i <= 12; i++) {
            p.add(make("Cell " + i));
        }

        return wrap(p, "특징: 모든 컴포넌트가 같은 크기로 맞춰진다.");
    }

    // 4) BoxLayout: 한 방향 정렬(X_AXIS / Y_AXIS)
    private static JPanel boxLayoutDemo() {
        JPanel outer = new JPanel(new GridLayout(1, 2, 10, 10));
        outer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel vertical = new JPanel();
        vertical.setBorder(new TitledBorder("BoxLayout Y_AXIS (세로)"));
        vertical.setLayout(new BoxLayout(vertical, BoxLayout.Y_AXIS));
        vertical.add(new JButton("버튼 1"));
        vertical.add(Box.createVerticalStrut(8));
        vertical.add(new JButton("버튼 2"));
        vertical.add(Box.createVerticalStrut(8));
        vertical.add(new JButton("버튼 3"));

        JPanel horizontal = new JPanel();
        horizontal.setBorder(new TitledBorder("BoxLayout X_AXIS (가로)"));
        horizontal.setLayout(new BoxLayout(horizontal, BoxLayout.X_AXIS));
        horizontal.add(new JButton("버튼 A"));
        horizontal.add(Box.createHorizontalStrut(8));
        horizontal.add(new JButton("버튼 B"));
        horizontal.add(Box.createHorizontalStrut(8));
        horizontal.add(new JButton("버튼 C"));

        outer.add(vertical);
        outer.add(horizontal);

        return wrap(outer, "특징: 가로 또는 세로 한 방향으로 정렬한다.");
    }

    // 5) CardLayout: 화면을 카드처럼 교체 (다중 화면)
    private static JPanel cardLayoutDemo() {
        JPanel root = new JPanel(new BorderLayout(8, 8));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        CardLayout card = new CardLayout();
        JPanel cards = new JPanel(card);
        cards.setBorder(new TitledBorder("CardLayout: 화면 전환"));

        cards.add(make("카드 1"), "C1");
        cards.add(make("카드 2"), "C2");
        cards.add(make("카드 3"), "C3");

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        JButton prev = new JButton("이전");
        JButton next = new JButton("다음");
        buttons.add(prev);
        buttons.add(next);

        prev.addActionListener(e -> card.previous(cards));
        next.addActionListener(e -> card.next(cards));

        root.add(buttons, BorderLayout.NORTH);
        root.add(cards, BorderLayout.CENTER);

        return wrap(root, "특징: 여러 화면을 한 패널에서 전환할 때 유용하다.");
    }

    // 6) GridBagLayout: 가장 유연(복잡하지만 강력)
    private static JPanel gridBagLayoutDemo() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(new TitledBorder("GridBagLayout: 위치/비율/확장 제어"));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        // 0행: 라벨 + 입력
        c.gridx = 0; c.gridy = 0; c.weightx = 0;
        p.add(new JLabel("이름:"), c);

        c.gridx = 1; c.gridy = 0; c.weightx = 1;
        p.add(new JTextField(15), c);

        // 1행: 라벨 + 긴 입력
        c.gridx = 0; c.gridy = 1; c.weightx = 0;
        p.add(new JLabel("주소:"), c);

        c.gridx = 1; c.gridy = 1; c.weightx = 1;
        p.add(new JTextField(25), c);

        // 2행: 큰 영역(메모) - 세로 확장
        c.gridx = 0; c.gridy = 2; c.weightx = 0;
        c.anchor = GridBagConstraints.NORTH;
        p.add(new JLabel("메모:"), c);

        c.gridx = 1; c.gridy = 2;
        c.weightx = 1; c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        p.add(new JScrollPane(new JTextArea(6, 25)), c);

        // 3행: 버튼(우측 정렬 느낌)
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btns.add(new JButton("저장"));
        btns.add(new JButton("취소"));

        c.gridx = 0; c.gridy = 3;
        c.gridwidth = 2;
        c.weightx = 1; c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        p.add(btns, c);

        return wrap(p, "특징: 행/열 개념 + 비율/확장/정렬까지 제어 가능(실무 폼 화면에 자주 사용).");
    }

    // 7) null layout: 좌표 고정 (권장하지 않음)
    private static JPanel nullLayoutDemo() {
        JPanel p = new JPanel(null);
        p.setBorder(new TitledBorder("null layout: 좌표 고정 배치(비권장)"));

        JButton b1 = new JButton("버튼(50, 50)");
        b1.setBounds(50, 50, 140, 30);

        JTextField tf = new JTextField("입력창(50, 100)");
        tf.setBounds(50, 100, 200, 30);

        JTextArea ta = new JTextArea("메모(50, 150)");
        JScrollPane sp = new JScrollPane(ta);
        sp.setBounds(50, 150, 300, 150);

        p.add(b1);
        p.add(tf);
        p.add(sp);

        return wrap(p, "특징: 창 크기 변경/글꼴 변화/DPI 변화에 매우 취약하다.");
    }

    // 보기 좋은 공통 컴포넌트(라벨 박스) 생성
    private static JComponent make(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(new Color(235, 242, 255));
        label.setBorder(BorderFactory.createLineBorder(new Color(140, 170, 220)));
        return label;
    }

    // 설명 라벨을 아래에 붙여주는 래퍼
    private static JPanel wrap(JComponent center, String tip) {
        JPanel root = new JPanel(new BorderLayout(8, 8));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        root.add(center, BorderLayout.CENTER);

        JLabel info = new JLabel("설명: " + tip);
        info.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        root.add(info, BorderLayout.SOUTH);
        return root;
    }
}
