package ru.gb.jdk.chat.server;

import ru.gb.jdk.chat.common.LogsWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerGUI extends LogsWindow {
    private JButton startBtn;
    private JButton stopBtn;
    ServerController server;

    public ServerGUI(ServerController server) {
        super("Chat server");
        this.server = server;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(logPanel);
        add(buttonPanel(), BorderLayout.SOUTH);
        setVisible(true);
    }

    private JPanel buttonPanel() {
        JPanel btnPanel = new JPanel(new GridLayout(1, 2));
        startBtn = new JButton("Start");
        stopBtn = new JButton("Stop");
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.startServer();
            }
        });

        stopBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.stopServer();
            }
        });

        btnPanel.add(startBtn);
        btnPanel.add(stopBtn);
        return btnPanel;
    }

}
