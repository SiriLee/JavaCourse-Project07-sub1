package com.example;

import java.io.FileInputStream;
import java.util.List;
import java.io.IOException;
import java.util.Scanner;

public class WordAnalyzer {
    private List<String> words;

    /**
     * Constructor that takes a file path and loads the words from the file.
     * @param filePath
     * @throws IOException
     */
    public WordAnalyzer(String filePath) throws IOException {
        loadFile(filePath);
    }



    /**
     * Loads the file and populates the list of words.
     * @param filePath
     * @throws IOException
     */
    private void loadFile(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        Scanner scanner = new Scanner(fis);
        while (scanner.hasNext()) {
            words.add(scanner.next());
        }
        scanner.close();
        fis.close();
    }

}