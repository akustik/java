import junit.framework.TestCase;
import junit.framework.Assert;

public class PointTest extends TestCase {
    
    public void testSlopeToUpPositive() {
        //prepare
        Point a = new Point(1, 1);
        Point b = new Point(3, 4);
        
        //test
        double slope = a.slopeTo(b);
        
        //assert
        Assert.assertEquals(1.5, slope);
    }
    
    public void testSlopeToUpNegative() {
        //prepare
        Point a = new Point(1, 4);
        Point b = new Point(3, 1);
        
        //test
        double slope = a.slopeTo(b);
        
        //assert
        Assert.assertEquals(-1.5, slope);
    }
    
    public void testSlopeToDown() {
        //prepare
        Point a = new Point(2, 2);
        Point b = new Point(1, 1);
        
        //test
        double slope = a.slopeTo(b);
        
        //assert
        Assert.assertEquals(1.0, slope);
    }
    
    public void testSlopeToHorizontal() {
        //prepare
        Point a = new Point(2, 2);
        Point b = new Point(1, 2);
        
        //test
        double slope = a.slopeTo(b);
        
        //assert
        Assert.assertEquals(+0.0, slope);
    }
    
    public void testSlopeToVertical() {
        //prepare
        Point a = new Point(2, 2);
        Point b = new Point(2, 4);
        
        //test
        double slope = a.slopeTo(b);
        
        //assert
        Assert.assertEquals(Double.POSITIVE_INFINITY, slope);
    }
    
    public void testSlopeToItself() {
        //prepare
        Point a = new Point(2, 2);
        Point b = new Point(2, 2);
        
        //test
        double slope = a.slopeTo(b);
        
        //assert
        Assert.assertEquals(Double.NEGATIVE_INFINITY, slope);
    }
    
    public void testComparatorSameSlope()
    {
         //prepare
        Point a = new Point(2, 2);
        Point b = new Point(4, 4);
        Point c = new Point(6, 6);
        
        //test
        int result = a.SLOPE_ORDER.compare(b, c);
        
        //assert
        Assert.assertEquals(0, result);       
    }
    
    public void testComparatorPositiveSlope()
    {
         //prepare
        Point a = new Point(2, 2);
        Point b = new Point(3, 4);
        Point c = new Point(3, 8);
        
        //test
        int result = a.SLOPE_ORDER.compare(c, b);
        
        //assert
        Assert.assertEquals(1, result);       
    }
    
    public void testComparatorNegativeSlope()
    {
         //prepare
        Point a = new Point(2, 2);
        Point b = new Point(3, 4);
        Point c = new Point(3, 8);
        
        //test
        int result = a.SLOPE_ORDER.compare(b, c);
        
        //assert
        Assert.assertEquals(-1, result);       
    }
    
    public void testComparatorBothPositiveInfiniteSlope()
    {
         //prepare
        Point a = new Point(2, 2);
        Point b = new Point(2, 4);
        Point c = new Point(2, 8);
        
        //test
        int result = a.SLOPE_ORDER.compare(b, c);
        
        //assert
        Assert.assertEquals(0, result);       
    }    
    
    public void testComparatorBothNegativeInfiniteSlope()
    {
         //prepare
        Point a = new Point(2, 2);
        
        //test
        int result = a.SLOPE_ORDER.compare(a, a);
        
        //assert
        Assert.assertEquals(0, result);       
    } 
    
    public void testComparatorBothPositiveZeroSlope()
    {
         //prepare
        Point a = new Point(2, 2);
        Point b = new Point(4, 2);
        Point c = new Point(8, 2);
        
        //test
        int result = a.SLOPE_ORDER.compare(b, c);
        
        //assert
        Assert.assertEquals(0, result);       
    }
    
    public void testComparatorPositiveInfiniteAgainstPositiveSlope()
    {
         //prepare
        Point a = new Point(2, 2);
        Point b = new Point(2, 4);
        Point c = new Point(3, 3);
        
        //test
        int result = a.SLOPE_ORDER.compare(b, c);
        
        //assert
        Assert.assertEquals(1, result);       
    }
    
    public void testComparatorPositiveInfiniteAgainstNegativeInfiniteSlope()
    {
         //prepare
        Point a = new Point(2, 2);
        Point b = new Point(2, 4);
        Point c = new Point(4, 2);
        
        //test
        int result = a.SLOPE_ORDER.compare(b, c);
        
        //assert
        Assert.assertEquals(1, result);       
    } 
    
}
