package edu.msu.kapnick1.project1;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


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
        chessBoard = new ChessBoard(getContext());
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        chessBoard.draw(canvas);
    }


}