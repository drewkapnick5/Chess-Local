package edu.msu.kapnick1.project1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;


/**
 * Custom view class for our Game.
 */
public class ChessView extends View {

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return chessBoard.onTouchEvent(this, event);
    }

    /**
     * The actual Chess Board
     */
    private ChessBoard chessBoard;

    private Paint playerPaint;

    private String winner = null;
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
        playerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        playerPaint.setColor(Color.BLACK);
    }

    private int turn;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(chessBoard.getPlayer(), canvas.getWidth()/2, canvas.getHeight()/10, playerPaint);
        chessBoard.draw(canvas);
    }


    public ChessBoard getBoard() {
        return chessBoard;
    }

    /**
     * Save the board to a bundle
     * @param bundle The bundle we save to
     */
    public void saveInstanceState(Bundle bundle) {
//        chessBoard.saveInstanceState(bundle);
        chessBoard.putToBundle("board", bundle);

    }

    /**
     * Read the board from a bundle
     * @param bundle The bundle we save to
     */
    public void loadInstanceState(Bundle bundle) {
//        chessBoard.loadInstanceState(bundle);
        chessBoard.getFromBundle("board", bundle);
    }

    /**
     *
     * @param player
     */
    public void addPlayer(String player) {
//        players.add(player);
        chessBoard.addPlayer(player);
    }

    /**
     *
     */
    public void nextTurn() {
        chessBoard.nextTurn();
        invalidate();
    }

    public void setWinner() {
        winner = chessBoard.getPlayer();
    }


}