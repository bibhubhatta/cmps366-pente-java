package edu.ramapo.bbhatta.cmps366_pente_java;

import java.util.ArrayList;

/**
 * Strategy class represents a strategy for a game.
 * It can be used to get the best move for a player.
 */
public class Strategy {

    Round round;

    public Strategy(Round round) {
        this.round = round;
    }

    public Strategy(Strategy strategy) {
        this.round = strategy.round;
    }

    /**
     * Get the best move
     *
     * @return The best move for the player.
     */
    public Position getBestMove() {
        ArrayList<Position> availablePositions = (ArrayList<Position>) round.getAvailableMoves();

        Position bestMove = availablePositions.get(0);
        int bestScore = 0;

        for (Position position : availablePositions) {

            int score = getPseudoScore(position);
            if (score > bestScore) {
                bestMove = position;
                bestScore = score;
            }
        }

        return bestMove;
    }

    private int getPseudoScore(Position position) {
        int score = 0;

        for (Player player : round.getPlayers()) {
            Round testRound = round.setCurrentPlayer(player);
            testRound = testRound.makeMove(position);

            score += testRound.getScore(player);
        }

        return score;
    }
}
