package com.osgi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Set;
import java.util.stream.Collectors;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Класс помощник копирователь файлов
 *
 * @author shamilbikchentaev
 */
public class Copier {

    /**
     * Логгер
     */
    final static Logger logger = LoggerFactory.getLogger(TaskLoader.class);

    /**
     * Поиск файлов в каталоге-источнике todo: подумать над реализацией
     *
     * @param ctm модель задания
     * @return filepath для копирования
     */
    public static Set<Path> findFilesInSourceWithTask(CopierTaskModel ctm) throws Exception {
        logger.debug(ctm.toString());
        Path sourceFolder = ctm.getSourceFolder();
        Set<Path> filePaths = null;
        try {
            filePaths = Files.list(sourceFolder)
                    .filter(path -> Files.isRegularFile(path))
                    .filter(path -> path.getFileName().toString().contains(ctm.getMask()))
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new Exception(MessageFormat.format("Source folder ''{0}'' is in not available", sourceFolder));
        }
        logger.info("{} -- Files found by mask in source filder", filePaths.size());
        return filePaths;
    }

    /**
     * Собственно само копирование
     *
     * @param files             файлы, который нужно скопировать
     * @param destinationFolder путь до папки назначения копирования
     */
    public static void makeCopy(Set<Path> files, Path destinationFolder) throws Exception {
        if (isFolderAvailable(destinationFolder)) {
            if (files != null) {
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
    }

    /**
     * Поиск папки нахождения для произведения копирования
     *
     * @param folderPath путь
     * @return <code>true</code> - папка найдена. <code>false</code> - папка не
     * найдена
     */
    private static boolean isFolderAvailable(Path folderPath) throws Exception {
        if (Files.notExists(folderPath) && !Files.isDirectory(folderPath)) {
            throw new Exception("Folder is not exsists");
        }
        return true;
    }
}
