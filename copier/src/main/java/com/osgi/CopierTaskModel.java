/**
 *
 */
package com.osgi;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Класс модель задачи для копирования по вводным данным : копирование должно
 * осуществляться с определенным промежутком времени, копирование может
 * осуществляться по маске, при копировании известны источник и целевая папка
 *
 * @author shamilbikchentaev
 */
public class CopierTaskModel {

    /**
     * Маска выбора файлов для копирования
     */
    private String mask;

    /**
     * Папка источник (откуда копируются файлы)
     */
    private Path sourceFolder;

    /**
     * Папка назначение (куда копируются файлы)
     */
    private Path destinationFolder;

    /**
     * Флаг повторяющегося задания
     */
    private boolean repeatableTask;

    /**
     * Время до повтора следующего копирования
     */
    private long taskTimeoutInSeconds;

    /**
     * Конструктор неповторяющегося задания на копирование
     *
     * @param mask              маска
     * @param sourceFolder      исходная папка
     * @param destinationFolder папка назначение
     * @throws IOException
     */
    public CopierTaskModel(String mask, String sourceFolder, String destinationFolder) throws IOException {
        this(mask, sourceFolder, destinationFolder, 0);
    }

    /**
     * Конструктор повторяющегося задания на копирование
     *
     * @param mask                 маска
     * @param sourceFolder         исходная папка
     * @param destinationFolder    папка назначение
     * @param taskTimeoutInSeconds таймаут
     * @throws IOException ошибка инициализации
     */
    private CopierTaskModel(String mask, String sourceFolder, String destinationFolder, long taskTimeoutInSeconds)
            throws IOException {
        this.mask = mask;
        this.sourceFolder = Paths.get(sourceFolder);
        this.destinationFolder = Paths.get(destinationFolder);
        this.repeatableTask = taskTimeoutInSeconds != 0L ? true : false;
        this.taskTimeoutInSeconds = taskTimeoutValidator(taskTimeoutInSeconds);
    }

    private long taskTimeoutValidator(long timeout) throws IOException {
        if (timeout < 0L) {
            throw new IOException("Время повторного действия меньше нуля");
        }
        return timeout;
    }

    /**
     * Конвертация маски поиска файлов в regexp <br>
     * <code>*</code> -- множество символов <br>
     * <code>?</code> --  один символ <br>
     * todo: тестирование метода
     *
     * @param mask - маска формата поиска
     * @return регулярное выражение
     */
    @Deprecated
    public String maskToRegexConverter(String mask) {
        String regex = mask.replaceAll(".", "\\."). //  Защищаем точку в маске
                replaceAll("\\?", "."); // Меняем одиночный символ в соответствии с regex
       //         replaceAll("\\*", "+"); // Меняем множественный символ в соответствии с regex
        return regex;
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

    /**
     * @return Повторяющеся задание
     */
    public boolean isRepeatableTask() {
        return repeatableTask;
    }

    /**
     * @return промежуток времени до следующего копирования
     */
    public long getTaskTimeoutInSeconds() {
        return taskTimeoutInSeconds;
    }

}
