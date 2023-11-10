package edu.ramapo.bbhatta.cmps366_pente_java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static edu.ramapo.bbhatta.cmps366_pente_java.models.Player.COMPUTER;
import static edu.ramapo.bbhatta.cmps366_pente_java.models.Player.HUMAN;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.ramapo.bbhatta.cmps366_pente_java.models.Player;
import edu.ramapo.bbhatta.cmps366_pente_java.models.Position;
import edu.ramapo.bbhatta.cmps366_pente_java.models.Stone;
import edu.ramapo.bbhatta.cmps366_pente_java.models.Tournament;

public class DemoTest {

    private final File serial1File = new File("C:\\Users\\bibhu\\Documents\\Projects\\cmps366-pente-java\\app\\src\\test\\resources\\serials\\case1-prioritizing_capturing.txt");


    private void isSameGameState(GameState gameState, Tournament tournament) {
        assertEquals(gameState.humanTournamentScore, tournament.getScore(HUMAN));
        assertEquals(gameState.computerTournamentScore, tournament.getScore(COMPUTER));
        assertEquals(gameState.humanCaptures, tournament.getCaptures(HUMAN));
        assertEquals(gameState.computerCaptures, tournament.getCaptures(COMPUTER));
        assertEquals(gameState.humanRoundScore, tournament.getRoundScore(HUMAN));
        assertEquals(gameState.computerRoundScore, tournament.getRoundScore(COMPUTER));
        assertEquals(gameState.currentPlayer, tournament.getCurrentPlayer());
        assertEquals(gameState.currentStone, tournament.getCurrentStone());

        Position bestMove = tournament.getBestMove();
        String bestMoveString = tournament.getBoard().positionToString(bestMove);
        assertTrue(gameState.possibleBestMoves.contains(bestMoveString));
    }

    @Test
    public void testSerial1() throws IOException {
        ArrayList<GameState> gameStates = new ArrayList<>();
        final File serial = new File("C:\\Users\\bibhu\\Documents\\Projects\\cmps366-pente-java\\app\\src\\test\\resources\\serials\\case1-prioritizing_capturing.txt");

        ArrayList<String> possibleBestMoves = new ArrayList<>();
        possibleBestMoves.add("J12");

        gameStates.add(new GameState(0, 0, 0, 0, 0, 0, HUMAN, Stone.BLACK, possibleBestMoves));

        possibleBestMoves = new ArrayList<>();
        possibleBestMoves.add("C18");
        possibleBestMoves.add("C2");

        gameStates.add(new GameState(0, 0, 2, 0, 2, 0, COMPUTER, Stone.WHITE, possibleBestMoves));

        // The best moves are the same as before, because there were two before.
        gameStates.add(new GameState(0, 0, 2, 0, 2, 0, HUMAN, Stone.BLACK, possibleBestMoves));


        Tournament tournament = Tournament.fromFile(serial);
        for (GameState gameState : gameStates) {
            isSameGameState(gameState, tournament);
            tournament = tournament.makeMove(gameState.possibleBestMoves.get(0));
        }

    }

    @Test
    public void testInitialCaptures() {
        Tournament tournament = getPenteFromFile();
        assertEquals(0, (int) tournament.getCaptures(HUMAN));
        assertEquals(0, (int) tournament.getCaptures(COMPUTER));
    }

    @Test
    public void testInitialRoundScore() {
        Tournament tournament = getPenteFromFile();
        assertEquals(0, (int) tournament.getRoundScore(HUMAN));
        assertEquals(0, (int) tournament.getRoundScore(COMPUTER));
    }

    @Test
    public void testCurrentPlayerAndStone() {
        Tournament tournament = getPenteFromFile();
        assertEquals(HUMAN, tournament.getCurrentPlayer());
        assertEquals(Stone.BLACK, tournament.getCurrentStone());
    }

    @Test
    public void testMakeMove() {
        Tournament tournament = getPenteFromFile();
        tournament = tournament.makeMove("J12");
        assertEquals(2, (int) tournament.getNoCaptures(HUMAN));
        assertEquals(0, (int) tournament.getNoCaptures(COMPUTER));
    }

    private Tournament getPenteFromFile() {
        try {
            return Tournament.fromFile(serial1File);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    protected class GameState {
        public Integer humanTournamentScore;
        public Integer computerTournamentScore;
        public Integer humanCaptures;
        public Integer computerCaptures;
        public Integer humanRoundScore;
        public Integer computerRoundScore;
        public Player currentPlayer;
        public Stone currentStone;

        public List<String> possibleBestMoves;

        public GameState(int humanTournamentScore, int computerTournamentScore, int humanCaptures, int computerCaptures, int humanRoundScore, int computerRoundScore, Player currentPlayer, Stone currentStone, List<String> possibleBestMoves) {
            this.humanTournamentScore = humanTournamentScore;
            this.computerTournamentScore = computerTournamentScore;
            this.humanCaptures = humanCaptures;
            this.computerCaptures = computerCaptures;
            this.humanRoundScore = humanRoundScore;
            this.computerRoundScore = computerRoundScore;
            this.currentPlayer = currentPlayer;
            this.currentStone = currentStone;
            this.possibleBestMoves = possibleBestMoves;
        }


    }
}
