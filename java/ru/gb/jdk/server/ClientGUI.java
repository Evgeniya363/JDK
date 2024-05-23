package ru.gb.jdk.server;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class ClientGUI extends JFrame{

    private final ServerWindow serverWindow;
    private boolean isConnected = false;

    private String login = "Иван Сидоров";
    private String port = "3128";
    private String ip = "127.0.0.1";

    private static final int WINDOW_HEIGHT = 300;
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_POSX = 200;
    private static final int WINDOW_POSY = 300;

    public static final String MSG_SERVER_READY = "Вы успешно подключились!";
    public static final String MSG_SERVER_NOT_AVAILABLE = "Подключение не удалось!";
    public static final String MSG_EMPTY_FIELD = "Поле не может быть пустым!";

    private JTextField ipField = new JTextField(this.ip);;
    private JTextField loginField = new JTextField(this.login);
    private JTextField portField = new JTextField(this.port);
    private JTextField passwordField = new JTextField("********");
    private JButton btnLogin = new JButton("Login");
    private JTextField messageField = new JTextField();
    private JButton btnSend = new JButton("Send");
    private JPanel sendMsgPanel = new JPanel(new GridLayout());
    private JPanel loginPanel = new JPanel(new GridLayout(2, 3));
    private JTextArea logsArea = new JTextArea();


    public ClientGUI(ServerWindow serverWindow) {
        this.serverWindow = serverWindow;

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("Chat client");
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                disconnect();
            }
        });

        mainPanel();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void mainPanel() {
        add(loginPanel(), BorderLayout.NORTH);
        add(logsArea);
        logsArea.setEditable(false);
        logsArea.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        add(sendMessagePanel(), BorderLayout.SOUTH);
    }

    private void disconnect() {
        serverWindow.disconnect(this);
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
                connect();
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

    private void connect() {
        if(serverWindow.connect(this)) {
            getMessage(MSG_SERVER_READY);
            getMessage(serverWindow.readLogs());
            loginPanel.setVisible(false);
            isConnected = true;
        } else {
            getMessage(MSG_SERVER_NOT_AVAILABLE);
        }
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
        if (Objects.equals(messageField.getText(), ""))
            throw new RuntimeException(MSG_EMPTY_FIELD);
        if (!serverWindow.sendMessage(login + ": " + messageField.getText())) {
            isConnected = false;
            getMessage(MSG_SERVER_NOT_AVAILABLE);
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
