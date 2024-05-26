package ru.gb.jdk.lesson3.calculator;

import java.util.Arrays;
import java.util.List;

/*
1. Написать класс Калькулятор (необобщенный), который содержит обобщенные статические методы:
 sum(), multiply(), divide(), subtract(). Параметры этих методов – два числа разного типа,
 над которыми должна быть произведена операция. Методы должны возвращать результат своей работы.
 */
public class Calc {
    private static final List<String> types = Arrays.asList("Byte", "Short", "Integer", "Long", "Float", "Double");

    public static <T extends Number, V extends Number> String sum(T a, V b) {
        if (defineType(a, b) < 4)
            return String.valueOf((a.longValue() + b.longValue()));
        else
            return String.valueOf(a.doubleValue() + b.doubleValue());
    }

    public static <T extends Number, V extends Number> String multiply(T a, V b) {
        if (defineType(a, b) < 4)
            return String.valueOf((a.longValue() - b.longValue()));
        else
            return String.valueOf(a.doubleValue() - b.doubleValue());
    }


    public static <T extends Number, V extends Number> String divide(T a, V b) {
        try {
            if (defineType(a, b) < 4)
                return String.valueOf((a.longValue() / b.longValue()));
            else
                return String.valueOf(a.doubleValue() / b.doubleValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T extends Number, V extends Number> String subtract(T a, V b) {
        if (defineType(a, b) < 4)
            return String.valueOf((a.longValue() - b.longValue()));
        else
            return String.valueOf(a.doubleValue() - b.doubleValue());
    }


    private static <T extends Number, V extends Number> int defineType(T a, V b) {
        int indexTypeA = types.indexOf(a.getClass().getSimpleName());
        int indexTypeB = types.indexOf(b.getClass().getSimpleName());
        return Math.max(indexTypeA, indexTypeB);
    }


    public static void main(String[] args) {
        System.out.println(sum(44f, 13));
        System.out.println(multiply(67, 13.0));
        System.out.println(divide(44.1, 1300000000));
        System.out.println(subtract(0.1111f, (byte) -100));
    }


}

