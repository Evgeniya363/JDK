package ru.gb.jdk.chat.server;

public interface Repositiry {
    String readLogs();
    void saveLog(String msg);
}
