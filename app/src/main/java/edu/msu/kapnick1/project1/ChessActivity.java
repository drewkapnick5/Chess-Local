package edu.msu.kapnick1.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ChessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess);

//        players.add(player1);

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

    private ArrayList<Player> players;
}
