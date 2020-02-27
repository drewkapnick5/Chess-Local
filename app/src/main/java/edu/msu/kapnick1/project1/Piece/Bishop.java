package edu.msu.kapnick1.project1.Piece;

import android.content.Context;
import android.graphics.BitmapFactory;

import edu.msu.kapnick1.project1.R;

public class Bishop extends Piece {

    public Bishop(Context context, int id, float initialX, float initialY, boolean white) {
        super(context, id, initialX, initialY, white);
        int pictureID = white ? R.drawable.chess_plt45 : R.drawable.chess_pdt45;
        piece = BitmapFactory.decodeResource(context.getResources(), pictureID);
    }
}
