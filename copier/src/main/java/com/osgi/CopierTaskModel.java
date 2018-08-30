/**
 *
 */
package com.osgi;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Класс модель задачи для копирования
 *
 * @author shamilbikchentaev
 */
public class CopierTaskModel {

    /**
     * Папка источник (откуда копируются файлы)
     */
    private Path sourceFolder;

    /**
     * Папка назначение (куда копируются файлы)
     */
    private Path destinationFolder;

    /**
     * Маска выбора файлов для копирования
     */
    private String mask;

    /**
     * Конструктор неповторяющегося задания на копирование
     *
     * @param mask              маска
     * @param sourceFolder      исходная папка
     * @param destinationFolder папка назначение
     */
    public CopierTaskModel(String sourceFolder, String destinationFolder, String mask) {
        this.mask = mask;
        this.sourceFolder = Paths.get(sourceFolder);
        this.destinationFolder = Paths.get(destinationFolder);
    }

    private long taskTimeoutValidator(long timeout) {
        if (timeout < 0L) {
            return 0;
        }
        return timeout;
    }

    /**
     * @return Маска названия файлов для копирования
     */
    public String getMask() {
        return mask;
    }

    /**
     * @return Исходная папка для копирования
     */
    public Path getSourceFolder() {
        return sourceFolder;
    }

    /**
     * @return Папка назначения для копирования
     */
    public Path getDestinationFolder() {
        return destinationFolder;
    }

    @Override
    public String toString() {
        return "CopierTaskModel{" +
                "sourceFolder=" + sourceFolder +
                ", destinationFolder=" + destinationFolder +
                ", mask='" + mask + '\'' +
                '}';
    }
}
