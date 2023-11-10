package edu.ramapo.bbhatta.cmps366_pente_java.views;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Scanner;

import edu.ramapo.bbhatta.cmps366_pente_java.models.Player;
import edu.ramapo.bbhatta.cmps366_pente_java.models.Position;
import edu.ramapo.bbhatta.cmps366_pente_java.models.Tournament;

public class ConsolePente {
    public static void main(String[] args) {
        Tournament tournament;
        boolean userWantsToLoad = askYesNoQuestion("Do you want to load a game? (y/n)");

        if (userWantsToLoad) {
            tournament = getTournamentFromUserFile();

        } else {
            tournament = getDefaultTournament();
        }

        while (true) {
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

            try {
                tournament = tournament.initializeRound();
            } catch (IllegalStateException e) {
                // Conduct a toss to see who goes first
            }


            boolean userWantsToPlayAgain = askYesNoQuestion("Do you want to play another round? (y/n)");
            if (!userWantsToPlayAgain) {
                break;
            }
        }
    }

    private static Tournament getDefaultTournament() {
        // Create a new game
        Tournament newTournament = new Tournament(19, 19);
        Player human = new Player("Human");
        Player computer = new Player("Computer");

        newTournament = newTournament.addPlayer(human);
        newTournament = newTournament.addPlayer(computer);
        return newTournament;
    }

    @NotNull
    private static Tournament getTournamentFromUserFile() {
        Tournament tournament;

        while (true) {
            try {
                System.out.println("Enter the path to the file:");
                Scanner scanner = new Scanner(System.in);
                String inputPath = scanner.nextLine();

                File inputFile = new File(inputPath);

                tournament = Tournament.fromFile(inputFile);
                return tournament;
            } catch (Exception e) {
                System.out.println("Invalid path");
                System.out.println(e.getMessage());
                System.out.println("Try again");
            }
        }

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

    private static void printBestMove(Tournament tournament) {
        // Print the best move
        System.out.println("Best Move:");

        Position bestMove = tournament.getBestMove();
        String bestMoveString = tournament.getBoard().positionToString(bestMove);

        System.out.println(bestMoveString);
    }

    private static Position getUserMove(Tournament tournament) {
        // Surround the move in a try-catch block to catch invalid moves
        while (true) {
            try {
                // Prompt the user for a move
                System.out.println("Enter a move (h for help):");
                Scanner scanner = new Scanner(System.in);
                String userInput = scanner.nextLine();

                // Check if the user wants help
                if (userInput.equals("h")) {
                    printBestMove(tournament);
                    continue;
                }

                // Convert the move to a position
                Position position = tournament.getBoard().stringToPosition(userInput);

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

    public static boolean askYesNoQuestion(String question) {
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            System.out.println(question);
            input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("y")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        }
    }
}
