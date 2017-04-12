/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER; 

    private final int x;                              
    private final int y;                              

    /**
     * create the point (x, y)
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        
        SLOPE_ORDER = new SlopeToPointComparator(this);
    }

    /**
     * plot this point to standard drawing
     */
    public void draw() {
        StdDraw.point(x, y);
    }

    /**
     * draw line between this point and that point to standard drawing
     */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * slope between this point and that point
     */ 
    public double slopeTo(Point that) {
        double slope;
        boolean vertical = that.x == this.x;
        boolean horizontal = that.y == this.y;
        if (!horizontal && !vertical)
        {
            slope = ((double) (that.y - this.y)) / ((double) (that.x - this.x));
        } 
        else if (vertical && !horizontal)
        {
            slope = Double.POSITIVE_INFINITY;
        } 
        else if (horizontal && !vertical)
        {
            slope = 0.0;
        } 
        else 
        {
            slope = Double.NEGATIVE_INFINITY;
        }
                    
        return slope;
    }

    /**
     * is this point lexicographically smaller than that one?
     * comparing y-coordinates and breaking ties by x-coordinates
     */
    public int compareTo(Point that) {
        if (this.y != that.y)
        {
            return this.y - that.y;
        } 
        else
        {
            return this.x - that.x;
        }     
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
    
    /**
     * A comparator to an origin point p
     */
    private class SlopeToPointComparator implements Comparator<Point>
    {   
        private final Point origin;
        
        public SlopeToPointComparator(Point p)
        {
            origin = p;
        }
        
        public Point origin()
        {
            return origin;
        }
        
        public int compare(Point a, Point b)
        {
            return (int) Math.signum(origin.slopeTo(a) - origin.slopeTo(b));
        }
        
        public boolean equals(Object obj)
        {
            if (obj instanceof SlopeToPointComparator)
            {
                SlopeToPointComparator c = (SlopeToPointComparator) obj;
                return  origin.toString()
                    .compareTo(c.origin().toString()) == 0;
            }
            
            return false;
        }
    }
}