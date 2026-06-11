package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link SparseMatrix}.
 * Covers construction, set/get, copy, add, multiply, and edge cases.
 */
public class SparseMatrixTest {

    private SparseMatrix matrix;

    @BeforeEach
    void setUp() {
        matrix = new SparseMatrix(3, 3);
    }

    // --- Constructor tests ---

    @Test
    void testDefaultConstructor() {
        SparseMatrix empty = new SparseMatrix();
        // Default 0x0 matrix: all positions are out of bounds
        assertThrows(IndexOutOfBoundsException.class, () -> empty.get(0, 0),
                "Getting any element from default 0x0 matrix should throw IndexOutOfBoundsException");
        assertThrows(IndexOutOfBoundsException.class, () -> empty.set(0, 0, 1),
                "Setting any element in default 0x0 matrix should throw IndexOutOfBoundsException");
    }

    @Test
    void testParameterizedConstructor() {
        SparseMatrix m = new SparseMatrix(4, 5);
        // Should not throw and return 0 for any valid position
        assertEquals(0, m.get(0, 0), "New matrix should return 0 for any position");
        assertEquals(0, m.get(3, 4), "New matrix should return 0 for corner position");
    }

    // --- Set and Get tests ---

    @Test
    void testSetAndGetNonZeroValue() {
        matrix.set(1, 1, 42);
        assertEquals(42, matrix.get(1, 1), "get should return the value that was set");
    }

    @Test
    void testGetUnsetPositionReturnsZero() {
        assertEquals(0, matrix.get(0, 0), "Unset position should return 0");
        assertEquals(0, matrix.get(2, 2), "Unset position should return 0");
    }

    @Test
    void testSetZeroRemovesEntry() {
        matrix.set(1, 1, 99);
        assertEquals(99, matrix.get(1, 1));

        // Setting to zero should remove the entry
        matrix.set(1, 1, 0);
        assertEquals(0, matrix.get(1, 1), "After setting to 0, get should return 0");
    }

    @Test
    void testSetMultipleValues() {
        matrix.set(0, 0, 10);
        matrix.set(0, 1, 20);
        matrix.set(1, 1, 30);
        matrix.set(2, 2, 40);

        assertEquals(10, matrix.get(0, 0));
        assertEquals(20, matrix.get(0, 1));
        assertEquals(30, matrix.get(1, 1));
        assertEquals(40, matrix.get(2, 2));
        assertEquals(0, matrix.get(0, 2), "Unset position (0,2) should still return 0");
    }

    // --- Bounds checking tests ---

    @Test
    void testSetRowOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> matrix.set(-1, 0, 5),
                "Negative row index should throw IndexOutOfBoundsException");
        assertThrows(IndexOutOfBoundsException.class, () -> matrix.set(3, 0, 5),
                "Row index equal to number of rows should throw IndexOutOfBoundsException");
    }

    @Test
    void testSetColOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> matrix.set(0, -1, 5),
                "Negative column index should throw IndexOutOfBoundsException");
        assertThrows(IndexOutOfBoundsException.class, () -> matrix.set(0, 3, 5),
                "Column index equal to number of columns should throw IndexOutOfBoundsException");
    }

    @Test
    void testGetRowOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> matrix.get(-1, 0),
                "Negative row index should throw IndexOutOfBoundsException");
        assertThrows(IndexOutOfBoundsException.class, () -> matrix.get(3, 0),
                "Row index equal to number of rows should throw IndexOutOfBoundsException");
    }

    @Test
    void testGetColOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> matrix.get(0, -1),
                "Negative column index should throw IndexOutOfBoundsException");
        assertThrows(IndexOutOfBoundsException.class, () -> matrix.get(0, 3),
                "Column index equal to number of columns should throw IndexOutOfBoundsException");
    }

    // --- Copy constructor tests ---

    @Test
    void testCopyConstructorCreatesIdenticalMatrix() {
        matrix.set(0, 0, 5);
        matrix.set(1, 2, 10);

        SparseMatrix copy = new SparseMatrix(matrix);

        assertEquals(5, copy.get(0, 0), "Copied matrix should have the same values");
        assertEquals(10, copy.get(1, 2), "Copied matrix should have the same values");
        assertEquals(0, copy.get(2, 2), "Unset positions should be 0 in copy");
    }

    @Test
    void testCopyConstructorCreatesIndependentMatrix() {
        matrix.set(0, 0, 5);
        SparseMatrix copy = new SparseMatrix(matrix);

        // Modifying the original should not affect the copy
        matrix.set(0, 0, 99);
        assertEquals(5, copy.get(0, 0),
                "Copy should remain unchanged when original is modified");

        // Modifying the copy should not affect the original
        copy.set(0, 0, 77);
        assertEquals(99, matrix.get(0, 0),
                "Original should remain unchanged when copy is modified");
    }

    @Test
    void testCopyConstructorPreservesDimensions() {
        SparseMatrix m = new SparseMatrix(4, 5);
        SparseMatrix copy = new SparseMatrix(m);
        // Accessing the last valid position should work without throwing
        assertDoesNotThrow(() -> copy.get(3, 4),
                "Copy should have the same dimensions as original");
        assertThrows(IndexOutOfBoundsException.class, () -> copy.get(4, 0),
                "Copy should enforce same row bounds as original");
        assertThrows(IndexOutOfBoundsException.class, () -> copy.get(0, 5),
                "Copy should enforce same column bounds as original");
    }

    // --- Addition tests ---

    @Test
    void testAddTwoMatrices() {
        matrix.set(0, 0, 1);
        matrix.set(1, 1, 2);

        SparseMatrix other = new SparseMatrix(3, 3);
        other.set(0, 0, 3);
        other.set(0, 1, 4);
        other.set(1, 1, 5);

        SparseMatrix result = matrix.add(other);

        assertEquals(4, result.get(0, 0), "1 + 3 = 4");
        assertEquals(4, result.get(0, 1), "0 + 4 = 4");
        assertEquals(7, result.get(1, 1), "2 + 5 = 7");
        assertEquals(0, result.get(2, 2), "0 + 0 = 0");

        // Original matrices should not be modified
        assertEquals(1, matrix.get(0, 0), "Original matrix should not be modified by add");
        assertEquals(3, other.get(0, 0), "Other matrix should not be modified by add");
    }

    @Test
    void testAddMatricesWithDimensionMismatch() {
        SparseMatrix differentRows = new SparseMatrix(2, 3);
        assertThrows(IllegalArgumentException.class, () -> matrix.add(differentRows),
                "Adding matrices with different row counts should throw IllegalArgumentException");

        SparseMatrix differentCols = new SparseMatrix(3, 2);
        assertThrows(IllegalArgumentException.class, () -> matrix.add(differentCols),
                "Adding matrices with different column counts should throw IllegalArgumentException");
    }

    @Test
    void testAddMatricesWithOverlappingEntries() {
        matrix.set(0, 0, 5);
        matrix.set(1, 1, -3);

        SparseMatrix other = new SparseMatrix(3, 3);
        other.set(0, 0, -5);  // 5 + (-5) = 0, should be removed
        other.set(1, 1, 3);   // -3 + 3 = 0, should be removed

        SparseMatrix result = matrix.add(other);

        assertEquals(0, result.get(0, 0), "5 + (-5) = 0, entry should be removed");
        assertEquals(0, result.get(1, 1), "-3 + 3 = 0, entry should be removed");
    }

    // --- Multiplication tests ---

    @Test
    void testMultiplyTwoMatrices() {
        // Matrix A (2x3):
        // [1, 2, 3]
        // [4, 5, 6]
        SparseMatrix a = new SparseMatrix(2, 3);
        a.set(0, 0, 1); a.set(0, 1, 2); a.set(0, 2, 3);
        a.set(1, 0, 4); a.set(1, 1, 5); a.set(1, 2, 6);

        // Matrix B (3x2):
        // [7,  8 ]
        // [9,  10]
        // [11, 12]
        SparseMatrix b = new SparseMatrix(3, 2);
        b.set(0, 0, 7);  b.set(0, 1, 8);
        b.set(1, 0, 9);  b.set(1, 1, 10);
        b.set(2, 0, 11); b.set(2, 1, 12);

        // Expected result (2x2):
        // [1*7+2*9+3*11, 1*8+2*10+3*12] = [58, 64]
        // [4*7+5*9+6*11, 4*8+5*10+6*12] = [139, 154]
        SparseMatrix result = a.multiply(b);

        assertEquals(58, result.get(0, 0));
        assertEquals(64, result.get(0, 1));
        assertEquals(139, result.get(1, 0));
        assertEquals(154, result.get(1, 1));

        // Original matrices should not be modified
        assertEquals(1, a.get(0, 0), "Original matrix A should not be modified by multiply");
        assertEquals(7, b.get(0, 0), "Original matrix B should not be modified by multiply");
    }

    @Test
    void testMultiplyMatricesWithDimensionMismatch() {
        SparseMatrix a = new SparseMatrix(2, 3);
        SparseMatrix b = new SparseMatrix(2, 2); // 3 != 2

        assertThrows(IllegalArgumentException.class, () -> a.multiply(b),
                "Multiplying matrices with mismatched inner dimensions should throw IllegalArgumentException");
    }

    @Test
    void testMultiplyWithIdentityLikeSparseMatrix() {
        // 3x3 matrix with 1s on the diagonal
        SparseMatrix identity = new SparseMatrix(3, 3);
        identity.set(0, 0, 1);
        identity.set(1, 1, 1);
        identity.set(2, 2, 1);

        matrix.set(0, 0, 10);
        matrix.set(0, 1, 20);
        matrix.set(1, 1, 30);

        SparseMatrix result = matrix.multiply(identity);

        // Multiplying by identity should yield the original
        assertEquals(10, result.get(0, 0));
        assertEquals(20, result.get(0, 1));
        assertEquals(30, result.get(1, 1));
        assertEquals(0, result.get(2, 2));
    }

    @Test
    void testMultiplySparseMatricesWithZeros() {
        SparseMatrix a = new SparseMatrix(2, 2);
        a.set(0, 0, 5);

        SparseMatrix b = new SparseMatrix(2, 2);
        b.set(1, 1, 3);

        SparseMatrix result = a.multiply(b);

        // Only non-zero contribution: a[0][0] * b[0][?]
        // b[0][*] are all 0, so result should be all zeros
        assertEquals(0, result.get(0, 0));
        assertEquals(0, result.get(0, 1));
        assertEquals(0, result.get(1, 0));
        assertEquals(0, result.get(1, 1));
    }

    // --- Overwrite tests ---

    @Test
    void testSetOverwritesExistingValue() {
        matrix.set(1, 1, 10);
        assertEquals(10, matrix.get(1, 1));

        matrix.set(1, 1, 20);
        assertEquals(20, matrix.get(1, 1), "set should overwrite the previous value");
    }
}
