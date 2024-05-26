package ru.gb.jcore;

import java.io.*;

/**
 * Класс для сохранения игры "Крестики-нолики" в файл
 * метод saveGame(String file) сохраняет игровое поле 3х3
 * метод restoreGame(String file) - восстанавливает игровое поле 3х3
 */
public class StoreGame {
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 3;
    private static final int SIZE_X = 3;
    private static final int SIZE_Y = 3;

    /**
     * метод сохраняет игровое поле 3х3
     * @param file путь сохранения файла
     * @param arr массив состояний 3х3
     * @return результат сохранения
     */
    public static boolean saveGame(String file, int[][] arr) {
        try {
            saveToFile(file, convertToByteArrayOS(arr));
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());;
            return false;
        }
    }

    /**
     * восстанавливает игровое поле 3х3
     * @param file путь к файлу данных
     * @return массив состояния игрового поля
     */

    public static int[][] restoreGame(String file) {
        try {
            return convertFromByteArrayOS(restoreFromFile(file));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static ByteArrayOutputStream convertToByteArrayOS(int[][] arr) {
        if (arr.length != SIZE_X) throw new StringException(arr.length);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        for (int i = 0; i < arr.length; i++) {
            if (arr[i].length != SIZE_Y) throw new ColumnException(arr.length);
            int byteCode = 0;
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] < MIN_VALUE || arr[i][j] > MAX_VALUE)
                    throw new NumericException(arr[i][j]);
                byteCode = (byteCode << 2) + arr[i][j];
            }
            out.write(byteCode);
        }
        return out;
    }

    private static int[][] convertFromByteArrayOS(byte[] bytes) {
        int[][] arr = new int[3][3];
        if (bytes.length != SIZE_X) throw new StringException(bytes.length);
        for (int i = 0; i < SIZE_X; i++) {
            for (int j = SIZE_Y - 1; j >= 0; j--) {
                arr[i][j] = bytes[i] % 4;
                bytes[i] >>= 2;
            }
        }
        return arr;
    }

    private static void saveToFile(String file, ByteArrayOutputStream baos) {
        try(FileOutputStream outputStream = new FileOutputStream(file)) {
            baos.writeTo(outputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] restoreFromFile(String file) {
        try(FileInputStream inputStream = new FileInputStream(file)) {
            return inputStream.readAllBytes();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static class ColumnException extends RuntimeException {
        public ColumnException(int col) {
            super("Неверное число столбцов: " + col + ", ожидается: " + SIZE_Y);
        }
    }

    private static class StringException extends RuntimeException {
        public StringException(int col) {
            super("Неверное число столбцов: " + col + ", ожидается: " + SIZE_X);
        }
    }

    private static class NumericException extends RuntimeException {
        public NumericException(int value) {
            super("Неверное значение элемента массива: " + value +
                    ", ожидается диапазон: [" + MIN_VALUE + ", " + MAX_VALUE + "]");
        }
    }
}
