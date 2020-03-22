package edu.msu.kapnick1.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ChessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess);

        Intent intent = getIntent();
        String name1 = intent.getExtras().getString("Player1");
        String name2 = intent.getExtras().getString("Player2");

        name1 = (name1.equals("")) ? "Player1" : name1;
        name2 = (name2.equals("")) ? "Player2" : name2;

        getChessView().addPlayer(name1);
        getChessView().addPlayer(name2);


        if (savedInstanceState != null) {
             getChessView().loadInstanceState(savedInstanceState);
        }

    }

    /**
     * Save the instance state into a bundle
     * @param outState the bundle to save into
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);

        getChessView().saveInstanceState(outState);

    }



    public ChessView getChessView() {
        return (ChessView)this.findViewById(R.id.chessView);
    }

    /**
     * Go to next player turn
     */
    public void nextTurn(View view) {
        if (getChessView().nextTurn()){
            win(getChessView().getOtherPlayer());
        }
    }

    public void resign(View view) {
        win(getChessView().getOtherPlayer());
    }

    public void win(String winner) {
        Intent intent = new Intent(this, WinActivity.class);
        intent.putExtra(WinActivity.WINNER_KEY, winner);

        startActivity(intent);
    }

}
