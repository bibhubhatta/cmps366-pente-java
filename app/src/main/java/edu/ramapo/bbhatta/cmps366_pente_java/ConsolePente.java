package edu.ramapo.bbhatta.cmps366_pente_java;

import java.util.Scanner;

public class ConsolePente {
    public static void main(String[] args) {
        Tournament tournament = new Tournament(19, 19);
        Player human1 = new Player("Human1");
        Player human2 = new Player("Human2");

        tournament = tournament.addPlayer(human1);
        tournament = tournament.addPlayer(human2);

        while (!tournament.getRound().isOver()) {
            printTournamentScores(tournament);
            printRoundScores(tournament);
            printCaptures(tournament);
            printBoard(tournament);
            printCurrentPlayerAndStone(tournament);

            Position userMove = getUserMove(tournament);
            tournament = tournament.makeMove(userMove);
        }

        printBoard(tournament);

        printRoundScores(tournament);

        printTournamentScores(tournament);
    }

    private static void printCurrentPlayerAndStone(Tournament tournament) {
        // Print the current player and stone
        System.out.println("Current Player: " + tournament.getCurrentPlayer());
        System.out.println("Current Stone: " + tournament.getCurrentStone());
    }

    private static void printBoard(Tournament tournament) {
        // Print the board
        System.out.println("Board:");
        System.out.println(tournament.getBoard().displayString());
    }

    private static void printCaptures(Tournament tournament) {
        // Print the captures
        System.out.println("Captures:");

        for (Player player : tournament.getRound().getPlayers()) {
            System.out.println(player + ": " + tournament.getCaptures(player));
        }
    }

    private static Position getUserMove(Tournament tournament) {
        // Surround the move in a try-catch block to catch invalid moves
        while (true) {
            try {
                // Prompt the user for a move
                System.out.println("Enter a move:");
                Scanner scanner = new Scanner(System.in);
                String inputMove = scanner.nextLine();

                // Convert the move to a position
                Position position = tournament.getBoard().stringToPosition(inputMove);

                // Make the move to see if it is valid
                tournament.makeMove(position);

                return position;
            } catch (Exception e) {
                System.out.println("Invalid move");
                System.out.println(e.getMessage());
                System.out.println("Try again");
            }
        }
    }

    private static void printRoundScores(Tournament tournament) {
        // Print the round scores
        System.out.println("Round Scores:");

        for (Player player : tournament.getRound().getPlayers()) {
            System.out.println(player + ": " + tournament.getRoundScore(player));
        }

    }

    private static void printTournamentScores(Tournament tournament) {
        // Print the tournament scores
        System.out.println("Tournament Scores:");

        for (Player player : tournament.getPlayers()) {
            System.out.println(player + ": " + tournament.getScore(player));
        }
    }
}
