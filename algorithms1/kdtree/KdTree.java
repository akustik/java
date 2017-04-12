import java.util.ArrayList;

/**
 * Brute force implementation to perform operations in a set of 2D points.
 */
public class KdTree {
    
    private class Node
    {
        private Node left;
        private Node right;
        private Point2D point;
        
        public Node(Point2D p)
        {
            point = p;
            left = null;
            right = null;
        }
        
        public void setLeft(Node n)
        {
            assert (left == null);
            left = n;
        }
        
        public void setRight(Node n)
        {
            assert (right == null);
            right = n;
        }
        
        public Node left()
        {
            return left;
        }
        
        public Node right()
        {
            return right;   
        }
        
        public Point2D point()
        {
            return point;
        }
    }
    
    private Node root;
    private int size;

   /**
    * construct an empty set of points
    */
    public KdTree()
    {
        root = null;
        size = 0;
    }
   
   /**
    * is the set empty?
    */
    public boolean isEmpty()
    {
        return size == 0;
    }
   
   /**
    *  number of points in the set
    */
    public int size()
    {
        return size;
    }
   
   /**
    * add the point p to the set (if it is not already in the set)
    */
    public void insert(Point2D p)
    {
        if (p != null)
        {
            if (size > 0)
            {
                if (!p.equals(root.point()))
                {
                    new TreeTraverser(root, p){
                        public void onFoundNode()
                        {
                            //The node is already set
                        }
                        public void onFoundPosition(Node parent, boolean isLeft)
                        {
                            if (isLeft)
                            {
                                parent.setLeft(new Node(target));
                            }
                            else
                            {
                                parent.setRight(new Node(target));
                            }
                            
                            size++;
                        }
                    }.traverse();
                }
            }
            else
            {
                root = new Node(p);
                size = 1;
            }
        }
    }
    
    private abstract class TreeTraverser
    {
        private Node current;
        private int level;
        protected Point2D target;
        
        public TreeTraverser(Node root, Point2D target)
        {
            this.current = root;
            this.level = 0;
            this.target = target;
        }
        
        public boolean less()
        {
            boolean less;
            if (level % 2 == 0) 
            {
                less = target.x() < current.point().x();
            }
            else
            {
                less = target.y() < current.point().y();
            }
            return less;
        }
        
        public void traverse()
        {
            while (current != null)
            {
                Node next = null;
                boolean isLeft = true;
                if (less())
                {
                    next = current.left();               
                }
                else
                {
                    next = current.right();
                    isLeft = false;
                }
                
                if (next == null)
                {
                   onFoundPosition(current, isLeft);
                   current = null;
                }
                else if (next.point().equals(target))
                {
                   onFoundNode();
                   current = null;
                }
                else
                {
                    current = next;
                    level++;
                }
            }
        }
        
        public abstract void onFoundNode();
        public abstract void onFoundPosition(Node parent, boolean isLeft);
    }
   
   /**
    * does the set contain the point p?
    */
    public boolean contains(Point2D p)
    {
        if (p == null) return false;
        if (root == null) return false;
        if (root.point().equals(p)) return true;
        final ArrayList<Boolean> found = new ArrayList<Boolean>(1);
        
            new TreeTraverser(root, p){
                public void onFoundNode()
                {
                    found.add(true);
                }
                public void onFoundPosition(Node parent, boolean isLeft)
                {
                    found.add(false);
                }
            }.traverse();          
     
        
        assert (found.size() == 1);
        return found.get(0);
    }
   
   /**
    * draw all of the points to standard draw
    */
    public void draw()
    {
        draw(root, 0, 0, 1, 0, 1);
    }
    
    private void draw(Node n, int level, double left, 
                      double right, double top, double bottom)
    {
        if (n != null)
        {
            if (level % 2 == 0)
            {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(n.point().x(), top, n.point().x(), bottom);
                draw(n.left(), level + 1, left, n.point().x(), top, bottom);
                draw(n.right(), level + 1, n.point().x(), right, top, bottom);
            } 
            else
            {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(left, n.point().y(), right, n.point().y());
                draw(n.left(), level + 1, left, right, top, n.point().y());
                draw(n.right(), level + 1, left, right, n.point().y(), bottom);
            }
            
            StdDraw.setPenColor(StdDraw.BLACK);
            n.point().draw();
        }
    }
   
   /**
    * all points in the set that are inside the rectangle
    */
    public Iterable<Point2D> range(RectHV rect)
    {
        ArrayList<Point2D> l = new ArrayList<Point2D>();
        findSubtreePointsInRange(l, root, rect, 0, 0, 1, 0, 1);
        return l;
    }
    
    private void findSubtreePointsInRange(
        ArrayList<Point2D> l, Node n, RectHV rect, 
        int level, double left, double right, double top, double bottom)
    {
        if (n != null)
        {
            if (rect.contains(n.point()))
            {
                l.add(n.point()); 
            }
            
            if (level % 2 == 0)
            {
                if (rect.intersects(new RectHV(left, top, n.point().x(), bottom)))
                {
                    findSubtreePointsInRange(
                    l, n.left(), rect, 
                    level + 1, left, n.point().x(), top, bottom);
                }
                
                if (rect.intersects(new RectHV(n.point().x(), top, right, bottom)))
                {
                    findSubtreePointsInRange(
                    l, n.right(), rect, 
                    level + 1, n.point().x(), right, top, bottom);
                }            
            }
            else
            {
                if (rect.intersects(new RectHV(left, top, right, n.point().y())))
                {
                    findSubtreePointsInRange(
                    l, n.left(), rect, 
                    level + 1, left, right, top, n.point().y());
                }
                
                if (rect.intersects(new RectHV(left, n.point().y(), right, bottom)))
                {
                    findSubtreePointsInRange(
                    l, n.right(), rect, 
                    level + 1, left, right, n.point().y(), bottom);
                }             
            }
        }
    }
   
   /**
    * a nearest neighbor in the set to p; null if set is empty
    */
    public Point2D nearest(Point2D p)
    {
        assert (p != null);
        ChampionPoint2D c = new ChampionPoint2D();
        c.setPoint(null, Double.POSITIVE_INFINITY);
        findNearestPointInSubtree(root, p, 0, c, 0, 0, 1, 1);
        //System.out.println("Inspected: " + c.inspected);
        return c.point();
    }
    
    private class ChampionPoint2D 
    {
        private Point2D point;
        private double distance;
        //public int inspected;
        
        public ChampionPoint2D()
        {
            //inspected = 0;
        }
        
        public void setPoint(Point2D p, double d)
        {
            point = p;
            distance = d;
        }
        
        public double distance()
        {
            return distance;
        }
        
        public Point2D point()
        {
            return point;
        }
    }
    
    private void findNearestPointInSubtree(Node n, Point2D p, 
                                           int level, ChampionPoint2D c,
                                           double left, double top, double right, double bottom)
    {
        if (n != null)
        {
            //Update champion
            double d = p.distanceSquaredTo(n.point());
            //c.inspected++;
            if (d < c.distance())
            {
                c.setPoint(n.point(), d);
            }
            
            //Calculate the distance
            if (level % 2 == 0)
            {
                if (p.x() < n.point().x())
                {
                    findNearestPointInSubtree(n.left(), p, level + 1, c, left, top, n.point().x(), bottom);
                    RectHV r = new RectHV(n.point().x(), top, right, bottom);
                    if(r.distanceSquaredTo(p) < c.distance())
                    {   
                        findNearestPointInSubtree(n.right(), p, level + 1, c, n.point().x(), top, right, bottom);
                    }
                }
                else
                {
                    findNearestPointInSubtree(n.right(), p, level + 1, c, n.point().x(), top, right, bottom);
                    RectHV r = new RectHV(left, top, n.point().x(), bottom);
                    if(r.distanceSquaredTo(p) < c.distance())
                    {   
                        findNearestPointInSubtree(n.left(), p, level + 1, c, left, top, n.point().x(), bottom);
                    }
                }
            }
            else
            {
                if (p.y() < n.point().y())
                {
                    findNearestPointInSubtree(n.left(), p, level + 1, c, left, top, right, n.point().y());
                    RectHV r = new RectHV(left, n.point().y(), right, bottom);
                    if(r.distanceSquaredTo(p) < c.distance())
                    {   
                        findNearestPointInSubtree(n.right(), p, level + 1, c, left, n.point().y(), right, bottom);
                    }                     
                }
                else
                {
                    findNearestPointInSubtree(n.right(), p, level + 1, c, left, n.point().y(), right, bottom);
                    RectHV r = new RectHV(left, top, right, n.point().y());
                    if(r.distanceSquaredTo(p) < c.distance())
                    {   
                        findNearestPointInSubtree(n.left(), p, level + 1, c, left, top, right, n.point().y());
                    }                     
                }
            }
        }    
    }
    
//    protected ArrayList<ArrayList<Point2D>> toList()
//    {
//        ArrayList<ArrayList<Point2D>> insertedPoints = 
//            new ArrayList<ArrayList<Point2D>>();
//        fill(insertedPoints, root, 0);
//        return insertedPoints;
//    }
//    
//    protected void fill(ArrayList<ArrayList<Point2D>> list, Node n, int level)
//    {
//        if (n != null)
//        {
//           if (list.size() <= level)
//           {
//               list.add(new ArrayList<Point2D>());
//           }
//           list.get(level).add(n.point());
//           fill(list, n.left(), level + 1);
//           fill(list, n.right(), level + 1);
//        }
//    }  
}