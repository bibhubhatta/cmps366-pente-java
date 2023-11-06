package edu.ramapo.bbhatta.cmps366_pente_java;

import static edu.ramapo.bbhatta.cmps366_pente_java.MainActivity.tournament;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class CoinTossActivity extends AppCompatActivity {

    static final String COIN_TOSS_RESULT_KEY = "coin_toss_result";
    private final Random random = new Random();
    boolean isWon;
    private Button headsButton;
    private Button tailsButton;
    private TextView resultTextView;
    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_toss);

        headsButton = findViewById(R.id.headsButton);
        tailsButton = findViewById(R.id.tailsButton);
        resultTextView = findViewById(R.id.resultTextView);
        continueButton = findViewById(R.id.continueButton);

        headsButton.setOnClickListener(view -> performCoinToss(getString(R.string.heads_option)));
        tailsButton.setOnClickListener(view -> performCoinToss(getString(R.string.tails_option)));

        continueButton.setOnClickListener(view -> {

            // Add the players to the round in the tournament
            ArrayList<Player> players = new ArrayList<>();
            if (isWon) {
                players.add(Player.HUMAN);
                players.add(Player.COMPUTER);

            } else {
                players.add(Player.COMPUTER);
                players.add(Player.HUMAN);

            }
            tournament = tournament.initializeRound(players);

            // Start the round activity
            Intent intent = new Intent(CoinTossActivity.this, RoundActivity.class);
            startActivity(intent);

            finish();
        });
    }

    private void performCoinToss(String userChoice) {
        isWon = random.nextBoolean();

        String result;
        if (isWon) {
            result = getString(R.string.result_won);
        } else {
            result = getString(R.string.result_lost);
        }

        resultTextView.setText(getString(R.string.result_prefix) + result);

        //Show a continue button and hide the heads and tails buttons
        headsButton.setVisibility(View.GONE);
        tailsButton.setVisibility(View.GONE);
        continueButton.setVisibility(View.VISIBLE);
    }
}
