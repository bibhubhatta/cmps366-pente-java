package edu.ramapo.bbhatta.cmps366_pente_java;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RoundActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);

        init();

        Round round = MainActivity.tournament.getRound();
        Board board = round.getBoard();

        // Get the boardlayout from the xml file
        LinearLayout boardLayout = findViewById(R.id.boardLinearLayout);


        for (int row = 0; row < board.getNoRows(); row++) {
            // Create a new row
            LinearLayout rowLayout = new LinearLayout(this);
            // Set orientation to horizontal
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);

            // Set width to match parent and height to wrap content
            LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            rowLayout.setLayoutParams(rowParams);

            for (int col = 0; col < board.getNoCols(); col++) {

                Position position = new Position(row, col);
                String postionString = board.positionToString(position);

                // Create a new button
                Button button = new Button(this);
                // Set the text to the row and column number
                button.setText(postionString);
                // Match width to wrap content
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                button.setLayoutParams(params);
                // Add the button to the row
                rowLayout.addView(button);
            }

            // Add the row to the boardlayout
            boardLayout.addView(rowLayout);
        }

    }


    public void init() {
        TableLayout playerScoresTable = findViewById(R.id.playerScoresTableLayout);

        Round round = MainActivity.tournament.getRound();

        ArrayList<Player> players = (ArrayList<Player>) round.getPlayers();


        for (Player player : players) {
            TableRow row = new TableRow(this);

            TextView playerNameTextView = getTableCellTextView(player.getName());
            TextView playerCapturesTextView = getTableCellTextView(String.valueOf(round.getCaptures(player)));
            TextView playerScoreTextView = getTableCellTextView(String.valueOf(round.getScore(player)));

            row.addView(playerNameTextView);
            row.addView(playerCapturesTextView);
            row.addView(playerScoreTextView);

            playerScoresTable.addView(row);
        }


    }

    private TextView getTableCellTextView(String text) {
        TextView tv = new TextView(this);
        // set width and height
        tv.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        // align the text to the center of the TextView
        tv.setGravity(android.view.Gravity.CENTER);

        tv.setText(text);

        return tv;
    }
}
