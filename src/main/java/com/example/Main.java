package com.example;

public class Main {
    public static void main(String[] args) {
        // WordAnalyzer test
        String filePath = "src/main/resources/sample.txt";
        try {
            WordAnalyzer analyzer = new WordAnalyzer(filePath);
            int distinctWordCount = analyzer.getDistinctWordCount();
            System.out.println("Number of distinct words: " + distinctWordCount);
            int frequency = analyzer.getWordFrequency("Java");
            System.out.println("Frequency of 'Java': " + frequency);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        // SparseMatrix test
        /**
         * Example of using SparseMatrix:
         * Given two sparse matrices A and B:
         * A=
         * [1, 0, 0]
         * [0, 0, 2]
         * [0, 0, 0]
         * B=
         * [0, 3, 0]
         * [0, 0, 0]
         * [4, 0, 0]
         * The sum A+B and product A*B would be:
         * A+B=
         * [1, 3, 0]
         * [0, 0, 2]
         * [4, 0, 0]
         * A*B=
         * [0, 3, 0]
         * [0, 0, 0]
         * [0, 0, 0]
         * The above example demonstrates the addition and multiplication of two sparse matrices.
         * The resulting matrices are printed in a readable format.
         */
        SparseMatrix matrix = new SparseMatrix(3, 3);
        matrix.set(0, 0, 1);
        matrix.set(1, 2, 2);
        SparseMatrix otherMatrix = new SparseMatrix(3, 3);
        otherMatrix.set(0, 1, 3);
        otherMatrix.set(2, 0, 4);
        SparseMatrix sumMatrix = matrix.add(otherMatrix);
        SparseMatrix productMatrix = matrix.multiply(otherMatrix);
        System.out.println("Matrix A:");
        matrix.print();
        System.out.println("Matrix B:");
        otherMatrix.print();
        System.out.println("A + B:");
        sumMatrix.print();
        System.out.println("A * B:");
        productMatrix.print();
    }
}
