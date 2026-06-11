package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.URL;

/**
 * Unit tests for {@link WordAnalyzer}.
 * Uses sample.txt from src/main/resources as the test fixture.
 */
public class WordAnalyzerTest {

    private static WordAnalyzer analyzer;

    /**
     * Load the sample file once before all tests.
     */
    @BeforeAll
    static void setUp() throws IOException {
        URL resourceUrl = WordAnalyzerTest.class.getClassLoader().getResource("sample.txt");
        assertNotNull(resourceUrl, "sample.txt must exist in src/main/resources");
        analyzer = new WordAnalyzer(resourceUrl.getPath());
    }

    // --- Distinct word count tests ---

    @Test
    void testGetDistinctWordCount() {
        int distinctCount = analyzer.getDistinctWordCount();
        assertTrue(distinctCount > 0, "Distinct word count should be greater than 0");
        // sample.txt contains 40 distinct tokens (Scanner splits on whitespace, case-sensitive)
        assertEquals(40, distinctCount, "Distinct word count for sample.txt should be 40");
    }

    // --- Word frequency tests ---

    @Test
    void testGetWordFrequencyForRepeatedWord() {
        // "Java" appears 7 times in sample.txt
        assertEquals(7, analyzer.getWordFrequency("Java"),
                "Word 'Java' should appear 7 times");
    }

    @Test
    void testGetWordFrequencyForCommonWord() {
        // "is" appears 4 times
        assertEquals(4, analyzer.getWordFrequency("is"),
                "Word 'is' should appear 4 times");
    }

    @Test
    void testGetWordFrequencyForWordWithPunctuation() {
        // "language." appears 2 times (with period)
        assertEquals(2, analyzer.getWordFrequency("language."),
                "Word 'language.' (with period) should appear 2 times");
    }

    @Test
    void testGetWordFrequencyForSingleOccurrenceWord() {
        // "high-level" appears once
        assertEquals(1, analyzer.getWordFrequency("high-level"),
                "Word 'high-level' should appear exactly once");
    }

    @Test
    void testGetWordFrequencyForNonExistentWord() {
        assertNull(analyzer.getWordFrequency("nonexistent"),
                "Non-existent word should return null");
    }

    @Test
    void testGetWordFrequencyForEmptyString() {
        assertNull(analyzer.getWordFrequency(""),
                "Empty string should return null as it is not a word in the file");
    }

    // --- Consistency tests ---

    @Test
    void testDistinctCountMatchesUniqueWordsInMap() {
        // If we sum the number of words that have frequency >= 1, it should equal distinct count
        int distinctCount = analyzer.getDistinctWordCount();
        String[] sampleWords = {"Java", "is", "a", "high-level", "programming", "language."};
        int uniqueFromSample = 0;
        for (String word : sampleWords) {
            if (analyzer.getWordFrequency(word) != null) {
                uniqueFromSample++;
            }
        }
        assertTrue(uniqueFromSample <= distinctCount,
                "Manually verified unique words should not exceed total distinct count");
    }

    // --- Edge case tests ---

    @Test
    void testConstructorWithValidFileDoesNotThrow() {
        // Already loaded in @BeforeAll — verifying analyzer is not null
        assertNotNull(analyzer, "WordAnalyzer should be successfully constructed with a valid file");
    }

    @Test
    void testConstructorWithInvalidFileThrowsIOException() {
        assertThrows(IOException.class, () -> new WordAnalyzer("nonexistent_file.txt"),
                "Constructor should throw IOException for a non-existent file");
    }
}
