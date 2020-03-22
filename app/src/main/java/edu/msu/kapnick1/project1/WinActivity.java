package edu.msu.kapnick1.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class WinActivity extends AppCompatActivity {

    final static String WINNER_KEY = "winner";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        Intent intent = getIntent();
        String winner = intent.getExtras().getString(WINNER_KEY);
        TextView winnerText = (TextView) this.findViewById(R.id.winner);
        winnerText.setText("Congratulations " + winner + ", You are the winner!");
    }

    public void onFinish(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
