package edu.msu.kapnick1.project1.Piece;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Pair;

import java.io.Serializable;
import java.nio.channels.FileLock;
import java.util.List;

public class Piece {


    protected float beforeDragX;
    protected float beforeDragY;

    /**
     * We consider a valid move for a piece if it is within SNAP_DISTANCE
     */
    protected final static float SNAP_DISTANCE = 0.07f;


    public Piece(Context context, int id, float initialX, float initialY, boolean white) {
        /*this.finalX = finalX;
        this.finalY = finalY;
        this.possX = possX;*/
        //this.possY = possY;
        params.id = id;
        params.x = initialX;
        params.y = initialY;
        beforeDragX = params.x;
        beforeDragY = params.y;
        params.color = white;
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
        canvas.save();

        // Convert x,y to pixels and add the margin, then draw
        //x = finalX;
        //y = finalY;
        canvas.translate(marginX + params.x * boardSize, marginY + params.y * boardSize);

        // Scale it to the right size
        canvas.scale(scaleFactor/4, scaleFactor/4);

        // This magic code makes the center of the piece at 0, 0
        canvas.translate(-params.piece.getWidth() / 2f, -params.piece.getHeight() / 2f);

        // Draw the bitmap
        canvas.drawBitmap(params.piece, 0, 0, null);
        canvas.restore();
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
                params.piece.getWidth() / 2;
        int pY = (int)((testY - params.y) * boardSize / scaleFactor) +
                params.piece.getHeight() / 2;

        if(pX < 0 || pX >= params.piece.getWidth() ||
                pY < 0 || pY >= params.piece.getHeight()) {
            return false;
        }

        // We are within the rectangle of the pawn.
        // Are we touching actual picture?
        return (params.piece.getPixel(pX, pY) & 0xff000000) != 0;
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
        params.x = beforeDragX;
        params.y = beforeDragY;
    }

    /**
     * After turn over, drags set to position piece was moved to
     */
    public void setDrags() {
        beforeDragX = params.x;
        beforeDragY = params.y;
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
        params.x = beforeDragX = x;
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
        params.y = beforeDragY = y;
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
     * Set the image
     * @param piece Image set to
     */
    public void setImagePath(Bitmap piece) { params.piece = piece; }

    /**
     * Find if piece is active
     * @return True if active
     */
    public boolean isActive() {
        return params.active;
    }

    public Parameters getParams() { return params; }

    public void setParams(Parameters params) {this.params = params;}

    /**
     * If we are within SNAP_DISTANCE of the correct
     * answer, snap to the correct answer exactly.
     * @return
     */
    public boolean maybeSnap(List<Pair> poss_moves) {
//        for (float pX : possX && float pY : possY){
//            if(Math.abs(x - finalX) < SNAP_DISTANCE &&
//                    Math.abs(y - finalY)  < SNAP_DISTANCE) {
//
//                x = finalX;
//                y = finalY;
//                beforeDragX = x;
//                beforeDragY = y;
//                //finalX += .125f;
//                finalY -= .125f;
//                return true;
//            }
//            x = beforeDragX;
//            y = beforeDragY;
//
//            return false;
//        }
        for (int i = 0, j = 1; i < poss_moves.size(); i++, j+= 2) {
            Pair<Float, Float> pair = poss_moves.get(i);
            float finalX = pair.first;
            float finalY = pair.second;

            if (Math.abs(params.x - finalX) < SNAP_DISTANCE &&
                    Math.abs(params.y - finalY) < SNAP_DISTANCE) {

                params.x = finalX;
                params.y = finalY;
//                beforeDragX = params.x;
//                beforeDragY = params.y;
                //finalX += .125f;
                finalY -= .125f;
                return true;
            }
        }

//            params.x = finalX;
//            params.y = finalY;
//            beforeDragX = params.x;
//            beforeDragY = params.y;
//            //finalX += .125f;
//            finalY -= .125f;
//            return true;

        params.x = beforeDragX;
        params.y = beforeDragY;

        return false;
    }

    /**
     * Determine if this piece is snapped in place
     * @return true if snapped into place
     */

//    public boolean isSnapped() {
//        return maybeSnap();
//    }


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
    public void getFromBundle(String key, Bundle bundle) {
        Parameters params = (Parameters)bundle.getSerializable(key);

        // Ensure the options are all set
        setColor(params.color);
        setImagePath(params.piece);
        setX(params.x);
        setY(params.y);
    }

    protected static class Parameters implements Serializable {
        /**
         * The image for the actual piece.
         */
        protected Bitmap piece;

        /**
         * x location.
         * We use relative x locations in the range 0-1 for the center
         * of the chess piece.
         * NEEDS TO BE CHANGED
         */
        protected float x;

        /**
         * y location
         * NEEDS TO BE CHANGED
         */
        protected float y;

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

    protected Parameters params = new Parameters();

}
