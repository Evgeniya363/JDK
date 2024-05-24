package ru.gb.jdk.chat.client;

import ru.gb.jdk.chat.common.Registered;

public interface ClientView extends Registered {
    void connectToServer(boolean connected);
}
