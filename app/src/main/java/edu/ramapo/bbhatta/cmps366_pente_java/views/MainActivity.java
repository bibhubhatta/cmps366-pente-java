package edu.ramapo.bbhatta.cmps366_pente_java.views;

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

import edu.ramapo.bbhatta.cmps366_pente_java.R;
import edu.ramapo.bbhatta.cmps366_pente_java.models.Log;
import edu.ramapo.bbhatta.cmps366_pente_java.models.Player;
import edu.ramapo.bbhatta.cmps366_pente_java.models.Tournament;

/**
 * Activity for the main menu screen.
 * <p>
 * Also stores the game state and the log as static variables.
 */
public class MainActivity extends AppCompatActivity {

    public static final int BOARD_SIZE = 19;
    private static final int PICK_SERIAL_FILE = 1;
    static Tournament pente;

    static Log log;

    private static void initializeTournament() {

        log.clear();
        log.add("New tournament started");
        pente = new Tournament(BOARD_SIZE, BOARD_SIZE);

        pente = pente.addPlayer(Player.HUMAN);
        pente = pente.addPlayer(Player.COMPUTER);

    }

    /**
     * Initialize the main menu activity.
     * <p>
     * Assistance from: https://developer.android.com/guide/components/activities/activity-lifecycle
     * https://stackoverflow.com/questions/55865800/how-read-text-file-in-android
     * <p>
     * https://stackoverflow.com/questions/1177020/android-how-to-make-all-elements-inside-linearlayout-same-size
     * https://developer.android.com/reference/android/widget/LinearLayout.LayoutParams
     * https://developer.android.com/develop/ui/views/layout/declaring-layout
     * https://developer.android.com/reference/androidx/constraintlayout/widget/ConstraintSet
     * https://stackoverflow.com/questions/8698687/android-layout-width-layout-height-how-it-works
     * https://developer.android.com/develop/ui/views/layout/declaring-layout
     * https://stackoverflow.com/questions/53299673/how-to-make-layout-height-equal-to-its-width-in-android
     * https://developer.android.com/reference/androidx/constraintlayout/widget/ConstraintSet
     * https://stackoverflow.com/questions/44249150/set-constraintlayout-width-to-match-parent-width-programmatically
     * https://stackoverflow.com/questions/26773668/i-want-to-put-image-over-button
     * https://stackoverflow.com/questions/46519388/method-invocation-may-produce-nullpointerexception-retrofit-body
     * https://medium.com/@harjotsingh01/why-should-you-use-material-button-in-android-e7f9199bdd4e
     * https://stackoverflow.com/questions/57925853/is-any-difference-between-a-materialbutton-and-a-simple-button
     * https://stackoverflow.com/questions/6917496/adding-a-view-to-a-linearlayout-at-a-specified-position
     * https://stackoverflow.com/questions/1177020/android-how-to-make-all-elements-inside-linearlayout-same-size
     * https://www.digitalocean.com/community/tutorials/android-layout-linearlayout-relativelayout
     * <p>
     * https://stackoverflow.com/questions/4967799/how-to-know-the-calling-activity-in-android     *
     * <p>
     * https://stackoverflow.com/questions/46619578/android-open-txt-file-from-intent-chooser
     * https://stackoverflow.com/questions/38976963/choose-a-text-file-and-then-read-it-on-android-platform
     * https://developer.android.com/guide/topics/data
     * https://www.youtube.com/watch?v=Ukb9SzPBNms
     * https://www.youtube.com/watch?v=-5zeJyQ31rM
     * https://www.youtube.com/watch?v=cwCNP7ZKRKY
     * https://www.youtube.com/watch?v=mz-Wyno8WIk
     * https://medium.com/@jsonkile/reading-a-file-or-document-with-android-studio-b8c8cbc6518
     * https://stackoverflow.com/questions/30789116/implementing-a-file-picker-in-android-and-copying-the-selected-file-to-another-l
     * https://developer.android.com/training/data-storage/shared/documents-files
     * <p>
     *
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        log = new Log();

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


    /**
     * Handle the result of the file picker.
     * Assistance: https://developer.android.com/training/data-storage/shared/media
     * https://stackoverflow.com/questions/27723975/files-are-unselectable-after-calling-action-get-content-intent
     * https://stackoverflow.com/questions/76161743/android-sdk-30-and-above-how-to-open-any-file-from-external-storage
     * https://stackoverflow.com/questions/76130170/android-api-33-how-to-get-permission-to-read-write-in-external-storage
     * https://developer.android.com/training/data-storage/shared/media
     * https://developer.android.com/training/data-storage/shared/documents-files
     * https://developer.android.com/training/data-storage/shared
     * https://developer.android.com/training/data-storage
     *
     * @param requestCode
     * @param resultCode
     * @param resultData
     */
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
            log.clear();
            log.add("Tournament loaded from file");

            Intent intent = new Intent(MainActivity.this, TournamentActivity.class);
            startActivity(intent);

        }


    }

}