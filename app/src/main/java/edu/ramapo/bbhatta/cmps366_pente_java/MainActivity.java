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
                // Conduct a toss to see who goes first
                // Start the CoinTossActivity Activity when the button is clicked
                Intent intent = new Intent(MainActivity.this, CoinTossActivity.class);
                startActivity(intent);
                
                // Start a new tournament
            }
        });

    }


}