/**
 *
 */
package com.osgi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

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
    public static Set<Path> findFilesInSourceWithTask(CopierTaskModel ctm) {
        Path sourceFolder = ctm.getSourceFolder();
        Set<Path> pathStream = null;
        try {
            pathStream = Files.list(sourceFolder)
                    .filter(path -> Files.isRegularFile(path))
                    .filter(path -> path.getFileName().toString().matches(ctm.getMask()))
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            System.out.println("SourceFolder in not available");
        }
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
     * @param destinationFolder путь до папки назначения копирования
     */
    public static void makeCopy(Set<Path> files, Path destinationFolder) {
        files.forEach((Path file) -> {
            try {
                Path destinationPath = Paths.get(destinationFolder.toString(), file.getFileName().toString());
                Files.copy(file, destinationPath, REPLACE_EXISTING);
            } catch (IOException e) {
                System.out.println(file.toString() + " can't be copied");
            }
        });
    }

}
