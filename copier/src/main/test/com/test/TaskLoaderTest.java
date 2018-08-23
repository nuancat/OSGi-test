package com.test;

import com.osgi.CopierTaskModel;
import com.osgi.TaskLoader;
import org.junit.Ignore;
import org.junit.Test;

public class TaskLoaderTest {
    /**
     * Кастомный тест
     */
    @Ignore
    @Test
    public void loadTask() throws Exception {
        TaskLoader taskLoader = new TaskLoader();
        taskLoader.loadTask();
        taskLoader.getTasks().forEach(System.out::println);
    }

    /**
     * Кастомный тест
     */
    @Ignore
    @Test
    public void executeTask() {
        CopierTaskModel copierTaskModel = new CopierTaskModel("/Users/shamilbikchentaev/Documents",
                "/Users/shamilbikchentaev/Desktop/", ".+.txt");
        TaskLoader tl = new TaskLoader();
        tl.getTasks().add(copierTaskModel);
        tl.executeTask();
    }

}