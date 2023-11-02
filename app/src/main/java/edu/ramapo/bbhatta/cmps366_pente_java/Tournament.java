package edu.ramapo.bbhatta.cmps366_pente_java;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Map;

public class Tournament {


    protected LinkedHashMap<Player, Integer> roster;
    protected Round round;

    public Tournament(int numRows, int numCols) {
        this.round = new Round(numRows, numCols);
        this.roster = new LinkedHashMap<>();
    }

    public Tournament(Tournament tournament) {
        this.round = tournament.getRound();
        this.roster = (LinkedHashMap<Player, Integer>) tournament.getRoster();
    }

    /**
     * Get a Pente object from a file.
     *
     * @param file The file to read from.
     * @return The Pente object.
     */
    public static Tournament fromFile(File file) throws IOException {
        // Open file and read it as string
        String serialString = new String(Files.readAllBytes(file.toPath()));

        // Create a serial object from the string
        Serial serial = new Serial(serialString);

        // Create a Pente object from the serial object
        Player human = new Player("Human");
        Player computer = new Player("Computer");

        int humanScore = serial.getHumanScore();
        int computerScore = serial.getComputerScore();

        Tournament tournament = new Tournament(19, 19);
        tournament.roster.put(human, humanScore);
        tournament.roster.put(computer, computerScore);

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
        tournament.round = round;

        return tournament;
    }

    /**
     * Gets all the players in the tournament.
     *
     * @return All the players in the tournament.
     */
    public Iterable<Player> getPlayers() {
        return roster.keySet();
    }

    /**
     * Adds a player to the tournament.
     * <p>
     * If the player is already in the tournament, then the player is not added.
     *
     * @param player The player to add.
     * @param score  The score of the player.
     * @return The resulting tournament state.
     */
    public Tournament addPlayer(Player player, int score) {
        Tournament resultingTournament = new Tournament(this);
        resultingTournament.roster.putIfAbsent(player, score);
        resultingTournament.round = round.addPlayer(player);
        return resultingTournament;
    }

    /**
     * Adds a player to the tournament.
     * <p>
     * The score of the player is set to 0.
     * If the player is already in the tournament, then the player is not added.
     *
     * @param player The player to add.
     * @return The resulting tournament state.
     */
    public Tournament addPlayer(Player player) {
        return addPlayer(player, 0);
    }

    /**
     * Gets the tournament score of a player.
     *
     * @return The tournament score of a player.
     * If the player is not in the tournament, null is returned.
     */
    public Integer getScore(Player player) {
        if (!roster.containsKey(player)) return null;
        return roster.get(player);
    }

    public Board getBoard() {
        return round.getBoard();
    }

    /**
     * Gets the round that is being played.
     *
     * @return A copy of the round that is being played.
     */
    public Round getRound() {
        return new Round(round);
    }

    /**
     * Gets the roster of the tournament.
     *
     * @return A copy of the roster of the tournament.
     */
    public Map<Player, Integer> getRoster() {
        return new LinkedHashMap<>(roster);
    }


    public Integer getCaptures(Player player) {
        return round.getCaptures(player);
    }

    public Integer getRoundScore(Player player) {
        return round.getScore(player);
    }

    public Integer getNoCaptures(Player player) {
        return round.getCaptures(player);
    }

    public Player getCurrentPlayer() {
        return round.getCurrentPlayer();
    }

    public Stone getCurrentStone() {
        return round.getCurrentStone();
    }

    public Tournament makeMove(Position position) {
        Tournament resultingTournament = new Tournament(this);
        Round resultingRound = round.makeMove(position);

        if (resultingRound.isOver()) {

            // Add the round score to the tournament score
            for (Player player : resultingRound.getPlayers()) {
                resultingTournament.roster.put(player, roster.get(player) + resultingRound.getScore(player));
            }
        }

        resultingTournament.round = resultingRound;

        return resultingTournament;
    }

    public Tournament makeMove(String move) {
        Position position = round.getBoard().stringToPosition(move);
        return makeMove(position);
    }

    public Position getBestMove() {
        return null;
    }


}
