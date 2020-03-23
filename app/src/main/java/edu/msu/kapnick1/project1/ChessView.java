package edu.msu.kapnick1.project1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;


/**
 * Custom view class for our Game.
 */
public class ChessView extends View {

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return chessBoard.onTouchEvent(this, event);
    }

    @Override
    public boolean performClick() {

        return super.performClick();
    }

    /**
     * The actual Chess Board
     */
    private ChessBoard chessBoard;

    /**
     * Players in the game
     */
    private ArrayList<String> players = new ArrayList<>();

    public ChessView(Context context) {
        super(context);
        init(null, 0);
    }

    public ChessView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ChessView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        chessBoard = new ChessBoard(getContext(), this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        chessBoard.draw(canvas);
    }

    /**
     * Save the board to a bundle
     * @param bundle The bundle we save to
     */
    public void saveInstanceState(Bundle bundle) {
        chessBoard.putToBundle("board", bundle);

    }

    /**
     * Read the board from a bundle
     * @param bundle The bundle we save to
     */
    public void loadInstanceState(Bundle bundle) {
        chessBoard.getFromBundle("board", bundle, getContext());
    }

    /**
     * Add a player to the game
     * @param player name
     */
    public void addPlayer(String player) {
        chessBoard.addPlayer(player);
    }

    /**
     * Check the orientation of the screen
     * @param orientation
     */
    public void checkOrientation(Boolean orientation){
        chessBoard.checkOrientation(orientation);
    }

    /**
     * Go to the next player's turn
     * @return boolean if the game is won
     */
    public boolean nextTurn() {
        boolean won = chessBoard.nextTurn(this);
        invalidate();
        return won;
    }

    /**
     * Get the name of the players whose turn is next
     * @return Player name
     */
    public String getOtherPlayer() {
        return chessBoard.getOtherPlayer();
    }


}