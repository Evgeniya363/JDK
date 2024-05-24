package ru.gb.jdk.chat.server;

import ru.gb.jdk.chat.client.ClientController;
import ru.gb.jdk.chat.common.Registered;

import java.util.ArrayList;
import java.util.List;

public class ServerController {
    public static final String MSG_SERVER_STARTED = "Сервер запущен!";
    public static final String MSG_SERVER_STOPPED = "Сервер остановлен!";
    public static final String MSG_USER_CONNECT = "подключился к беседе";
    public static final String MSG_USER_DISCONNECT = "покинул чат";
    public static final String MSG_SERVER_NOT_AVAILABLE = "Сервер недоступен!";
    public static final String MSG_SERVER_AVAILABLE = "Подключение восстановлено!";
    public static final String MSG_USER_CONNECTED = "Вы успешно подключились!";

    private boolean isServerStarted = false;
    Repositiry repositiry;
    Registered registered;

    private final List<ClientController> clientsList = new ArrayList<>();

    public ServerController(Repositiry repositiry) {
        this.repositiry = repositiry;
    }

    public void setRegistered(Registered registered) {
        this.registered = registered;
    }

    public void startServer() {
        isServerStarted = true;
        showLog(MSG_SERVER_STARTED);
        sendLogAll(MSG_SERVER_AVAILABLE);
        repositiry.saveLog(MSG_SERVER_STARTED);
    }

    public void stopServer() {
        isServerStarted = false;
        showLog(MSG_SERVER_STOPPED);
        sendLogAll(MSG_SERVER_NOT_AVAILABLE);
        repositiry.saveLog(MSG_SERVER_STOPPED);
    }

    public boolean connect(ClientController client) {
        if (isServerStarted) {
            String message = client.getClientInfo().getLogin() + " " + MSG_USER_CONNECT;
            groupOperations(message);  // Логировать в файл, на сервер, в окна клиентов

            clientsList.add(client);
            client.sendAnswer(MSG_USER_CONNECTED, isServerStarted);  // Сообщение новому клиенту
            client.sendAnswer(repositiry.readLogs(), isServerStarted);  // История сообщений из файла

            return true;
        }
        return false;
    }

    private void groupOperations(String message) {
        showLog(message);
        sendLogAll(message);
        repositiry.saveLog(message);
    }

    public void disconnect(ClientController client) {
        clientsList.remove(client);
        String message = client.getClientInfo().getLogin() + " " + MSG_USER_DISCONNECT;
        groupOperations(message);
    }

    public boolean sendMessage(String message) {
        if (isServerStarted) {
            groupOperations(message);
            return true;
        }
        return false;
    }

    private void sendLogAll(String message) {
        for (ClientController cl : clientsList) {
            cl.sendAnswer(message, isServerStarted);
        }
    }

    private void showLog(String message) {
        registered.showLog(message);
    }
}
