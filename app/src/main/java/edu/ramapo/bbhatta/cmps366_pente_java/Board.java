package edu.ramapo.bbhatta.cmps366_pente_java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.UnaryOperator;

/**
 * The Board class represents the board.
 * It is represented by a 2D array of positions.
 */

public class Board {
    /*
     * The character representing an empty position.
     */
    static final Stone EMPTY = null;

    /*
     * The number of rows in the board.
     */
    protected int numRows;
    /*
     * The number of columns in the board.
     */
    protected int numCols;

    /*
     * The 2D array of characters.
     */
    protected Stone[][] boardArray;

    /**
     * Default constructor for the Board class.
     *
     * @param numRows The number of rows of the board.
     * @param numCols The number of columns of the board.
     */
    public Board(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.boardArray = new Stone[numRows][numCols];

        // Initialize the board with empty cells.
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                this.boardArray[row][col] = EMPTY;
            }
        }
    }

    /**
     * Copy constructor for the Board class.
     *
     * @param board The board to copy.
     */
    public Board(Board board) {
        this.numRows = board.getNoRows();
        this.numCols = board.getNoCols();
        this.boardArray = new Stone[this.numRows][this.numCols];

        // Copy the board.
        for (int row = 0; row < this.numRows; row++) {
            for (int col = 0; col < this.numCols; col++) {
                this.boardArray[row][col] = board.get(row, col);
            }
        }
    }

    /**
     * Gets the number of rows of the board.
     *
     * @return The number of rows of the board.
     */
    public int getNoRows() {
        return this.numRows;
    }

    /**
     * Gets the number of columns of the board.
     *
     * @return The number of columns of the board.
     */
    public int getNoCols() {
        return this.numCols;
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

        return this.boardArray[row][col];
    }

    /**
     * Gets the character at the given position.
     *
     * @param position The position.
     */
    public Stone get(Position position) {
        return this.get(position.getRow(), position.getCol());
    }

    /**
     * Sets the stone at the given position.
     *
     * @param row   The row of the position.
     *              The row must be in the range [0, numRows).
     * @param col   The column of the position.
     *              The column must be in the range [0, numCols).
     * @param stone The stone to set.
     * @return The resulting board.
     */
    public Board set(int row, int col, Stone stone) {
        Board resultingBoard = new Board(this);
        resultingBoard.boardArray[row][col] = stone;
        return resultingBoard;
    }

    /**
     * Sets the stone at the given position.
     *
     * @param position The position.
     * @param stone    The stone to set.
     * @return The resulting board.
     */
    public Board set(Position position, Stone stone) {
        return this.set(position.getRow(), position.getCol(), stone);
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
        return this.isEmpty(position.getRow(), position.getCol());
    }

    /**
     * Checks if the given position is in the board.
     *
     * @param row The row of the position.
     * @param col The column of the position.
     * @return True if the given position is in the board.
     */
    public boolean isInBoard(int row, int col) {
        return row >= 0 && row < this.numRows && col >= 0 && col < this.numCols;
    }

    /**
     * Checks if the given position is in the board.
     *
     * @param position The position.
     * @return True if the given position is in the board.
     */
    public boolean isInBoard(Position position) {
        return this.isInBoard(position.getRow(), position.getCol());
    }

    /**
     * Checks if the board is full.
     *
     * @return True if the board is full.
     */
    public boolean isFull() {
        for (int row = 0; row < this.numRows; row++) {
            for (int col = 0; col < this.numCols; col++) {
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
     *                 The letter is the (col + 1) converted to a letter.
     *                 For example, if the col is 0, then the letter is 'A'.
     *                 The number is the (noRowsInBoard - row) converted to a string.
     * @return The position as a string.
     */
    public String positionToString(Position position) {
        char col = (char) ('A' + position.getCol());
        return String.format(Locale.US, "%c%d", col, this.numRows - position.getRow());
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

            char colChar = position.charAt(0);
            int col = colChar - 'A';

            int row = numRows - Integer.parseInt(position.substring(1));

            Position pos = new Position(row, col);

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
        if (row < 0 || row >= this.numRows) {
            return new Stone[0];
        }

        return this.boardArray[row];
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

        return this.getRow(position.getRow());
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
        if (col < 0 || col >= this.numCols) {
            return new Stone[0];
        }

        Stone[] column = new Stone[this.numRows];
        for (int row = 0; row < this.numRows; row++) {
            column[row] = this.boardArray[row][col];
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

        return this.getColumn(position.getCol());
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
        Position downLeft = Position.downLeft(diagonalStart);

        while (isInBoard(downLeft)) {
            diagonalStart = downLeft;
            downLeft = Position.downLeft(diagonalStart);
        }

        ArrayList<Stone> diagonal = new ArrayList<>();

        Position current = diagonalStart;
        while (isInBoard(current)) {
            diagonal.add(get(current));
            current = Position.upRight(current);
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
        Position upLeft = Position.upLeft(diagonalStart);
        while (isInBoard(upLeft)) {
            diagonalStart = upLeft;
            upLeft = Position.upLeft(diagonalStart);
        }

        ArrayList<Stone> diagonal = new ArrayList<>();

        Position current = diagonalStart;
        while (isInBoard(current)) {
            diagonal.add(get(current));
            current = Position.downRight(current);
        }

        return diagonal.toArray(new Stone[0]);
    }

    /**
     * Gets all the starting positions for the positive diagonals.
     *
     * @return All the starting positions for the positive diagonals.
     */
    public Position[] getAllPositiveDiagonalStarts() {
        Position[] starts = new Position[getNoRows() + getNoCols() - 1];

        int index = 0;
        for (int row = 0; row < getNoRows(); row++) {
            starts[index] = new Position(row, 0);
            index++;
        }

        for (int col = 1; col < getNoCols(); col++) {
            starts[index] = new Position(numRows - 1, col);
            index++;
        }

        return starts;
    }

    /**
     * Gets all the starting positions for the negative diagonals.
     *
     * @return All the starting positions for the negative diagonals.
     */
    public Position[] getAllNegativeDiagonalStarts() {
        Position[] starts = new Position[getNoRows() + getNoCols() - 1];

        int index = 0;
        for (int col = 0; col < getNoCols(); col++) {
            starts[index] = new Position(0, col);
            index++;
        }

        for (int row = 1; row < getNoRows(); row++) {
            starts[index] = new Position(row, numCols - 1);
            index++;
        }

        return starts;
    }

    /**
     * Gets all the sequences of the board.
     * The sequences are in the following order:
     * 1. All the rows.
     * 2. All the columns.
     * 3. All the positive diagonals.
     * 4. All the negative diagonals.
     *
     * @return All the sequences of the board.
     */
    public Stone[][] getAllBoardSequences() {
        Stone[][] sequences = new Stone[(getNoRows() + getNoCols()) * 3 - 2][];
        int index = 0;

        // Get all the rows.
        for (int row = 0; row < getNoRows(); row++) {
            sequences[index] = getRow(row);
            index++;
        }

        // Get all the columns.
        for (int col = 0; col < getNoCols(); col++) {
            sequences[index] = getColumn(col);
            index++;
        }

        // Get all the positive diagonals.
        Position[] positiveDiagonalStarts = getAllPositiveDiagonalStarts();
        for (Position start : positiveDiagonalStarts) {
            sequences[index] = getPositiveDiagonal(start);
            index++;
        }

        // Get all the negative diagonals.
        Position[] negativeDiagonalStarts = getAllNegativeDiagonalStarts();
        for (Position start : negativeDiagonalStarts) {
            sequences[index] = getNegativeDiagonal(start);
            index++;
        }

        return sequences;

    }

    /**
     * Converts a board sequence to a stone sequence.
     * For example, if the board sequence is [Stone.BLACK, Stone.WHITE, Stone.BLACK, Stone.Black, Stone.EMPTY],
     * then the stone sequence is [[Stone.BLACK], [Stone.WHITE], [Stone.BLACK, Stone.BLACK]].
     *
     * @param boardSequence The board sequence to convert.
     * @return The stone sequence.
     */
    public Stone[][] convertBoardSequenceToStoneSequence(Stone[] boardSequence) {
        ArrayList<Stone[]> stoneSequences = new ArrayList<>();

        ArrayList<Stone> stoneSequence = new ArrayList<>();

        int index = 0;

        while (index < boardSequence.length) {
            if (boardSequence[index] == EMPTY) {
                index++;
                continue;
            }

            stoneSequence.add(boardSequence[index]);
            while (index < boardSequence.length - 1 && boardSequence[index] == boardSequence[index + 1]) {
                index++;
                stoneSequence.add(boardSequence[index]);
            }

            stoneSequences.add(stoneSequence.toArray(new Stone[0]));
            stoneSequence.clear();
            index++;
        }

        return stoneSequences.toArray(new Stone[0][]);

    }


    /**
     * Gets all the sequences of the board as stone sequences.
     * The sequences are in the following order:
     * 1. All the rows.
     * 2. All the columns.
     * 3. All the positive diagonals.
     * 4. All the negative diagonals.
     *
     * @return All the sequences of the board as stone sequences.
     */
    public Stone[][] getAllStoneSequences() {
        Stone[][] boardSequences = getAllBoardSequences();
        ArrayList<Stone[]> stoneSequences = new ArrayList<>();

        for (Stone[] boardSequence : boardSequences) {
            Stone[][] sequences = convertBoardSequenceToStoneSequence(boardSequence);
            stoneSequences.addAll(Arrays.asList(sequences));
        }

        return stoneSequences.toArray(new Stone[0][]);
    }


    /**
     * Gets all the sequences of the board that contain the given stone.
     *
     * @param stone The stone to get the sequences of.
     * @return All the sequences of the board that contain the given stone.
     */
    public Stone[][] getAllStoneSequences(Stone stone) {
        Stone[][] stoneSequences = getAllStoneSequences();
        ArrayList<Stone[]> stoneSequencesOfStone = new ArrayList<>();

        for (Stone[] stoneSequence : stoneSequences) {
            if (stoneSequence[0] == stone) {
                stoneSequencesOfStone.add(stoneSequence);
            }
        }

        return stoneSequencesOfStone.toArray(new Stone[0][]);
    }


    public int getNoStoneSequences(Stone stone, int length) {
        int noSequences = 0;

        Stone[][] stoneSequences = getAllStoneSequences();

        for (Stone[] stoneSequence : stoneSequences) {
            if (stoneSequence[0] != stone) {
                continue;
            }
            if (stoneSequence.length == length) {
                noSequences++;
            }
        }

        return noSequences;
    }

    /**
     * Gets all the empty positions of the board.
     *
     * @return All the empty positions of the board.
     */
    public List<Position> getEmptyPositions() {
        ArrayList<Position> emptyPositions = new ArrayList<>();
        for (int row = 0; row < this.numRows; row++) {
            for (int col = 0; col < this.numCols; col++) {
                if (this.isEmpty(row, col)) {
                    emptyPositions.add(new Position(row, col));
                }
            }
        }
        return emptyPositions;
    }

    /**
     * Gets the number of stones on the board.
     *
     * @return The number of stones on the board.
     */
    public int getNoStones() {
        int noStones = 0;
        for (int row = 0; row < this.numRows; row++) {
            for (int col = 0; col < this.numCols; col++) {
                if (!this.isEmpty(row, col)) {
                    noStones++;
                }
            }
        }
        return noStones;
    }


    /**
     * Gets the number of stones of the given stone on the board.
     *
     * @param stone The stone to get the number of.
     * @return The number of stones of the given stone on the board.
     */
    public int getNoStones(Stone stone) {
        int noStones = 0;
        for (int row = 0; row < this.numRows; row++) {
            for (int col = 0; col < this.numCols; col++) {
                if (this.get(row, col) == stone) {
                    noStones++;
                }
            }
        }
        return noStones;
    }


    /**
     * Gets the center position of the board.
     *
     * @return The center position of the board.
     */
    public Position getCenter() {
        return new Position(this.numRows / 2, this.numCols / 2);
    }

    /**
     * Gets the captured positions in the given direction.
     * The direction is a static function that takes a position and returns the next position in the direction.
     * For example, the up method takes a position and returns the position above it.
     *
     * @param position        The position to get the captured positions of.
     * @param directionMethod The direction method.
     *                        The direction method takes a position and returns the next position in the direction.
     * @return The captured positions in the given direction method.
     */
    protected List<Position> getCapturedPositions(Position position, UnaryOperator<Position> directionMethod) {
        ArrayList<Position> capturedPositions = new ArrayList<>();

        // Check if the position is empty.
        if (isEmpty(position)) {
            return capturedPositions;
        }

        // Check if the position is in the board.
        if (!isInBoard(position)) {
            return capturedPositions;
        }

        Position oneAway = directionMethod.apply(position);
        Position twoAway = directionMethod.apply(oneAway);
        Position threeAway = directionMethod.apply(twoAway);

        Stone stone = get(position);
        Stone oneAwayStone = get(oneAway);
        Stone twoAwayStone = get(twoAway);
        Stone threeAwayStone = get(threeAway);

        if (oneAwayStone != null
                && stone != oneAwayStone
                && oneAwayStone == twoAwayStone
                && stone == threeAwayStone
        ) {
            capturedPositions.add(oneAway);
            capturedPositions.add(twoAway);
        }

        return capturedPositions;
    }


    public List<Position> getCapturedPositions(Position position) {
        ArrayList<Position> capturedPositions = new ArrayList<>();

        // Check if the position is empty.
        if (isEmpty(position)) {
            return capturedPositions;
        }

        // Check if the position is in the board.
        if (!isInBoard(position)) {
            return capturedPositions;
        }

        capturedPositions.addAll(getCapturedPositions(position, Position::up));
        capturedPositions.addAll(getCapturedPositions(position, Position::down));
        capturedPositions.addAll(getCapturedPositions(position, Position::left));
        capturedPositions.addAll(getCapturedPositions(position, Position::right));
        capturedPositions.addAll(getCapturedPositions(position, Position::upLeft));
        capturedPositions.addAll(getCapturedPositions(position, Position::upRight));
        capturedPositions.addAll(getCapturedPositions(position, Position::downLeft));
        capturedPositions.addAll(getCapturedPositions(position, Position::downRight));

        return capturedPositions;

    }

    /**
     * Gets all the positions in the board.
     *
     * @return All the positions in the board.
     */
    public List<Position> getAllPositions() {
        ArrayList<Position> allPositions = new ArrayList<>();

        for (int row = 0; row < this.numRows; row++) {
            for (int col = 0; col < this.numCols; col++) {
                allPositions.add(new Position(row, col));
            }
        }

        return allPositions;
    }

    public String displayString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int row = 0; row < this.numRows; row++) {
            // Add the getRow number
            stringBuilder.append(String.format(Locale.US, "%2d ", this.numRows - row));
            for (int col = 0; col < this.numCols; col++) {
                Stone stone = this.get(row, col);
                if (stone == null) {
                    stringBuilder.append("O");
                } else {
                    stringBuilder.append(stone);
                }
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }

        // Add the column letters
        stringBuilder.append("   ");
        for (int col = 0; col < this.numCols; col++) {
            stringBuilder.append(String.format(Locale.US, "%c ", 'A' + col));
        }
        stringBuilder.append("\n");


        return stringBuilder.toString();
    }
}
