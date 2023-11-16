package edu.ramapo.bbhatta.cmps366_pente_java.views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.ramapo.bbhatta.cmps366_pente_java.R;

/**
 * Activity for the log screen.
 * Displays the log of the game.
 */
public class LogActivity extends AppCompatActivity {

    private TextView logTextView;
    private Button goBackButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        logTextView = findViewById(R.id.logTextView);
        goBackButton = findViewById(R.id.backButton);

        logTextView.setText(MainActivity.log.toString());

        goBackButton.setOnClickListener(view -> finish());
    }


}
