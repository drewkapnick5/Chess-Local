package edu.msu.kapnick1.project1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Pair;
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
    final static int BOARD_SIZE = 8;

    /**
     * Number of pieces
     */
    final static int PIECE_COUNT = 32;

    /**
     * Board Reset initial values
     */
    final static int initTopRow = 1;
    final static int initBottomRow = 0;
    final static int initRookColumn = 0;
    final static int initKnightColumn = 1;
    final static int initBishopColumn = 2;
    final static int initQueenColumn = 3;

    private static String LOCATIONS = "board.Locations";

    /**
     * Initial center of tile
     */
    final static float col_init = .0625f;
    final static float row_init = .0625f;

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
     * Paint for writing the players names
     */
    private Paint playerPaint;

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

    /**
     * The possible moves of the current dragged piece
     */
    private List<Pair> poss_moves = new ArrayList<>();

    /**
     * Positions of pieces based on color
     */
    private List<Pair> white_positions = new ArrayList<>();
    private List<Pair> black_positions = new ArrayList<>();

    /**
     * Positions of all pieces
     */
    private List<Pair> positions = new ArrayList<>();

    /**
     * Whether or not a capture has happened
     */
    private boolean remove = false;
    /**
     * Index of captured piece
     */
    private int r_index = -1;

    /**
     * Current turn
     */
    private int turn = 0;

    /**
     * The player names in the game
     */
    private ArrayList<String> players = new ArrayList<>();

    private ArrayList<Integer> promotionIds = new ArrayList<>();
    private ArrayList<String> promotions = new ArrayList<>();

    /**
     * Constructor
     * @param context Context of the view
     * @param v
     */
    public ChessBoard(Context context, ChessView v) {
        // Load the empty chess board image
        emptyBoard = BitmapFactory.decodeResource(context.getResources(), R.drawable.chess_board);

        // outline box for board
        outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outlinePaint.setStyle(Paint.Style.STROKE);
        outlinePaint.setColor(Color.BLACK);
        outlinePaint.setStrokeWidth(5);

        playerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        playerPaint.setColor(Color.BLACK);

        columns[0] = col_init;
        rows[0] = row_init;
        for (int i = 1; i < 8; i++) {
            columns[i] = columns[i-1] + .125f;
            rows[i] = rows[i-1] + .125f;
        }

        initializeBoard(context);

    }

    /**
     * Initialize all the pieces on their correct squares
     * @param context Context of the view
     */
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

    /**
     * Resets all pieces to their starting locations
     */
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

    /**
     * Go to the next player's turn
     */
    public void nextTurn(final View view) {
        if (dragging!=null) {
            dragging.setDrags();
            if (dragging instanceof Pawn &&
                    ((dragging.isWhite() && dragging.getY() == rows[0])
                    || (dragging.isBlack() && dragging.getY() == rows[7]))){
                final int id = dragging.getId();
                final Piece copy = dragging;
                final Context context = view.getContext();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Select a piece to promote to:");
                String[] names = {"Queen", "Rook", "Knight", "Bishop"};
                builder.setItems(names, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                pieces[id] = new Queen(context, copy.getParameters());
                                promotionIds.add(copy.getId());
                                promotions.add("Queen");
                                break;
                            case 1:
                                pieces[id] = new Rook(context, copy.getParameters());
                                promotionIds.add(copy.getId());
                                promotions.add("Rook");
                                break;
                            case 2:
                                pieces[id] = new Knight(context, copy.getParameters());
                                promotionIds.add(copy.getId());
                                promotions.add("Knight");
                                break;
                            case 3:
                                pieces[id] = new Bishop(context, copy.getParameters());
                                promotionIds.add(copy.getId());
                                promotions.add("Bishop");
                                break;
                        }
                        view.invalidate();
                    }
                });
                builder.create().show();

            }
        }
        if (remove) { pieces[r_index].remove(); }
        remove = false;
        dragging = null;
        turn = (turn==1) ? 0 : 1;
    }

    /**
     * Adds a player to the game
     * @param player Name of the player
     */
    public void addPlayer(String player) {
        players.add(player);
    }

    /**
     * Get the players who turn it currently is
     * @return String player name
     */
    public String getPlayer() {
        return players.get(turn);
    }

    /**
     * Draws the board and all the pieces currently active
     * @param canvas What is being drawn to
     */
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
            int color = (pieces[p].isBlack()) ? 1 : 0;
            if (color == turn && pieces[p].isActive()) {
                if(pieces[p].hit(x, y, boardSize, scaleFactor/2)) {
                    if (dragging != null) {
                        dragging.reset();
                    } // If a piece was removed but turn was not ended and another piece moved,
                      // bring that piece back
                    if (remove) {
                        pieces[r_index].reset();
                        remove = false;
                    }
                    // We hit a piece!
                    dragging = pieces[p];

                    // Two separate lists for the positions of each piece on the board
                    for(Piece piece : pieces) {
                        // Add the positions of all the pieces, null if not active
                        positions.add(new Pair<>(piece.getX(), piece.getY()));
                        if(piece.getId()<16){
                            // White pieces
                            if (piece.isActive()) {
                                white_positions.add(new Pair<>(piece.getX(), piece.getY()));
                            } else {
                                white_positions.add(null);
                            }
                        }else{
                            // Black pieces
                            if (piece.isActive()) {
                                black_positions.add(new Pair<>(piece.getX(), piece.getY()));
                            } else {
                                black_positions.add(null);
                            }
                        }
                    }
                    poss_moves = dragging.checkMoves(white_positions, black_positions);
                    lastRelX = x;
                    lastRelY = y;

                    return true;
                }
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
            // Check if piece placed on top of another piece of opposite color
            r_index = -1;
            if (dragging.getId() < 16) {
                r_index = black_positions.indexOf(new Pair<>(dragging.getX(), dragging.getY()));
                r_index = (r_index>-1) ? r_index+16 : r_index;
            } else {
                r_index = white_positions.indexOf(new Pair<>(dragging.getX(), dragging.getY()));
            }

            // remove a piece if its active and a piece of the other color placed on top of it
            if (r_index > -1 && pieces[r_index].isActive()) {
                remove = true;
                pieces[r_index].remove();

            }

            view.invalidate();
            poss_moves.clear();
            white_positions.clear();
            black_positions.clear();
            return true;
        }

        return false;
    }

    /**
     * Reset promoted pawns when restoring state
     */
    public void setPromotions(Context context) {
        for (int i = 0; i < promotionIds.size(); i++) {
            int id = promotionIds.get(i);
            switch (promotions.get(i)){
                case "Queen":
                    pieces[id] = new Queen(context, pieces[id].getParameters());
                    break;
                case "Rook":
                    pieces[id] = new Rook(context, pieces[id].getParameters());
                    break;
                case "Knight":
                    pieces[id] = new Knight(context, pieces[id].getParameters());
                    break;
                case "Bishop":
                    pieces[id] = new Bishop(context, pieces[id].getParameters());
                    break;
            }
        }
    }

    /**
     * Save the view state to a bundle
     * @param key key name to use in the bundle
     * @param bundle bundle to save to
     */
    public void putToBundle(String key, Bundle bundle) {

        Serializable [] params = new Serializable[PIECE_COUNT];

        for (int i = 0; i < PIECE_COUNT; i++) {
            pieces[i].putToBundle(Integer.toString(i), bundle);
        }

        bundle.putIntegerArrayList("promoId", promotionIds);
        bundle.putStringArrayList("promos", promotions);

        bundle.putInt("turn", turn);
        int dragid = (dragging == null ? -1 : dragging.getId());
        bundle.putInt("dragIdx", dragid);
        bundle.putBoolean("remove", remove);
        bundle.putInt("rIndex", r_index);
    }

    /**
     * Get the view state from a bundle
     * @param key key name to use in bundle
     * @param bundle bundle to load from
     */
    public void getFromBundle(String key, Bundle bundle, Context context) {


        for (int i = 0; i < PIECE_COUNT; i++) {
            pieces[i].getFromBundle(Integer.toString(i), bundle, context);
        }
        promotionIds = bundle.getIntegerArrayList("promoId");
        promotions = bundle.getStringArrayList("promos");
        setPromotions(context);

        turn = bundle.getInt("turn");
        int dragIdx = bundle.getInt("dragIdx");
        if (dragIdx != -1){
            dragging = pieces[dragIdx];
        }
        remove = bundle.getBoolean("remove");
        r_index = bundle.getInt("rIndex");
    }

}
