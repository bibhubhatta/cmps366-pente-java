package edu.ramapo.bbhatta.cmps366_pente_java;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button startNewTournamentButton;
    private Button loadTournamentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startNewTournamentButton = findViewById(R.id.btn_start_new_tournament);
        loadTournamentButton = findViewById(R.id.btn_load_tournament);

        startNewTournamentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tournament newTournament = getNewTournament();
            }
        });

    }

    private Tournament getNewTournament() {
        boolean humanWinsToss = conductToss();

        // Create a new game
        Tournament newTournament = new Tournament(19, 19);
        Player human = Player.HUMAN;
        Player computer = Player.COMPUTER;

        if (humanWinsToss) {
            newTournament = newTournament.addPlayer(human);
            newTournament = newTournament.addPlayer(computer);
        } else {
            newTournament = newTournament.addPlayer(computer);
            newTournament = newTournament.addPlayer(human);
        }
        return newTournament;
    }

    private boolean conductToss() {
        // Conduct a toss to see who goes first
        // Start the CoinTossActivity Activity when the button is clicked
        Intent intent = new Intent(MainActivity.this, CoinTossActivity.class);
        startActivity(intent);

        // Get the result of the toss from the CoinTossActivity Activity
        return getIntent().getBooleanExtra(CoinTossActivity.COIN_TOSS_RESULT_KEY, false);
    }


}