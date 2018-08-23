package com.test;

import com.osgi.Copier;
import com.osgi.CopierTaskModel;
import org.junit.Ignore;
import org.junit.Test;

import java.nio.file.Path;
import java.util.Set;



public class CopierTest {
    /**
     * Кастомный тест
     * @throws Exception
     */
    @Ignore
    @Test
    public void findFilesInSourceWithTask() throws Exception {
        String[] masks = {".+", ".+bz2", ".+txt"};
        for (String mask : masks) {
            System.out.println("\nTEST findFilesInSourceFolder With Mask  - "+ mask+" \n\n");
            CopierTaskModel copierTaskModel = new CopierTaskModel(mask,
                    "/Users/shamilbikchentaev/Documents",
                    "/Users/shamilbikchentaev/OneDrive");
            Set<Path> filesInSourceWithTask = Copier.findFilesInSourceWithTask(copierTaskModel);
            filesInSourceWithTask.stream().forEach(System.out::println);
        }
    }


}