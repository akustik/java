import junit.framework.TestCase;
import junit.framework.Assert;

public class PointSETTest extends TestCase {
    
    public void testEmptySetConstructor() {
        PointSET s = new PointSET();
        
        Assert.assertTrue("The set is not empty", s.isEmpty());
        Assert.assertEquals(0, s.size());
    }
    
    public void testInsert() {
        PointSET s = new PointSET();
        s.insert(new Point2D(0.5, 0.5));
        
        Assert.assertFalse("The set is empty", s.isEmpty());
        Assert.assertEquals(1, s.size());
        Assert.assertTrue(s.contains(new Point2D(0.5, 0.5)));
    }
    
    public void testInsertSamePointTwice(){
        PointSET s = new PointSET();
        s.insert(new Point2D(0.5, 0.5));
        s.insert(new Point2D(0.5, 0.5));
        
        Assert.assertEquals(1, s.size());   
    }
    
    public void testRangeWithNoPoints(){
        PointSET s = new PointSET();
        s.insert(new Point2D(0.5, 0.5));
        RectHV r = new RectHV(0.2, 0.2, 0.3, 0.3);
        
        Assert.assertFalse(s.range(r).iterator().hasNext());
    }
    
    public void testRangeWithAPointInRangeAndTheOtherOut(){
        PointSET s = new PointSET();
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
    
    public void testNearestWithoutPoints(){
        PointSET s = new PointSET();
        
        Assert.assertTrue(null == s.nearest(new Point2D(1, 1)));
    }
    
    public void testNearestWithTwoPoints(){
        PointSET s = new PointSET();
        s.insert(new Point2D(1, 1));
        s.insert(new Point2D(4, 4));
        
        Assert.assertTrue(new Point2D(4, 4).equals(s.nearest(new Point2D(3, 3))));
    }
    
}
