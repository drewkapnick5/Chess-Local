package edu.msu.kapnick1.project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ChessBoard {

    /**
     * Percentage of the display width or height that
     * is occupied by the chess board.
     */
    final static float SCALE_IN_VIEW = 0.95f;

    /**
     * Paint for outlining the area the board is in
     */
    private Paint outlinePaint;

    /**
     * Completed board bitmap
     */
    private Bitmap emptyBoard;

    /**
     * Collection of white pawn pieces
     */
    public ArrayList<Pawn> white_pawns = new ArrayList<Pawn>();

    /**
     * Designations for rows and columns
     */
    final static float row_1 = 0.0625f *15;
    final static float row_2 = 0.0625f *13;
    final static float row_3 = 0.0625f *11;
    final static float row_4 = 0.0625f *9;
    final static float row_5 = 0.0625f *7;
    final static float row_6 = 0.0625f *5;
    final static float row_7 = 0.0625f *3;
    final static float row_8 = 0.0625f;
    final static float col_h = 0.0625f *15;
    final static float col_g = 0.0625f *13;
    final static float col_f = 0.0625f *11;
    final static float col_e = 0.0625f *9;
    final static float col_d = 0.0625f *7;
    final static float col_c = 0.0625f *5;
    final static float col_b = 0.0625f *3;
    final static float col_a = 0.0625f;

    private int a = 1;
    private int b = 2;
    private int c = 3;
    private int d = 4;
    private int e = 5;
    private int f = 6;
    private int g = 7;
    private int h = 8;


    private float col_init = .0625f;
    private float row_init = .9375f;

    float [] rows = new float[9];
    float [] columns = new float[9];



    /**
     * The size of the board in pixels
     */
    private int boardSize;

    /**
     * How much we scale the chess pieces
     */
    private float scaleFactor;

    /**
     * Left margin in pixels
     */
    private int marginX;

    /**
     * Top margin in pixels
     */
    private int marginY;

    /**
     * This variable is set to a piece we are dragging. If
     * we are not dragging, the variable is null.
     */
    private Pawn dragging = null;

    /**
     * Most recent relative X touch when dragging
     */
    private float lastRelX;

    /**
     * Most recent relative Y touch when dragging
     */
    private float lastRelY;

    /**
     * The name of the bundle keys to save the puzzle
     */
    private final static String LOCATIONS = "ChessBoard.locations";
    private final static String IDS = "ChessBoard.ids";

    public ChessBoard(Context context) {
        // Load the empty chess board image
        emptyBoard = BitmapFactory.decodeResource(context.getResources(), R.drawable.chess_board);

        // outline box for board
        outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outlinePaint.setStyle(Paint.Style.STROKE);
        outlinePaint.setColor(Color.BLACK);
        outlinePaint.setStrokeWidth(5);


        for (int i = 1; i < 9; i++) {
            columns[i] = col_init;
            rows[i] = row_init;
            col_init += .125f;
            row_init -= .125f;
        }

//        white_pawns.add(new Pawn(context, R.drawable.chess_plt45, 1, columns[a], rows[2], columns[a], rows[3]));
//        white_pawns.add(new Pawn(context, R.drawable.chess_plt45, 2, columns[b], rows[2], columns[b], rows[3]));

        for (int i = 1; i < 9; i++) {
            // Load the chess pawns
            white_pawns.add(new Pawn(context, R.drawable.chess_plt45, i , columns[i], rows[2], columns[i], rows[3]));
        }



    }

    public void draw(Canvas canvas) {
        int wid = canvas.getWidth();
        int hit = canvas.getHeight();

        // Determine the minimum of the two dimensions
        int minDim = wid < hit ? wid : hit;

        boardSize = (int)(minDim * SCALE_IN_VIEW);

        // Compute the margins so we center the board
        marginX = (wid - boardSize) / 2;
        marginY = (hit - boardSize) / 2;

        scaleFactor = (float)boardSize /
                (float)emptyBoard.getWidth();

        // Draw the outline around board and empty board
        canvas.drawRect(marginX, marginY, marginX + boardSize, marginY + boardSize, outlinePaint);
        canvas.save();
        canvas.translate(marginX, marginY);
        canvas.scale(scaleFactor, scaleFactor);
        canvas.drawBitmap(emptyBoard, 0, 0, null);
        canvas.restore();

        for(Pawn pawn : white_pawns) {
            pawn.draw(canvas, marginX, marginY, boardSize, scaleFactor);
        }

    }

    /**
     * Handle a touch event from the view.
     * @param view The view that is the source of the touch
     * @param event The motion event describing the touch
     * @return true if the touch is handled.
     */
    public boolean onTouchEvent(View view, MotionEvent event) {
        // Convert an x,y location to a relative location in the board
        float relX = (event.getX() - marginX) / boardSize;
        float relY = (event.getY() - marginY) / boardSize;

        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:
                return onTouched(relX, relY);

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                return onReleased(view, relX, relY);

            case MotionEvent.ACTION_MOVE:
                // If we are dragging, move the piece and force a redraw
                if(dragging != null) {
                    dragging.move(relX - lastRelX, relY - lastRelY);
                    lastRelX = relX;
                    lastRelY = relY;
                    view.invalidate();
                    return true;
                }
        }
        return false;
    }

    /**
     * Handle a touch message. This is when we get an initial touch
     * @param x x location for the touch, relative to the puzzle - 0 to 1 over the puzzle
     * @param y y location for the touch, relative to the puzzle - 0 to 1 over the puzzle
     * @return true if the touch is handled
     */
    private boolean onTouched(float x, float y) {

        // Check each piece to see if it has been hit
        // We do this in reverse order so we find the pieces in front
        for(int p=white_pawns.size()-1; p>=0;  p--) {
            if(white_pawns.get(p).hit(x, y, boardSize, scaleFactor/2)) {
                // We hit a piece!
                dragging = white_pawns.get(p);

                //Code implemented to solve drawing order problem
                white_pawns.remove(dragging);
                white_pawns.add(white_pawns.size(), dragging);

                lastRelX = x;
                lastRelY = y;

                return true;
            }
        }

        return false;
    }

    /**
     * Handle a release of a touch message.
     * @param x x location for the touch release, relative to the board - 0 to 1 over the board
     * @param y y location for the touch release, relative to the board - 0 to 1 over the board
     * @return true if the touch is handled
     */
    private boolean onReleased(View view, float x, float y) {

        if(dragging != null) {
            if(dragging.maybeSnap()) {
                // We have snapped into a valid move
                view.invalidate();

            }
            view.invalidate();
            dragging = null;
            return true;
        }

        return false;
    }


}
