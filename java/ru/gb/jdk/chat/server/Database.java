package ru.gb.jdk.chat.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Database implements Repositiry{
    @Override
    public void saveLog(String msg) {
        try (FileWriter fileWriter = new FileWriter("logs.txt", true)) {
            fileWriter.write(msg + System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String readLogs() {
        try (BufferedReader reader = new BufferedReader(new FileReader("logs.txt"))) {
            StringBuilder outputText = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                outputText.append(line)
                        .append(System.lineSeparator());
            }
            return outputText.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
