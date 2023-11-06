package edu.ramapo.bbhatta.cmps366_pente_java;

import android.graphics.drawable.Drawable;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);

        initRoundLayout();
        initBoard();

        // If the round is over, show the winner, hide the help and save buttons, and show the continue button
        if (MainActivity.tournament.getRound().isOver()) {

            // Disable the boardLayout from being clicked
            setUnclickable(findViewById(R.id.boardLinearLayout));

            // Hide the help and save buttons
            findViewById(R.id.helpButton).setVisibility(View.GONE);
            findViewById(R.id.saveGameButton).setVisibility(View.GONE);

            // Show the continue button
            findViewById(R.id.continueButton).setVisibility(View.VISIBLE);

            // Set the on click listener for the continue button
            findViewById(R.id.continueButton).setOnClickListener(view -> {
                // Start the tournament activity
                finish();
            });

            // If there is a winner, show the winner
            // Otherwise, show that the round is a draw
            if (MainActivity.tournament.getRound().getWinner() != null) {
                TextView messageTextView = findViewById(R.id.messageTextView);
                messageTextView.setVisibility(View.VISIBLE);
                messageTextView.setText(String.format("%s won!", MainActivity.tournament.getRound().getWinner().getName()));
            } else {
                TextView messageTextView = findViewById(R.id.messageTextView);
                messageTextView.setVisibility(View.VISIBLE);
                messageTextView.setText("The round is a draw!");
            }
        }
    }

    /**
     * Sets the view and all of its children to unclickable.
     * Copied from <a href="https://stackoverflow.com/a/21107367/">...</a>
     *
     * @param view The view to set unclickable.
     */
    private void setUnclickable(View view) {
        if (view != null) {
            view.setClickable(false);
            if (view instanceof ViewGroup) {
                ViewGroup vg = ((ViewGroup) view);
                for (int i = 0; i < vg.getChildCount(); i++) {
                    setUnclickable(vg.getChildAt(i));
                }
            }
        }
    }

    public void initRoundLayout() {
        TableLayout playerScoresTable = findViewById(R.id.playerScoresTableLayout);

        Round round = MainActivity.tournament.getRound();

        ArrayList<Player> players = (ArrayList<Player>) round.getPlayers();

        // Sort players by score
        players.sort((player1, player2) -> round.getScore(player2) - round.getScore(player1));


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

            addRowNumberToBoardLayout(board, row, rowLayout);
        }

        LinearLayout columnLettersLayout = getColumnLabelRow(board);

        boardLayout.addView(columnLettersLayout);
    }

    private void addRowNumberToBoardLayout(Board board, int row, LinearLayout rowLayout) {
        // Add row number to the left of the row
        int displayRow = board.getNoRows() - row;

        TextView rowNumberTextView = new TextView(this);
        rowNumberTextView.setText(String.valueOf(displayRow));
        rowNumberTextView.setGravity(android.view.Gravity.CENTER);
        rowNumberTextView.setLayoutParams(new LinearLayout.LayoutParams(100, ViewGroup.LayoutParams.MATCH_PARENT));

        // Set the background color to white because the background of board layout is black to
        // show the lines between the buttons
        rowNumberTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        rowLayout.addView(rowNumberTextView, 0);
    }

    @NonNull
    private LinearLayout getColumnLabelRow(Board board) {
        // Add column letters to the bottom of the board
        LinearLayout columnLettersLayout = new LinearLayout(this);
        columnLettersLayout.setOrientation(LinearLayout.HORIZONTAL);
        columnLettersLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

        // Set the background color to white because the background of board layout is black to
        // show the lines between the buttons
        columnLettersLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white));

        // Add empty textview to the left of the column letters
        TextView emptyTextView = new TextView(this);
        emptyTextView.setLayoutParams(new LinearLayout.LayoutParams(100, ViewGroup.LayoutParams.MATCH_PARENT));
        columnLettersLayout.addView(emptyTextView);

        // Add column letters
        for (int col = 0; col < board.getNoCols(); col++) {
            TextView columnLetterTextView = new TextView(this);
            columnLetterTextView.setText(String.valueOf((char) (col + 'A')));
            columnLetterTextView.setGravity(android.view.Gravity.CENTER);
            columnLetterTextView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            columnLettersLayout.addView(columnLetterTextView);
        }
        return columnLettersLayout;
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

    @NonNull
    private Button createCellButton(Board board, int row, int col) {
        Position position = new Position(row, col);

        // Create a new button
        Button button = new Button(this);

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

        // Set the color of the button to the color of the stone at the position
        Stone stone = board.get(position);
        if (stone != null) {
            Drawable stoneShape = ContextCompat.getDrawable(this, R.drawable.stone);

            // Add the stone shape to the button foreground
            button.setForeground(stoneShape);
            // Set the button background to gray
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.light_gray));
            if (stone == Stone.BLACK) {
                // Set the stone shape color to black
                assert stoneShape != null;
                stoneShape.setTint(ContextCompat.getColor(this, R.color.black));
            } else {
                // Set the stone shape color to white
                assert stoneShape != null;
                stoneShape.setTint(ContextCompat.getColor(this, R.color.white));
            }
        }

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

            try {
                // Make the move
                MainActivity.tournament = MainActivity.tournament.makeMove(pos);
                // Call the RoundActivity again
                recreate();
            } catch (Exception e) {
                // Show the error message on the message textview
                messageTextView.setText(e.getMessage());
                // Make the message text view visible
                messageTextView.setVisibility(View.VISIBLE);
            }


        });

        return button;
    }
}

