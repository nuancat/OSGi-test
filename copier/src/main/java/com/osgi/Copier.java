/**
 *
 */
package com.osgi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author shamilbikchentaev
 */
public class Copier {

    /**
     * Поиск файлов в каталоге-источнике todo: подумать над реализацией
     * рекурсивного поиска файлов
     *
     * @param ctm
     * @return filepath для копирования
     * @throws Exception
     */
    public static Set<Path> findFilesInSourceWithTask(CopierTaskModel ctm) throws Exception {
        Path sourceFolder = ctm.getSourceFolder();
        isFolderAvailable(sourceFolder);
        Set<Path> pathStream = Files.list(sourceFolder)
                .filter(path -> Files.isRegularFile(path))
                .filter(path -> path.getFileName().toString().matches(ctm.getMask()))
                .collect(Collectors.toSet());
        return pathStream;
    }

    /**
     * Поиск папки нахождения для произведения копирования
     *
     * @param folderPath - путь
     * @return <code>true</code> - папка найдена. <code>false</code> - папка не
     *         найдена
     */
    private static boolean isFolderAvailable(Path folderPath) throws Exception {
        if (Files.notExists(folderPath) && !Files.isDirectory(folderPath)) {
            throw new Exception("Source Folder is not exsists");
        }
        return true;
    }

    /**
     * Собственно само копирование
     *
     * @param files файлы, который нужно скопировать
     * @param destination путь до папки назначения копирования
     */
    public static void makeCopy(Set<Path> files, Path destination) {
        files.stream().forEach(file -> {
            try {
                Files.copy(file, destination);
            } catch (IOException e) {
                System.out.println(file.toString() + " can't be copied");
            }
        });
    }

}
