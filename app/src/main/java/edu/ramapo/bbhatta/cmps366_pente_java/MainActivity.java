package edu.ramapo.bbhatta.cmps366_pente_java;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static final int BOARD_SIZE = 19;
    private static final int PICK_SERIAL_FILE = 1;
    public static Tournament pente;

    private static void initializeTournament() {

        pente = new Tournament(BOARD_SIZE, BOARD_SIZE);

        pente = pente.addPlayer(Player.HUMAN);
        pente = pente.addPlayer(Player.COMPUTER);

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

            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            startActivityForResult(intent, PICK_SERIAL_FILE);


        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);

        if (requestCode == PICK_SERIAL_FILE && resultCode == RESULT_OK) {

            Uri uri = resultData.getData();

            StringBuilder stringBuilder = new StringBuilder();
            try (InputStream inputStream =
                         getContentResolver().openInputStream(uri);
                 BufferedReader reader = new BufferedReader(
                         new InputStreamReader(Objects.requireNonNull(inputStream)))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Add a newline character to the end of each line
                    // because BufferedReader.readLine() strips them
                    stringBuilder.append(line).append("\n");
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String serialString = stringBuilder.toString();

            pente = Tournament.fromString(serialString);

            Intent intent = new Intent(MainActivity.this, TournamentActivity.class);
            startActivity(intent);

        }


    }

}