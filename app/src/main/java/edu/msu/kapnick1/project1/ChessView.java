package edu.msu.kapnick1.project1;

import android.content.Context;
import android.graphics.Canvas;
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

    /**
     * Players in the game
     */
    private ArrayList<Player> players;

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


<<<<<<< HEAD
=======
    public void putToBundle(String key, Bundle bundle) {
//        bundle.putSerializable(key, this.chessBoard);
    }

    public void getFromBundle(String key, Bundle bundle) {
//        board = (ChessBoard)bundle.getSerializable(key);

    }

>>>>>>> 2b882164d9031090472ae4fb7e4bbd1409747c34
}