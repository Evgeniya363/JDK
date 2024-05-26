package ru.gb.jcore;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Класс для создания резервной копии всех файлов в директории
 * (без поддиректорий) во вновь созданную папку ./backup, данную
 * функцию выполняет метод createBackup(String source, String target)
 **/
public class CreateBackup {

    /**
     * Создает резервную копию файлов
     * @param source папка с файлами
     * @param target папка для копирования
     * @return результат копирования
     */
    public static boolean createBackup(String source, String target) {
        Path path = Paths.get(source);
        Path copyPath = Paths.get(target);

        try {
            // Создаем директорию для резервных копий
            Files.createDirectories(copyPath);

            // Формируем список файлов
            List<Path> files = getFilesList(path);

            // Копирование файлов, согласно списка
            copyFilesList(files, copyPath);

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private static void copyFilesList(List<Path> files, Path copyPath) {

        files.forEach(f -> {
            try {
                Files.copy(f, copyPath.resolve(f.getFileName()), REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException("Ошибка копирования файла " + f);
            }
        });
    }



    /**
     * Возвращает список файлов, исключая файлы поддиректорий
     * @param path путь к файлам
     * @return список файлов
     */
    private static List<Path> getFilesList(Path path){
        List<Path> filesList = null;
        try(Stream<Path> files = Files.list(path)) {
            filesList = files.filter(Files::isRegularFile).collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Отсутствует директория " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filesList;
    }

}