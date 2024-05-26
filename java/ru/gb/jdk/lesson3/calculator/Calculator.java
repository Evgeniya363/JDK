package ru.gb.jdk.lesson3.calculator;

import ru.gb.jdk.lesson3.common.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;

/*
1. Написать класс Калькулятор (необобщенный), который содержит обобщенные статические методы:
 sum(), multiply(), divide(), subtract(). Параметры этих методов – два числа разного типа,
 над которыми должна быть произведена операция. Методы должны возвращать результат своей работы.
 */

/**
 * Класс содержит методы для выполнения бинарных операций над типами, являющимися подклассами
 * класса Number. Cодержит обобщенные статические методы: sum(), multiply(), divide(), subtract()
 */
public class Calculator {
    private static final List<String> types = Arrays.asList("Byte", "Short", "Integer", "Long", "Float", "Double");

    public static <T extends Number, V extends Number, K extends Number> K sum(T a, V b) {
        return changeType(defineType(a, b), a, b, (x, y) -> x + y, (x, y) -> x + y);
    }

    public static <T extends Number, V extends Number, K extends Number> K multiply(T a, V b) {
        return changeType(defineType(a, b), a, b, (x, y) -> x * y, (x, y) -> x * y);
    }

    public static <T extends Number, V extends Number, K extends Number> K divide(T a, V b) {
        return changeType(defineType(a, b), a, b, (x, y) -> x / y, (x, y) -> x / y);
    }

    public static <T extends Number, V extends Number, K extends Number> K subtract(T a, V b) {
        return changeType(defineType(a, b), a, b, (x, y) -> x - y, (x, y) -> x - y);
    }

    public static <T extends Number, V extends Number, K extends Number> K sum(Pair<T, V> p) {
        return sum(p.getFirst(), p.getSecond());
    }

    public static <T extends Number, V extends Number, K extends Number> K multiply(Pair<T, V> p) {
        return sum(p.getFirst(), p.getSecond());
    }

    public static <T extends Number, V extends Number, K extends Number> K subtract(Pair<T, V> p) {
        return sum(p.getFirst(), p.getSecond());
    }

    public static <T extends Number, V extends Number, K extends Number> K divide(Pair<T, V> p) {
        return sum(p.getFirst(), p.getSecond());
    }

    /**
     * Метод возвращает тип результата предполагаемой операции
     *
     * @param a   первый операнд
     * @param b   второй операнд
     * @param <T> тип первого операнда
     * @param <V> тип второго операнда
     * @return тип результата
     */
    private static <T extends Number, V extends Number> String defineType(T a, V b) {
        int typeA = types.indexOf(a.getClass().getSimpleName());
        int typeB = types.indexOf(b.getClass().getSimpleName());
        return types.get(Math.max(typeA, typeB));
    }

    /**
     * Метод выполняет бинарную операцию над данными разных типов - подклассов Number
     *
     * @param type     имя типа
     * @param a        первый операнд
     * @param b        второй операнд
     * @param doubleBO бинарный оператор для типа Double
     * @param longBO   бинарный оператор для типа Long
     * @param <T>      тип первого операнда
     * @param <V>      тип второго операнда
     * @param <K>      тип результата
     * @return резудьтат бинарной операции
     */
    public static <T extends Number, V extends Number, K extends Number> K changeType(
            String type, T a, V b, BinaryOperator<Double> doubleBO, BinaryOperator<Long> longBO) {

        Number result;
        if (type.equals("Long"))
            result = longBO.apply(a.longValue(), b.longValue());
        else
            result = doubleBO.apply(a.doubleValue(), b.doubleValue());

        return switch (type) {
            case "Byte" -> (K) changeTypeError(result.byteValue());
            case "Short" -> (K) changeTypeError(result.shortValue());
            case "Integer" -> (K) changeTypeError(result.intValue());
            case "Long" -> (K) changeTypeError(result.longValue());
            case "Float" -> (K) changeTypeError(result.floatValue());
            case "Double" -> (K) changeTypeError(result.doubleValue());
            default -> throw new RuntimeException("Неизвестный тип");
        };
    }

    private static <K extends Number> K changeTypeError(K arg) {
        return arg;
    }

    public static void main(String[] args) {
        Pair<Integer, Long> pair = new Pair<>(153, 2000L);
        System.out.print(pair + ", ");
        printResalt(sum(pair), pair);
    }

    private static <T extends Number> void printResult(T t) {
        System.out.printf("Result = (%s) %s\n", t.getClass().getSimpleName(), t);
    }

    private static <T extends Number, V extends Number, K extends Number> void printResalt(T t, Pair<V, K> p) {
        System.out.printf("Result = (%s) %s\n", t.getClass().getSimpleName(), t);
    }
}
