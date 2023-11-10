package edu.ramapo.bbhatta.cmps366_pente_java.models;

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
    protected int row;
    /**
     * The column of the position.
     */
    protected int col;


    /**
     * Default constructor for the Position class.
     *
     * @param row The row of the position.
     * @param col The column of the position.
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Copy constructor for the Position class.
     *
     * @param position The position to copy.
     */
    public Position(Position position) {
        this.row = position.row;
        this.col = position.col;
    }

    /**
     * Gets the position above this position.
     *
     * @param position The position to get the position above.
     * @return The position above this position.
     */
    public static Position up(Position position) {
        return new Position(position.row - 1, position.col);
    }

    /**
     * Gets the position below this position.
     *
     * @param position The position to get the position below.
     * @return The position below this position.
     */
    public static Position down(Position position) {
        return new Position(position.row + 1, position.col);
    }

    /**
     * Gets the position to the left of this position.
     *
     * @param position The position to get the position to the left of.
     * @return The position to the left of this position.
     */
    public static Position left(Position position) {
        return new Position(position.row, position.col - 1);
    }

    /**
     * Gets the position to the right of this position.
     *
     * @param position The position to get the position to the right of.
     * @return The position to the right of this position.
     */
    public static Position right(Position position) {
        return new Position(position.row, position.col + 1);
    }

    /**
     * Gets the position above and to the left of this position.
     *
     * @param position The position to get the position above and to the left of.
     * @return The position above and to the left of this position.
     */
    public static Position upLeft(Position position) {
        return new Position(position.row - 1, position.col - 1);
    }

    /**
     * Gets the position above and to the right of this position.
     *
     * @param position The position to get the position above and to the right of.
     * @return The position above and to the right of this position.
     */
    public static Position upRight(Position position) {
        return new Position(position.row - 1, position.col + 1);
    }

    /**
     * Gets the position below and to the left of this position.
     *
     * @param position The position to get the position below and to the left of.
     * @return The position below and to the left of this position.
     */
    public static Position downLeft(Position position) {
        return new Position(position.row + 1, position.col - 1);
    }

    /**
     * Gets the position below and to the right of this position.
     *
     * @param position The position to get the position below and to the right of.
     * @return The position below and to the right of this position.
     */
    public static Position downRight(Position position) {
        return new Position(position.row + 1, position.col + 1);
    }

    /**
     * Gets the distance between this position and another position.
     * The distance is the maximum of the absolute values of the differences
     * between the rows and the columns.
     *
     * @param position1 The first position.
     * @param position2 The second position.
     * @return The distance between this position and another position.
     */
    public static int distance(Position position1, Position position2) {
        return Math.max(Math.abs(position1.row - position2.row), Math.abs(position1.col - position2.col));
    }

    /**
     * Gets the row of the position.
     *
     * @return The row of the position.
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column of the position.
     *
     * @return The column of the position.
     */
    public int getCol() {
        return col;
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

        if (row != position.row) return false;
        return col == position.col;
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
        return Objects.hash(row, col);
    }
}
