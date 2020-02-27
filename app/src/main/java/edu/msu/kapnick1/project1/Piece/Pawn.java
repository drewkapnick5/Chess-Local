package edu.msu.kapnick1.project1.Piece;

import android.content.Context;
import android.graphics.BitmapFactory;

import edu.msu.kapnick1.project1.R;


/**
 * As of now:
 * IMPLEMENTED: Pawns all spawn in and can move up one space, return to original space if let go,
 * and update their next move location
 * NOT IMPLEMENTED: No attack movement, cannot see other pieces, other pieces will be able to land on
 * pawn, initial two space move not currently valid
 */
public class Pawn extends Piece{

    public Pawn(Context context, int id, float initialX, float initialY, boolean white) {
        super( context, id, initialX, initialY, white);
        int pictureID = white ? R.drawable.chess_plt45 : R.drawable.chess_pdt45;
        piece = BitmapFactory.decodeResource(context.getResources(), pictureID);
    }
}
