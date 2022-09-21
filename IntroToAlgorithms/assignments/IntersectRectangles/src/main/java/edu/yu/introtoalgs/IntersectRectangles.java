package edu.yu.introtoalgs;

import java.util.Objects;

public class IntersectRectangles {

    /** This constant represents the fact that two rectangles don't intersect.
     *
     * @see #intersectRectangle
     * @warn you may not modify this constant in any way
     */
    public final static Rectangle NO_INTERSECTION =
            new Rectangle(0, 0, -1, -1);

    /** An immutable class that represents a 2D Rectangle.
     *
     * @warn you may not modify the instance variables in any way, you are
     * encouraged to add to the current set of variables and methods as you feel
     * necesssary.
     */
    public static class Rectangle {
        // safe to make instance variables public because they are final, now no
        // need to make getters
        public final int x;
        public final int y;
        public final int width;
        public final int height;

        /** Constructor: see the requirements doc for the precise semantics.
         *
         * @warn you may not modify the currently defined semantics in any way, you
         * may add more code if you so choose.
         */
        public Rectangle (final int x, final int y, final int width, final int height) {
            //Unsure if I have to catch nulls here
            //or negative w and h

            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        @Override //check
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Rectangle rectangle = (Rectangle) o;
            return x == rectangle.x && y == rectangle.y && width == rectangle.width && height == rectangle.height;
        }

        @Override //check
        public int hashCode() {
            return Objects.hash(x, y, width, height);
        }

        @Override
        public String toString() {
            return "Rectangle: [x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + ']';
        }
    }

    /** If the two rectangles intersect, returns the rectangle formed by their
     * intersection; otherwise, returns the NO_INTERSECTION Rectangle constant.
     *
     * @param r1 one rectangle
     * @param r2 the other rectangle
     * @param a rectangle representing the intersection of the input parameters
     * if they intersect, NO_INTERSECTION otherwise.  See the requirements doc
     * for precise definition of "rectangle intersection"
     * @throws IllegalArgumentException if either parameter is null.
     */
    public static Rectangle intersect (final Rectangle r1, final Rectangle r2) {
        if (r1 == null || r2 == null) {
            throw new IllegalArgumentException();
        }

        Rectangle smallerX = (r1.x <= r2.x ? r1 : r2);
        Rectangle smallerY = (r1.y <= r2.y ? r1 : r2);
        //try
        Rectangle largerX = (r1.x > r2.x ? r1 : r2);
        Rectangle largerY = (r1.y > r2.y ? r1 : r2);

        if(r1.x == r2.x){
            smallerX = (r1.width < r2.width ? r1 : r2);
        }
        if(r1.y == r2.y){
            smallerY = (r1.height < r2.height ? r1 : r2);
        }

        int newX = Math.abs(r2.x - r1.x) + smallerX.x;
        int newY = Math.abs(r2.y - r1.y) + smallerY.y;

        boolean validNewX = ((newX >= smallerX.x) && (newX <= (smallerX.x + smallerX.width)));
        boolean validNewY = ((newY >= smallerY.y) && (newY <= (smallerY.y + smallerY.height)));

        if(validNewX && validNewY){
            //for inner
            if ((smallerX == smallerY) && (largerX == largerY) || (smallerX == largerX) || (smallerY == largerY)){
                if (((largerX.x + largerX.width) <= (smallerX.x + smallerX.width)) && ((largerY.y + largerY.height) <= (smallerY.y + smallerY.height))){
                    return largerX;
                }
            }

            int newWidth = Math.abs((smallerX.x + smallerX.width) - newX);
            int newHeight = Math.abs((smallerY.y + smallerY.height) - newY);
            return new Rectangle(newX, newY, newWidth, newHeight);
        }

        return NO_INTERSECTION;
    }

} // class