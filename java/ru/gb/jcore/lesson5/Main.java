package ru.gb.jcore;

import java.io.IOException;
import java.nio.file.Paths;

/*
1. Написать функцию, создающую резервную копию всех файлов в директории(
без поддиректорий) во вновь созданную папку ./backup

2. Предположить, что числа в исходном массиве из 9 элементов имеют диапазон[0, 3],
и представляют собой, например, состояния ячеек поля для игры в крестикинолики,
где 0 – это пустое поле, 1 – это поле с крестиком, 2 – это поле с ноликом,
3 – резервное значение. Такое предположение позволит хранить в одном числе типа int всё поле 3х3.
Записать в файл 9 значений так, чтобы они заняли три байта.
 */

public class Main {
    public static void main(String[] args) throws IOException {

        // Создание резервной копии всех файлов в директории
        String dataPath = "./src/main/resources";
        String backupPath = "./backup";

        System.out.println(CreateBackup.createBackup(dataPath, backupPath)
                ? "Creation of the copy is successful"
                : "Copy creation failed");

        // Запись состояния игры "Крестики-нолики" в виде 3 байт

        int[][] arr = { {1, 2, 0},
                        {3, 1, 2},
                        {3, 0, 0}};

        String file = Paths.get(dataPath).resolve("test3.txt").toString();
        System.out.println(StoreGame.saveGame(file, arr)
                ? "Data is saved"
                : "Data saving failed");

        // Восстановление состояния игры "Крестики-нолики" в целочисленный массив 3х3
       int[][] arr2 = StoreGame.restoreGame(file);
       if (arr2 != null){
           System.out.println("Restored array:");
           for (int[] ints : arr2) {
               for (int anInt : ints) {
                   System.out.print(anInt + "  ");
               }
               System.out.println();
           }
       } else System.out.println("null");
    }
}
