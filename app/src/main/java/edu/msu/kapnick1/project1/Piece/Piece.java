package edu.msu.kapnick1.project1.Piece;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Pair;

import java.io.Serializable;
import java.nio.channels.FileLock;
import java.util.List;

public class Piece {

    /**
     * The image for the actual piece.
     */
    protected Bitmap piece;

    /**
     * We consider a valid move for a piece if it is within SNAP_DISTANCE
     */
    protected final static float SNAP_DISTANCE = 0.07f;


    /**
     * Constructor
     * @param context context of the view
     * @param id index of the piece
     * @param initialX X location of initial draw
     * @param initialY Y location of initial draw
     * @param white Color of the piece
     */
    public Piece(Context context, int id, float initialX, float initialY, boolean white) {
        params.id = id;
        params.x = initialX;
        params.y = initialY;
        params.beforeDragX = params.x;
        params.beforeDragY = params.y;
        params.color = white;
    }

    /**
     * Constructor
     * @param context context of the view
     * @param params piece parameters
     */
    public Piece(Context context, Parameters params){
        this.params.id = params.id;
        this.params.x = params.x;
        this.params.y = params.y;
        this.params.beforeDragX = params.beforeDragX;
        this.params.beforeDragY = params.beforeDragY;
        this.params.color = params.color;
        this.params.active = params.active;
    }

    /**
     * Draw the chess piece
     * @param canvas Canvas we are drawing on
     * @param marginX Margin x value in pixels
     * @param marginY Margin y value in pixels
     * @param boardSize Size we draw the board in pixels
     * @param scaleFactor Amount we scale the chess pieces when we draw them
     */
    public void draw(Canvas canvas, int marginX, int marginY,
                     int boardSize, float scaleFactor) {
        if (params.active) {
            canvas.save();

            // Convert x,y to pixels and add the margin, then draw
            canvas.translate(marginX + params.x * boardSize, marginY + params.y * boardSize);

            // Scale it to the right size
            canvas.scale(scaleFactor / 4, scaleFactor / 4);

            // This magic code makes the center of the piece at 0, 0
            canvas.translate(-piece.getWidth() / 2f, -piece.getHeight() / 2f);

            // Draw the bitmap
            canvas.drawBitmap(piece, 0, 0, null);
            canvas.restore();
        }
    }

    /**
     * Test to see if we have touched a chess piece
     * @param testX X location as a normalized coordinate (0 to 1)
     * @param testY Y location as a normalized coordinate (0 to 1)
     * @param boardSize the size of the puzzle in pixels
     * @param scaleFactor the amount to scale a piece by
     * @return true if we hit the piece
     */
    public boolean hit(float testX, float testY,
                       int boardSize, float scaleFactor) {

        // Make relative to the location and size to the piece size
        int pX = (int)((testX - params.x) * boardSize / scaleFactor) +
                piece.getWidth() / 2;
        int pY = (int)((testY - params.y) * boardSize / scaleFactor) +
                piece.getHeight() / 2;

        if(pX < 0 || pX >= piece.getWidth() ||
                pY < 0 || pY >= piece.getHeight()) {
            return false;
        }

        // We are within the rectangle of the pawn.
        // Are we touching actual picture?
        return (piece.getPixel(pX, pY) & 0xff000000) != 0;
    }

    /**
     * Checks the possible moves for this piece
     * @param white_positions Positions for all white pieces on the board
     * @param black_positions Positions for all black pieces on the board
     * @return List<Pair> Locations of all the spaces the piece can be placed
     */
    public List<Pair> checkMoves(List<Pair> white_positions, List<Pair> black_positions) {
        return white_positions;
    }

    /**
     * Move the piece by dx, dy
     * @param dx x amount to move
     * @param dy y amount to move
     */
    public void move(float dx, float dy) {
        params.x += dx;
        params.y += dy;
    }

    /**
     * Reset to position at before drag started
     */
    public void reset() {
        params.x = params.beforeDragX;
        params.y = params.beforeDragY;
        params.active = true;
    }

    /**
     * After turn over, drags set to position piece was moved to
     */
    public void setDrags() {
        params.beforeDragX = params.x;
        params.beforeDragY = params.y;
    }

    /**
     * Gets x value
     * @return x
     */
    public float getX() {
        return params.x;
    }

    /**
     * Sets x value
     * @param x new value
     */
    public void setX(float x) {
        params.x = params.beforeDragX = x;
    }

    /**
     * Gets y value
     * @return y
     */
    public float getY() {
        return params.y;
    }

    /**
     * Sets y value
     * @param y new value
     */
    public void setY(float y) {
        params.y = params.beforeDragY = y;
    }

    /**
     * Gets id value
     * @return id
     */
    public int getId() {
        return params.id;
    }

    /**
     * Find if piece is white
     * @return True if white
     */
    public boolean isWhite() {
        return params.color;
    }

    /**
     * Find if piece is black
     * @return True if black
     */
    public boolean isBlack() {
        return !params.color;
    }

    /**
     * Color to set the piece to
     * @param color Black or white
     */
    public void setColor(boolean color) { params.color = color; }

    /**
     * Set the Bitmap id
     * @param id Bitmap id
     */
    public void setBitmapID(int id) {
        params.bitmapID = id;
    }

    /**
     * Set the image
     * @param piece Image set to
     */
    public void setImagePath(Bitmap piece) { this.piece = piece; }

    /**
     * Find if piece is active
     * @return True if active
     */
    public boolean isActive() {
        return params.active;
    }

    /**
     * Reset X drag
     * @param x X location
     */
    public void setBeforeDragX(float x){
        params.beforeDragX = x;
    }

    /**
     * Reset Y drag
     * @param y Y location
     */
    public void setBeforeDragY(float y){
        params.beforeDragY = y;
    }

    /**
     * Set the active flag
     * @param active whether or not the piece has been captured
     */
    public void setActive(boolean active) {
        params.active = active;
    }

    /**
     * Piece captured and is no longer part of the game
     */
    public void remove() {
        params.active = false;
    }

    /**
     * If we are within SNAP_DISTANCE of the correct
     * answer, snap to the correct answer exactly.
     * @return
     */
    public boolean maybeSnap(List<Pair> poss_moves) {

        for (int i = 0, j = 1; i < poss_moves.size(); i++, j+= 2) {
            Pair<Float, Float> pair = poss_moves.get(i);
            float finalX = pair.first;
            float finalY = pair.second;

            // If the piece is close to a valid space
            if (Math.abs(params.x - finalX) < SNAP_DISTANCE &&
                    Math.abs(params.y - finalY) < SNAP_DISTANCE) {

                params.x = finalX;
                params.y = finalY;
                return true;
            }
        }

        params.x = params.beforeDragX;
        params.y = params.beforeDragY;

        return false;
    }


    /**
     * Save the view state to a bundle
     * @param key key name to use in the bundle
     * @param bundle bundle to save to
     */
    public void putToBundle(String key, Bundle bundle) {
        bundle.putSerializable(key, params);
    }

    /**
     * Get the view state from a bundle
     * @param key key name to use in bundle
     * @param bundle bundle to load from
     */
    public void getFromBundle(String key, Bundle bundle, Context context) {
        Parameters params = (Parameters)bundle.getSerializable(key);

        // Ensure the options are all set
        setColor(params.color);
        setBitmapID(params.bitmapID);
        setImagePath(BitmapFactory.decodeResource(context.getResources(), params.bitmapID));
        setX(params.x);
        setY(params.y);
        setBeforeDragX(params.beforeDragX);
        setBeforeDragY(params.beforeDragY);
        setActive(params.active);
    }

    /**
     * Get the parameters of a unique piece
     * @return parameters
     */
    public Parameters getParameters(){
        return params;
    }

    protected static class Parameters implements Serializable {
        /**
         * Resource id for the piece bitmap
         */
        protected int bitmapID;

        /**
         * x location.
         * We use relative x locations in the range 0-1 for the center
         * of the chess piece.
         */
        protected float x;

        /**
         * y location
         */
        protected float y;

        protected float beforeDragX;
        protected float beforeDragY;

        /**
         * Piece color
         * True for white, False for black
         */
        protected boolean color;


        /**
         * The chess piece ID
         */
        protected int id;

        /**
         * The board piece is active and should be drawn
         */
        protected boolean active = true;

    }

    /**
     * Parameters describing the state of the piece
     */
    protected Parameters params = new Parameters();

}
