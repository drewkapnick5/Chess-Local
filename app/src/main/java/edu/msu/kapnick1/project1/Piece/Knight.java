package edu.msu.kapnick1.project1.Piece;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import edu.msu.kapnick1.project1.R;

public class Knight extends Piece {

    public Knight(Context context, int id, float initialX, float initialY, boolean white) {
        super(context, id, initialX, initialY, white);
        int pictureID = white ? R.drawable.chess_nlt45 : R.drawable.chess_ndt45;
        params.piece = BitmapFactory.decodeResource(context.getResources(), pictureID);
    }


    @Override
    public List<Pair> checkMoves(List<Pair> white_positions, List<Pair> black_positions) {
        List<Pair> poss_moves = new ArrayList<>();
        float curY = params.y;
        float curX = params.x;

        List<Pair> positions = params.color ? white_positions : black_positions;

        //up2-right
        if(!(curY - 2*.125f < .0625f || curX + .125f > .9375f)){
            if(!positions.contains(new Pair<>(curX + .125f, curY - 2*.125f))){
                poss_moves.add(new Pair<>(curX + .125f, curY - 2*.125f));
            }
//            u_block = false;
        }
        //up-right2
        if(!(curY - .125f < .0625f || curX + 2*.125f > .9375f)){
            if(!positions.contains(new Pair<>(curX + 2*.125f, curY - .125f))){
                poss_moves.add(new Pair<>(curX + 2*.125f, curY - .125f));
            }
//            r_block = false;
        }
        //up2-left
        if(!(curY - 2*.125f < .0625f || curX - .125f < .0625f)){
            if(!positions.contains(new Pair<>(curX - .125f, curY - 2*.125f))){
                poss_moves.add(new Pair<>(curX - .125f, curY - 2*.125f));
            }
//            l_block = false;
        }
        //up-left2
        if(!(curY - .125f < .0625f || curX - 2*.125f < .0625f)){
            if(!positions.contains(new Pair<>(curX - 2*.125f, curY - .125f))){
                poss_moves.add(new Pair<>(curX - 2*.125f, curY - .125f));
            }
//            d_block = false;
        }
        //down2-right
        if(!(curY + 2*.125f > .9375f || curX + .125f > .9375f)){
            if(!positions.contains(new Pair<>(curX + .125f, curY + 2*.125f))){
                poss_moves.add(new Pair<>(curX + .125f, curY + 2*.125f));
            }
//            ur_block = false;
        }
        //down-right2
        if(!(curY + .125f > .9375f || curX + 2*.125f > .9375f)){
            if(!positions.contains(new Pair<>(curX + 2*.125f, curY + .125f))){
                poss_moves.add(new Pair<>(curX + 2*.125f, curY + .125f));
            }
//            ul_block = false;
        }
        //down2-left
        if(!(curY + 2*.125f > .9375f || curX - .125f < .0625f)){
            if(!positions.contains(new Pair<>(curX - .125f, curY + 2*.125f))){
                poss_moves.add(new Pair<>(curX - .125f, curY + 2*.125f));
            }
//            dr_block = false;
        }
        //down-left2
        if(!(curY + .125f > .9375f || curX - 2*.125f < .0625f)){
            if(!positions.contains(new Pair<>(curX - 2*.125f, curY + .125f))){
//                dl_block = true;
//            }
//            if(!dl_block){
                poss_moves.add(new Pair<>(curX - 2*.125f, curY + .125f));
            }
//            dl_block = false;
        }

        return poss_moves;
    }
}
