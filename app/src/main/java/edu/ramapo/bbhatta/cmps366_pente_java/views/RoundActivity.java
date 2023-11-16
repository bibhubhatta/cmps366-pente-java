package edu.ramapo.bbhatta.cmps366_pente_java.views;

import android.content.Intent;
import android.content.res.Resources;
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

import edu.ramapo.bbhatta.cmps366_pente_java.R;
import edu.ramapo.bbhatta.cmps366_pente_java.models.Board;
import edu.ramapo.bbhatta.cmps366_pente_java.models.Player;
import edu.ramapo.bbhatta.cmps366_pente_java.models.Position;
import edu.ramapo.bbhatta.cmps366_pente_java.models.Round;
import edu.ramapo.bbhatta.cmps366_pente_java.models.Stone;
import edu.ramapo.bbhatta.cmps366_pente_java.models.Strategy;

/**
 * Activity for the round screen. Shows the board and the player scores.
 * Also handles the game over state and the next move.
 * Waits for the user to click on a cell to make a move.
 */
public class RoundActivity extends AppCompatActivity {

    private TableLayout playerScoresTable;
    private LinearLayout boardLayout;
    private TextView messageTextView;
    private Button helpButton;
    private Button playBestMoveButton;
    private Button saveGameButton;
    private Button logButton;
    private View continueButton;

    private Resources resources;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);

        resources = getResources();

        findViews();
        init();
    }

    /**
     * Find the views by their id, so that they can be used in the activity.
     */
    private void findViews() {
        playerScoresTable = findViewById(R.id.playerScoresTableLayout);
        boardLayout = findViewById(R.id.boardLinearLayout);
        messageTextView = findViewById(R.id.messageTextView);
        helpButton = findViewById(R.id.helpButton);
        playBestMoveButton = findViewById(R.id.playBestMoveButton);
        saveGameButton = findViewById(R.id.saveGameButton);
        continueButton = findViewById(R.id.continueButton);
        logButton = findViewById(R.id.logButton);
    }

    /**
     * Updates the layout and handles game over and checks if computer plays next.
     * Assistance from: https://stackoverflow.com/questions/45903391/how-to-change-layout-of-fragment-without-recreating-it?rq=1
     * https://stackoverflow.com/questions/72011727/how-to-update-content-of-the-activity-without-creating-new-activity
     */
    private void init() {
        initRoundLayout();
        initBoard();
        initButtons();
        handleGameOver();
        handleNextMove();
    }


    /**
     * Initializes the buttons with the appropriate on click listeners.
     * Assistance from: https://developer.android.com/reference/android/widget/Button
     */
    private void initButtons() {

        // Show the help and save buttons, but hide the play best move button
        helpButton.setVisibility(View.VISIBLE);
        saveGameButton.setVisibility(View.VISIBLE);
        playBestMoveButton.setVisibility(View.GONE);

        // Set the on click listener for the help button
        helpButton.setOnClickListener(view -> {
            Position bestMove = new Strategy(MainActivity.pente.getRound()).getBestMove();
            String bestMoveString = MainActivity.pente.getRound().getBoard().positionToString(bestMove);
            String rationale = new Strategy(MainActivity.pente.getRound()).getRationale(bestMove);

            String message = String.format("The best move is %s.%n%s", bestMoveString, rationale);

            showMessage(message);
            highlightBoardCell(bestMove, R.color.best_move_highlight);

            // Set the tag of the playBestMoveButton to the best move so that the button knows which move to play
            playBestMoveButton.setTag(bestMove);

            // Show the play best move button
            playBestMoveButton.setVisibility(View.VISIBLE);
            // Hide the help button
            helpButton.setVisibility(View.GONE);
        });


        // Set the on click listener for the play best move button
        playBestMoveButton.setOnClickListener(view -> {
            Position pos = (Position) view.getTag();
            // Remove the tag to prevent ambiguity with the actual cell button
            view.setTag(null);
            // Simulate a click on the button
            Button cellButton = boardLayout.findViewWithTag(pos);
            cellButton.performClick();
        });


        // Set the on click listener for the continue button
        continueButton.setOnClickListener(view -> {
            // Start the tournament activity
            Intent intent = new Intent(RoundActivity.this, TournamentActivity.class);
            startActivity(intent);

            finish();
        });

        // Set the on click listener for the save game button
        saveGameButton.setOnClickListener(view -> {
            Intent intent = new Intent(RoundActivity.this, SaveActivity.class);
            startActivity(intent);
        });

        // Set the on click listener for the log button
        logButton.setOnClickListener(view -> {
            Intent intent = new Intent(RoundActivity.this, LogActivity.class);
            startActivity(intent);
        });

    }

    /**
     * Highlights the cell at the given position with the given color.
     * Used for highlighting the best move and the cell that was played.
     * <p>
     * Assistance:
     * https://stackoverflow.com/questions/20743124/setting-transparency-to-buttons-in-android
     * https://stackoverflow.com/questions/3263169/android-how-to-make-view-highlight-when-clicked
     *
     * @param position
     * @param color
     */
    private void highlightBoardCell(Position position, int color) {
        Button button = boardLayout.findViewWithTag(position);

        ConstraintLayout cellToHighlight;
        int opacity;
        if (button.getForeground() != null) {
            cellToHighlight = (ConstraintLayout) button.getParent().getParent();
            opacity = 64;
        } else {
            cellToHighlight = (ConstraintLayout) button.getParent();
            opacity = 255;
        }

        Drawable highlight = ContextCompat.getDrawable(this, color);
        highlight.setAlpha(opacity);
        cellToHighlight.setForeground(ContextCompat.getDrawable(this, color));

    }


    /**
     * Shows the given message in the message textview and adds it to the log by default.
     *
     * @param message
     */
    private void showMessage(String message) {
        showMessage(message, true);
    }

    /**
     * Shows the given message in the message textview and adds it to the log if showInLog is true.
     *
     * @param message
     * @param showInLog Whether to add the message in the log.
     */
    private void showMessage(String message, Boolean showInLog) {
        if (Boolean.TRUE.equals(showInLog)) MainActivity.log.add(message);
        messageTextView.setText(message);
        messageTextView.setVisibility(View.VISIBLE);
    }

    /**
     * Handles the game over state.
     * Assistance from:
     * https://stackoverflow.com/questions/7493287/android-how-do-i-get-string-from-resources-using-its-name
     */
    private void handleGameOver() {
        // If the round is over, show the winner, hide the help and save buttons, and show the continue button
        if (MainActivity.pente.getRound().isOver()) {

            // Disable the boardLayout from being clicked
            setUnclickable(boardLayout);

            // Hide the help and save buttons
            helpButton.setVisibility(View.GONE);
            saveGameButton.setVisibility(View.GONE);

            // Show the continue button
            continueButton.setVisibility(View.VISIBLE);

            // If there is a winner, show the winner
            // Otherwise, show that the round is a draw
            if (MainActivity.pente.getRound().getWinner() != null) {
                String result = String.format("%s wins!", MainActivity.pente.getRound().getWinner().getName());
                showMessage(result);
            } else {
                showMessage(String.valueOf(R.string.the_round_is_a_draw));
            }
        }
    }

    /**
     * Sets the view and all of its children to unclickable.
     * Assistance from <a href="https://stackoverflow.com/a/21107367/">...</a>
     * https://stackoverflow.com/questions/22639218/how-to-get-all-buttons-ids-in-one-time-on-android
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

    /**
     * Initializes the round layout.
     * Shows the player stones, captures, and scores.
     * Highlights the current player and the winner.
     */
    public void initRoundLayout() {
        // Clear the table
        playerScoresTable.removeAllViews();

        // Clear the message textview
        messageTextView.setVisibility(View.GONE);

        Round round = MainActivity.pente.getRound();

        ArrayList<Player> players = (ArrayList<Player>) round.getPlayers();

        // Sort players by score
        players.sort((player1, player2) -> round.getScore(player2) - round.getScore(player1));

        // Add the table headers
        TableRow headerRow = new TableRow(this);

        headerRow.addView(getHeaderCellTextView(resources.getString(R.string.player_name)));
        headerRow.addView(getHeaderCellTextView(resources.getString(R.string.stone)));
        headerRow.addView(getHeaderCellTextView(resources.getString(R.string.captures)));
        headerRow.addView(getHeaderCellTextView(resources.getString(R.string.score)));

        playerScoresTable.addView(headerRow);

        StringBuilder roundScoreLog = new StringBuilder();

        roundScoreLog.append("Round scores:\n");

        for (Player player : players) {
            TableRow row = new TableRow(this);

            TextView playerNameTextView = getTableCellTextView(player.getName());
            TextView playerCapturesTextView = getTableCellTextView(String.valueOf(round.getCaptures(player)));
            TextView playerScoreTextView = getTableCellTextView(String.valueOf(round.getScore(player)));
            TextView playerStoneTextView = getTableCellTextView(getStoneEmoji(round.getStone(player)));

            row.addView(playerNameTextView);
            row.addView(playerStoneTextView);
            row.addView(playerCapturesTextView);
            row.addView(playerScoreTextView);

            // If the player is a winner, or is the current player, highlight the row
            if (round.getWinner() == null && player.equals(round.getCurrentPlayer())) {
                row.setBackgroundColor(ContextCompat.getColor(this, R.color.current_player_highlight));
            } else if (round.getWinner() != null && player.equals(round.getWinner())) {
                row.setBackgroundColor(ContextCompat.getColor(this, R.color.winner_highlight));
            }

            roundScoreLog.append(player.getName()).append(" Captures: ").append(round.getCaptures(player)).append("\n");
            roundScoreLog.append(player.getName()).append(" Score: ").append(round.getScore(player)).append("\n");

            playerScoresTable.addView(row);
        }

        roundScoreLog.append("Current player: ").append(round.getCurrentPlayer().getName()).append("\n");
        MainActivity.log.add(roundScoreLog.toString());


    }

    /**
     * Creates a new ConstraintLayout with the given button as its child.
     * The view is a square so that the board cells are square.
     * Assistance from:
     * https://developer.android.com/develop/ui/views/layout/improving-layouts/reusing-layouts
     * https://stackoverflow.com/questions/12311346/how-to-set-fixed-aspect-ratio-for-a-layout-in-android
     * https://stackoverflow.com/questions/16748124/custom-square-linearlayout-how
     *
     * @param playerStoneButton
     * @return
     */
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

    /**
     * Creates a new TextView for the table cell.
     * Assistance from: https://developer.android.com/develop/ui/views/layout/improving-layouts/reusing-layouts
     * https://developer.android.com/develop/ui/views/layout/improving-layouts/reusing-layouts
     *
     * @param text
     * @return
     */
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

    /**
     * Initializes the board layout.
     * Creates a new board layout with the given board.
     * <p>
     * Assistance from:
     * https://developer.android.com/develop/ui/views/layout/relative
     * https://developer.android.com/develop/ui/views/layout/recyclerview
     * https://developer.android.com/develop/ui/views/layout/recyclerview-custom
     * https://developer.android.com/develop/ui/views/layout/linear
     * https://developer.android.com/reference/android/widget/ArrayAdapter
     * https://stackoverflow.com/questions/14728157/dynamic-gridlayout
     * https://stackoverflow.com/questions/14647810/easier-way-to-get-views-id-string-by-its-id-int
     * https://stackoverflow.com/questions/42860162/imitate-tablelayout-with-constraintlayout
     * https://karishma-agr1996.medium.com/linear-layout-v-s-constraint-layout-6b64e7a08ed7
     * https://stackoverflow.com/questions/58777314/should-i-always-use-constraintlayout-for-everything
     * https://stackoverflow.com/questions/41411721/convert-linearlayout-to-constraintlayout-issue
     * https://www.geeksforgeeks.org/framelayout-in-android/#
     * https://developer.android.com/reference/android/widget/FrameLayout
     * https://stackoverflow.com/questions/6917496/adding-a-view-to-a-linearlayout-at-a-specified-position
     * https://stackoverflow.com/questions/42607556/how-to-add-linear-layout-to-be-always-the-first-element-in-recyclerview
     * https://stackoverflow.com/questions/8698687/android-layout-width-layout-height-how-it-works
     * https://stackoverflow.com/questions/5049852/android-drawing-separator-divider-line-in-layout
     * https://stackoverflow.com/questions/25740594/remove-the-space-between-two-linearlayout
     * https://developer.android.com/develop/ui/views/layout/relative
     * https://developer.android.com/develop/ui/views/layout/recyclerview
     * https://developer.android.com/develop/ui/views/layout/recyclerview-custom
     * https://developer.android.com/develop/ui/views/layout/linear
     * https://developer.android.com/reference/android/widget/ArrayAdapter
     * https://stackoverflow.com/questions/14728157/dynamic-gridlayout
     * https://stackoverflow.com/questions/14647810/easier-way-to-get-views-id-string-by-its-id-int
     * https://developer.android.com/reference/android/widget/GridLayout.LayoutParams
     * https://stackoverflow.com/questions/11307218/gridview-vs-gridlayout-in-android-apps
     * https://copyprogramming.com/howto/dynamically-change-the-number-of-columns-of-a-gridlayoutmanager
     * https://developer.android.com/reference/android/widget/ArrayAdapter
     * https://stackoverflow.com/questions/42340991/how-to-create-a-dynamic-gridview
     * https://stackoverflow.com/questions/44285444/android-studio-creating-board-programmatically
     * https://stackoverflow.com/questions/4259467/how-to-make-space-between-linearlayout-children
     * https://developer.android.com/reference/android/support/v7/widget/LinearLayoutCompat
     * https://stackoverflow.com/questions/24293069/android-when-is-my-view-complete-drawn-and-visible-on-the-display
     * https://stackoverflow.com/questions/3470420/is-it-possible-to-evenly-distribute-buttons-across-the-width-of-a-linearlayout
     * https://stackoverflow.com/questions/66619024/how-to-create-a-square-button-90-degree-angles-android-studio
     * https://stackoverflow.com/questions/2948212/android-layout-with-square-buttons
     */
    private void initBoard() {
        Round round = MainActivity.pente.getRound();
        Board board = round.getBoard();

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

    /**
     * Adds the row number to the left of the row.
     *
     * @param board
     * @param row
     * @param rowLayout
     */
    private void addRowNumberToBoardLayout(Board board, int row, LinearLayout rowLayout) {
        // Add row number to the left of the row
        int displayRow = board.getNoRows() - row;

        TextView rowNumberTextView = new TextView(this);
        rowNumberTextView.setText(String.valueOf(displayRow));
        rowNumberTextView.setGravity(android.view.Gravity.CENTER);
        rowNumberTextView.setLayoutParams(new LinearLayout.LayoutParams(100, ViewGroup.LayoutParams.MATCH_PARENT));

        rowLayout.addView(rowNumberTextView, 0);
    }

    /**
     * Create a row for the column letters.
     *
     * @param board
     * @return The row for the column letters.
     */
    @NonNull
    private LinearLayout getColumnLabelRow(Board board) {
        // Add column letters to the bottom of the board
        LinearLayout columnLettersLayout = new LinearLayout(this);
        columnLettersLayout.setOrientation(LinearLayout.HORIZONTAL);
        columnLettersLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

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

    /**
     * Creates a new row for the board.
     * Assistance from:
     * https://stackoverflow.com/questions/37518745/evenly-spacing-views-using-constraintlayout
     * https://stackoverflow.com/questions/4641072/how-to-set-layout-weight-attribute-dynamically-from-code
     * https://stackoverflow.com/questions/21671356/padding-inside-a-button-element-not-removable
     *
     * @param board
     * @param row
     * @return
     */
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

    /**
     * Creates a new button for the given cell.
     * <p>
     * Assistance from:
     * https://stackoverflow.com/questions/23251068/using-layout-as-a-button
     * https://stackoverflow.com/questions/5646944/how-to-set-shapes-opacity
     * https://stackoverflow.com/questions/15111402/how-can-i-create-a-border-around-an-android-linearlayout
     * <p>
     * https://stackoverflow.com/questions/59166753/how-do-i-create-a-drawable-circle
     * https://stackoverflow.com/questions/3185103/how-to-define-a-circle-shape-in-an-android-xml-drawable-file
     * https://stackoverflow.com/questions/6061387/android-drawable-specifying-shape-width-in-percent-in-the-xml-file
     * <p>
     * https://stackoverflow.com/questions/25883504/android-image-button-how-to-apply-different-sizes-for-the-foreground-and-backg
     *
     * @param board
     * @param row
     * @param col
     * @return
     */
    @NonNull
    private Button createCellButton(Board board, int row, int col) {
        Position position = new Position(row, col);

        Stone stone = board.get(position);

        // Create a new stone button
        Button button = getStoneButton(stone);

        // Set layout width to 0 and weight to 1 so that the buttons are evenly spaced
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        button.setLayoutParams(params);

        // Set the tag to the position
        button.setTag(position);

        // Set the on click listener
        button.setOnClickListener(view -> {
            Position pos = (Position) view.getTag();
            makeMove(pos);
        });

        return button;
    }

    /**
     * Makes a move at the given position and updates the round layout.
     * Also logs the move.
     *
     * @param position
     */
    private void makeMove(Position position) {
        try {
            String logMessage = String.format("%s played %s", MainActivity.pente.getRound().getCurrentPlayer().getName(), MainActivity.pente.getRound().getBoard().positionToString(position));
            MainActivity.pente = MainActivity.pente.makeMove(position);
            MainActivity.log.add(logMessage);
            // Update the round layout
            init();
        } catch (Exception e) {
            showMessage(e.getMessage());
        }
    }

    /**
     * Handles the next move if it is the computer's turn.
     */
    private void handleNextMove() {

        if (MainActivity.pente.getRound().isOver()) return;

        // Check if it is computer's turn
        if (MainActivity.pente.getRound().getCurrentPlayer().equals(Player.COMPUTER)) {
            // Get the best move and rationale
            Strategy strategy = new Strategy(MainActivity.pente.getRound());
            Position bestMove = strategy.getBestMove();
            String rationale = strategy.getRationale(bestMove);

            String logMessage = String.format("Computer is playing %s.%n%s", MainActivity.pente.getRound().getBoard().positionToString(bestMove), rationale);
            MainActivity.log.add(logMessage);

            makeMove(bestMove);

            String message = String.format("Computer played %s.%n%s", MainActivity.pente.getRound().getBoard().positionToString(bestMove), rationale);
            showMessage(message, false);

            // Highlight the cell that was played
            highlightBoardCell(bestMove, R.color.played_cell_highlight);
        }
    }

    /**
     * Returns a new button with the given stone in the foreground.
     * <p>
     * Assistance:
     * https://developer.android.com/develop/ui/views/layout/improving-layouts/reusing-layouts
     * https://stackoverflow.com/questions/39847823/add-stroke-to-a-drawable-with-xml
     * https://stackoverflow.com/questions/54672076/how-to-dynamically-add-stroke-to-drawables
     * <p>
     * https://stackoverflow.com/questions/49639231/constraintlayout-proportional-width-height-to-self
     * https://intellipaat.com/community/26808/set-margins-in-a-linearlayout-programmatically
     * https://stackoverflow.com/questions/11376516/change-drawable-color-programmatically
     * https://www.youtube.com/watch?v=MeCjfgR86MU
     * https://stackoverflow.com/questions/16884524/programmatically-add-border-to-linearlayout
     * https://stackoverflow.com/questions/17823451/set-android-shape-color-programmatically
     * https://stackoverflow.com/questions/65613925/how-can-you-set-the-fill-color-of-a-shape-from-an-imageview-without-affecting-th
     * https://stackoverflow.com/questions/3402787/how-to-have-a-transparent-imagebutton-android
     * <p>
     * https://stackoverflow.com/questions/44249150/set-constraintlayout-width-to-match-parent-width-programmatically
     *
     * @param stone
     * @return
     */
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

    /**
     * Returns the emoji for the given stone.
     * This was originally Ankit's idea to use emojis for the stones.
     *
     * @param stone The stone to get the emoji for.
     * @return The emoji for the given stone.
     */
    String getStoneEmoji(Stone stone) {
        if (stone.equals(Stone.BLACK)) {
            return "⚫";
        } else if (stone.equals(Stone.WHITE)) {
            return "⚪";
        } else {
            return "";
        }
    }
}

