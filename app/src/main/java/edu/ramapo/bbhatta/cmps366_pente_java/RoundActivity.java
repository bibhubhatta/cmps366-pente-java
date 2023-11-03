package edu.ramapo.bbhatta.cmps366_pente_java;

import android.app.Activity;
import android.widget.*;

import java.util.ArrayList;

public class RoundActivity extends Activity {


    GridView boardGridView;

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);

        init();
        initBoard();

    }

    private void initBoard() {
        boardGridView = findViewById(R.id.boardGridView);

        Round round = MainActivity.tournament.getRound();
        Board board = round.getBoard();
        int numRows = board.getNoRows();
        int numCols = board.getNoCols();

        // Set the number of columns in the grid view
        boardGridView.setNumColumns(numCols);


        ArrayList<String> boardArrayList = new ArrayList<>();

        for (Position position : board.getAllPositions()) {
            // Create a button with the id as the position string
            Button button = new Button(this);
            button.setTag(position.toString());

            // Set the button's text to the stone's symbol
            Stone stone = board.get(position);
            if (stone == Stone.WHITE) {
                button.setText("W");
            } else if (stone == Stone.BLACK) {
                button.setText("B");
            } else {
                button.setText("O");
            }

            // Add the button to the array list
            boardArrayList.add(button.toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.board_cell, R.id.cellTextView, boardArrayList);
        boardGridView.setAdapter(adapter);

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
