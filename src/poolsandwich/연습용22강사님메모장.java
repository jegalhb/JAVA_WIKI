package poolsandwich;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class 연습용22강사님메모장 extends JFrame {

    private final JTextArea textArea = new JTextArea();
    private File currentFile = null; // 현재 열려 있는 파일(없으면 null)
    private boolean dirty = false;    // 수정 여부(저장 안 된 변경이 있는지)

    public 연습용22강사님메모장() {
        super("Swing 메모장");

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // 닫히지않게
        setSize(900, 650); // 메인사이즈
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

        setJMenuBar(createMenuBar());
    }

    private JMenuBar createMenuBar() {
        JMenuBar mb = new JMenuBar();

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
        fileMenu.addSeparator(); // 구분선만들기
        fileMenu.add(miOpen);
        fileMenu.add(miSave);
        fileMenu.add(miSaveAs);
        fileMenu.addSeparator();
        fileMenu.add(miExit);

        mb.add(fileMenu);
        return mb;
    }

    private void initEvents() {
        // 문서 변경 감지(저장 여부 표시용)
        textArea.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override public void insertUpdate(javax.swing.event.DocumentEvent e) {markDirty(); }
            @Override public void removeUpdate(javax.swing.event.DocumentEvent e) { markDirty(); }
            @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { markDirty(); }
        });

        // 창 닫기 버튼 처리
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override public void windowClosing(java.awt.event.WindowEvent e) {
                exitApp();
            }
        });
    }

    private void markDirty() {
        if (!dirty) {
            dirty = true;
            updateTitle();
        }
    }

    private void updateTitle() {
        String fileName = (currentFile == null) ? "제목 없음" : currentFile.getName();
        String star = dirty ? " *" : "";
        setTitle("Swing 메모장 - " + fileName + star);
    }

    private void newFile() {
        if (!confirmDiscardIfDirty()) return;

        textArea.setText("");
        currentFile = null;
        dirty = false;
        updateTitle();
    }

    private void openFile() {
        if (!confirmDiscardIfDirty()) return;

        JFileChooser chooser = createChooser();
        int ret = chooser.showOpenDialog(this);
        if (ret != JFileChooser.APPROVE_OPTION) return;

        File file = chooser.getSelectedFile();

        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

            textArea.setText(sb.toString());
            textArea.setCaretPosition(0);

            currentFile = file;
            dirty = false;
            updateTitle();

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

        File file = chooser.getSelectedFile();
        if (!file.getName().toLowerCase().endsWith(".txt")) {
            file = new File(file.getParentFile(), file.getName() + ".txt");
        }

        // 이미 존재하면 덮어쓸지 확인
        if (file.exists()) {
            int ans = JOptionPane.showConfirmDialog(
                    this,
                    "이미 존재하는 파일입니다. 덮어쓸까요?\n" + file.getName(),
                    "덮어쓰기 확인",
                    JOptionPane.YES_NO_OPTION
            );
            if (ans != JOptionPane.YES_OPTION) return;
        }

        currentFile = file;
        writeToFile(file);
    }

    private void writeToFile(File file) {
        try (FileOutputStream fos = new FileOutputStream(file);
             OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
             BufferedWriter bw = new BufferedWriter(osw)) {

            bw.write(textArea.getText());
            bw.flush();

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

        if (ans == JOptionPane.CANCEL_OPTION || ans == JOptionPane.CLOSED_OPTION) {
            return false;
        }

        if (ans == JOptionPane.YES_OPTION) {
            // 저장을 시도했는데 사용자가 취소하면 종료/이동을 막아야 함
            boolean saved = trySaveBeforeContinue();
            return saved;
        }

        // NO 선택이면 그냥 진행
        return true;
    }

    private boolean trySaveBeforeContinue() {
        int before = JOptionPane.YES_OPTION;

        // saveFile() 호출 후 dirty 여부로 판단
        saveFile();
        return !dirty;
    }

    private JFileChooser createChooser() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("파일 선택");
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
