package ru.gb.jdk.lesson3.fruits;

import static ru.gb.jdk.lesson3.fruits.CompareArrays.compareArrays;

/*
2.Напишите обобщенный метод compareArrays(), который принимает два массива и возвращает true,
если они одинаковые, и false в противном случае. Массивы могут быть любого типа данных, но
должны иметь одинаковую длину и содержать элементы одного типа по парно по индексам.
 */
public abstract class Main {

    public static void main(String[] args) {
        Apple a1 = new Apple(1);
        Apple a2 = new Apple(4);
        Apple a3 = new Apple(2);
        Orange o1 = new Orange(1);
        Orange o2 = new Orange(2);
        Orange o3 = new Orange(5);
        Orange o4 = new Orange(3);

        Fruit[] f1 = {o1, o2, o3, a1};
        Fruit[] f2 = {o2, o4, o2, a2};
        Orange[] f3 = {o4, o1, o2, o2};
        Fruit[] f4 = {o2, o4, o2, a2, o1};
        Object[] f5 = {o1, o1, o1, o1};

        System.out.println(compareArrays(f1, f2));  // одинаковые
        System.out.println(compareArrays(f1, f3));  // разные элементы
        System.out.println(compareArrays(f1, f4));  // разная длина
        System.out.println(compareArrays(f3, f5));  // одинаковые
    }

}
