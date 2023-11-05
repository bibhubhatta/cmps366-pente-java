package edu.ramapo.bbhatta.cmps366_pente_java;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class RoundActivity extends AppCompatActivity {

    @NonNull
    private Button createCellButton(Board board, int row, int col) {
        Position position = new Position(row, col);
        String postionString = board.positionToString(position);

        // Create a new button
        Button button = new Button(this);
        // Set the text to the row and column number
        button.setText(postionString);

        // Set layout width to 0 and weight to 1
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        button.setLayoutParams(params);

        // Set the tag to the position
        button.setTag(position);

        // Set the background color to gray so that shadows are not added to the button
        // which causes whitespace between the buttons
        button.setBackgroundColor(ContextCompat.getColor(this, R.color.light_gray));

        // Set the margin so that the lines between the buttons are visible
        int boardCellStrokeWidth = (int) getResources().getDimension(R.dimen.board_cell_margin);

        int left = (col == 0) ? boardCellStrokeWidth : 0;
        int top = (row == 0) ? boardCellStrokeWidth : 0;
        int right = boardCellStrokeWidth;
        int bottom = boardCellStrokeWidth;

        params.setMargins(left, top, right, bottom);
        button.setLayoutParams(params);

        // Set the on click listener
        button.setOnClickListener(view -> {
            Position pos = (Position) view.getTag();

            // Show the message on the message textview
            TextView messageTextView = findViewById(R.id.messageTextView);
            messageTextView.setText(board.positionToString(pos));
            // Make the message textview visible
            messageTextView.setVisibility(View.VISIBLE);

        });


        return button;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);

        init();
        initBoard();
    }

    private void initBoard() {
        Round round = MainActivity.tournament.getRound();
        Board board = round.getBoard();

        // Get the boardlayout from the xml file
        LinearLayout boardLayout = findViewById(R.id.boardLinearLayout);


        for (int row = 0; row < board.getNoRows(); row++) {
            LinearLayout rowLayout = createNewRow(board, row);

            // Set height to 0 and weight to 1
            LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100, 1);
            rowLayout.setLayoutParams(rowParams);

            // Add the row to the boardlayout
            boardLayout.addView(rowLayout);
        }
    }

    @NonNull
    private LinearLayout createNewRow(Board board, int row) {

        LinearLayout rowLayout = new LinearLayout(this);
        rowLayout.setOrientation(LinearLayout.HORIZONTAL);

        // Set width to match parent and height to wrap content
        LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rowLayout.setLayoutParams(rowParams);

        for (int col = 0; col < board.getNoCols(); col++) {
            Button button = createCellButton(board, row, col);
            rowLayout.addView(button);
        }
        return rowLayout;
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
