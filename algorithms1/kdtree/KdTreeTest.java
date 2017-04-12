import junit.framework.TestCase;
import junit.framework.Assert;
import java.util.ArrayList;

public class KdTreeTest extends TestCase {
    
    public void testEmptySetConstructor() {
        KdTree s = new KdTree();
        
        Assert.assertTrue("The set is not empty", s.isEmpty());
        Assert.assertEquals(0, s.size());
    }
    
    public void testInsert() {
        KdTree s = new KdTree();
        s.insert(new Point2D(0.5, 0.5));
        
        Assert.assertFalse("The set is empty", s.isEmpty());
        Assert.assertEquals(1, s.size());
        Assert.assertTrue(s.contains(new Point2D(0.5, 0.5)));
    }
    
    public void testInsertSamePointTwice(){
        KdTree s = new KdTree();
        s.insert(new Point2D(0.5, 0.5));
        s.insert(new Point2D(0.5, 0.5));
        
        Assert.assertTrue(s.contains(new Point2D(0.5, 0.5)));
        Assert.assertEquals(1, s.size());   
    }
    
//    public void testInsertPointsForATree(){
//        KdTree s = new KdTree();
//        s.insert(new Point2D(0.5, 0.5));
//        
//        s.insert(new Point2D(0.2, 0.8));
//        s.insert(new Point2D(0.2, 0.4));
//        s.insert(new Point2D(0.2, 0.9));    
//        
//        s.insert(new Point2D(0.7, 0.8));
//        s.insert(new Point2D(0.7, 0.4));
//        s.insert(new Point2D(0.7, 0.9));
//        
//        Assert.assertEquals(7, s.size());
//        
//        ArrayList<ArrayList<Point2D>> i = s.toList();
//        
//        Assert.assertEquals(new Point2D(0.5, 0.5), i.get(0).get(0));
//        
//        Assert.assertEquals(new Point2D(0.2, 0.8), i.get(1).get(0));
//        Assert.assertEquals(new Point2D(0.2, 0.4), i.get(2).get(0));
//        Assert.assertEquals(new Point2D(0.2, 0.9), i.get(2).get(1));
//
//        Assert.assertEquals(new Point2D(0.7, 0.8), i.get(1).get(1));
//        Assert.assertEquals(new Point2D(0.7, 0.4), i.get(2).get(2));
//        Assert.assertEquals(new Point2D(0.7, 0.9), i.get(2).get(3));      
//        
//    }
    
    public void testRangeWithNoPoints(){
        KdTree s = new KdTree();
        s.insert(new Point2D(0.5, 0.5));
        RectHV r = new RectHV(0.2, 0.2, 0.3, 0.3);
        
        Assert.assertFalse(s.range(r).iterator().hasNext());
    }
    
    public void testRangeWithAPointInRangeAndTheOtherOut(){
        KdTree s = new KdTree();
        s.insert(new Point2D(0.5, 0.5));
        s.insert(new Point2D(0.25, 0.25));
        RectHV r = new RectHV(0.2, 0.2, 0.3, 0.3);
        
        int n = 0;
        for (Point2D p: s.range(r))
        {
            Assert.assertEquals(new Point2D(0.25, 0.25), p);
            n++;
        }
        
        Assert.assertEquals(1, n);
    }
    
    public void testRangeWithABuiltTree(){
        KdTree s = new KdTree();
        s.insert(new Point2D(0.5, 0.5));
        
        s.insert(new Point2D(0.2, 0.8));
        s.insert(new Point2D(0.2, 0.4));
        s.insert(new Point2D(0.2, 0.9));    
        
        s.insert(new Point2D(0.7, 0.8));
        s.insert(new Point2D(0.7, 0.4));
        s.insert(new Point2D(0.7, 0.9));
     
        assertSize(s.range(new RectHV(0, 0, 1, 1)), 7);
        assertSize(s.range(new RectHV(0, 0, 0.1, 0.1)), 0);
        
        assertSize(s.range(new RectHV(0, 0, 1, 0.5)), 3);
        assertPointAppears(s.range(new RectHV(0, 0, 1, 0.5)), new Point2D(0.5, 0.5));
        assertPointAppears(s.range(new RectHV(0, 0, 1, 0.5)), new Point2D(0.2, 0.4));
        assertSize(s.range(new RectHV(0, 0.5, 1, 1)), 5);
        assertPointAppears(s.range(new RectHV(0, 0.5, 1, 1)), new Point2D(0.5, 0.5));
        assertPointAppears(s.range(new RectHV(0, 0.5, 1, 1)), new Point2D(0.7, 0.9));
        
        
    }
    
    private void assertSize(Iterable<Point2D> it, int size){
        int s = 0;
        for (Point2D p: it)
        {
            s++;
        }
        Assert.assertEquals(size, s);
    }
    
    private void assertPointAppears(Iterable<Point2D> it, Point2D p){
        boolean appears = false;
        for (Point2D p2: it)
        {
            if(p2.equals(p))
            {
                appears = true;
                break;
            }
        }
        Assert.assertTrue("The point " +  p + " does not appear", appears);
    }
    
    
    public void testNearestWithoutPoints(){
        KdTree s = new KdTree();
        
        Assert.assertTrue(null == s.nearest(new Point2D(0.1, 0.1)));
    }
    
    public void testNearestWithTwoPoints(){
        KdTree s = new KdTree();
        s.insert(new Point2D(0.1, 0.1));
        s.insert(new Point2D(0.4, 0.4));
        
        Assert.assertTrue(new Point2D(0.4, 0.4).equals(s.nearest(new Point2D(0.3, 0.3))));
    }
    
    public void testRandomInsertionsAndChecks(){
        KdTree k = new KdTree();
        PointSET s = new PointSET();
        
        java.util.Random r = new java.util.Random(1);
        for (int i=0; i<10000; i++)
        {
            Point2D p = new Point2D(r.nextDouble(), r.nextDouble());
            k.insert(p);
            s.insert(p);
            Assert.assertTrue("Containers size differ with " + p, k.size() == s.size());
            Assert.assertTrue("Containers contains() differ with " + p, k.contains(p) == s.contains(p));
        }
    }
    
    public void testCircle10kContains(){
        System.out.println("testfiles/circle10.txt");
        In in = new In("testfiles/circle10k.txt");
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
        
        Point2D point = kdtree.nearest(new Point2D(0.81, 0.3));
        System.out.println("Nearest: " + point);
    }
    
    public void testCircle10Contains(){
        System.out.println("testfiles/circle10.txt");
        In in = new In("testfiles/circle10.txt");
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
        
        Point2D point = kdtree.nearest(new Point2D(0.81, 0.3));
        System.out.println("Nearest: " + point);
    }
    
}
