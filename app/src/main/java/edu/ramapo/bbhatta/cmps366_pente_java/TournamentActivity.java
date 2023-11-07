package edu.ramapo.bbhatta.cmps366_pente_java;

import static edu.ramapo.bbhatta.cmps366_pente_java.MainActivity.pente;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TournamentActivity extends AppCompatActivity {

    private TextView playerScoresTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament);


        playerScoresTextView = findViewById(R.id.playerScoresTextView);
        Button endTournamentButton = findViewById(R.id.endTournamentButton);
        Button playRoundButton = findViewById(R.id.playRoundButton);

        displayPlayerScores();

        endTournamentButton.setOnClickListener(view -> {

            displayPlayerScores();

            // If there is a winner, display the winner
            if (pente.getWinner() != null) {
                String winner = pente.getWinner().getName() + " wins!";

                playerScoresTextView.append("\n" + winner);
            }
            // Otherwise, display that the tournament is a draw
            else {
                playerScoresTextView.append("\nDraw!");
            }

            // Hide the play round button
            playRoundButton.setVisibility(View.GONE);

            // Rename the end tournament button to "Main Menu"
            endTournamentButton.setText(R.string.main_menu);
            // The end tournament button should take the user back to the main menu
            endTournamentButton.setOnClickListener(view1 -> {
                Intent intent = new Intent(TournamentActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            });


        });

        playRoundButton.setOnClickListener(view -> {
                    try {

                        // Initialize the round if the round is over
                        if (pente.getRound().isOver()) {
                            pente = pente.initializeRound();

                        }

                        // If the round has not been initialized, initialize it
                        // The round is not initialized if the current player is null
                        if (pente.getRound().getCurrentPlayer() == null) {
                            pente = pente.initializeRound();
                        }

                        Intent intent = new Intent(TournamentActivity.this, RoundActivity.class);
                        startActivity(intent);
                    } catch (IllegalStateException e) {

                        Intent intent = new Intent(TournamentActivity.this, CoinTossActivity.class);
                        startActivity(intent);
                    }

                }
        );
    }


    private void displayPlayerScores() {
        // Get the player scores from the Tournament instance and display them
        StringBuilder playerScores = new StringBuilder("Player Scores:\n");
        for (Player player : pente.getPlayers()) {
            playerScores.append(player.getName()).append(": ").append(pente.getScore(player)).append("\n");
        }
        playerScoresTextView.setText(playerScores.toString());
    }
}
