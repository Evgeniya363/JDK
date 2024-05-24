package ru.gb.jdk.chat.common;

import javax.swing.*;
import java.awt.*;

public class LogsWindow extends JFrame implements Registered{
    private static final int WINDOW_HEIGHT = 300;
    private static final int WINDOW_WIDTH = 400;

    private JTextArea logsArea;
    protected JPanel logPanel;

    public LogsWindow(String title) {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle(title);
        setResizable(false);
        logPanel = logPanel();
        setLocationRelativeTo(null);
    }

    private JPanel logPanel() {
        JPanel chatLogPanel = new JPanel(new GridLayout());
        logsArea = new JTextArea();
        chatLogPanel.add(logsArea);
        logsArea.setEditable(false);
        chatLogPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        return chatLogPanel;
    }

    @Override
    public void showLog(String msg) {
        logsArea.append(msg + System.lineSeparator());
    }
}
