package edu.ramapo.bbhatta.cmps366_pente_java.models;

/**
 * Interface for analyzing moves.
 * Learned about command pattern from: https://en.wikipedia.org/wiki/Command_pattern
 * https://stackoverflow.com/questions/2172718/can-java-store-methods-in-arrays
 */
public interface MoveAnalyzer {
    boolean analyzeMove(Round round, Position position);
}
