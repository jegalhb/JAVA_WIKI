package com.smu8.game;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class E09TodoApp extends JFrame {

    static class Task {
        final int id;
        final String title;
        boolean done;

        Task(int id, String title) {
            this.id = id;
            this.title = title;
            this.done = false;
        }

        @Override
        public String toString() {
            return (done ? "[완료] " : "[ ] ") + title + " (#" + id + ")";
        }
    }

    private final List<Task> tasks = new ArrayList<>();
    private int nextId = 1;

    private final JTextField inputField = new JTextField(20);
    private final JButton addButton = new JButton("추가");

    private final DefaultListModel<Task> listModel = new DefaultListModel<>();
    private final JList<Task> taskList = new JList<>(listModel);

    private final JButton toggleDoneButton = new JButton("완료/취소");
    private final JButton deleteButton = new JButton("삭제");

    private final JComboBox<String> filterCombo = new JComboBox<>(new String[]{"전체", "미완료", "완료"});
    private final JTextField searchField = new JTextField(12);

    private final JLabel statusLabel = new JLabel("총 0개");

    public E09TodoApp() {
        super("To-Do 목록");
        //기본 할일
        tasks.add(new Task(nextId++, "자바 컬렉션(List/Set/Map) 복습하기"));
        tasks.add(new Task(nextId++, "LinkedList vs ArrayList 차이 정리하기"));
        tasks.add(new Task(nextId++, "Map entrySet() 순회 예제 만들어보기"));
        tasks.add(new Task(nextId++, "Swing으로 To-Do UI 레이아웃 잡기"));
        tasks.add(new Task(nextId++, "완료/삭제 기능 테스트하기"));

        // 상단 입력 영역
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("할 일:"));
        top.add(inputField);
        top.add(addButton);

        // 가운데 목록
        JScrollPane center = new JScrollPane(taskList);

        // 하단 제어 영역
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.add(toggleDoneButton);
        bottom.add(deleteButton);
        bottom.add(new JLabel("필터:"));
        bottom.add(filterCombo);
        bottom.add(new JLabel("검색:"));
        bottom.add(searchField);
        bottom.add(statusLabel);

        setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        // 이벤트 연결
        addButton.addActionListener(e -> addTask());
        inputField.addActionListener(e -> addTask());

        toggleDoneButton.addActionListener(e -> toggleDone());
        deleteButton.addActionListener(e -> deleteTask());

        filterCombo.addActionListener(e -> refreshView());
        searchField.addActionListener(e -> refreshView()); // Enter 시 갱신

        // 더블클릭으로 완료 토글(선택)
        taskList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) toggleDone();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 450);
        setLocationRelativeTo(null);
        refreshView();
    }

    private void addTask() {
        String title = inputField.getText().trim();
        if (title.isEmpty()) return;

        Task t = new Task(nextId++, title);
        tasks.add(t);

        inputField.setText("");
        refreshView();
    }

    private void toggleDone() {
        Task selected = taskList.getSelectedValue();
        if (selected == null) return;

        selected.done = !selected.done;
        refreshView();
    }

    private void deleteTask() {
        Task selected = taskList.getSelectedValue();
        if (selected == null) return;

        tasks.remove(selected);
        refreshView();
    }
    //할일 목록 검색
    private void refreshView() {
        listModel.clear();

        String filter = (String) filterCombo.getSelectedItem(); //검색시 전체, 미완료, 완료 중 검색
        String keyword = searchField.getText().trim(); //검색에 있력한 내용, 공백 제거

        int doneCount = 0;

        for (Task t : tasks) {
            if (t.done) doneCount++;

            // 필터 조건 t.done 이 ture 이면 완료 false 이면 미완료
            if ("미완료".equals(filter) && t.done) continue; //미완료 검색인데 완료일때
            if ("완료".equals(filter) && !t.done) continue; //완료 검색인데 미완료일때

            // 검색 조건
            if (!keyword.isEmpty() && !t.title.contains(keyword)) continue; //키워드가 비었거나 검색이 아닌 경우 제외

            listModel.addElement(t);
        }

        statusLabel.setText("총 " + tasks.size() + "개 / 완료 " + doneCount + "개");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new E09TodoApp().setVisible(true));
    }
}
