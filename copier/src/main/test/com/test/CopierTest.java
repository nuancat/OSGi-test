package com.test;

import com.osgi.Copier;
import com.osgi.CopierTaskModel;
import org.junit.Test;

import java.nio.file.Path;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by shamilbikchentaev on 22.08.18.
 */
public class CopierTest {
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