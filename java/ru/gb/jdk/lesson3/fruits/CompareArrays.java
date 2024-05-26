package ru.gb.jdk.lesson3.fruits;

/*
2.Напишите обобщенный метод compareArrays(), который принимает два массива и возвращает true,
если они одинаковые, и false в противном случае. Массивы могут быть любого типа данных, но
должны иметь одинаковую длину и содержать элементы одного типа по парно по индексам.
 */

public class CompareArrays {
       public static <T, K> boolean compareArrays(T[] array1, K[] array2) {
        if (array1.length != array2.length)
            return  false;
        for (int i = 0; i < array1.length; i++) {
            if(array1[i].getClass() != array2[i].getClass())
                return false;
        }
        return true;
    }

}
