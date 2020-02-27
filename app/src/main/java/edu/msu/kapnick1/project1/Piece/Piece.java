package edu.msu.kapnick1.project1.Piece;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Piece {

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


    protected float beforeDragX;
    protected float beforeDragY;

    /**
     * We consider a valid move for a piece if it is within SNAP_DISTANCE
     */
    protected final static float SNAP_DISTANCE = 0.07f;

    /**
     * The chess piece ID
     */
    protected int id;

    /**
     * The board piece is active and should be drawn
     */
    protected boolean active = true;


    public Piece(Context context, int id, float initialX, float initialY, boolean white) {
        /*this.finalX = finalX;
        this.finalY = finalY;
        this.possX = possX;*/
        //this.possY = possY;
        this.id = id;
        x = initialX;
        y = initialY;
        beforeDragX = x;
        beforeDragY = y;
        color = white;
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
        canvas.translate(marginX + x * boardSize, marginY + y * boardSize);

        // Scale it to the right size
        canvas.scale(scaleFactor/4, scaleFactor/4);

        // This magic code makes the center of the piece at 0, 0
        canvas.translate(-piece.getWidth() / 2f, -piece.getHeight() / 2f);

        // Draw the bitmap
        canvas.drawBitmap(piece, 0, 0, null);
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
        int pX = (int)((testX - x) * boardSize / scaleFactor) +
                piece.getWidth() / 2;
        int pY = (int)((testY - y) * boardSize / scaleFactor) +
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
     * Move the piece by dx, dy
     * @param dx x amount to move
     * @param dy y amount to move
     */
    public void move(float dx, float dy) {
        x += dx;
        y += dy;
    }

    /**
     * Gets x value
     * @return x
     */
    public float getX() {
        return x;
    }

    /**
     * Sets x value
     * @param x new value
     */
    public void setX(float x) {
        this.x = beforeDragX = x;
    }

    /**
     * Gets y value
     * @return y
     */
    public float getY() {
        return y;
    }

    /**
     * Sets y value
     * @param y new value
     */
    public void setY(float y) {
        this.y = beforeDragY = y;
    }

    /**
     * Gets id value
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Find if piece is white
     * @return True if white
     */
    public boolean isWhite() {
        return color;
    }

    /**
     * Find if piece is black
     * @return True if black
     */
    public boolean isBlack() {
        return !color;
    }

    /**
     * Find if piece is active
     * @return True if active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * If we are within SNAP_DISTANCE of the correct
     * answer, snap to the correct answer exactly.
     * @return
     */
    public boolean maybeSnap() {

//        for (float pX : possX && float pY : possY){
//            if(Math.abs(x - finalX) < SNAP_DISTANCE &&
//                    Math.abs(y - finalY) < SNAP_DISTANCE) {
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


        /*if(Math.abs(x - finalX) < SNAP_DISTANCE &&
                Math.abs(y - finalY) < SNAP_DISTANCE) {

            x = finalX;
            y = finalY;
            beforeDragX = x;
            beforeDragY = y;
            //finalX += .125f;
            finalY -= .125f;
            return true;
        }*/
        x = beforeDragX;
        y = beforeDragY;

        return false;
    }

    /**
     * Determine if this piece is snapped in place
     * @return true if snapped into place
     */
    public boolean isSnapped() {
        return maybeSnap();

    }
}
