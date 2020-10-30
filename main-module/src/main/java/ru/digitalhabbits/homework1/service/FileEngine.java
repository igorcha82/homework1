package ru.digitalhabbits.homework1.service;

import javax.annotation.Nonnull;
import java.io.*;

import static java.util.Arrays.stream;

public class FileEngine {
    private static final String RESULT_FILE_PATTERN = "results-%s.txt";
    private static final String RESULT_DIR = "results";
    private static final String RESULT_EXT = "txt";

    public boolean writeToFile(@Nonnull String text, @Nonnull String pluginName) {

        String fileName = System.getProperty("user.dir") + "/" + RESULT_DIR + "/" + String.format(RESULT_FILE_PATTERN, pluginName);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            bufferedWriter.write(text);
            bufferedWriter.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    public void cleanResultDir() {
        final String currentDir = System.getProperty("user.dir");
        final File resultDir = new File(currentDir + "/" + RESULT_DIR);
        resultDir.mkdir();
        stream(resultDir.list((dir, name) -> name.endsWith(RESULT_EXT)))
                .forEach(fileName -> new File(resultDir + "/" + fileName).delete());
    }
}
