package edu.ramapo.bbhatta.cmps366_pente_java;

import java.util.ArrayList;
import java.util.Locale;

/**
 * The Board class represents the board.
 * It is represented by a 2D array of positions.
 */

// Suppressing the warning for attribute names
// because it has an underscore in it.
@SuppressWarnings("java:S116")
public class Board {
    /*
     * The character representing an empty position.
     */
    static final Stone EMPTY = null;

    /*
     * The number of rows in the board.
     */
    protected int _numRows;
    /*
     * The number of columns in the board.
     */
    protected int _numCols;

    /*
     * The 2D array of characters.
     */
    protected Stone[][] _board;

    /**
     * Default constructor for the Board class.
     *
     * @param numRows The number of rows of the board.
     * @param numCols The number of columns of the board.
     */
    public Board(int numRows, int numCols) {
        this._numRows = numRows;
        this._numCols = numCols;
        this._board = new Stone[numRows][numCols];

        // Initialize the board with empty cells.
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                this._board[row][col] = EMPTY;
            }
        }
    }

    /**
     * Copy constructor for the Board class.
     *
     * @param board The board to copy.
     */
    public Board(Board board) {
        this._numRows = board.numRows();
        this._numCols = board.numCols();
        this._board = new Stone[this._numRows][this._numCols];

        // Copy the board.
        for (int row = 0; row < this._numRows; row++) {
            for (int col = 0; col < this._numCols; col++) {
                this._board[row][col] = board.get(row, col);
            }
        }
    }

    /**
     * Gets the number of rows of the board.
     *
     * @return The number of rows of the board.
     */
    public int numRows() {
        return this._numRows;
    }

    /**
     * Gets the number of columns of the board.
     *
     * @return The number of columns of the board.
     */
    public int numCols() {
        return this._numCols;
    }

    /**
     * Gets the stone at the given position.
     * The position must be in the board.
     * If the position is not in the board, null is returned.
     *
     * @param row The row of the position.
     *            The row must be in the range [0, numRows).
     * @param col The column of the position.
     *            The column must be in the range [0, numCols).
     * @return The stone at the given position.
     */
    public Stone get(int row, int col) {
        // return null if the position is not in the board.
        if (!this.isInBoard(row, col)) {
            return null;
        }

        return this._board[row][col];
    }

    /**
     * Gets the character at the given position.
     *
     * @param position The position.
     */
    public Stone get(Position position) {
        return this.get(position.row(), position.col());
    }

    /**
     * Sets the stone at the given position.
     *
     * @param row   The row of the position.
     *              The row must be in the range [0, numRows).
     * @param col   The column of the position.
     *              The column must be in the range [0, numCols).
     * @param stone The stone to set.
     */
    public void set(int row, int col, Stone stone) {
        this._board[row][col] = stone;
    }

    /**
     * Sets the stone at the given position.
     *
     * @param position The position.
     * @param stone    The stone to set.
     */
    public void set(Position position, Stone stone) {
        this.set(position.row(), position.col(), stone);
    }

    /**
     * Checks if the given position is empty.
     *
     * @param row The row of the position.
     *            The row must be in the range [0, numRows).
     * @param col The column of the position.
     *            The column must be in the range [0, numCols).
     * @return True if the given position is empty.
     */
    public boolean isEmpty(int row, int col) {
        return this.get(row, col) == Board.EMPTY;
    }

    /**
     * Checks if the given position is empty.
     *
     * @param position The position.
     * @return True if the given position is empty.
     */
    public boolean isEmpty(Position position) {
        return this.isEmpty(position.row(), position.col());
    }

    /**
     * Checks if the given position is in the board.
     *
     * @param row The row of the position.
     * @param col The column of the position.
     * @return True if the given position is in the board.
     */
    public boolean isInBoard(int row, int col) {
        return row >= 0 && row < this._numRows && col >= 0 && col < this._numCols;
    }

    /**
     * Checks if the given position is in the board.
     *
     * @param position The position.
     * @return True if the given position is in the board.
     */
    public boolean isInBoard(Position position) {
        return this.isInBoard(position.row(), position.col());
    }

    /**
     * Checks if the board is full.
     *
     * @return True if the board is full.
     */
    public boolean isFull() {
        for (int row = 0; row < this._numRows; row++) {
            for (int col = 0; col < this._numCols; col++) {
                if (this.isEmpty(row, col)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Converts the position to a string.
     *
     * @param position The position to convert.
     *                 The string is in the format of "A1", "B2", etc.
     *                 The letter is the (noRowsInBoard - row + 1) converted to a letter.
     *                 For example, if noRowsInBoard is 19 and the row is 0, then the letter is 'S'.
     *                 If noRowsInBoard is 19 and the row is 18, then the letter is 'A'.
     * @return The position as a string.
     */
    public String positionToString(Position position) {
        char row = (char) ('A' + (_numRows - position.row() - 1));
        return String.format(Locale.US, "%c%d", row, position.col() + 1);
    }


    /**
     * Converts the string to a position.
     * The string must be in the format of "A1", "B2", etc.
     * If the string is not in the correct format, then null is returned.
     * If the resulting position is not in the board, then null is returned.
     *
     * @param position The string to convert.
     * @return The position.
     */
    public Position stringToPosition(String position) {
        // Inside try/catch block to catch format exceptions.
        try {
            // Convert the string to uppercase and remove all whitespace.
            position = position.toUpperCase().replaceAll("\\s+", "");

            char row = position.charAt(0);
            int rowInt = _numRows - (row - 'A') - 1;

            int col = Integer.parseInt(position.substring(1)) - 1;

            Position pos = new Position(rowInt, col);

            if (isInBoard(pos)) {
                return pos;
            }

        } catch (Exception e) {
            return null;
        }

        return null;
    }

    /**
     * Gets the row of the board.
     *
     * @param row The row to get.
     *            The row must be in the range [0, numRows).
     *            If the row is not in the range, then an empty array is returned.
     * @return The row of the board.
     */
    public Stone[] getRow(int row) {
        if (row < 0 || row >= this._numRows) {
            return new Stone[0];
        }

        return this._board[row];
    }

    /**
     * Gets the row of the board
     *
     * @param position The position to get the row of.
     *                 The position must be in the board.
     *                 If the position is not in the board or is null, then an empty array is returned.
     */
    public Stone[] getRow(Position position) {
        if (position == null || !this.isInBoard(position)) {
            return new Stone[0];
        }

        return this.getRow(position.row());
    }

    /**
     * Gets the column of the board.
     *
     * @param col The column to get.
     *            The column must be in the range [0, numCols).
     *            If the column is not in the range, then an empty array is returned.
     * @return The column of the board.
     */
    public Stone[] getColumn(int col) {
        if (col < 0 || col >= this._numCols) {
            return new Stone[0];
        }

        Stone[] column = new Stone[this._numRows];
        for (int row = 0; row < this._numRows; row++) {
            column[row] = this._board[row][col];
        }

        return column;
    }

    /**
     * Gets the column of the board.
     *
     * @param position The position to get the column of.
     *                 The position must be in the board.
     *                 If the position is not in the board or is null, then an empty array is returned.
     */
    public Stone[] getColumn(Position position) {
        if (position == null || !this.isInBoard(position)) {
            return new Stone[0];
        }

        return this.getColumn(position.col());
    }


    /**
     * Gets the positive diagonal that goes through the given position.
     *
     * @param position The position to get the positive diagonal of.
     *                 The position must be in the board.
     *                 If the position is not in the board or is null, then an empty array is returned.
     */
    public Stone[] getPositiveDiagonal(Position position) {
        if (position == null || !this.isInBoard(position)) {
            return new Stone[0];
        }

        Position diagonalStart = position;
        Position downLeft = diagonalStart.downLeft();

        while (isInBoard(downLeft)) {
            diagonalStart = downLeft;
            downLeft = diagonalStart.downLeft();
        }

        ArrayList<Stone> diagonal = new ArrayList<>();

        Position current = diagonalStart;
        while (isInBoard(current)) {
            diagonal.add(get(current));
            current = current.upRight();
        }

        return diagonal.toArray(new Stone[0]);
    }

    /**
     * Gets the negative diagonal that goes through the given position.
     *
     * @param position The position to get the negative diagonal of.
     *                 The position must be in the board.
     *                 If the position is not in the board or is null, then an empty array is returned.
     */
    public Stone[] getNegativeDiagonal(Position position) {
        if (position == null || !this.isInBoard(position)) {
            return new Stone[0];
        }

        Position diagonalStart = position;
        Position upLeft = diagonalStart.upLeft();
        while (isInBoard(upLeft)) {
            diagonalStart = upLeft;
            upLeft = diagonalStart.upLeft();
        }

        ArrayList<Stone> diagonal = new ArrayList<>();

        Position current = diagonalStart;
        while (isInBoard(current)) {
            diagonal.add(get(current));
            current = current.downRight();
        }

        return diagonal.toArray(new Stone[0]);
    }

}
