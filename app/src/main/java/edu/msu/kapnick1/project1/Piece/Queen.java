package edu.msu.kapnick1.project1.Piece;

import android.content.Context;
import android.graphics.BitmapFactory;

import edu.msu.kapnick1.project1.R;

public class Queen extends Piece {

    public Queen(Context context, int id, float initialX, float initialY, boolean white) {
        super(context, id, initialX, initialY, white);
        int pictureID = white ? R.drawable.chess_qlt45 : R.drawable.chess_qdt45;
        params.piece = BitmapFactory.decodeResource(context.getResources(), pictureID);
    }
}
