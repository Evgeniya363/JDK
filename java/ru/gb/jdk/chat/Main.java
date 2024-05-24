package ru.gb.jdk.chat;

import ru.gb.jdk.chat.client.ClientController;
import ru.gb.jdk.chat.client.ClientGUI;
import ru.gb.jdk.chat.client.ClientInfo;
import ru.gb.jdk.chat.server.Database;
import ru.gb.jdk.chat.server.ServerController;
import ru.gb.jdk.chat.server.ServerGUI;

/*
1. Реализовать клиент-серверное приложение. Начало его можно увидеть в презентации к первому уроку, а можно
ориентироваться на скриншоты. Результат можно увидеть на скриншотах, которые также можно найти в материалах к уроку
2. Клиентское приложение должно отправлять сообщения из текстового поля сообщения в серверное приложение по нажатию
кнопки или по нажатию клавиши Enter на поле ввода сообщения;
3. Продублировать импровизированный лог (историю) чата в файле;
4. При запуске клиента чата заполнять поле истории из файла, если он существует.
 Обратите внимание, что чаще всего история сообщений хранится на сервере и заполнение истории чата лучше делать
 при соединении с сервером, а не при открытии окна клиента.
 */
public class Main {
    public static void main(String[] args) {
        ServerController server = new ServerController(new Database());
        server.setRegistered(new ServerGUI(server));

        ClientInfo c1 = new ClientInfo("Ponchic", "12345",1234,"127.0.0.1");
        ClientGUI clientGUI1 = new ClientGUI(c1.getLogin(), c1.getPassword(), c1.getPort(), c1.getIp());
        clientGUI1.setClientController(new ClientController(server, clientGUI1, c1));

        ClientInfo c2 = new ClientInfo("Batonchic", "54321",3344,"127.0.0.1");
        ClientGUI clientGUI2 = new ClientGUI(c2.getLogin(), c2.getPassword(), c2.getPort(), c2.getIp());
        clientGUI2.setClientController(new ClientController(server, clientGUI2, c2));
    }
}
