package edu.msu.kapnick1.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ChessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_chess);

//        players.add(player1);

    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        getChessView().putToBundle("board", outState);

    }

    public ChessView getChessView() {
        return (ChessView)findViewById(R.id.chessView);
    }

    private ArrayList<Player> players;
}
