package edu.msu.kapnick1.project1.Piece;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

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
        params.piece = BitmapFactory.decodeResource(context.getResources(), pictureID);
    }

    @Override
    public List<Pair> checkMoves(List<Pair> positions, List<Pair> black_positions) {
        List<Pair> poss_moves = new ArrayList<>();
        if (params.color) {
            if(!(params.y - .125f < .0625f)){
                if(!positions.contains(new Pair<>(params.x, params.y - .125f))){
                    poss_moves.add(new Pair<>(params.x, params.y - .125f));
                }
            }
            //determine move from initial position
            if (params.y == .8125f){
                if(!positions.contains(new Pair<>(params.x, params.y - .25f))){
                    poss_moves.add(new Pair<>(params.x, params.y - .25f));
                }
            }
        } else {
            if(!(params.y + .125f > .9375f)){
                if(!positions.contains(new Pair<>(params.x, params.y + .125f))){
                    poss_moves.add(new Pair<>(params.x, params.y + .125f));
                }
            }
            //determine move from initial position
            if (params.y == .1875f){
                if(!positions.contains(new Pair<>(params.x, params.y + .25f))){
                    poss_moves.add(new Pair<>(params.x, params.y + .25f));
                }
            }
        }

        return poss_moves;
    }
}
