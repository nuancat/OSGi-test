package com.osgi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Set;

/**
 * Загрузчик конфигураций для операций копирования, управляется посредством флага <code>isRunning</code>
 * и таймаутом между сканированиями файла с задачами <code>timeoutBetweenTasksLoading</code>. При инициализации
 * конструктора задается начальный файл где хранятся задания на копирование
 */
public class TaskLoader extends Thread {

    private static String CONFIG_SEPARATOR = " ";

    /**
     * Внутреннее хранилище заданий
     */
    private Queue<CopierTaskModel> tasks = new ArrayDeque<>();
    /**
     * Путь до файла с заданиями на копирование
     */
    private Path tasksFilePath = null;
    /**
     * Флаг обозначающий что логика копирования сканирует файл с заданиями и отправляет задания на исполнение
     */
    private boolean isRunning = false;
    /**
     * Таймаут между сканированиями файла с конфигурацией
     */
    private long timeoutBetweenTasksLoading = 500L;

    public TaskLoader(String tasksFilePath) {
        this.tasksFilePath = Paths.get(tasksFilePath);
    }

    @Override
    public void run() {
        while (isRunning && timeout()) {
            loadTask(tasksFilePath);
            executeTask();
        }
    }

    public void loadTask(Path path) {
        try {
            Files.lines(path)
                    .map(line -> line.split(CONFIG_SEPARATOR))
                    .forEach(elements -> tasks.add(new CopierTaskModel(elements[0], elements[1], elements[2])));
        } catch (IOException e) {
            System.out.println("Unable to open tasks file "+ path.getFileName());
        }
    }

    public void executeTask() {
        CopierTaskModel ctm = tasks.poll();
        Set<Path> paths = Copier.findFilesInSourceWithTask(ctm);
        Copier.makeCopy(paths, ctm.getDestinationFolder());
    }

    public Queue<CopierTaskModel> getTasks() {
        return tasks;
    }

    private boolean timeout() {
        try {
            Thread.sleep(timeoutBetweenTasksLoading);
        } catch (InterruptedException e) {
            System.out.println("timeout Error");
        }
        return true;
    }
}
