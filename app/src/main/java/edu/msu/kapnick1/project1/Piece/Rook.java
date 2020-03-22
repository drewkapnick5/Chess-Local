package edu.msu.kapnick1.project1.Piece;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import edu.msu.kapnick1.project1.R;

public class Rook extends Piece {

    public Rook(Context context, int id, float initialX, float initialY, boolean white) {
        super(context, id, initialX, initialY, white);
        int pictureID = white ? R.drawable.chess_rlt45 : R.drawable.chess_rdt45;
        params.piece = BitmapFactory.decodeResource(context.getResources(), pictureID);
    }

    @Override
    public List<Pair> checkMoves(List<Pair> white_positions, List<Pair> black_positions) {
        List<Pair> poss_moves = new ArrayList<>();

        float curX = params.x;
        float curY = params.y;

        List<Pair> samePositions = params.color ? white_positions : black_positions;
        List<Pair> otherPositions = !params.color ? white_positions : black_positions;

        boolean u_block = false;
        boolean d_block = false;
        boolean r_block = false;
        boolean l_block = false;

        for(float filler = .125f; filler < 1f; filler += .125f){
            //move up
            if(!(curY - filler < .0625f)){
                if(samePositions.contains(new Pair<>(curX, curY - filler))){
                    u_block = true;
                }
                if(!u_block){
                    poss_moves.add(new Pair<>(curX, curY - filler));
                    if (otherPositions.contains(new Pair<>(curX, curY - filler))) {
                        u_block = true;
                    }
                }

            }
            //move down
            if(!(curY + filler > .9375f)){
                if(samePositions.contains(new Pair<>(curX, curY + filler))){
                    d_block = true;
                }
                if(!d_block){
                    poss_moves.add(new Pair<>(curX, curY + filler));
                    if (otherPositions.contains(new Pair<>(curX, curY + filler))) {
                        d_block = true;
                    }
                }

            }
            //move right
            if(!(curX + filler > .9375f)){
                if(samePositions.contains(new Pair<>(curX + filler, curY))){
                    r_block = true;
                }
                if(!r_block){
                    poss_moves.add(new Pair<>(curX + filler, curY));
                    if (otherPositions.contains(new Pair<>(curX + filler, curY))) {
                        r_block = true;
                    }
                }
            }
            //move left
            if(!(curX - filler < .0625f)){
                if(samePositions.contains(new Pair<>(curX - filler, curY))){
                    l_block = true;
                }
                if(!l_block){
                    poss_moves.add(new Pair<>(curX - filler, curY));
                    if (otherPositions.contains(new Pair<>(curX - filler, curY))) {
                        l_block = true;
                    }
                }
            }
        }
        return poss_moves;
    }
}
