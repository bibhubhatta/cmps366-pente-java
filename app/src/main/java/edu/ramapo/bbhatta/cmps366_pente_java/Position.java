package edu.ramapo.bbhatta.cmps366_pente_java;

import java.util.Objects;

/**
 * The Position class represents a position on the board.
 * It is represented by a row and a column.
 * <p>
 * The row and column are zero-based.
 * Bounds checking is not performed.
 * The position is immutable.
 */
public class Position {
    /**
     * The row of the position.
     */
    protected int _row;
    /**
     * The column of the position.
     */
    protected int _col;


    /**
     * Default constructor for the Position class.
     *
     * @param row The row of the position.
     * @param col The column of the position.
     */
    public Position(int row, int col) {
        this._row = row;
        this._col = col;
    }

    /**
     * Copy constructor for the Position class.
     *
     * @param position The position to copy.
     */
    public Position(Position position) {
        this._row = position.row();
        this._col = position.col();
    }

    /**
     * Gets the row of the position.
     *
     * @return The row of the position.
     */
    public int row() {
        return _row;
    }

    /**
     * Gets the column of the position.
     *
     * @return The column of the position.
     */
    public int col() {
        return _col;
    }

    /**
     * Checks if the position is equal to another position.
     * Two positions are equal if their rows and columns are equal.
     * This method overrides the equals method in the Object class.
     *
     * @param o The object to compare to.
     * @return True if the positions are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;

        Position position = (Position) o;

        if (_row != position._row) return false;
        return _col == position._col;
    }

    /**
     * Gets the hash code of the position.
     * This method overrides the hashCode method in the Object class.
     * This method is required because the equals method is overridden.
     *
     * @return The hash code of the position.
     */
    @Override
    public int hashCode() {
        return Objects.hash(_row, _col);
    }

    /**
     * Gets the position above this position.
     *
     * @return The position above this position.
     */
    public Position up() {
        return new Position(_row - 1, _col);
    }

    /**
     * Gets the position below this position.
     *
     * @return The position below this position.
     */
    public Position down() {
        return new Position(_row + 1, _col);
    }

    /**
     * Gets the position to the left of this position.
     *
     * @return The position to the left of this position.
     */
    public Position left() {
        return new Position(_row, _col - 1);
    }

    /**
     * Gets the position to the right of this position.
     *
     * @return The position to the right of this position.
     */
    public Position right() {
        return new Position(_row, _col + 1);
    }

    /**
     * Gets the position above and to the left of this position.
     *
     * @return The position above and to the left of this position.
     */
    public Position upLeft() {
        return new Position(_row - 1, _col - 1);
    }

    /**
     * Gets the position above and to the right of this position.
     *
     * @return The position above and to the right of this position.
     */
    public Position upRight() {
        return new Position(_row - 1, _col + 1);
    }

    /**
     * Gets the position below and to the left of this position.
     *
     * @return The position below and to the left of this position.
     */
    public Position downLeft() {
        return new Position(_row + 1, _col - 1);
    }

    /**
     * Gets the position below and to the right of this position.
     *
     * @return The position below and to the right of this position.
     */
    public Position downRight() {
        return new Position(_row + 1, _col + 1);
    }


    /**
     * Converts the position to a string.
     *
     * @param noRowsInBoard The number of rows in the board.
     *                      This is used to convert the row to a letter.
     *                      The letter is the (noRowsInBoard - row + 1) converted to a letter.
     *                      For example, if noRowsInBoard is 19 and the row is 0, then the letter is 'S'.
     *                      If noRowsInBoard is 19 and the row is 18, then the letter is 'A'.
     *                      The number of columns is always a number, so the total number of columns
     *                      is not required.
     * @return The position as a string.
     */
    public String toString(int noRowsInBoard) {
        char row = (char) ('A' + (noRowsInBoard - _row - 1));
        return String.format("%c%d", row, _col + 1);
    }
}
