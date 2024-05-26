package ru.gb.jdk.lesson3.common;

/*
3.Напишите обобщенный класс Pair, который представляет собой пару значений разного типа.
Класс должен иметь методы getFirst(), getSecond() для получения значений каждого из
составляющих пары, а также переопределение метода toString(), возвращающее строковое
представление пары. Работу сдать в виде ссылки на гит репозиторий.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println((new Pair<String, Integer>("Ivanov", 24)));
    }
}
