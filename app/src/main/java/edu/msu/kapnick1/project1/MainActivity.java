package edu.msu.kapnick1.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    final static String PLAYER1 = "Player1";
    final static String PLAYER2 = "Player2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Start the actual game
     * @param view current view
     */
    public void onStartChess(View view) {
        EditText edit = (EditText)findViewById(R.id.editPlayer1);
        EditText edit2 = (EditText)findViewById(R.id.editPlayer2);

        String name1 = edit.getText().toString();
        String name2 = edit2.getText().toString();

        Intent intent = new Intent(this, ChessActivity.class);
        intent.putExtra(PLAYER1, name1);
        intent.putExtra(PLAYER2, name2);
        startActivity(intent);
    }

    /**
     * Pull up instructions dialog
     * @param view
     */
    public void onInstructions(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

        builder.setTitle(R.string.instructions)
                .setMessage("Chess is a two-player board game utilizing a chessboard and sixteen " +
                        "pieces of six types for each player. Each type of piece moves in a " +
                        "distinct way. The goal of the game is to checkmate " +
                        "(threaten with inescapable capture) the opponent's king. " +
                        "Games do not necessarily end in checkmate; players often resign if they " +
                        "believe they will lose. A game can also end in a draw in several ways."
                )
                .create()
                .show();
    }


}
