package edu.ramapo.bbhatta.cmps366_pente_java;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedHashMap;

public class Pente {


    protected LinkedHashMap<Player, Integer> roster;
    protected Round round;

    public Pente(int numRows, int numCols) {
        this.round = new Round(numRows, numCols);
        this.roster = new LinkedHashMap<>();
    }

    public Pente(Pente pente) {
        this.round = pente.round;
        this.roster = pente.roster;
    }

    /**
     * Get a Pente object from a file.
     *
     * @param file The file to read from.
     * @return The Pente object.
     */
    public static Pente fromFile(File file) throws IOException {
        // Open file and read it as string
        String serialString = new String(Files.readAllBytes(file.toPath()));

        // Create a serial object from the string
        Serial serial = new Serial(serialString);

        // Create a Pente object from the serial object
        Player human = new Player("Human");
        Player computer = new Player("Computer");

        int humanScore = serial.getHumanScore();
        int computerScore = serial.getComputerScore();

        Pente pente = new Pente(19, 19);
        pente.roster.put(human, humanScore);
        pente.roster.put(computer, computerScore);

        // Create a round object from the serial object

        int humanCaptures = serial.getHumanCaptures();
        int computerCaptures = serial.getComputerCaptures();

        Stone humanStone = serial.getHumanStone();
        Stone computerStone = serial.getComputerStone();

        Player currentPlayer = serial.getCurrentPlayer();

        Board board = serial.getBoard();

        Round round = new Round(board);
        // Add the player that plays white first because white always plays first
        if (humanStone == Stone.WHITE) {
            round = round.addPlayer(human, humanStone, humanCaptures);
            round = round.addPlayer(computer, computerStone, computerCaptures);
        } else {
            round = round.addPlayer(computer, computerStone, computerCaptures);
            round = round.addPlayer(human, humanStone, humanCaptures);
        }
        // Set the current player
        round = round.setCurrentPlayer(currentPlayer);
        // Create a Pente object from the round object
        pente.round = round;

        return pente;
    }

    /**
     * Adds a player to the tournament.
     * <p>
     * If the player is already in the tournament, then the player is not added.
     *
     * @param player The player to add.
     * @return The resulting game state.
     */
    public Pente addPlayer(Player player, int score) {
        Pente resultingTournament = new Pente(this);
        resultingTournament.roster.putIfAbsent(player, score);
        return resultingTournament;
    }

    public Board getBoard() {
        return round.getBoard();
    }

    public int getCaptures(Player player) {
        return round.getCaptures(player);
    }

    public int getRoundScore(Player player) {
        return round.getScore(player);
    }

    public int getNoCaptures(Player player) {
        return round.getCaptures(player);
    }

    public Player getCurrentPlayer() {
        return round.getCurrentPlayer();
    }

    public Stone getCurrentStone() {
        return round.getCurrentStone();
    }

    public Pente makeMove(Position position) {
        Pente resultingTournament = new Pente(this);
        resultingTournament.round = round.makeMove(position);
        return resultingTournament;
    }

    public Pente makeMove(String move) {
        Position position = round.getBoard().stringToPosition(move);
        return makeMove(position);
    }

    public Position getBestMove() {
        return null;
    }


}
