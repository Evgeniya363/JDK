package ru.gb.jdk.lesson3.common;

/*
3.Напишите обобщенный класс Pair, который представляет собой пару значений разного типа.
Класс должен иметь методы getFirst(), getSecond() для получения значений каждого из
составляющих пары, а также переопределение метода toString(), возвращающее строковое
представление пары. Работу сдать в виде ссылки на гит репозиторий.
 */
public class Pair<T, V> {
    private T first;
    private V second;

    public Pair(T first, V second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public V getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "Operands: (" + first.getClass().getSimpleName() + ") " + first +
                ", (" + second.getClass().getSimpleName() + ") " + second;
    }
}
