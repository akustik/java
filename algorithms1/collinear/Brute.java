import java.util.ArrayList;
import java.util.TreeSet;

public class Brute {
    
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
    
    public static void main(String[] args)
    {
        
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        ArrayList<Point> points = new ArrayList<Point>(N);
        for (int i = 0; i < N; i++) 
        {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            p.draw();
            points.add(p);
        }
        
        // iterate through all 4 tuples
        for (int p = 0; p < points.size() - 3; p++)
        {
            Point pp = points.get(p);
           
            for (int q = p + 1; q < points.size() - 2; q++)
            {
                Point pq = points.get(q);
                double slope = pp.slopeTo(pq);
                
                for (int r = q + 1; r < points.size() - 1; r++)
                {
                    Point pr = points.get(r);
                    if (slope != pq.slopeTo(pr)) continue;
                    
                    for (int s = r + 1; s < points.size(); s++)
                    {
                        Point ps = points.get(s);
                        if (slope != pr.slopeTo(ps)) continue;
                        
                        TreeSet<Point> selected = new TreeSet<Point>();
                        selected.add(pp);
                        selected.add(pq);
                        selected.add(pr);
                        selected.add(ps);
                        
                        printSolution(selected);
                       
                    }
                    
                }
            }
        }
        
        // display to screen all at once
        StdDraw.show(0);
        
    }
}
