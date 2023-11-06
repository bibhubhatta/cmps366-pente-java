package edu.ramapo.bbhatta.cmps366_pente_java;

import static edu.ramapo.bbhatta.cmps366_pente_java.MainActivity.tournament;

import android.content.Intent;
import android.os.Bundle;
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
            //  TODO: Show winner
            finish();
        });

        playRoundButton.setOnClickListener(view -> {
                    try {

                        // Initialize the round if the round is over
                        if (tournament.getRound().isOver()) {
                            tournament = tournament.initializeRound();

                        }

                        // If the round has not been initialized, initialize it
                        // The round is not initialized if the current player is null
                        if (tournament.getRound().getCurrentPlayer() == null) {
                            tournament = tournament.initializeRound();
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
        for (Player player : tournament.getPlayers()) {
            playerScores.append(player.getName()).append(": ").append(tournament.getScore(player)).append("\n");
        }
        playerScoresTextView.setText(playerScores.toString());
    }
}
