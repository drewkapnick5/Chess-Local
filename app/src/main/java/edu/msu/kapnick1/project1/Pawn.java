package edu.msu.kapnick1.project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Pair;

import java.util.List;


/**
 * As of now:
 * IMPLEMENTED: Pawns all spawn in and can move up one space, return to original space if let go,
 * and update their next move location
 * NOT IMPLEMENTED: No attack movement, cannot see other pieces, other pieces will be able to land on
 * pawn, initial two space move not currently valid
 */
public class Pawn {
    /**
     * The image for the actual piece.
     */
    private Bitmap pawn;

    /**
     * x location.
     * We use relative x locations in the range 0-1 for the center
     * of the puzzle piece.
     * NEEDS TO BE CHANGED
     */
    private float x;

    /**
     * y location
     * NEEDS TO BE CHANGED
     */
    private float y;

    /**
     * x location when the puzzle is solved
     * NEEDS TO BE CHANGED
     */
    private float finalX;
    float [] possX = new float[3];
    //public Pair(.6f,.6f);





    /**
     * y location when the puzzle is solved
     * NEEDS TO BE CHANGED
     */
    private float finalY;

    private float beforeDragX;
    private float beforeDragY;

    /**
     * We consider a valid move for a piece if it is within SNAP_DISTANCE
     */
    final static float SNAP_DISTANCE = 0.07f;

    /**
     * The pawn piece ID
     */
    private int id;

    public int getId() {
        return id;
    }

    public Pawn(Context context, int pic, int id, float initialX, float initialY, float finalX, float finalY) {
        this.finalX = finalX;
        this.finalY = finalY;
        this.possX = possX;
        //this.possY = possY;
        this.id = id;
        x = initialX;
        y = initialY;
        beforeDragX = x;
        beforeDragY = y;



        pawn = BitmapFactory.decodeResource(context.getResources(), pic);
    }

    /**
     * Draw the puzzle piece
     * @param canvas Canvas we are drawing on
     * @param marginX Margin x value in pixels
     * @param marginY Margin y value in pixels
     * @param boardSize Size we draw the puzzle in pixels
     * @param scaleFactor Amount we scale the puzzle pieces when we draw them
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
        canvas.translate(-pawn.getWidth() / 2f, -pawn.getHeight() / 2f);

        // Draw the bitmap
        canvas.drawBitmap(pawn, 0, 0, null);
        canvas.restore();
    }

    /**
     * Test to see if we have touched a puzzle piece
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
                pawn.getWidth() / 2;
        int pY = (int)((testY - y) * boardSize / scaleFactor) +
                pawn.getHeight() / 2;

        if(pX < 0 || pX >= pawn.getWidth() ||
                pY < 0 || pY >= pawn.getHeight()) {
            return false;
        }

        // We are within the rectangle of the pawn.
        // Are we touching actual picture?
        return (pawn.getPixel(pX, pY) & 0xff000000) != 0;
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

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
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


        if(Math.abs(x - finalX) < SNAP_DISTANCE &&
                Math.abs(y - finalY) < SNAP_DISTANCE) {

            x = finalX;
            y = finalY;
            beforeDragX = x;
            beforeDragY = y;
            //finalX += .125f;
            finalY -= .125f;
            return true;
        }
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
