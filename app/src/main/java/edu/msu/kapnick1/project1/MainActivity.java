package edu.msu.kapnick1.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onStartChess(View view) {
        Intent intent = new Intent(this, ChessActivity.class);
        startActivity(intent);
    }

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

//        builder.create().show();
    }
}
