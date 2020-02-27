package edu.msu.kapnick1.project1.Piece;

import android.content.Context;
import android.graphics.BitmapFactory;

import edu.msu.kapnick1.project1.R;

public class Knight extends Piece {

    public Knight(Context context, int id, float initialX, float initialY, boolean white) {
        super(context, id, initialX, initialY, white);
        int pictureID = white ? R.drawable.chess_nlt45 : R.drawable.chess_ndt45;
        piece = BitmapFactory.decodeResource(context.getResources(), pictureID);
    }
}
