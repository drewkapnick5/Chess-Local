package edu.msu.kapnick1.project1.Piece;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import edu.msu.kapnick1.project1.R;

public class King extends Piece {

    public King(Context context, int id, float initialX, float initialY, boolean white) {
        super(context, id, initialX, initialY, white);
        int pictureID = white ? R.drawable.chess_klt45 : R.drawable.chess_kdt45;
        params.piece = BitmapFactory.decodeResource(context.getResources(), pictureID);
    }

    @Override
    public List<Pair> checkMoves(List<Pair> white_positions) {
        List<Pair> poss_moves = new ArrayList<>();

        float curX = params.x;
        float curY = params.y;

        if(!(curY - .125f < .0625f)){
            if(!white_positions.contains(new Pair<>(curX, curY + .125f))){
                poss_moves.add(new Pair<>(curX, curY - .125f));
            }
        }
        //move down
        if(!(curY + .125f > .9375f)){
            if(!white_positions.contains(new Pair<>(curX, curY + .125f))){
                poss_moves.add(new Pair<>(curX, curY + .125f));
            }
        }
        //move right
        if(!(curX + .125f > .9375f)){
            if(!white_positions.contains(new Pair<>(curX + .125f, curY))){
                poss_moves.add(new Pair<>(curX + .125f, curY));
            }
        }
        //move left
        if(!(curX - .125f < .0625f)){
            if(!white_positions.contains(new Pair<>(curX - .125f, curY))){
                poss_moves.add(new Pair<>(curX - .125f, curY));
            }
        }

        //up-right
        if(!(curY - .125f < .0625f || curX + .125f > .9375f)){
            if(!white_positions.contains(new Pair<>(curX + .125f, curY - .125f))){
                poss_moves.add(new Pair<>(curX + .125f, curY - .125f));
            }
        }
        //up-left
        if(!(curY - .125f < .0625f || curX - .125f < .0625f)){
            if(!white_positions.contains(new Pair<>(curX - .125f, curY - .125f))){
                poss_moves.add(new Pair<>(curX - .125f, curY - .125f));
            }
        }
        //down-right
        if(!(curY + .125f > .9375f || curX + .125f > .9375f)){
            if(!white_positions.contains(new Pair<>(curX + .125f, curY + .125f))){
                poss_moves.add(new Pair<>(curX + .125f, curY + .125f));
            }
        }
        //down-left
        if(!(curY + .125f > .9375f || curX - .125f < .0625f)){
            if(!white_positions.contains(new Pair<>(curX - .125f, curY + .125f))){
                poss_moves.add(new Pair<>(curX - .125f, curY + .125f));
            }
        }

        return poss_moves;
    }
}
