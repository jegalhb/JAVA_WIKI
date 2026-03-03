package poolsandwich;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class E10ContactManagerMapApp extends JFrame {

    // 연락처 정보(값)
    static class ContactInfo {
        String phone;
        String nickname;
        String memo;

        ContactInfo(String phone, String nickname, String memo) {
            this.phone = phone;
            this.nickname = nickname;
            this.memo = memo;
        }

        @Override
        public String toString() {
            String nn = (nickname == null || nickname.isBlank()) ? "-" : nickname;
            return "전화: " + phone + " / 별명: " + nn;
        }
    }

    // Map: 이름이 Key
    private final Map<String, ContactInfo> contacts = new LinkedHashMap<>();

    // UI
    private final JTextField nameField = new JTextField(10);
    private final JTextField phoneField = new JTextField(12);
    private final JTextField nicknameField = new JTextField(10);
    private final JTextArea memoArea = new JTextArea(3, 30);

    private final JButton addButton = new JButton("등록");
    private final JButton updateButton = new JButton("수정");
    private final JButton deleteButton = new JButton("삭제");
    private final JButton clearButton = new JButton("입력 초기화");

    private final JTextField searchField = new JTextField(12);
    private final JButton searchButton = new JButton("검색(이름)");

    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> nameList = new JList<>(listModel);

    private final JLabel statusLabel = new JLabel("총 0명");

    public E10ContactManagerMapApp() {
        super("연락처 관리 앱(Map 버전)");

        // 입력 패널
        JPanel input = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        c.gridx = 0; c.gridy = row; input.add(new JLabel("이름(키)"), c);
        c.gridx = 1; c.gridy = row; input.add(nameField, c);
        c.gridx = 2; c.gridy = row; input.add(new JLabel("폰번호"), c);
        c.gridx = 3; c.gridy = row; input.add(phoneField, c);
        row++;

        c.gridx = 0; c.gridy = row; input.add(new JLabel("별명"), c);
        c.gridx = 1; c.gridy = row; input.add(nicknameField, c);
        c.gridx = 2; c.gridy = row; input.add(new JLabel("메모"), c);
        c.gridx = 3; c.gridy = row; input.add(new JScrollPane(memoArea), c);
        row++;

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttons.add(addButton);
        buttons.add(updateButton);
        buttons.add(deleteButton);
        buttons.add(clearButton);

        c.gridx = 0; c.gridy = row; c.gridwidth = 4; input.add(buttons, c);

        // 검색 패널
        JPanel search = new JPanel(new FlowLayout(FlowLayout.LEFT));
        search.add(new JLabel("검색(이름)"));
        search.add(searchField);
        search.add(searchButton);
        search.add(statusLabel);

        // 리스트
        nameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroll = new JScrollPane(nameList);

        setLayout(new BorderLayout());
        add(input, BorderLayout.NORTH);
        add(listScroll, BorderLayout.CENTER);
        add(search, BorderLayout.SOUTH);

        // 기본 데이터
        loadDefaultContacts();
        refreshListAll();

        // 이벤트
        addButton.addActionListener(e -> addContact());
        updateButton.addActionListener(e -> updateContact());
        deleteButton.addActionListener(e -> deleteContact());
        clearButton.addActionListener(e -> clearInputs());

        searchButton.addActionListener(e -> searchByName());
        searchField.addActionListener(e -> searchByName());

        nameList.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            String selectedName = nameList.getSelectedValue();
            if (selectedName == null) return;

            ContactInfo info = contacts.get(selectedName);
            if (info == null) return;

            nameField.setText(selectedName);
            phoneField.setText(info.phone);
            nicknameField.setText(info.nickname);
            memoArea.setText(info.memo);
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 420);
        setLocationRelativeTo(null);
    }

    // ---------------- 기능 ----------------
    private void addContact() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String nickname = nicknameField.getText().trim();
        String memo = memoArea.getText().trim();

        if (name.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "이름과 폰번호는 필수입니다.");
            return;
        }

        // 이름이 Key이므로, 같은 이름이면 등록 불가(수정 사용)
        if (contacts.containsKey(name)) {
            JOptionPane.showMessageDialog(this,
                    "이미 존재하는 이름(키)입니다.\n" +
                            "다른 이름으로 등록하거나, 수정 버튼을 사용하세요.");
            return;
        }

        // 폰번호 중복도 막고 싶다면(권장) 체크
        if (existsPhone(phone, null)) {
            JOptionPane.showMessageDialog(this,
                    "이미 등록된 폰번호입니다.\n" +
                            "다른 폰번호로 등록하거나 기존 연락처를 수정하세요.");
            return;
        }

        contacts.put(name, new ContactInfo(phone, nickname, memo));
        refreshListAll();
        clearInputs();
    }

    private void updateContact() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "수정할 이름(키)을 입력하거나 선택하세요.");
            return;
        }

        if (!contacts.containsKey(name)) {
            JOptionPane.showMessageDialog(this, "존재하지 않는 이름입니다. 먼저 등록하세요.");
            return;
        }

        String phone = phoneField.getText().trim();
        String nickname = nicknameField.getText().trim();
        String memo = memoArea.getText().trim();

        if (phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "폰번호는 필수입니다.");
            return;
        }

        // 본인(현재 name)은 제외하고 폰번호 중복 검사
        if (existsPhone(phone, name)) {
            JOptionPane.showMessageDialog(this, "이미 다른 연락처에서 사용하는 폰번호입니다.");
            return;
        }

        ContactInfo info = contacts.get(name);
        info.phone = phone;
        info.nickname = nickname;
        info.memo = memo;

        refreshListAll();
        clearInputs();
    }

    private void deleteContact() {
        String selected = nameList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "삭제할 대상을 리스트에서 선택하세요.");
            return;
        }

        int ok = JOptionPane.showConfirmDialog(
                this,
                "정말 삭제하시겠습니까?\n" + selected,
                "삭제 확인",
                JOptionPane.YES_NO_OPTION
        );

        if (ok == JOptionPane.YES_OPTION) {
            contacts.remove(selected);
            refreshListAll();
            clearInputs();
        }
    }

    private void searchByName() {
        String keyword = searchField.getText().trim();

        if (keyword.isEmpty()) {
            refreshListAll();
            return;
        }

        listModel.clear();
        int count = 0;

        for (String name : contacts.keySet()) {
            if (name.contains(keyword)) {
                listModel.addElement(name);
                count++;
            }
        }

        statusLabel.setText("검색 " + count + "명 / 총 " + contacts.size() + "명");
    }

    // ---------------- 헬퍼 ----------------
    private boolean existsPhone(String phone, String excludeName) {
        for (Map.Entry<String, ContactInfo> e : contacts.entrySet()) {
            String name = e.getKey();
            if (excludeName != null && excludeName.equals(name)) continue;

            if (e.getValue().phone.equals(phone)) return true;
        }
        return false;
    }

    private void refreshListAll() {
        listModel.clear();
        for (String name : contacts.keySet()) {
            listModel.addElement(name);
        }
        statusLabel.setText("총 " + contacts.size() + "명");
    }

    private void clearInputs() {
        nameField.setText("");
        phoneField.setText("");
        nicknameField.setText("");
        memoArea.setText("");
        nameField.requestFocus();
    }

    private void loadDefaultContacts() {
        contacts.put("김민준", new ContactInfo("010-1111-2222", "민준", "대학 동기"));
        contacts.put("이서연", new ContactInfo("010-2222-3333", "서연", "프로젝트 팀원"));
        contacts.put("박지훈", new ContactInfo("010-3333-4444", "지훈", "헬스장 친구"));
        contacts.put("최유진", new ContactInfo("010-4444-5555", "유진", "중학교 동창"));
        contacts.put("정하늘", new ContactInfo("010-5555-6666", "하늘", "카페 사장님"));
        contacts.put("강도윤", new ContactInfo("010-6666-7777", "도윤", "스터디 리더"));
        contacts.put("윤서아", new ContactInfo("010-7777-8888", "서아", "이웃"));
        contacts.put("장민재", new ContactInfo("010-8888-9999", "민재", "회사 동료"));
        contacts.put("오지민", new ContactInfo("010-1234-5678", "지민", "친한 후배"));
        contacts.put("한수빈", new ContactInfo("010-2345-6789", "수빈", "학원 선생님"));
        contacts.put("신예린", new ContactInfo("010-3456-7890", "예린", "병원 예약 담당"));
        contacts.put("서준호", new ContactInfo("010-4567-8901", "준호", "이전 직장 동료"));
        contacts.put("문채원", new ContactInfo("010-5678-9012", "채원", "가족 모임"));
        contacts.put("배현우", new ContactInfo("010-6789-0123", "현우", "동네 친구"));
        contacts.put("송지아", new ContactInfo("010-7890-1234", "지아", "동호회"));
        contacts.put("임도현", new ContactInfo("010-8901-2345", "도현", "미팅 담당"));
        contacts.put("노유나", new ContactInfo("010-9012-3456", "유나", "사진관 사장님"));
        contacts.put("홍시우", new ContactInfo("010-1122-3344", "시우", "프리랜서 연결"));
        contacts.put("유나연", new ContactInfo("010-2233-4455", "나연", "교회 지인"));
        contacts.put("전태훈", new ContactInfo("010-3344-5566", "태훈", "친구의 친구"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new E10ContactManagerMapApp().setVisible(true));
    }
}