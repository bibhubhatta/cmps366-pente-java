package edu.ramapo.bbhatta.cmps366_pente_java;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final int BOARD_SIZE = 19;
    public static Tournament tournament;

    private static void initializeTournament() {

        tournament = new Tournament(BOARD_SIZE, BOARD_SIZE);

        tournament = tournament.addPlayer(Player.HUMAN);
        tournament = tournament.addPlayer(Player.COMPUTER);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startNewTournamentButton = findViewById(R.id.btn_start_new_tournament);
        Button loadTournamentButton = findViewById(R.id.btn_load_tournament);

        startNewTournamentButton.setOnClickListener(view -> {

            initializeTournament();

            Intent intent = new Intent(MainActivity.this, TournamentActivity.class);
            startActivity(intent);
        });

        loadTournamentButton.setOnClickListener(view -> {
//            Intent intent = new Intent(MainActivity.this, LoadTournamentActivity.class);
//            startActivity(intent);
        });

    }


}