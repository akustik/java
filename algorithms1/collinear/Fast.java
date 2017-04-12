import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

public class Fast {
    
    private static void printSolution(TreeSet<Point> selected)
    {
        StringBuilder builder = new StringBuilder();
        boolean firstAdded = false;
        for (Point point: selected)
        {
            if (!firstAdded)
            {
                firstAdded = true;
                builder.append(point);
            }
            else
            {
                builder.append(" -> " + point);
            }
        }
                        
        selected.first().drawTo(selected.last());
        System.out.println(builder.toString());        
    }
    
    private static class PointAndSlope
    {
        private final Point point;
        private final double slope;
        
        public PointAndSlope(Point point, double slope)
        {
            this.point = point;
            this.slope = slope;
        }
        
        public Point point()
        {
            return point;
        }
        
        public double slope()
        {
            return slope;
        }
        
        public boolean equals(Object o) 
        {
            return (o instanceof PointAndSlope) 
                && ((PointAndSlope) o).slope() == (this.slope())
                && ((PointAndSlope) o).point().compareTo(this.point()) == 0;
        }
        
        public int hashCode() 
        {
            return ("" + this.point + this.slope).hashCode();
        }     
    }
    
    public static void main(String[] args) { 
      
        //MIRAR: Improve the filter to avoid repetitions
        Set<PointAndSlope> drawnLines = new HashSet<PointAndSlope>();
        
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        ArrayList<Point> origins = new ArrayList<Point>(N);
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            p.draw();
            points[i] = p;
            origins.add(p);
        }
        
        // for each possible origin
        for (Point origin: origins)
        {                        
            // sort the points for this order
            Arrays.sort(points, origin.SLOPE_ORDER);
                         
            // find 3 or + adjacent items with same slope
            TreeSet<Point> selected = new TreeSet<Point>();
            double currentSlope = origin.slopeTo(points[0]);
            for (int j = 0; j < N; j++)
            {
                
                double nextSlope = origin.slopeTo(points[j]);
                if (nextSlope == currentSlope)
                {
                    selected.add(points[j]);
                }
                else 
                {
                    if (selected.size() > 2)
                    {
                        selected.add(origin);                        
                        PointAndSlope l = 
                            new PointAndSlope(selected.first(), currentSlope);
                        if (!drawnLines.contains(l))
                        {
                            drawnLines.add(l);
                            printSolution(selected);
                        }
                    }
                    
                    currentSlope = nextSlope;
                    selected.clear();
                    selected.add(points[j]);
                }                                
            }
                        
            if (selected.size() > 2)
            {
                selected.add(origin);
                PointAndSlope l = 
                    new PointAndSlope(selected.first(), currentSlope);
                if (!drawnLines.contains(l))
                {
                    drawnLines.add(l);
                    printSolution(selected);
                }
            }
        }
                   
        // display to screen all at once
        StdDraw.show(0);
    }
}
