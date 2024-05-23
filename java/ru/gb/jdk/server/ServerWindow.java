package ru.gb.jdk.server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ServerWindow extends JFrame {
    private static final int WINDOW_HEIGHT = 300;
    private static final int WINDOW_WIDTH = 400;
    public static final String MSG_SERVER_STARTED = "Сервер запущен!";
    public static final String MSG_SERVER_STOPPED = "Сервер остановлен!";
    public static final String MSG_USER_CONNECT = "подключился к беседе";
    public static final String MSG_USER_DISCONNECT = "покинул чат";

    public static final String MSG_SERVER_NOT_AVAILABLE = "Сервер недоступен!";

    private boolean isServerStarted = false;
    private final JButton startBtn = new JButton("Start");
    private final JButton stopBtn = new JButton("Stop");
    private final JTextArea chatLogsArea = new JTextArea();
    private final List<ClientGUI> clientsList = new ArrayList<>();

    public ServerWindow() {

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("Chat server");
        setResizable(false);

        add(chatLogsPanel());
        add(buttonPanel(), BorderLayout.SOUTH);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private JPanel chatLogsPanel() {
        JPanel chatLogPanel = new JPanel(new GridLayout());
        chatLogPanel.add(chatLogsArea);
        chatLogsArea.setEditable(false);
        chatLogPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        return chatLogPanel;
    }

    private JPanel buttonPanel() {
        JPanel btnPanel = new JPanel(new GridLayout(1, 2));
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startServer();
            }
        });

        stopBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopServer();
            }
        });

        btnPanel.add(startBtn);
        btnPanel.add(stopBtn);
        return btnPanel;
    }

    private void stopServer() {
        showLog(MSG_SERVER_STOPPED + System.lineSeparator());
        sendLogAll(MSG_SERVER_NOT_AVAILABLE);
        isServerStarted = false;
    }

    private void startServer() {
        chatLogsArea.append(MSG_SERVER_STARTED + System.lineSeparator());
        isServerStarted = true;
    }



    private void saveLog(String msg) {
        try (FileWriter fileWriter = new FileWriter("logs.txt", true)) {
            fileWriter.write(msg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readLogs() {
        try (BufferedReader reader = new BufferedReader(new FileReader("logs.txt"))) {
            StringBuilder outputText = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                outputText.append(line)
                        .append(System.lineSeparator());
            }
            return outputText.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean connect(ClientGUI client) {
        if (isServerStarted) {
            clientsList.add(client);
            String log = client.getLogin() + " " + MSG_USER_CONNECT + System.lineSeparator();
            showLog(log);
            saveLog(log);
            return true;
        }
        return false;
    }

    public void disconnect(ClientGUI client) {
        clientsList.remove(client);
        showLog(client.getLogin() + " " + MSG_USER_DISCONNECT + System.lineSeparator());
    }

    public boolean sendMessage(String message) {
        if (isServerStarted) {
            sendLogAll(message);
            saveLog(message);
            showLog(message);
            return true;
        }
        return false;
    }

    private void sendLogAll(String message) {
        for (ClientGUI cl : clientsList) {
            cl.getMessage(message);
        }
    }

    private void showLog(String message) {
        chatLogsArea.append(message);
    }
}
