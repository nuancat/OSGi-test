/**
 *
 */
package com.osgi;

import lombok.Data;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Класс модель задачи для копирования
 *
 * @author shamilbikchentaev
 */
public @Data
class CopierTaskModel {

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
}
