package edu.ramapo.bbhatta.cmps366_pente_java;

/**
 * Represents a stone on the board.
 * It is represented by a color.
 * <p>
 * The color is either black or white.
 * The stone is immutable.
 * </p>
 */
// Suppressing the warning for attribute names
// because it has an underscore in it.
@SuppressWarnings("java:S116")
public class Stone {

    /*
     * The black stone.
     */
    static final Stone BLACK = new Stone('B');
    /*
     * The white stone.
     */
    static final Stone WHITE = new Stone('W');

    /*
     * The empty stone.
     */
    static final Stone EMPTY = null;

    /**
     * The color of the stone.
     */
    protected char _color;

    /**
     * Default constructor for the Stone class.
     *
     * @param color The color of the stone.
     */
    public Stone(char color) {
        this._color = color;
    }

    /**
     * Copy constructor for the Stone class.
     *
     * @param stone The stone to copy.
     */
    public Stone(Stone stone) {
        this._color = stone.color();
    }

    /**
     * Gets the color of the stone.
     *
     * @return The color of the stone.
     */
    public char color() {
        return _color;
    }


    /**
     * Checks if two stones are equal.
     * Two stones are equal if they have the same color.
     *
     * @param obj The object to compare to.
     * @return True if the stones are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Stone)) return false;

        Stone stone = (Stone) obj;
        return stone.color() == this.color();
    }

    /**
     * Gets the hash code of the stone.
     * This method overrides the hashCode method in the Object class.
     * The hash code of the stone is the hash code of its color.
     * This is required because the equals method is overridden.
     *
     * @return The hash code of the stone.
     */
    @Override
    public int hashCode() {
        return this.color();
    }

    /**
     * Gets the string representation of the stone.
     * The string representation is the color of the stone.
     *
     * @return The string representation of the stone.
     */
    @Override
    public String toString() {
        return String.valueOf(this.color()).substring(0, 1);
    }
}
