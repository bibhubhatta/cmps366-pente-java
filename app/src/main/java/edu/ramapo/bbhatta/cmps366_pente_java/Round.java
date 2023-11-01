package edu.ramapo.bbhatta.cmps366_pente_java;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Objects;


/**
 * A class representing a round of Pente.
 * It is immutable.
 */
public class Round {
    /**
     * The board of the round
     */
    protected Board board;

    /**
     * The stones available to play
     * Stones are removed as they are assigned to players.
     */
    protected ArrayList<Stone> availableStones;

    /**
     * The players in the round.
     * It is stored as a linked hash map to preserve the order of insertion, and to store the data of the player.
     * The key is the player, and the value is the data of the player.
     * The data contains the stone, the next player, and the number of captures.
     */
    protected LinkedHashMap<Player, PlayerData> players;

    protected Player currentPlayer;


    /**
     * Constructs a round of Pente.
     *
     * @param numRows the number of rows in the board
     * @param numCols the number of columns in the board
     */
    public Round(int numRows, int numCols) {
        this.board = new Board(numRows, numCols);
        this.availableStones = new ArrayList<>();
        this.availableStones.add(Stone.WHITE);
        this.availableStones.add(Stone.BLACK);
        this.players = new LinkedHashMap<>();
        this.currentPlayer = null;

    }

    /**
     * Constructs a round of Pente from a board.
     *
     * @param board the board to copy
     */

    public Round(Board board) {
        this.board = new Board(board);
        this.availableStones = new ArrayList<>();
        this.availableStones.add(Stone.WHITE);
        this.availableStones.add(Stone.BLACK);
        this.players = new LinkedHashMap<>();
        this.currentPlayer = null;
    }

    /**
     * Constructs a round of Pente from a round.
     *
     * @param round the round to copy
     */
    public Round(Round round) {
        this.board = new Board(round.board);
        this.availableStones = new ArrayList<>(round.availableStones);
        this.players = new LinkedHashMap<>(round.players);
        this.currentPlayer = round.currentPlayer;
    }


    /**
     * Gets the first player of the round.
     *
     * @return the first player of the round
     * null if there are no players
     */
    protected Player getFirstPlayer() {
        if (players.isEmpty()) {
            return null;
        }
        return players.entrySet().iterator().next().getKey();
    }

    /**
     * Gets the last player of the round.
     * The last player is the player that was added to the round last,
     * and therefore the player that plays last.
     *
     * @return the last player of the round
     */
    protected Player getLastPlayer() {
        if (players.isEmpty()) {
            return null;
        }

        Player lastPlayer = null;

        for (Player player : players.keySet()) {
            lastPlayer = player;
        }

        return lastPlayer;
    }

    /**
     * Adds a player to the round.
     * The player is assigned a stone, and the next player is set.
     * The order in which the players are added is the order in which they play.
     *
     * @param player the player to add
     * @return the round
     * @throws IllegalStateException if the player is already in the round
     * @throws IllegalStateException if there are no stones available
     * @throws IllegalStateException if the stone is not available
     */
    public Round addPlayer(Player player, Stone stone, int numCaptures) {

        if (players.containsKey(player)) {
            throw new IllegalStateException("Player already in round");
        }

        if (availableStones.isEmpty()) {
            throw new IllegalStateException("No stones available");
        } else if (!availableStones.contains(stone)) {
            throw new IllegalStateException("Stone not available");
        }


        Player nextPlayer = getFirstPlayer();
        Player previousPlayer = getLastPlayer();

        PlayerData playerData = new PlayerData(player, stone, nextPlayer, numCaptures);

        Round resultingRound = new Round(this);

        if (previousPlayer != null) {
            resultingRound.players.get(previousPlayer).nextPlayer = player;
        }

        resultingRound.availableStones.remove(stone);
        resultingRound.players.put(player, playerData);

        return resultingRound;
    }

    /**
     * Adds a player to the round.
     * The player is assigned a stone, and the next player is set.
     * The order in which the players are added is the order in which they play.
     *
     * @param player the player to add
     * @return the round
     * @throws IllegalStateException if the player is already in the round
     * @throws IllegalStateException if there are no stones available
     */
    public Round addPlayer(Player player) {
        return addPlayer(player, availableStones.get(0), 0);
    }


    /**
     * Gets the board of the round.
     *
     * @return the board of the round
     */
    public Board getBoard() {
        return new Board(board);
    }

    /**
     * Gets the current player.
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Sets the current player.
     *
     * @param currentPlayer the current player
     * @return the round
     * @throws IllegalArgumentException if the player has not been previously added to the round
     */
    public Round setCurrentPlayer(Player currentPlayer) {
        if (!players.containsKey(currentPlayer)) {
            throw new IllegalArgumentException("Player not added to round");
        }
        Round resultingRound = new Round(this);
        resultingRound.currentPlayer = currentPlayer;
        return resultingRound;
    }

    /**
     * Gets the current stone.
     *
     * @return the current stone
     */
    public Stone getCurrentStone() {
        return Objects.requireNonNull(players.get(currentPlayer)).stone;
    }

    /**
     * Gets the number of captures of the specified player.
     *
     * @param player the player to get the number of captures of
     * @return the number of captures of the specified player
     */
    public Integer getCaptures(Player player) {
        return Objects.requireNonNull(players.get(player)).numCaptures;
    }

    public Round makeMove(Position position) {

        validateMove(position);

        Round resultingRound = new Round(this);
        resultingRound.board = board.set(position, getCurrentStone());

        // Process captures
        ArrayList<Position> capturedPositions = (ArrayList<Position>) resultingRound.board.getCapturedPositions(position);
        resultingRound.players.get(currentPlayer).numCaptures += capturedPositions.size() / 2;
        for (Position capturedPosition : capturedPositions) {
            resultingRound.board = resultingRound.board.set(capturedPosition, Stone.EMPTY);
        }

        // Set the next player
        resultingRound.currentPlayer = players.get(currentPlayer).nextPlayer;

        return resultingRound;

    }

    /**
     * Checks if a move is valid.
     *
     * @param position the position to check
     * @throws IllegalStateException    if the player is not added to the round
     * @throws IllegalArgumentException if the position is not empty
     * @throws IllegalArgumentException if the first move is not in the center
     * @throws IllegalArgumentException if the second move of the first player is not at least 3 spaces away from the center
     */
    private void validateMove(Position position) {
        // Check if the first player is set
        if (getCurrentPlayer() == null) {
            throw new IllegalStateException("Player not added to round");
        }

        // Check if the position is empty
        ArrayList<Position> emptyPositions = (ArrayList<Position>) board.getEmptyPositions();
        if (!emptyPositions.contains(position)) {
            throw new IllegalArgumentException("Position is not empty");
        }

        // Check if it is the first move, and if it is, check if the position is the center
        if (board.getNoStones() == 0 && !position.equals(board.getCenter())) {
            throw new IllegalArgumentException("First move must be in the center");
        }

        // Check if it is the second turn of the first player, and if it is, check if the move is at least 3 spaces away from the center
        if (board.getNoStones() != 0 && getCurrentPlayer().equals(getFirstPlayer()) && Position.distance(board.getCenter(), position) < 3) {
            throw new IllegalArgumentException("Second move of first player must be at least 3 spaces away from the center");
        }
    }

    /**
     * Makes a move.
     *
     * @param move the move to make
     * @return the round
     * @throws IllegalArgumentException if the move is invalid
     */
    public Round makeMove(String move) {
        Position position = board.stringToPosition(move);
        return makeMove(position);
    }

    public Integer getScore(Player player) {
        return Objects.requireNonNull(players.get(player)).numCaptures;
    }

    /**
     * Stores the data of a player in a round.
     * It is immutable.
     */
    protected static class PlayerData {
        protected Player player;
        protected Stone stone;
        protected Player nextPlayer;
        protected int numCaptures;

        public PlayerData(Player player, Stone stone, Player nextPlayer, int numCaptures) {
            this.player = player;
            this.stone = stone;
            this.nextPlayer = nextPlayer;
            this.numCaptures = numCaptures;
        }

        public PlayerData(PlayerData playerData) {
            this.player = playerData.player;
            this.stone = playerData.stone;
            this.nextPlayer = playerData.nextPlayer;
            this.numCaptures = playerData.numCaptures;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof PlayerData)) return false;
            PlayerData playerData = (PlayerData) obj;
            return player.equals(playerData.player) && stone == playerData.stone && nextPlayer.equals(playerData.nextPlayer) && numCaptures == playerData.numCaptures;
        }

        @Override
        public int hashCode() {
            return Objects.hash(player, stone, nextPlayer, numCaptures);
        }

    }


}

