package com.example;

import java.util.Map;
import java.util.HashMap;

public class SparseMatrix {
    /**
     * The number of rows in the matrix
     */
    private int rows;
    /**
     * The number of columns in the matrix
     */
    private int cols;
    /**
     * The map to store the sparse matrix data
     */
    private Map<Integer, Map<Integer, Integer>> matrix;

    /**
     * Default constructor to initialize an empty sparse matrix.
     */
    public SparseMatrix() {
        this(0, 0);
    }

    /**
     * Constructor to initialize the sparse matrix with the given number of rows and columns.
     * @param rows the number of rows in the matrix
     * @param cols the number of columns in the matrix
     */
    public SparseMatrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.matrix = new HashMap<>();
    }

    /**
     * Copy constructor to create a new sparse matrix that is a copy of the given matrix.
     * @param other the sparse matrix to copy
     */
    public SparseMatrix(SparseMatrix other) {
        this.rows = other.rows;
        this.cols = other.cols;
        this.matrix = new HashMap<>();
        for (Map.Entry<Integer, Map<Integer, Integer>> rowEntry : other.matrix.entrySet()) {
            int row = rowEntry.getKey();
            for (Map.Entry<Integer, Integer> colEntry : rowEntry.getValue().entrySet()) {
                int col = colEntry.getKey();
                int value = colEntry.getValue();
                this.set(row, col, value);
            }
        }
    }

    /**
     * Sets the value at the specified row and column in the sparse matrix.
     * @param row
     * @param col
     * @param value
     */
    public void set(int row, int col, int value) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IndexOutOfBoundsException("Row or column index out of bounds");
        }
        if (value != 0) {
            // If the value is non-zero, we need to store it in the matrix
            if (!matrix.containsKey(row)) {
                matrix.put(row, new HashMap<>());
            }
            matrix.get(row).put(col, value);
        } else {
            // If the value is zero, we need to remove it from the matrix if it exists
            if (matrix.containsKey(row)) {
                matrix.get(row).remove(col);
                if (matrix.get(row).isEmpty()) {
                    matrix.remove(row);
                }
            }
        }
    }

    /**
     * Gets the value at the specified row and column in the sparse matrix.
     * @param row
     * @param col
     * @return
     */
    public int get(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IndexOutOfBoundsException("Row or column index out of bounds");
        }
        if (matrix.containsKey(row) && matrix.get(row).containsKey(col)) {
            return matrix.get(row).get(col);
        }
        return 0; // Return 0 for any position that is not explicitly set
    }

    /**
     * Adds the given sparse matrix to this sparse matrix.
     * @param other the sparse matrix to add
     * @return the resulting sparse matrix
     */
    public SparseMatrix add(SparseMatrix other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("Matrices must have the same dimensions for addition");
        }
        SparseMatrix result = new SparseMatrix(this);
        // Add values from the second matrix
        for (Map.Entry<Integer, Map<Integer, Integer>> rowEntry : other.matrix.entrySet()) {
            int row = rowEntry.getKey();
            for (Map.Entry<Integer, Integer> colEntry : rowEntry.getValue().entrySet()) {
                int col = colEntry.getKey();
                int value = colEntry.getValue();
                result.set(row, col, result.get(row, col) + value);
            }
        }
        return result;
    }

    /**
     * Multiplies this sparse matrix with the given sparse matrix.
     * @param other the sparse matrix to multiply with
     * @return the resulting sparse matrix
     */
    public SparseMatrix multiply(SparseMatrix other) {
        if (this.cols != other.rows) {
            throw new IllegalArgumentException("Number of columns of the first matrix must equal the number of rows of the second matrix for multiplication");
        }
        SparseMatrix result = new SparseMatrix(this.rows, other.cols);
        for (Map.Entry<Integer, Map<Integer, Integer>> rowEntry : this.matrix.entrySet()) {
            int row = rowEntry.getKey();
            for (Map.Entry<Integer, Integer> colEntry : rowEntry.getValue().entrySet()) {
                int col = colEntry.getKey();
                int value = colEntry.getValue();
                if (other.matrix.containsKey(col)) {
                    for (Map.Entry<Integer, Integer> otherColEntry : other.matrix.get(col).entrySet()) {
                        int otherCol = otherColEntry.getKey();
                        int otherValue = otherColEntry.getValue();
                        result.set(row, otherCol, result.get(row, otherCol) + value * otherValue);
                    }
                }
            }
        }
        return result;
    }
}
