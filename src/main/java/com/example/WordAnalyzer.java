package com.example;

import java.util.List;
import java.util.Map;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class WordAnalyzer {
    /**
     * List to hold the words loaded from the file
     */
    private List<String> words;
    /**
     * Map to hold the frequency of each word
     */
    private Map<String, Integer> wordFrequency;

    /**
     * Constructor that takes a file path and loads the words from the file.
     * @param filePath
     * @throws IOException
     */
    public WordAnalyzer(String filePath) throws IOException {
        loadFile(filePath);
        calculateWordFrequency();
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

    /**
     * Calculates the frequency of each word and populates the wordFrequency map.
     */
    private void calculateWordFrequency() {
        for (String word : words) {
            wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
        }
    }

    /**
     * Returns the distinct word count in the loaded file.
     * @return the number of distinct words
     */
    public int getDistinctWordCount() {
        return wordFrequency.size();
    }

}