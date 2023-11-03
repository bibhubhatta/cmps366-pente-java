package edu.ramapo.bbhatta.cmps366_pente_java;

import android.app.Activity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class RoundActivity extends Activity {


    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);

        init();

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
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
        // align the text to the center of the TextView
        tv.setGravity(android.view.Gravity.CENTER);

        tv.setText(text);

        return tv;
    }
}
