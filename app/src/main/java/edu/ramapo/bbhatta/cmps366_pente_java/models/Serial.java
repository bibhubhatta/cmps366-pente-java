package edu.ramapo.bbhatta.cmps366_pente_java.models;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class representing a serial string.
 */
public class Serial {
    /**
     * The lines of the serial string.
     */
    private final ArrayList<String> lines;

    /**
     * Constructs a Serial object from a serial string.
     *
     * @param serialString the serial string to parse
     */
    public Serial(String serialString) {
        lines = new ArrayList<>();
        String line;
        try (Scanner scanner = new Scanner(serialString)) {
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                lines.add(line);
            }
        }
    }

    /**
     * Gets the number in the specified line.
     *
     * @param lineNumber the line number to get the number from
     * @return the number in the specified line
     */
    public int getNumberInLine(int lineNumber) {
        String line = lines.get(lineNumber);
        String numberStr = line.substring(line.indexOf(":") + 1).trim();
        return Integer.parseInt(numberStr);
    }

    /**
     * Gets the human score.
     *
     * @return the human score
     */
    public int getHumanScore() {
        return getNumberInLine(23);
    }

    /**
     * Gets the computer score.
     *
     * @return the computer score
     */
    public int getComputerScore() {
        return getNumberInLine(27);
    }

    /**
     * Gets the number of captured pairs by the human.
     *
     * @return the number of captured pairs by the human
     */
    public int getHumanCaptures() {
        return getNumberInLine(22);
    }

    /**
     * Gets the number of captured pairs by the computer.
     *
     * @return the number of captured pairs by the computer
     */
    public int getComputerCaptures() {
        return getNumberInLine(26);
    }

    /**
     * Gets the stone of the human player.
     *
     * @return the stone of the human player
     */
    public Stone getHumanStone() {
        String line = lines.get(29);
        String playerStone = line.substring(line.indexOf(":") + 1).trim();
        String[] playerStoneArr = playerStone.split(" - ");
        String player = playerStoneArr[0];
        String stoneString = playerStoneArr[1];
        char stoneChar = stoneString.charAt(0);

        if (player.equals("Human")) {
            return getStone(stoneChar);
        } else if (player.equals("Computer")) {
            return otherStone(getStone(stoneChar));
        } else {
            throw new IllegalStateException("Invalid player: " + player);
        }
    }

    protected Stone getStone(char stone) {
        if (stone == 'W') {
            return Stone.WHITE;
        } else if (stone == 'B') {
            return Stone.BLACK;
        } else if (stone == 'O') {
            return Stone.EMPTY;
        } else {
            throw new IllegalStateException("Invalid stone: " + stone);
        }
    }

    /**
     * Gets the stone of the computer player.
     *
     * @return the stone of the computer player
     */
    public Stone getComputerStone() {
        Stone humanStone = getHumanStone();
        return otherStone(humanStone);
    }

    /**
     * Returns the opposite stone color.
     *
     * @param stone the stone color to get the opposite of
     * @return the opposite stone color
     */
    private Stone otherStone(Stone stone) {
        return stone == Stone.WHITE ? Stone.BLACK : Stone.WHITE;
    }


    /**
     * Gets the next turn.
     *
     * @return the next turn
     */
    public Player getCurrentPlayer() {

        String line = lines.get(29);
        String playerStone = line.substring(line.indexOf(":") + 1).trim();
        String[] playerStoneArr = playerStone.split(" - ");
        String player = playerStoneArr[0];


        if (player.equals("Human")) {
            return Player.HUMAN;
        } else if (player.equals("Computer")) {
            return Player.COMPUTER;
        } else {
            throw new IllegalStateException("Invalid player: " + player);
        }

    }

    public Board getBoard() {
        Board board = new Board(19, 19);
        String line;

        for (int row = 0; row < 19; row++) {
            line = lines.get(row + 1);
            for (int col = 0; col < 19; col++) {
                char stoneChar = line.charAt(col);
                Stone stone = getStone(stoneChar);
                board = board.set(new Position(row, col), stone);
            }
        }

        return board;

    }
}