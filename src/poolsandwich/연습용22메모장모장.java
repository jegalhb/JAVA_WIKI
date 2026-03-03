package poolsandwich;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class 연습용22메모장모장 extends JFrame {

    private final JTextArea textArea = new JTextArea();
    private File currentFile = null;
    private boolean dirty = false;
    private boolean wordWrap = true;
    private JLabel statusBar = new JLabel("글자 수: 0");
    private static int untitledCount = 1;
    private final ArrayList<File> recentFiles = new ArrayList<>();

    public 연습용22메모장모장() {
        super("Swing 메모장");

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);

        initUI();
        initEvents();
        updateTitle();
    }

    private void initUI() {
        textArea.setFont(new Font("Dialog", Font.PLAIN, 16));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // 상태바
        statusBar.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
        add(statusBar, BorderLayout.SOUTH);

        setJMenuBar(createMenuBar());
    }

    private JMenuBar createMenuBar() {
        JMenuBar mb = new JMenuBar();

        // ===== 파일 =====
        JMenu fileMenu = new JMenu("파일");
        JMenuItem miNew = new JMenuItem("새로 만들기");
        JMenuItem miOpen = new JMenuItem("열기...");
        JMenuItem miSave = new JMenuItem("저장");
        JMenuItem miSaveAs = new JMenuItem("다른 이름으로 저장...");
        JMenuItem miExit = new JMenuItem("종료");

        miNew.addActionListener(e -> newFile());
        miOpen.addActionListener(e -> openFile());
        miSave.addActionListener(e -> saveFile());
        miSaveAs.addActionListener(e -> saveFileAs());
        miExit.addActionListener(e -> exitApp());

        fileMenu.add(miNew);
        fileMenu.addSeparator();
        fileMenu.add(miOpen);
        fileMenu.add(miSave);
        fileMenu.add(miSaveAs);
        fileMenu.addSeparator();

        // 최근 파일 서브메뉴
        JMenu recentMenu = new JMenu("최근 파일");
        fileMenu.add(recentMenu);
        updateRecentMenu(recentMenu);

        fileMenu.addSeparator();
        fileMenu.add(miExit);

        // ===== 편집 =====
        JMenu editMenu = new JMenu("편집");
        JMenuItem miCut = new JMenuItem("잘라내기");
        JMenuItem miCopy = new JMenuItem("복사");
        JMenuItem miPaste = new JMenuItem("붙여넣기");
        JMenuItem miSelectAll = new JMenuItem("전체 선택");
        JMenuItem miFind = new JMenuItem("찾기...");

        miCut.addActionListener(e -> textArea.cut());
        miCopy.addActionListener(e -> textArea.copy());
        miPaste.addActionListener(e -> textArea.paste());
        miSelectAll.addActionListener(e -> textArea.selectAll());
        miFind.addActionListener(e -> findText());

        editMenu.add(miCut);
        editMenu.add(miCopy);
        editMenu.add(miPaste);
        editMenu.addSeparator();
        editMenu.add(miSelectAll);
        editMenu.addSeparator();
        editMenu.add(miFind);

        // ===== 서식 =====
        JMenu formatMenu = new JMenu("서식");
        JCheckBoxMenuItem miWordWrap = new JCheckBoxMenuItem("자동 줄바꿈", true);
        JMenuItem miFont = new JMenuItem("글꼴...");

        miWordWrap.addActionListener(e -> toggleWordWrap());
        miFont.addActionListener(e -> changeFont());

        formatMenu.add(miWordWrap);
        formatMenu.add(miFont);

        // ===== 보기 =====
        JMenu viewMenu = new JMenu("보기");
        JMenuItem miWordCount = new JMenuItem("글자 수 보기");
        miWordCount.addActionListener(e -> showWordCount());
        viewMenu.add(miWordCount);

        // ===== 도움말 =====
        JMenu helpMenu = new JMenu("도움말");
        JMenuItem miAbout = new JMenuItem("정보");
        miAbout.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Swing 메모장\n버전 1.0\n제작자: 나",
                        "정보",
                        JOptionPane.INFORMATION_MESSAGE));
        helpMenu.add(miAbout);

        mb.add(fileMenu);
        mb.add(editMenu);
        mb.add(formatMenu);
        mb.add(viewMenu);
        mb.add(helpMenu);

        return mb;
    }

    private void updateRecentMenu(JMenu recentMenu) {
        recentMenu.removeAll();
        for (File f : recentFiles) {
            JMenuItem item = new JMenuItem(f.getName());
            item.addActionListener(e -> {
                currentFile = f;
                openFile(f);
            });
            recentMenu.add(item);
        }
    }

    private void toggleWordWrap() {
        wordWrap = !wordWrap;
        textArea.setLineWrap(wordWrap);
        textArea.setWrapStyleWord(wordWrap);
    }

    private void changeFont() {
        String sizeStr = JOptionPane.showInputDialog(this, "글자 크기 입력:", "16");
        if (sizeStr == null) return;

        try {
            int size = Integer.parseInt(sizeStr);
            textArea.setFont(new Font("Dialog", Font.PLAIN, size));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "숫자를 입력하세요.");
        }
    }

    private void showWordCount() {
        int chars = textArea.getText().length();
        JOptionPane.showMessageDialog(this, "총 글자 수: " + chars, "글자 수", JOptionPane.INFORMATION_MESSAGE);
    }

    private void findText() {
        String word = JOptionPane.showInputDialog(this, "찾을 단어:");
        if (word == null || word.isEmpty()) return;

        String content = textArea.getText();
        int idx = content.indexOf(word);
        if (idx >= 0) {
            textArea.select(idx, idx + word.length());
            textArea.requestFocus();
        } else {
            JOptionPane.showMessageDialog(this, "찾을 수 없습니다.");
        }
    }

    private void initEvents() {
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { markDirty(); updateStatus(); }
            @Override public void removeUpdate(DocumentEvent e) { markDirty(); updateStatus(); }
            @Override public void changedUpdate(DocumentEvent e) { markDirty(); updateStatus(); }
        });

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override public void windowClosing(java.awt.event.WindowEvent e) {
                exitApp();
            }
        });
    }

    private void updateStatus() {
        statusBar.setText("글자 수: " + textArea.getText().length());
    }

    private void markDirty() {
        if (!dirty) {
            dirty = true;
            updateTitle();
        }
    }

    private void updateTitle() {
        String fileName = (currentFile == null) ? ("제목 없음" + untitledCount) : currentFile.getName();
        String star = dirty ? " *" : "";
        setTitle("Swing 메모장 - " + fileName + star);
    }

    private void newFile() {
        if (!confirmDiscardIfDirty()) return;
        textArea.setText("");
        currentFile = null;
        dirty = false;
        untitledCount++;
        updateTitle();
    }

    private void openFile() {
        JFileChooser chooser = createChooser();
        if (!confirmDiscardIfDirty()) return;

        int ret = chooser.showOpenDialog(this);
        if (ret != JFileChooser.APPROVE_OPTION) return;

        openFile(chooser.getSelectedFile());
    }

    private void openFile(File file) {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

            textArea.read(br, null);
            currentFile = file;
            dirty = false;
            if (!recentFiles.contains(file)) {
                recentFiles.add(0, file);
                if (recentFiles.size() > 3) recentFiles.remove(3);
            }
            updateTitle();
            setJMenuBar(createMenuBar());
        } catch (IOException e) {
            showError("파일 열기 실패", e.getMessage());
        }
    }

    private void saveFile() {
        if (currentFile == null) {
            saveFileAs();
            return;
        }
        writeToFile(currentFile);
    }

    private void saveFileAs() {
        JFileChooser chooser = createChooser();
        chooser.setSelectedFile(new File("memo.txt"));

        int ret = chooser.showSaveDialog(this);
        if (ret != JFileChooser.APPROVE_OPTION) return;

        currentFile = chooser.getSelectedFile();
        writeToFile(currentFile);
    }

    private void writeToFile(File file) {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {

            textArea.write(bw);
            dirty = false;
            updateTitle();
        } catch (IOException e) {
            showError("저장 실패", e.getMessage());
        }
    }

    private void exitApp() {
        if (!confirmDiscardIfDirty()) return;
        dispose();
        System.exit(0);
    }

    private boolean confirmDiscardIfDirty() {
        if (!dirty) return true;

        int ans = JOptionPane.showConfirmDialog(
                this,
                "저장하지 않은 변경 사항이 있습니다. 저장할까요?",
                "저장 확인",
                JOptionPane.YES_NO_CANCEL_OPTION
        );

        if (ans == JOptionPane.CANCEL_OPTION || ans == JOptionPane.CLOSED_OPTION) return false;

        if (ans == JOptionPane.YES_OPTION) {
            saveFile();
            return !dirty;
        }

        return true;
    }

    private JFileChooser createChooser() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Text Files (*.txt)", "txt"));
        return chooser;
    }

    private void showError(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new 연습용22강사님메모장().setVisible(true));
    }
}
