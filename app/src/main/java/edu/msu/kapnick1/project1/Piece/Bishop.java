package edu.msu.kapnick1.project1.Piece;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import edu.msu.kapnick1.project1.R;

public class Bishop extends Piece {

    public Bishop(Context context, int id, float initialX, float initialY, boolean white) {
        super(context, id, initialX, initialY, white);
        int pictureID = white ? R.drawable.chess_blt45 : R.drawable.chess_bdt45;
        params.piece = BitmapFactory.decodeResource(context.getResources(), pictureID);
    }

    @Override
    public List<Pair> checkMoves(List<Pair> white_positions) {
        List<Pair> poss_moves = new ArrayList<>();

        float curX = params.x;
        float curY = params.y;
        // Blocked positions
        boolean ur_block = false;
        boolean ul_block = false;
        boolean dr_block = false;
        boolean dl_block = false;

        for(float filler = .125f; filler < 1f; filler += .125f){
            //up-right
            if(!(curY - filler < .0625f || curX + filler > .9375f)){
                if(white_positions.contains(new Pair<>(curX + filler, curY - filler))){
                    ur_block = true;
                }
                if(!ur_block){
                    poss_moves.add(new Pair<>(curX + filler, curY - filler));
                }
            }
            //up-left
            if(!(curY - filler < .0625f || curX - filler < .0625f)){
                if(white_positions.contains(new Pair<>(curX - filler, curY - filler))){
                    ul_block = true;
                }
                if(!ul_block){
                    poss_moves.add(new Pair<>(curX - filler, curY - filler));
                }
            }
            //down-right
            if(!(curY + filler > .9375f || curX + filler > .9375f)){
                if(white_positions.contains(new Pair<>(curX + filler, curY + filler))){
                    dr_block = true;
                }
                if(!dr_block){
                    poss_moves.add(new Pair<>(curX + filler, curY + filler));
                }
            }
            //down-left
            if(!(curY + filler > .9375f || curX - filler < .0625f)){
                if(white_positions.contains(new Pair<>(curX - filler, curY + filler))){
                    dl_block = true;
                }
                if(!dl_block){
                    poss_moves.add(new Pair<>(curX - filler, curY + filler));
                }
            }
        }

        return poss_moves;
    }
}
