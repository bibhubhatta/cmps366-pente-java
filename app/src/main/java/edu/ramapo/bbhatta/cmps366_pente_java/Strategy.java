package edu.ramapo.bbhatta.cmps366_pente_java;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;

/**
 * Strategy class represents a strategy for a game.
 * It can be used to get the best move for a player.
 */
public class Strategy {

    private final LinkedHashMap<MoveAnalyzer, String> moveAnalyzers = new LinkedHashMap<>();
    Round round;
    Random random = new Random();

    public Strategy(Round round) {
        this.round = round;

        moveAnalyzers.put(new MoveAnalysis.WinningMoveAnalyzer(), "is a winning move");
        moveAnalyzers.put(new MoveAnalysis.WinBlockingMoveAnalyzer(), "is a win-blocking move");
        moveAnalyzers.put(new MoveAnalysis.CapturingMoveAnalyzer(), "is a capturing move");
        moveAnalyzers.put(new MoveAnalysis.CaptureBlockingMoveAnalyzer(), "is a capture-blocking move");
        moveAnalyzers.put(new MoveAnalysis.SequenceMakingMoveAnalyzer(), "is a sequence-making move");
        moveAnalyzers.put(new MoveAnalysis.SequenceBlockingMoveAnalyzer(), "is a sequence-blocking move");
        moveAnalyzers.put(new MoveAnalysis.OnlyAvailableMoveAnalyzer(), "is the only available move");

    }

    public Strategy(Strategy strategy) {
        this.round = strategy.round;
    }

    /**
     * Get the best move
     * <p>
     * The best move is calculated by getting the pseudo score for each available move.
     * The moves with the highest pseudo score are stored in a list and a move is chosen at random.
     * <p>
     * See getPseudoScore() for more information on how the pseudo score is calculated.
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

    public String getRationale(Position position) {
        StringBuilder rationale = new StringBuilder();

        if (new MoveAnalysis.SecondMoveOfFirstPlayerAnalyzer().analyzeMove(round, position))
            rationale.append("This is the second move of the first player. So it is at least 3 spaces away from the center. ");

        for (MoveAnalyzer moveAnalyzer : moveAnalyzers.keySet()) {
            if (moveAnalyzer.analyzeMove(round, position)) {

                if (rationale.length() == 0) {
                    rationale.append("This move ");
                } else {
                    rationale.append("It also ");
                }
                rationale.append(moveAnalyzers.get(moveAnalyzer)).append(". ");
            }
        }

        if (rationale.length() == 0) {
            rationale.append("This move is random.");
        }

        return rationale.toString();
    }

    /**
     * Calculates the pseudo score for a given move.
     * <p>
     * The pseudo score is calculated by making the move on a copy of the current round.
     * The score is calculated by adding the following for each player:
     * - The score of the player
     * - The number of captures the player has
     * - The score of the player's sequences
     * The score is then subtracted by the distance of the move from the center of the board so that
     * moves closer to the center are prioritized over edge moves.
     * <p>
     * The score is weighted based on the player so that offensive moves are prioritized over
     * defensive moves.
     * <p>
     * See getSequenceScore() for more information on how the sequence score is calculated.
     *
     * @param position The move to calculate the pseudo score for.
     * @return The pseudo score for the given move.
     */
    private int getPseudoScore(Position position) {
        int score = 0;

        Player currentPlayer = round.getCurrentPlayer();

        for (Player player : round.getPlayers()) {
            Round testRound = round.setCurrentPlayer(player);
            testRound = testRound.makeMove(position);

            // Prioritize capturing move over capture blocking move
            int captureWeight = player == currentPlayer ? 150 : 100;

            // Prioritize sequence making move over sequence blocking move
            int sequenceWeight = player == currentPlayer ? 15 : 10;

            score += testRound.getScore(player) * 1000;
            score += testRound.getCaptures(player) * captureWeight;
            score += getSequenceScore(testRound, position) * sequenceWeight;
        }

        score -= Position.distance(position, round.getBoard().getCenter());
        return score;
    }

    /**
     * Calculates the sequence score for a given position.
     * <p>
     * The sequence score is calculated by getting all the sequences on the board and adding the
     * square of the length of the sequence if the sequence contains the stone at the given position.
     *
     * @param round    The round to calculate the sequence score for.
     * @param position The position to calculate the sequence score for.
     * @return The sequence score for the given position.
     */
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
