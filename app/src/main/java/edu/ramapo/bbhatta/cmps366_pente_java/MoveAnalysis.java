package edu.ramapo.bbhatta.cmps366_pente_java;

import java.util.ArrayList;

/**
 * Contains methods for analyzing moves for a given round.
 */
public class MoveAnalysis {

    /**
     * Determines if a move is a winning move.
     *
     * @param round    The round to analyze.
     * @param position The position to analyze.
     * @return True if the move is a winning move, false otherwise.
     */

    public static boolean isWinningMove(Round round, Position position) {

        try {
            // If the round is already over, return false
            if (round.isOver()) return false;
            Round resultingRound = round.makeMove(position);
            if (resultingRound.getWinner() != null) return true;
        } catch (Exception e) {
            return false;
        }

        return false;
    }


    /**
     * Determines if a move is a win blocking move, i.e. it prevents one or more opponents from winning.
     *
     * @param round    The round to analyze.
     * @param position The position to analyze.
     * @return True if the move is a blocking move, false otherwise.
     */
    public static boolean isWinBlockingMove(Round round, Position position) {

        for (Player player : round.getPlayers()) {
            if (player == round.getCurrentPlayer()) continue;

            Round testRound = round.setCurrentPlayer(player);

            if (isWinningMove(testRound, position)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Determines if a move is a capture move, i.e. it captures one or more opponent stones.
     *
     * @param round    The round to analyze.
     * @param position The position to analyze.
     * @return True if the move is a capture move, false otherwise.
     */
    public static boolean isCaptureMove(Round round, Position position) {

        try {

            Round resultingRound = round.makeMove(position);
            if (resultingRound.getCaptures(round.getCurrentPlayer()) > round.getCaptures(round.getCurrentPlayer()))
                return true;
        } catch (Exception e) {
            return false;
        }

        return false;
    }

    /**
     * Determines if a move is a capture block move, i.e. it prevents one or more opponents from capturing.
     *
     * @param round    The round to analyze.
     * @param position The position to analyze.
     * @return True if the move is a capture blocking move, false otherwise.
     */
    public static boolean isCaptureBlockMove(Round round, Position position) {

        for (Player player : round.getPlayers()) {
            if (player == round.getCurrentPlayer()) continue;

            Round testRound = round.setCurrentPlayer(player);

            if (isCaptureMove(testRound, position)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Determines if a move is a sequence making move, i.e. it extends a sequence of stones.
     * A sequence is a line of two or more stones of the same color.
     *
     * @param round    The round to analyze.
     * @param position The position to analyze.
     * @return True if the move is a sequence making move, false otherwise.
     */
    public static boolean isSequenceMakingMove(Round round, Position position) {
        try {
            ArrayList<Position> neighbors = (ArrayList<Position>) round.getBoard().getNeighbors(position);
            for (Position neighbor : neighbors) {
                if (round.getBoard().get(neighbor) == round.getCurrentStone()) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }

    /**
     * Determines if a move is a sequence blocking move, i.e. it prevents one or more opponents from extending a sequence.
     * A sequence is a line of two or more stones of the same color.
     *
     * @param round    The round to analyze.
     * @param position The position to analyze.
     * @return True if the move is a sequence blocking move, false otherwise.
     */
    public static boolean isSequenceBlockingMove(Round round, Position position) {

        for (Player player : round.getPlayers()) {
            if (player == round.getCurrentPlayer()) continue;

            Round testRound = round.setCurrentPlayer(player);

            if (isSequenceMakingMove(testRound, position)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Determine is the move is the only available move.
     *
     * @param round    The round to analyze.
     * @param position The position to analyze.
     * @return True if the move is the only available move, false otherwise.
     */
    public static boolean isOnlyAvailableMove(Round round, Position position) {
        try {
            ArrayList<Position> availableMoves = (ArrayList<Position>) round.getAvailableMoves();
            if (availableMoves.size() == 1) {
                if (availableMoves.get(0).equals(position)) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }
}
