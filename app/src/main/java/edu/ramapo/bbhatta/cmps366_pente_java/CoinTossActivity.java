package edu.ramapo.bbhatta.cmps366_pente_java;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class CoinTossActivity extends AppCompatActivity {

    private Button tossButton;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_toss);

        tossButton = findViewById(R.id.btn_toss);
        resultTextView = findViewById(R.id.tv_toss_result);

        tossButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform the coin toss
                String result = performCoinToss();

                // Update the TextView with the result
                resultTextView.setText(result);
            }
        });
    }

    private String performCoinToss() {
        Random random = new Random();
        boolean isHeads = random.nextBoolean();
        if (isHeads) {
            return "Heads";
        } else {
            return "Tails";
        }
    }
}
