package ru.gb.jdk.chat.client;

public class ClientInfo {

    private String login;
    private String password;
    private int port;
    private String ip;

    public ClientInfo(String login, String password, int port, String ip) {
        this.login = login;
        this.password = password;
        this.port = port;
        this.ip = ip;
    }

    public String getPassword() {
        return password;
    }

    public int getPort() {
        return port;
    }

    public String getIp() {
        return ip;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
