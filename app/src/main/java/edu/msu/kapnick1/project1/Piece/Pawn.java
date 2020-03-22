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
        params.bitmapID = white ? R.drawable.chess_plt45 : R.drawable.chess_pdt45;
        piece = BitmapFactory.decodeResource(context.getResources(), params.bitmapID);
    }

    @Override
    public List<Pair> checkMoves(List<Pair> white_positions, List<Pair> black_positions) {
        List<Pair> poss_moves = new ArrayList<>();

        List<Pair> positions = new ArrayList<>(white_positions);
        positions.addAll(black_positions);
//        list.addAll(list2);
        List<Pair> otherPositions = !params.color ? white_positions : black_positions;

        boolean u_block = false;
        boolean d_block = false;

        if (params.color) {
            // White pawn movement
            if(!(params.y - .125f < .0625f)){
                if(!positions.contains(new Pair<>(params.x, params.y - .125f))){
                    poss_moves.add(new Pair<>(params.x, params.y - .125f));
                } else { u_block = true; }
                // Up-right
                if (black_positions.contains(new Pair<>(params.x + .125f, params.y - .125f))) {
                    poss_moves.add(new Pair<>(params.x + .125f, params.y - .125f));
                }
                // Up-left
                if (black_positions.contains(new Pair<>(params.x - .125f, params.y - .125f))) {
                    poss_moves.add(new Pair<>(params.x - .125f, params.y - .125f));
                }

            }
            //determine move from initial position
            if (params.y == .8125f && !u_block){
                if(!positions.contains(new Pair<>(params.x, params.y - .25f))){
                    poss_moves.add(new Pair<>(params.x, params.y - .25f));
                }
            }

        }
        // Black pawn movement
        else {
            if(!(params.y + .125f > .9375f)){
                if(!positions.contains(new Pair<>(params.x, params.y + .125f))){
                    poss_moves.add(new Pair<>(params.x, params.y + .125f));
                } else { d_block = true; }

                // Down-right
                if(white_positions.contains(new Pair<>(params.x + .125f, params.y + .125f))){
                    poss_moves.add(new Pair<>(params.x + .125f, params.y + .125f));
                }
                // Down-left
                if(white_positions.contains(new Pair<>(params.x - .125f, params.y + .125f))){
                    poss_moves.add(new Pair<>(params.x - .125f, params.y + .125f));
                }
            }
            //determine move from initial position
            if (params.y == .1875f && !d_block){
                if(!positions.contains(new Pair<>(params.x, params.y + .25f))){
                    poss_moves.add(new Pair<>(params.x, params.y + .25f));
                }
            }

        }

        return poss_moves;
    }
}
