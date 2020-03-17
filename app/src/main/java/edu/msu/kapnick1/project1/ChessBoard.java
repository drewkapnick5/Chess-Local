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


import java.io.Serializable;

import edu.msu.kapnick1.project1.Piece.Bishop;
import edu.msu.kapnick1.project1.Piece.King;
import edu.msu.kapnick1.project1.Piece.Knight;
import edu.msu.kapnick1.project1.Piece.Pawn;
import edu.msu.kapnick1.project1.Piece.Piece;
import edu.msu.kapnick1.project1.Piece.Queen;
import edu.msu.kapnick1.project1.Piece.Rook;


public class ChessBoard {

    /**
     * Percentage of the display width or height that
     * is occupied by the chess board.
     */
    final static float SCALE_IN_VIEW = 0.95f;

    /**
     * Reflection constants to switch between black and white
     */
    int[] reflect = {7,0};

    /**
     * Board size in tiles
     */
    private static int BOARD_SIZE = 8;

    /**
     * Number of pieces
     */
    private static int PIECE_COUNT = 32;

    /**
     * Board Reset initial values
     */
    private static int initTopRow = 1;
    private static int initBottomRow = 0;
    private static int initRookColumn = 0;
    private static int initKnightColumn = 1;
    private static int initBishopColumn = 2;
    private static int initQueenColumn = 3;

    private static String LOCATIONS = "board.Locations";

    /**
     * Initial center of tile
     */
    private static float col_init = .0625f;
    private static float row_init = .0625f;

    /**
     * Corresponding relative location of each tile
     */
    private float[] rows = new float[BOARD_SIZE];
    private float[] columns = new float[BOARD_SIZE];

    /**
     * Paint for outlining the area the board is in
     */
    private Paint outlinePaint;

    /**
     * Completed board bitmap
     */
    private Bitmap emptyBoard;

    /**
     * Collection of all active pieces
     */
    private Piece[] pieces = new Piece[PIECE_COUNT];

    /**
     * Internal representation of board and pieces
     */
    private Piece[][] board = new Piece[BOARD_SIZE][BOARD_SIZE];

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
    private Piece dragging = null;

    /**
     * Most recent relative X touch when dragging
     */
    private float lastRelX;

    /**
     * Most recent relative Y touch when dragging
     */
    private float lastRelY;

    private View view;

    List<Float> poss_moves = new ArrayList<>();



    public ChessBoard(Context context, View v) {
        // Load the empty chess board image
        emptyBoard = BitmapFactory.decodeResource(context.getResources(), R.drawable.chess_board);

        // outline box for board
        outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outlinePaint.setStyle(Paint.Style.STROKE);
        outlinePaint.setColor(Color.BLACK);
        outlinePaint.setStrokeWidth(5);


        for (int i = 0; i < 8; i++) {
            columns[i] = col_init;
            rows[i] = row_init;
            col_init += .125f;
            row_init += .125f;
        }

        initializeBoard(context);
        view = v;

    }

    public void initializeBoard(Context context) {
        int idCount = 0;
        boolean color = true;

        for (int i = 0; i < 2; i++) {
            // Pawns
            for (int j = 0; j < 8; j++) {
                pieces[idCount] = new Pawn(context, idCount, 0, 0, color);
                idCount++;
            }
            // Rooks
            for (int j = 0; j < 2; j++) {
                pieces[idCount] = new Rook(context, idCount, 0, 0, color);
                idCount++;
            }
            // Knight
            for (int j = 0; j < 2; j++){
                pieces[idCount] = new Knight(context, idCount, 0, 0, color);
                idCount++;
            }
            // Bishop
            for (int j = 0; j < 2; j++){
                pieces[idCount] = new Bishop(context, idCount, 0, 0, color);
                idCount++;
            }
            // King/Queen
            pieces[idCount] = new Queen(context, idCount, 0, 0, color);
            idCount++;
            pieces[idCount] = new King(context, idCount, 0, 0, color);
            idCount++;

            color = !color;
        }

        resetBoard();
    }

    public void resetBoard() {
        int index = 0;

        for (int rowConstant : reflect) {
            // Pawns
            for (int i = 0; i < 8; i++) {
                int col = i;
                int row = Math.abs(rowConstant - initTopRow);
                pieces[index].setX(columns[col]);
                pieces[index].setY(rows[row]);
                board[row][col] = pieces[index];
                index++;
            }
            // Rooks
            for (int colConstant : reflect) {
                int col = Math.abs(colConstant - initRookColumn);
                int row = Math.abs(rowConstant - initBottomRow);
                pieces[index].setX(columns[col]);
                pieces[index].setY(rows[row]);
                board[row][col] = pieces[index];
                index++;
            }
            // Knights
            for (int colConstant : reflect) {
                int col = Math.abs(colConstant - initKnightColumn);
                int row = Math.abs(rowConstant - initBottomRow);
                pieces[index].setX(columns[col]);
                pieces[index].setY(rows[row]);
                board[row][col] = pieces[index];
                index++;
            }
            // Bishops
            for (int colConstant : reflect) {
                int col = Math.abs(colConstant - initBishopColumn);
                int row = Math.abs(rowConstant - initBottomRow);
                pieces[index].setX(columns[col]);
                pieces[index].setY(rows[row]);
                board[row][col] = pieces[index];
                index++;
            }
            // King/Queen
            for (int i = 0; i < 2; i++) {
                int col = initQueenColumn + i;
                int row = Math.abs(rowConstant - initBottomRow);
                pieces[index].setX(columns[col]);
                pieces[index].setY(rows[row]);
                board[row][col] = pieces[index];
                index++;
            }

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

        for(Piece piece : pieces) {
            piece.draw(canvas, marginX, marginY, boardSize, scaleFactor);
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
        for(int p=pieces.length-1; p>=0;  p--) {
            if(pieces[p].hit(x, y, boardSize, scaleFactor/2)) {
                // We hit a piece!
                dragging = pieces[p];
                poss_moves = calc_moves(dragging);


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
            if(dragging.maybeSnap(poss_moves)) {
                // We have snapped into a valid move
                view.invalidate();

            }
            view.invalidate();
            dragging = null;
            poss_moves.clear();
            return true;
        }

        return false;
    }

    private List<Float> calc_moves(Piece dragging){
        /**
         * Pawn moves
         */
        // Calculates white pawn movement
        if (dragging.getId() < 8){
            float curX = dragging.getX();
            float curY = dragging.getY();
            //move up
            if(curY - .125f < .0625f){
            }else {
                poss_moves.add(curX);
                poss_moves.add(curY - .125f);
            }
            if (dragging.getY() == .8125f){
                poss_moves.add(curX);
                poss_moves.add(curY - .25f);
            }
        }
        // Calculates black pawn movement
        if (dragging.getId() > 15 && dragging.getId() < 24){
            float curX = dragging.getX();
            float curY = dragging.getY();
            //move down
            if(curY + .125f > .9375f){
            }else {
                poss_moves.add(curX);
                poss_moves.add(curY + .125f);
            }
            if (dragging.getY() == .1875f){
                poss_moves.add(dragging.getX());
                poss_moves.add(dragging.getY() + .25f);
            }
        }

        /**
         * Rook moves
         */
        // Calculates white rook movement
        if (dragging.getId()  > 7 && dragging.getId() < 10){
            float curX = dragging.getX();
            float curY = dragging.getY();
            for(float filler = .125f; filler < 1f; filler += .125f){
                //move up
                if(curY - filler < .0625f){
                }else {
                    poss_moves.add(curX);
                    poss_moves.add(curY - filler);
                }
                //move down
                if(curY + filler > .9375f){
                }else {
                    poss_moves.add(curX);
                    poss_moves.add(curY + filler);
                }
                //move right
                if(curX + filler > .9375f){
                }else {
                    poss_moves.add(curX + filler);
                    poss_moves.add(curY);
                }
                //move left
                if(curX - filler < .0625f){
                }else {
                    poss_moves.add(curX - filler);
                    poss_moves.add(curY);
                }
            }

        }
        // Calculates black rook movement
        if (dragging.getId()  > 23 && dragging.getId() < 26){
            float curX = dragging.getX();
            float curY = dragging.getY();
            for(float filler = .125f; filler < 1f; filler += .125f){
                //move up
                if(curY - filler < .0625f){
                }else {
                    poss_moves.add(curX);
                    poss_moves.add(curY - filler);
                }
                //move down
                if(curY + filler > .9375f){
                }else {
                    poss_moves.add(curX);
                    poss_moves.add(curY + filler);
                }
                //move right
                if(curX + filler > .9375f){
                }else {
                    poss_moves.add(curX + filler);
                    poss_moves.add(curY);
                }
                //move left
                if(curX - filler < .0625f){
                }else {
                    poss_moves.add(curX - filler);
                    poss_moves.add(curY);
                }
            }
        }

        /**
         * Bishop moves
         */
        // Calculates white bishop movement
        if (dragging.getId()  > 11 && dragging.getId() < 14){
            float curX = dragging.getX();
            float curY = dragging.getY();
            for(float filler = .125f; filler < 1f; filler += .125f){
                //up-right
                if(curY - filler < .0625f || curX + filler > .9375f){
                }else {
                    poss_moves.add(curX + filler);
                    poss_moves.add(curY - filler);
                }
                //up-left
                if(curY - filler < .0625f || curX - filler < .0625f){
                }else {
                    poss_moves.add(curX - filler);
                    poss_moves.add(curY - filler);
                }
                //down-right
                if(curY + filler > .9375f || curX + filler > .9375f){
                }else {
                    poss_moves.add(curX + filler);
                    poss_moves.add(curY + filler);
                }
                //down-left
                if(curY + filler > .9375f || curX - filler < .0625f){
                }else {
                    poss_moves.add(curX - filler);
                    poss_moves.add(curY + filler);
                }
            }
        }
        // Calculates black bishop movement
        if (dragging.getId()  > 27 && dragging.getId() < 30){
            float curX = dragging.getX();
            float curY = dragging.getY();
            for(float filler = .125f; filler < 1f; filler += .125f){
                //up-right
                if(curY - filler < .0625f || curX + filler > .9375f){
                }else {
                    poss_moves.add(curX + filler);
                    poss_moves.add(curY - filler);
                }
                //up-left
                if(curY - filler < .0625f || curX - filler < .0625f){
                }else {
                    poss_moves.add(curX - filler);
                    poss_moves.add(curY - filler);
                }
                //down-right
                if(curY + filler > .9375f || curX + filler > .9375f){
                }else {
                    poss_moves.add(curX + filler);
                    poss_moves.add(curY + filler);
                }
                //down-left
                if(curY + filler > .9375f || curX - filler < .0625f){
                }else {
                    poss_moves.add(curX - filler);
                    poss_moves.add(curY + filler);
                }
            }
        }

        /**
         * Knight moves
         */
        // Calculates white knight movement
        if (dragging.getId()  > 9 && dragging.getId() < 12){
            float curX = dragging.getX();
            float curY = dragging.getY();
            //up2-right
            if(curY - 2*.125f < .0625f || curX + .125f > .9375f){
            }else {
                poss_moves.add(curX + .125f);
                poss_moves.add(curY - 2*.125f);
            }
            //up-right2
            if(curY - .125f < .0625f || curX + 2*.125f > .9375f){
            }else {
                poss_moves.add(curX + 2*.125f);
                poss_moves.add(curY - .125f);
            }
            //up2-left
            if(curY - 2*.125f < .0625f || curX - .125f < .0625f){
            }else {
                poss_moves.add(curX - .125f);
                poss_moves.add(curY - 2*.125f);
            }
            //up-left2
            if(curY - .125f < .0625f || curX - 2*.125f < .0625f){
            }else {
                poss_moves.add(curX - 2*.125f);
                poss_moves.add(curY - .125f);
            }
            //down2-right
            if(curY + 2*.125f > .9375f || curX + .125f > .9375f){
            }else {
                poss_moves.add(curX + .125f);
                poss_moves.add(curY + 2*.125f);
            }
            //down-right2
            if(curY + .125f > .9375f || curX + 2*.125f > .9375f){
            }else {
                poss_moves.add(curX + 2*.125f);
                poss_moves.add(curY + .125f);
            }
            //down2-left
            if(curY + 2*.125f > .9375f || curX - .125f < .0625f){
            }else {
                poss_moves.add(curX - .125f);
                poss_moves.add(curY + 2*.125f);
            }
            //down-left2
            if(curY + .125f > .9375f || curX - 2*.125f < .0625f){
            }else {
                poss_moves.add(curX - 2*.125f);
                poss_moves.add(curY + .125f);
            }
        }
        // Calculates black knight movement
        if (dragging.getId()  > 25 && dragging.getId() < 28){
            float curX = dragging.getX();
            float curY = dragging.getY();
            //up2-right
            if(curY - 2*.125f < .0625f || curX + .125f > .9375f){
            }else {
                poss_moves.add(curX + .125f);
                poss_moves.add(curY - 2*.125f);
            }
            //up-right2
            if(curY - .125f < .0625f || curX + 2*.125f > .9375f){
            }else {
                poss_moves.add(curX + 2*.125f);
                poss_moves.add(curY - .125f);
            }
            //up2-left
            if(curY - 2*.125f < .0625f || curX - .125f < .0625f){
            }else {
                poss_moves.add(curX - .125f);
                poss_moves.add(curY - 2*.125f);
            }
            //up-left2
            if(curY - .125f < .0625f || curX - 2*.125f < .0625f){
            }else {
                poss_moves.add(curX - 2*.125f);
                poss_moves.add(curY - .125f);
            }
            //down2-right
            if(curY + 2*.125f > .9375f || curX + .125f > .9375f){
            }else {
                poss_moves.add(curX + .125f);
                poss_moves.add(curY + 2*.125f);
            }
            //down-right2
            if(curY + .125f > .9375f || curX + 2*.125f > .9375f){
            }else {
                poss_moves.add(curX + 2*.125f);
                poss_moves.add(curY + .125f);
            }
            //down2-left
            if(curY + 2*.125f > .9375f || curX - .125f < .0625f){
            }else {
                poss_moves.add(curX - .125f);
                poss_moves.add(curY + 2*.125f);
            }
            //down-left2
            if(curY + .125f > .9375f || curX - 2*.125f < .0625f){
            }else {
                poss_moves.add(curX - 2*.125f);
                poss_moves.add(curY + .125f);
            }
        }

        /**
         * Queen moves
         */
        // Calculates white queen movement
        if (dragging.getId()  == 14){
            float curX = dragging.getX();
            float curY = dragging.getY();
            for(float filler = .125f; filler < 1f; filler += .125f){
                //move up
                if(curY - filler < .0625f){
                }else {
                    poss_moves.add(curX);
                    poss_moves.add(curY - filler);
                }
                //move down
                if(curY + filler > .9375f){
                }else {
                    poss_moves.add(curX);
                    poss_moves.add(curY + filler);
                }
                //move right
                if(curX + filler > .9375f){
                }else {
                    poss_moves.add(curX + filler);
                    poss_moves.add(curY);
                }
                //move left
                if(curX - filler < .0625f){
                }else {
                    poss_moves.add(curX - filler);
                    poss_moves.add(curY);
                }

                //up-right
                if(curY - filler < .0625f || curX + filler > .9375f){
                }else {
                    poss_moves.add(curX + filler);
                    poss_moves.add(curY - filler);
                }
                //up-left
                if(curY - filler < .0625f || curX - filler < .0625f){
                }else {
                    poss_moves.add(curX - filler);
                    poss_moves.add(curY - filler);
                }
                //down-right
                if(curY + filler > .9375f || curX + filler > .9375f){
                }else {
                    poss_moves.add(curX + filler);
                    poss_moves.add(curY + filler);
                }
                //down-left
                if(curY + filler > .9375f || curX - filler < .0625f){
                }else {
                    poss_moves.add(curX - filler);
                    poss_moves.add(curY + filler);
                }
            }
        }
        // Calculates black queen movement
        if (dragging.getId()  == 30){
            float curX = dragging.getX();
            float curY = dragging.getY();
            for(float filler = .125f; filler < 1f; filler += .125f){
                //move up
                if(curY - filler < .0625f){
                }else {
                    poss_moves.add(curX);
                    poss_moves.add(curY - filler);
                }
                //move down
                if(curY + filler > .9375f){
                }else {
                    poss_moves.add(curX);
                    poss_moves.add(curY + filler);
                }
                //move right
                if(curX + filler > .9375f){
                }else {
                    poss_moves.add(curX + filler);
                    poss_moves.add(curY);
                }
                //move left
                if(curX - filler < .0625f){
                }else {
                    poss_moves.add(curX - filler);
                    poss_moves.add(curY);
                }

                //up-right
                if(curY - filler < .0625f || curX + filler > .9375f){
                }else {
                    poss_moves.add(curX + filler);
                    poss_moves.add(curY - filler);
                }
                //up-left
                if(curY - filler < .0625f || curX - filler < .0625f){
                }else {
                    poss_moves.add(curX - filler);
                    poss_moves.add(curY - filler);
                }
                //down-right
                if(curY + filler > .9375f || curX + filler > .9375f){
                }else {
                    poss_moves.add(curX + filler);
                    poss_moves.add(curY + filler);
                }
                //down-left
                if(curY + filler > .9375f || curX - filler < .0625f){
                }else {
                    poss_moves.add(curX - filler);
                    poss_moves.add(curY + filler);
                }
            }
        }

        /**
         * King moves
         */
        // Calculates white king movement
        if (dragging.getId()  == 15){
            float curX = dragging.getX();
            float curY = dragging.getY();
            //move up
            if(curY - .125f < .0625f){
            }else {
                poss_moves.add(curX);
                poss_moves.add(curY - .125f);
            }
            //move down
            if(curY + .125f > .9375f){
            }else {
                poss_moves.add(curX);
                poss_moves.add(curY + .125f);
            }
            //move right
            if(curX + .125f > .9375f){
            }else {
                poss_moves.add(curX + .125f);
                poss_moves.add(curY);
            }
            //move left
            if(curX - .125f < .0625f){
            }else {
                poss_moves.add(curX - .125f);
                poss_moves.add(curY);
            }

            //up-right
            if(curY - .125f < .0625f || curX + .125f > .9375f){
            }else {
                poss_moves.add(curX + .125f);
                poss_moves.add(curY - .125f);
            }
            //up-left
            if(curY - .125f < .0625f || curX - .125f < .0625f){
            }else {
                poss_moves.add(curX - .125f);
                poss_moves.add(curY - .125f);
            }
            //down-right
            if(curY + .125f > .9375f || curX + .125f > .9375f){
            }else {
                poss_moves.add(curX + .125f);
                poss_moves.add(curY + .125f);
            }
            //down-left
            if(curY + .125f > .9375f || curX - .125f < .0625f){
            }else {
                poss_moves.add(curX - .125f);
                poss_moves.add(curY + .125f);
            }

        }
        // Calculates black king movement
        if (dragging.getId() == 31){
            float curX = dragging.getX();
            float curY = dragging.getY();
            //move up
            if(curY - .125f < .0625f){
            }else {
                poss_moves.add(curX);
                poss_moves.add(curY - .125f);
            }
            //move down
            if(curY + .125f > .9375f){
            }else {
                poss_moves.add(curX);
                poss_moves.add(curY + .125f);
            }
            //move right
            if(curX + .125f > .9375f){
            }else {
                poss_moves.add(curX + .125f);
                poss_moves.add(curY);
            }
            //move left
            if(curX - .125f < .0625f){
            }else {
                poss_moves.add(curX - .125f);
                poss_moves.add(curY);
            }

            //up-right
            if(curY - .125f < .0625f || curX + .125f > .9375f){
            }else {
                poss_moves.add(curX + .125f);
                poss_moves.add(curY - .125f);
            }
            //up-left
            if(curY - .125f < .0625f || curX - .125f < .0625f){
            }else {
                poss_moves.add(curX - .125f);
                poss_moves.add(curY - .125f);
            }
            //down-right
            if(curY + .125f > .9375f || curX + .125f > .9375f){
            }else {
                poss_moves.add(curX + .125f);
                poss_moves.add(curY + .125f);
            }
            //down-left
            if(curY + .125f > .9375f || curX - .125f < .0625f){
            }else {
                poss_moves.add(curX - .125f);
                poss_moves.add(curY + .125f);
            }
        }
        return poss_moves;
    }

//    public void saveInstanceState(Bundle bundle) {

    /**
     * Save the view state to a bundle
     * @param key key name to use in the bundle
     * @param bundle bundle to save to
     */
    public void putToBundle(String key, Bundle bundle) {
//        bundle.putSerializable(key, params);
//    }

//    public void saveInstanceState(Bundle bundle) {

        Serializable [] params = new Serializable[PIECE_COUNT];

        for (int i = 0; i < PIECE_COUNT; i++) {
            pieces[i].putToBundle(Integer.toString(i), bundle);
//            bundle.putSerializable(Integer.toString(i), pieces[i].getParams());
        }

//        bundle.putSerializable(key, params);

    }

    /**
     * Get the view state from a bundle
     * @param key key name to use in bundle
     * @param bundle bundle to load from
     */
    public void getFromBundle(String key, Bundle bundle) {
//        params = (Parameters)bundle.getSerializable(key);
//
//        // Ensure the options are all set
//        setColor(params.color);
//        setImagePath(params.piece);
//    }

//    public void loadInstanceState(Bundle bundle) {


        for (int i = 0; i < PIECE_COUNT; i++) {
            pieces[i].getFromBundle(Integer.toString(i), bundle);
        }
//        Serializable params = bundle.getSerializable(key);

    }

}
