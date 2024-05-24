package ru.gb.jdk.chat.client;

import ru.gb.jdk.chat.server.ServerController;

public class ClientController {
    public static final String MSG_CONNECTION_FAILED = "Подключение не удалось!";
    private final ServerController server;
    private final ClientInfo clientInfo;
    private final ClientView clientView;
    private boolean isConnected;

    public ClientController(ServerController server, ClientView clientView, ClientInfo clientInfo) {
        isConnected = false;
        this.server = server;
        this.clientInfo = clientInfo;
        this.clientView = clientView;
    }


    public ClientInfo getClientInfo() {
        return clientInfo;
    }


    public void connectToServer(String login) {
        clientInfo.setLogin(login);
        isConnected = server.connect(this);
        checkingStatusClient();
    }

    public boolean sendMessageToServer(String msg) {
        isConnected = server.sendMessage(clientInfo.getLogin() + ": " + msg);
        checkingStatusClient();
        return isConnected;
    }

    private void checkingStatusClient() {
        if (!isConnected)
            clientView.showLog(MSG_CONNECTION_FAILED);
        clientView.connectToServer(isConnected);
    }

    public void sendAnswer(String msg, boolean connected) {
        isConnected = connected;
        clientView.showLog(msg);
        clientView.connectToServer(connected);
    }

    public void disconnectFromServer() {
        if (!isConnected)
            return;
        server.disconnect(this);
        isConnected = false;
        sendAnswer(MSG_CONNECTION_FAILED, isConnected);
    }

}
