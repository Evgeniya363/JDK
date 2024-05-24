package ru.gb.jdk.chat.client;

import ru.gb.jdk.chat.common.LogsWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientGUI extends LogsWindow implements ClientView {
    public static final String MSG_EMPTY_FIELD = "Поле не может быть пустым!";

    private JTextField ipField;
    private JTextField loginField;
    private JTextField portField;
    private JPasswordField passwordField;
    private JPanel loginPanel;

    private JTextField messageField;
    private JButton btnSend;
//    private JPanel sendMsgPanel = new JPanel(new GridLayout());

//    private JTextArea logsArea = new JTextArea();

    private ClientController clientController;

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    public ClientGUI(String login, String passwodd, int port, String ip) {
        super("Chat client");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                disconnect();
            }
        });

        mainPanel();
        ipField.setText(ip);
        loginField.setText(login);
        portField.setText(String.valueOf(port));
        passwordField.setText(passwodd);

        setLocationRelativeTo(null);
        connectToServer(false);
        setVisible(true);
    }

    private void mainPanel() {
        add(loginPanel(), BorderLayout.NORTH);
        add(logPanel);
        add(MessagePanel(), BorderLayout.SOUTH);
    }

    private JPanel loginPanel() {
        ipField = new JTextField();
        loginField = new JTextField();
        portField = new JTextField();
        passwordField = new JPasswordField();

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connect();
            }
        });

        loginPanel = new JPanel(new GridLayout(2, 3));
        loginPanel.add(ipField);
        loginPanel.add(portField);
        loginPanel.add(new JPanel());
        loginPanel.add(loginField);
        loginPanel.add(passwordField);
        loginPanel.add(btnLogin);
        return loginPanel;
    }

    private JPanel MessagePanel() {
        JPanel sendMsgPanel = new JPanel(new GridLayout());

        btnSend = new JButton("Send");
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        messageField = new JTextField();
        messageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        sendMsgPanel.add(messageField);
        sendMsgPanel.add(btnSend, BorderLayout.EAST);
        return sendMsgPanel;
    }

    private void sendMessage() {
        if(messageField.getText().isEmpty())
            throw new RuntimeException(MSG_EMPTY_FIELD);
        if (clientController.sendMessageToServer(messageField.getText()))
            messageField.setText("");
    }

    private void connect() {
        if(loginField.getText().isEmpty())
            throw new RuntimeException(MSG_EMPTY_FIELD);
        clientController.connectToServer(loginField.getText());
    }

    private void disconnect() {
        clientController.disconnectFromServer();
    }

    @Override
    public void connectToServer(boolean connected) {
        loginPanel.setVisible(!connected);
        btnSend.setEnabled(connected);
    }

}
