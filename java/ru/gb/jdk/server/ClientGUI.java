package ru.gb.jdk.server;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientGUI extends JFrame {
    private ClientGUI client;
    private String login = "Иван Сидоров";
    private String port = "3128";
    private String ip = "127.0.0.1";
    private ServerWindow serverWindow;
    private static final int WINDOW_HEIGHT = 300;
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_POSX = 200;
    private static final int WINDOW_POSY = 300;
    public static final String MSG_SERVER_READY = "Вы успешно подключились!";
    public static final String MSG_SERVER_NOT_AVAILABLE = "Подключение не удалось!";
    private final JTextField ipField = new JTextField(this.ip);
    private final JTextField loginField = new JTextField(this.login);
    private final JTextField portField = new JTextField(this.port);
    private final JTextField passwordField = new JTextField("********");
    private final JButton btnLogin = new JButton("Login");
    private final JTextField messageField = new JTextField();
    private final JButton btnSend = new JButton("Send");
    private final JPanel sendMsgPanel = new JPanel(new GridLayout());
    private final JPanel loginPanel = new JPanel(new GridLayout(2, 3));
    private final JTextArea logsArea = new JTextArea();
    private boolean isConnected = false;

    public ClientGUI(ServerWindow serverWindow) {
        this.serverWindow = serverWindow;
        client = this;
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("Chat client");
        setResizable(false);
        add(loginPanel(), BorderLayout.NORTH);
        add(logsArea);
        logsArea.setEditable(false);
        logsArea.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        add(sendMessagePanel(), BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);

    }

    private JPanel loginPanel() {

        loginField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { loginChange(); }
            public void removeUpdate(DocumentEvent e) { loginChange(); }
            public void changedUpdate(DocumentEvent e) { loginChange(); }
            private void loginChange() {
                login = loginField.getText();
            }
        });

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(serverWindow.connect(client)) {
                    logsArea.append(MSG_SERVER_READY + System.lineSeparator());
                    logsArea.append(serverWindow.readLogs());
                    loginPanel.setVisible(false);
                    isConnected = true;
                } else {
                    logsArea.append(MSG_SERVER_NOT_AVAILABLE + System.lineSeparator());
                }
            }
        });

        loginPanel.add(ipField);
        loginPanel.add(portField);
        loginPanel.add(new JPanel());
        loginPanel.add(loginField);
        loginPanel.add(passwordField);
        loginPanel.add(btnLogin);
        return loginPanel;
    }

    private JPanel sendMessagePanel() {
        JPanel sendMsgPanel = new JPanel(new GridLayout(1, 6));
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        messageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        sendMsgPanel.add(messageField, BorderLayout.CENTER);
        sendMsgPanel.add(btnSend, BorderLayout.EAST);
        return sendMsgPanel;
    }

    private void sendMessage() {
        if (!serverWindow.sendMessage(client, messageField.getText())) {
            isConnected = false;
            logsArea.append(MSG_SERVER_NOT_AVAILABLE + System.lineSeparator());
            loginPanel.setVisible(true);
        } else messageField.setText("");

    }

    public String getLogin() {
        return login;
    }

    public void getMessage(String msg) {
       logsArea.append(msg + System.lineSeparator());
    }
}
