package edu.ramapo.bbhatta.cmps366_pente_java.views;

import static edu.ramapo.bbhatta.cmps366_pente_java.views.MainActivity.log;
import static edu.ramapo.bbhatta.cmps366_pente_java.views.MainActivity.pente;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

import edu.ramapo.bbhatta.cmps366_pente_java.R;
import edu.ramapo.bbhatta.cmps366_pente_java.models.Player;

/**
 * Activity for the coin toss screen.
 */
public class CoinTossActivity extends AppCompatActivity {

    static final String COIN_TOSS_RESULT_KEY = "coin_toss_result";
    private final Random random = new Random();
    boolean isWon;
    private Button headsButton;
    private Button tailsButton;
    private TextView resultTextView;
    private Button continueButton;

    /**
     * Initialize the coin toss activity.
     *
     * @param savedInstanceState The saved instance state.
     */
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
            pente = pente.initializeRound(players);

            // Start the round activity
            Intent intent = new Intent(CoinTossActivity.this, RoundActivity.class);
            startActivity(intent);

            finish();
        });
    }

    /**
     * Perform a coin toss.
     * Assistance: <a href="https://docs.oracle.com/javase/8/docs/api/java/util/Random.html">...</a>
     *
     * @param userChoice The user's choice.
     */
    private void performCoinToss(String userChoice) {
        isWon = random.nextBoolean();

        String result;
        if (isWon) {
            result = getString(R.string.result_won);
            log.add("Human won the coin toss. Human will go first.");
        } else {
            result = getString(R.string.result_lost);
            log.add("Human lost the coin toss. Computer will go first.");
        }

        resultTextView.setText(result);

        // Show a continue button and hide the heads and tails buttons
        headsButton.setVisibility(View.GONE);
        tailsButton.setVisibility(View.GONE);
        continueButton.setVisibility(View.VISIBLE);
    }
}
