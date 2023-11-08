package edu.ramapo.bbhatta.cmps366_pente_java;

import android.content.Intent;
import android.graphics.Typeface;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class RoundActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);

        init();
    }

    private void init() {
        initRoundLayout();
        initBoard();
        handleGameOver();
        initButtons();
        handleNextMove();
    }


    private void initButtons() {

        // Set the on click listener for the help button
        findViewById(R.id.helpButton).setOnClickListener(view -> {
            Position bestMove = new Strategy(MainActivity.pente.getRound()).getBestMove();
            String bestMoveString = MainActivity.pente.getRound().getBoard().positionToString(bestMove);
            String rationale = new Strategy(MainActivity.pente.getRound()).getRationale(bestMove);

            TextView messageTextView = findViewById(R.id.messageTextView);
            messageTextView.setText(String.format("The best move is %s.", bestMoveString));
            messageTextView.append(String.format("\n%s", rationale));
            messageTextView.setVisibility(View.VISIBLE);
        });

    }

    private void handleGameOver() {
        // If the round is over, show the winner, hide the help and save buttons, and show the continue button
        if (MainActivity.pente.getRound().isOver()) {

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
                Intent intent = new Intent(RoundActivity.this, TournamentActivity.class);
                startActivity(intent);

                finish();
            });

            // If there is a winner, show the winner
            // Otherwise, show that the round is a draw
            if (MainActivity.pente.getRound().getWinner() != null) {
                TextView messageTextView = findViewById(R.id.messageTextView);
                messageTextView.setVisibility(View.VISIBLE);
                messageTextView.setText(String.format("%s won!", MainActivity.pente.getRound().getWinner().getName()));
            } else {
                TextView messageTextView = findViewById(R.id.messageTextView);
                messageTextView.setVisibility(View.VISIBLE);
                messageTextView.setText(R.string.the_round_is_a_draw);
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
        // Clear the table
        playerScoresTable.removeAllViews();

        // Clear the message textview
        TextView messageTextView = findViewById(R.id.messageTextView);
        messageTextView.setVisibility(View.GONE);

        Round round = MainActivity.pente.getRound();

        ArrayList<Player> players = (ArrayList<Player>) round.getPlayers();

        // Sort players by score
        players.sort((player1, player2) -> round.getScore(player2) - round.getScore(player1));

        // Add the table headers
        TableRow headerRow = new TableRow(this);

        headerRow.addView(getHeaderCellTextView("Player"));
        headerRow.addView(getHeaderCellTextView("Stone"));
        headerRow.addView(getHeaderCellTextView("Captures"));
        headerRow.addView(getHeaderCellTextView("Score"));

        playerScoresTable.addView(headerRow);


        for (Player player : players) {
            TableRow row = new TableRow(this);

            TextView playerNameTextView = getTableCellTextView(player.getName());
            TextView playerCapturesTextView = getTableCellTextView(String.valueOf(round.getCaptures(player)));
            TextView playerScoreTextView = getTableCellTextView(String.valueOf(round.getScore(player)));
            Button playerStoneButton = getStoneButton(round.getStone(player));
            // Set the stone button to be unclickable
            playerStoneButton.setClickable(false);

            ConstraintLayout constraintLayout = getButtonView(playerStoneButton);


            row.addView(playerNameTextView);
            row.addView(constraintLayout);
            row.addView(playerCapturesTextView);
            row.addView(playerScoreTextView);

            // If the player is a winner, or is the current player, highlight the row
            if (player == round.getWinner()) {
                row.setBackgroundColor(ContextCompat.getColor(this, R.color.winner_highlight));
            } else if (round.getWinner() == null && player == round.getCurrentPlayer()) {
                row.setBackgroundColor(ContextCompat.getColor(this, R.color.current_player_highlight));
            }

            playerScoresTable.addView(row);
        }


    }

    @NonNull
    private ConstraintLayout getButtonView(Button playerStoneButton) {
        // Create the ConstraintLayout
        ConstraintLayout constraintLayout = new ConstraintLayout(this);
        // Create the button container
        ConstraintLayout buttonContainer = new ConstraintLayout(this);
        buttonContainer.setLayoutParams(new ConstraintLayout.LayoutParams(0, 0));

        // Set the dimension ratio for the FrameLayout
        ConstraintLayout.LayoutParams frameLayoutParams = (ConstraintLayout.LayoutParams) buttonContainer.getLayoutParams();
        frameLayoutParams.dimensionRatio = "H,1:1";
        frameLayoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        frameLayoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        frameLayoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;

        // Add the playerStoneButton to the buttonContainer
        buttonContainer.addView(playerStoneButton);

        // Add FrameLayout to ConstraintLayout
        constraintLayout.addView(buttonContainer);

        return constraintLayout;
    }

    private TextView getHeaderCellTextView(String text) {
        TextView tv = getTableCellTextView(text);

        // Set the text to bold
        tv.setTypeface(null, Typeface.BOLD);

        return tv;
    }

    private TextView getTableCellTextView(String text) {
        TextView tv = new TextView(this);
        // set width and height

        int layoutMargin = (int) getResources().getDimension(R.dimen.table_cell_margin);
        TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(layoutMargin, layoutMargin, layoutMargin, layoutMargin);

        tv.setLayoutParams(params);

        // align the text to the center of the TextView
        tv.setGravity(android.view.Gravity.CENTER);

        tv.setText(text);

        return tv;
    }

    private void initBoard() {
        Round round = MainActivity.pente.getRound();
        Board board = round.getBoard();

        // Get the boardlayout from the xml file
        LinearLayout boardLayout = findViewById(R.id.boardLinearLayout);
        // Clear the board layout
        boardLayout.removeAllViews();


        for (int row = 0; row < board.getNoRows(); row++) {
            LinearLayout rowLayout = createNewRow(board, row);

            // Set height to 0 and weight to 1
            LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
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
            ConstraintLayout buttonView = getButtonView(button);

            Drawable border = ContextCompat.getDrawable(this, R.drawable.board_cell);
            buttonView.setBackground(border);

            // Add padding inside the ConstraintLayout
            buttonView.setPadding(10, 10, 10, 10);

            // set the layout width to 0 and weight to 1
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            buttonView.setLayoutParams(params);
            rowLayout.addView(buttonView);
        }
        return rowLayout;
    }

    @NonNull
    private Button createCellButton(Board board, int row, int col) {
        Position position = new Position(row, col);

        Stone stone = board.get(position);

        // Create a new stone button
        Button button = getStoneButton(stone);

        // Set layout width to 0 and weight to 1
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        button.setLayoutParams(params);

        // Set the tag to the position
        button.setTag(position);

        // Set the on click listener
        button.setOnClickListener(view -> {
            Position pos = (Position) view.getTag();

            // Show the message on the message textview
            TextView messageTextView = findViewById(R.id.messageTextView);

            try {
                // Make the move
                MainActivity.pente = MainActivity.pente.makeMove(pos);

                // Update the round layout
                init();

            } catch (Exception e) {
                // Show the error message on the message textview
                messageTextView.setText(e.getMessage());
                // Make the message text view visible
                messageTextView.setVisibility(View.VISIBLE);
            }


        });

        return button;
    }

    private void handleNextMove() {

        // Check if it is computer's turn
        if (MainActivity.pente.getRound().getCurrentPlayer().equals(Player.COMPUTER)) {
            // Get the best move
            Position bestMove = new Strategy(MainActivity.pente.getRound()).getBestMove();

            // Find the button with the best move's position
            Button button = findViewById(R.id.boardLinearLayout).findViewWithTag(bestMove);

            // Explain the move
            TextView messageTextView = findViewById(R.id.messageTextView);
            String rationale = new Strategy(MainActivity.pente.getRound()).getRationale(bestMove);

            // Simulate a click on the button
            button.performClick();

            messageTextView.setText(String.format("Computer played %s.", MainActivity.pente.getRound().getBoard().positionToString(bestMove)));
            messageTextView.append(String.format("%n%s", rationale));
            messageTextView.setVisibility(View.VISIBLE);
        }
    }

    @NonNull
    private Button getStoneButton(Stone stone) {
        Button button = new Button(this);

        if (stone != null) {
            Drawable stoneShape = ContextCompat.getDrawable(this, R.drawable.stone);

            if (stone == Stone.BLACK) {
                // Set the stone shape color to black
                assert stoneShape != null;
                // Fill the stone shape with black
                stoneShape.setTint(ContextCompat.getColor(this, R.color.black));
            } else if (stone == Stone.WHITE) {
                // Set the stone shape color to white
                assert stoneShape != null;
                stoneShape.setTint(ContextCompat.getColor(this, R.color.white));
            }


            // Add the stone shape to the button foreground
            button.setForeground(stoneShape);
        }
        // Set background to null so that the background color is not changed
        button.setBackground(null);


        return button;
    }
}

