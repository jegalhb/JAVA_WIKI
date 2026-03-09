package Reproject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * 지식 추가/수정 입력 창.
 */
public class ConceptEditFrame extends JFrame {
    private final MainWikiFrame mainFrame;
    private final ConceptRepository repository;
    private final Concept editingConcept;

    private JTextField idField;
    private JTextField titleField;
    private JComboBox<String> categoryCombo;
    private JTextArea contentArea;

    public ConceptEditFrame(MainWikiFrame mainFrame, ConceptRepository repository) {
        // 메인 창에서 선택 항목 없이 열리면 기본적으로 "추가" 모드로 진입한다.
        this(mainFrame, repository, null);
    }

    public ConceptEditFrame(MainWikiFrame mainFrame, ConceptRepository repository, Concept editingConcept) {
        // 메인 창(리스트 갱신)과 저장소(데이터 반영)에 접근하기 위한 참조를 전달받는다.
        this.mainFrame = mainFrame;
        this.repository = repository;
        this.editingConcept = editingConcept;

        setTitle(editingConcept == null ? "자바 지식 추가" : "자바 지식 수정");
        setSize(500, 600);
        setLayout(new BorderLayout(10, 10));

        // 입력 폼 영역: ID, 제목, 카테고리
        JPanel formPanel = new JPanel(new GridLayout(6, 1, 5, 5));
        formPanel.setBorder(new EmptyBorder(20, 20, 10, 20));

        idField = new JTextField();
        titleField = new JTextField();
        String[] cats = {"기초", "중급", "고급", "메소드"};
        categoryCombo = new JComboBox<>(cats);

        formPanel.add(new JLabel("ID (예: B51, M101):"));
        formPanel.add(idField);
        formPanel.add(new JLabel("제목:"));
        formPanel.add(titleField);
        formPanel.add(new JLabel("카테고리:"));
        formPanel.add(categoryCombo);

        add(formPanel, BorderLayout.NORTH);

        // 본문 입력 영역: 설명/코드 라인을 여러 줄로 입력
        contentArea = new JTextArea();
        contentArea.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(contentArea);
        scroll.setBorder(BorderFactory.createTitledBorder("상세 내용 (여러 줄 입력)"));
        add(scroll, BorderLayout.CENTER);

        JButton saveBtn = new JButton(editingConcept == null ? "데이터 저장하기" : "수정 저장하기");
        saveBtn.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        saveBtn.addActionListener(e -> saveAction());
        add(saveBtn, BorderLayout.SOUTH);

        // 수정 모드일 때는 기존 값을 미리 채워서 "추가"가 아닌 "수정" 흐름으로 유도한다.
        // ID는 Repository(Map)의 key 역할이므로 수정 중 변경하지 않도록 잠근다.
        if (editingConcept != null) {
            idField.setText(editingConcept.getId());
            idField.setEditable(false);
            titleField.setText(editingConcept.getTitle());
            categoryCombo.setSelectedItem(editingConcept.getCategory());
            contentArea.setText(String.join("\n", editingConcept.getDescriptionLines()));
        }

        setLocationRelativeTo(mainFrame);
        setVisible(true);
    }

    private void saveAction() {
        // 1) 입력값 수집
        String id = idField.getText().trim();
        String title = titleField.getText().trim();
        String category = String.valueOf(categoryCombo.getSelectedItem());
        String content = contentArea.getText();

        if (id.isEmpty() || title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID와 제목은 필수입니다.");
            return;
        }

        // 2) Concept 객체 조립
        Concept newConcept = new Concept(id, title, category);
        for (String line : content.split("\\R")) {
            String trimmed = line.trim();
            if (!trimmed.isEmpty()) {
                newConcept.addLine(trimmed);
            }
        }

        // 3) 단일 반영 경로(mainFrame.onDataAdded):
        //    - 오프라인: 로컬 repository/list 반영
        //    - 온라인 : 위 반영 + client.send("ADD", ...) 서버 전파
        mainFrame.onDataAdded(newConcept);

        JOptionPane.showMessageDialog(this, editingConcept == null ? "성공적으로 추가되었습니다." : "성공적으로 수정되었습니다.");
        dispose();
    }
}
