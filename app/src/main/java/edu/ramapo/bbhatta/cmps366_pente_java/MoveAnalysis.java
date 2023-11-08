package edu.ramapo.bbhatta.cmps366_pente_java;

import java.util.ArrayList;

/**
 * Contains methods for analyzing moves for a given round.
 */
public class MoveAnalysis {

    private MoveAnalysis() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Analyzes whether a move is a winning move for the current player.
     */
    public static class WinningMoveAnalyzer implements MoveAnalyzer {
        @Override
        public boolean analyzeMove(Round round, Position position) {
            try {
                if (round.isOver()) return false;
                Round resultingRound = round.makeMove(position);
                return resultingRound.getWinner() != null;
            } catch (Exception e) {
                return false;
            }
        }
    }

    /**
     * Analyzes whether a move is a win-blocking move, preventing an opponent from winning.
     */
    public static class WinBlockingMoveAnalyzer implements MoveAnalyzer {
        @Override
        public boolean analyzeMove(Round round, Position position) {
            for (Player player : round.getPlayers()) {
                if (player == round.getCurrentPlayer()) continue;

                Round testRound = round.setCurrentPlayer(player);

                if (new WinningMoveAnalyzer().analyzeMove(testRound, position)) {
                    return true;
                }
            }

            return false;
        }
    }

    /**
     * Analyzes whether a move is a capture move, capturing one or more opponent stones.
     */
    public static class CapturingMoveAnalyzer implements MoveAnalyzer {
        @Override
        public boolean analyzeMove(Round round, Position position) {
            try {
                Round resultingRound = round.makeMove(position);
                return resultingRound.getCaptures(round.getCurrentPlayer()) > round.getCaptures(round.getCurrentPlayer());
            } catch (Exception e) {
                return false;
            }
        }
    }

    /**
     * Analyzes whether a move is a capture-blocking move, preventing an opponent from capturing.
     */
    public static class CaptureBlockingMoveAnalyzer implements MoveAnalyzer {
        @Override
        public boolean analyzeMove(Round round, Position position) {
            for (Player player : round.getPlayers()) {
                if (player == round.getCurrentPlayer()) continue;

                Round testRound = round.setCurrentPlayer(player);

                if (new CapturingMoveAnalyzer().analyzeMove(testRound, position)) {
                    return true;
                }
            }

            return false;
        }
    }

    /**
     * Analyzes whether a move is a sequence-making move, extending a sequence of stones.
     */
    public static class SequenceMakingMoveAnalyzer implements MoveAnalyzer {
        @Override
        public boolean analyzeMove(Round round, Position position) {
            try {
                ArrayList<Position> neighbors = new ArrayList<>(round.getBoard().getNeighbors(position));
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
    }

    /**
     * Analyzes whether a move is a sequence-blocking move, preventing an opponent from extending a sequence.
     */
    public static class SequenceBlockingMoveAnalyzer implements MoveAnalyzer {
        @Override
        public boolean analyzeMove(Round round, Position position) {
            for (Player player : round.getPlayers()) {
                if (player.equals(round.getCurrentPlayer())) continue;

                // Make the move before checking because it could be a capturing move
                Round resultingRound = round.makeMove(position);
                resultingRound = resultingRound.setCurrentPlayer(player);

                if (new SequenceMakingMoveAnalyzer().analyzeMove(resultingRound, position)) {
                    return true;
                }
            }

            return false;
        }
    }

    /**
     * Analyzes whether a move is the only available move for the current player.
     */
    public static class OnlyAvailableMoveAnalyzer implements MoveAnalyzer {
        @Override
        public boolean analyzeMove(Round round, Position position) {
            try {
                ArrayList<Position> availableMoves = new ArrayList<>(round.getAvailableMoves());
                return availableMoves.size() == 1 && availableMoves.get(0).equals(position);
            } catch (Exception e) {
                return false;
            }
        }
    }

    /**
     * Analyzes whether the current move is the second move of the first player.
     */
    public static class SecondMoveOfFirstPlayerAnalyzer implements MoveAnalyzer {
        @Override
        public boolean analyzeMove(Round round, Position position) {
            try {
                return round.isSecondTurnOfFirstPlayer();
            } catch (Exception e) {
                return false;
            }
        }
    }
}
