package edu.ramapo.bbhatta.cmps366_pente_java;

import java.util.ArrayList;
import java.util.Random;

/**
 * Strategy class represents a strategy for a game.
 * It can be used to get the best move for a player.
 */
public class Strategy {

    Round round;
    Random random = new Random();

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

        int bestScore = 0;
        ArrayList<Position> bestMoves = new ArrayList<>();

        for (Position position : availablePositions) {

            int score = getPseudoScore(position);

            if (score > bestScore) {
                bestMoves.clear();
                bestScore = score;
                bestMoves.add(position);
            } else if (score == bestScore) {
                bestMoves.add(position);
            }
        }

        return bestMoves.get(random.nextInt(bestMoves.size()));
    }

    private int getPseudoScore(Position position) {
        int score = 0;

        Player currentPlayer = round.getCurrentPlayer();

        for (Player player : round.getPlayers()) {
            Round testRound = round.setCurrentPlayer(player);
            testRound = testRound.makeMove(position);

            // Prioritize capturing move over capture blocking move
            int captureWeight = player == currentPlayer ? 150 : 100;

            score += testRound.getScore(player) * 1000;
            score += testRound.getCaptures(player) * captureWeight;
            score += getSequenceScore(testRound, position) * 10;
        }

        score -= Position.distance(position, round.getBoard().getCenter());
        return score;
    }

    private int getSequenceScore(Round round, Position position) {
        int score = 0;

        Board board = round.getBoard();
        Stone stone = board.get(position);

        if (stone == null) return 0;

        Stone[][] allStoneSequences = board.getAllStoneSequences();

        for (Stone[] stoneSequence : allStoneSequences) {
            int sequenceLength = stoneSequence.length;
            if (sequenceLength < 2) continue;
            if (stoneSequence[0] == stone) {
                score += sequenceLength * sequenceLength;
            }
        }

        return score;
    }
}
