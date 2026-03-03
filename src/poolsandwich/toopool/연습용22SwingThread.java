package poolsandwich.toopool;

import javax.swing.*;
import java.awt.*;

class TimerUI extends JFrame {

    JLabel timerNameLabel;
    JLabel timerLabel;
// fileMenu.addSeparator(); // 구분선만들기
    JButton startButton;
    JButton stopButton;
    JButton pauseButton;
    JButton plusButton;
    JButton LMinButton;
    JButton saveButton;

    JPanel bottomP;
    JPanel LSideIndex;
    JPanel RSideIndex;
    JPanel LeftPanel;

    boolean running = false;
    int count = 0;
    int step = 1;
    int saveIndex = 1;

    Thread timerThread;

    public TimerUI() {

        timerNameLabel = new JLabel("타이머", SwingConstants.CENTER);
        timerLabel = new JLabel("0", SwingConstants.CENTER);
         // 구분선만들기
        timerLabel.setFont(new Font("Dialog", Font.BOLD, 40));

        startButton = new JButton("시작");
        stopButton = new JButton("멈춰잇");
        pauseButton = new JButton("일시정지");

        plusButton = new JButton("2씩 증가");
        LMinButton = new JButton("1씩 감소");
        saveButton = new JButton("현재 값 저장");

        bottomP = new JPanel();
        bottomP.add(startButton);
        bottomP.add(stopButton);
        bottomP.add(pauseButton);

        LSideIndex = new JPanel();
        LSideIndex.setLayout(new BoxLayout(LSideIndex, BoxLayout.Y_AXIS));
        LSideIndex.add(plusButton);
        LSideIndex.add(LMinButton);
        LSideIndex.add(saveButton);

        LeftPanel = new JPanel(new BorderLayout());
        LeftPanel.add(LSideIndex, BorderLayout.CENTER);

        RSideIndex = new JPanel();
        RSideIndex.setLayout(new BoxLayout(RSideIndex, BoxLayout.Y_AXIS));
        RSideIndex.add(saveButton);

        setLayout(new BorderLayout());
        add(timerNameLabel, BorderLayout.NORTH);
        add(timerLabel, BorderLayout.CENTER);
        add(bottomP, BorderLayout.SOUTH);
        add(LeftPanel, BorderLayout.WEST);
        add(RSideIndex, BorderLayout.EAST);

        timerThread = new Thread(() -> {
            while (true) {
                if (!running) {
                    threadSleep(10);
                    continue;
                }
                threadSleep(1000);
                count += step;

                if (count <= 0) {
                    count = 0;
                    running = false;
                }

                timerLabel.setText(String.valueOf(count));
            }
        });
        timerThread.start();

        startButton.addActionListener(e -> {
            running = true;
            count = 0;
            timerLabel.setText("0");
        });

        stopButton.addActionListener((e)->{
            running = false;
        });

        pauseButton.addActionListener((e)->{
            running = !running;
        });

        plusButton.addActionListener((e) ->{
            step = 2;
        });
        LMinButton.addActionListener((e) ->{
            step = -1;
        });

        saveButton.addActionListener(e -> {
            JLabel save = new JLabel("저장 " + saveIndex + " : " + count);
            RSideIndex.add(save);
            saveIndex++;
            RSideIndex.revalidate();
            RSideIndex.repaint();
        });

        setBounds(200, 200, 500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    void threadSleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class 연습용22SwingThread {
    public static void main(String[] args) {
        new TimerUI();
    }
}
