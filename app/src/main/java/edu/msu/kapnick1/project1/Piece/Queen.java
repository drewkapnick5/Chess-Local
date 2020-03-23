package edu.msu.kapnick1.project1.Piece;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import edu.msu.kapnick1.project1.R;

public class Queen extends Piece {

    public Queen(Context context, int id, float initialX, float initialY, boolean white) {
        super(context, id, initialX, initialY, white);
        params.bitmapID = white ? R.drawable.chess_qlt45 : R.drawable.chess_qdt45;
        piece = BitmapFactory.decodeResource(context.getResources(), params.bitmapID);
    }

    public Queen(Context context, Piece.Parameters params) {
        super(context, params);
        params.bitmapID = params.color ? R.drawable.chess_qlt45 : R.drawable.chess_qdt45;
        piece = BitmapFactory.decodeResource(context.getResources(), params.bitmapID);
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
        boolean ur_block = false;
        boolean ul_block = false;
        boolean dr_block = false;
        boolean dl_block = false;

        for(float filler = .125f; filler < 1f; filler += .125f){
            //move up
            if(!(curY - filler < .0625f)){
                if(samePositions.contains(new Pair<>(curX, curY - filler))){
                    u_block = true;
                }
                if(!u_block){
                    poss_moves.add(new Pair<>(curX, curY - filler));
                    if(otherPositions.contains(new Pair<>(curX, curY - filler))){
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
                    if(otherPositions.contains(new Pair<>(curX, curY + filler))){
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
                    if(otherPositions.contains(new Pair<>(curX + filler, curY))){
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
                    if(otherPositions.contains(new Pair<>(curX - filler, curY))){
                        l_block = true;
                    }
                }
            }


            //up-right
            if(!(curY - filler < .0625f || curX + filler > .9375f)){
                if(samePositions.contains(new Pair<>(curX + filler, curY - filler))){
                    ur_block = true;
                }
                if(!ur_block){
                    poss_moves.add(new Pair<>(curX + filler, curY - filler));
                    if(otherPositions.contains(new Pair<>(curX + filler, curY - filler))){
                        ur_block = true;
                    }
                }
            }
            //up-left
            if(!(curY - filler < .0625f || curX - filler < .0625f)){
                if(samePositions.contains(new Pair<>(curX - filler, curY - filler))){
                    ul_block = true;
                }
                if(!ul_block){
                    poss_moves.add(new Pair<>(curX - filler, curY - filler));
                    if(otherPositions.contains(new Pair<>(curX - filler, curY - filler))){
                        ul_block = true;
                    }
                }
            }
            //down-right
            if(!(curY + filler > .9375f || curX + filler > .9375f)){
                if(samePositions.contains(new Pair<>(curX + filler, curY + filler))){
                    dr_block = true;
                }
                if(!dr_block){
                    poss_moves.add(new Pair<>(curX + filler, curY + filler));
                    if(otherPositions.contains(new Pair<>(curX + filler, curY + filler))){
                        dr_block = true;
                    }
                }
            }
            //down-left
            if(!(curY + filler > .9375f || curX - filler < .0625f)){
                if(samePositions.contains(new Pair<>(curX - filler, curY + filler))){
                    dl_block = true;
                }
                if(!dl_block){
                    poss_moves.add(new Pair<>(curX - filler, curY + filler));
                    if(otherPositions.contains(new Pair<>(curX - filler, curY + filler))){
                        dl_block = true;
                    }
                }
            }
        }

        return poss_moves;
    }
}
