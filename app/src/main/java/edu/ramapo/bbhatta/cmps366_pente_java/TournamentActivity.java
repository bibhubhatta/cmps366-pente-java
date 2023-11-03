package edu.ramapo.bbhatta.cmps366_pente_java;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static edu.ramapo.bbhatta.cmps366_pente_java.MainActivity.tournament;

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
                        tournament = tournament.initializeRound();
                    } catch (IllegalStateException e) {
                        boolean humanGoesFirst = conductToss();

                        ArrayList<Player> players = new ArrayList<>();
                        if (humanGoesFirst) {
                            players.add(Player.HUMAN);
                            players.add(Player.COMPUTER);

                        } else {
                            players.add(Player.COMPUTER);
                            players.add(Player.HUMAN);

                        }
                        tournament = tournament.initializeRound(players);
                    }

                    Intent intent = new Intent(TournamentActivity.this, RoundActivity.class);
                    startActivity(intent);
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


    private boolean conductToss() {
        // Conduct a toss to see who goes first
        // Start the CoinTossActivity Activity when the button is clicked
        Intent intent = new Intent(TournamentActivity.this, CoinTossActivity.class);
        startActivity(intent);

        // Get the result of the toss from the CoinTossActivity Activity
        return getIntent().getBooleanExtra(CoinTossActivity.COIN_TOSS_RESULT_KEY, false);
    }
}
