import java.awt.Color;

import org.junit.Assert;
import org.junit.Test;

public class SeamCarverTest {

    enum Datasets {
        INSTANCE;

        public final Picture image6per5;
        public final Picture image1per1;
        public final Picture image3per3;

        private Datasets() {
            image6per5 = new Picture(SeamCarverTest.class.getResource("6x5.png").getPath());
            image1per1 = new Picture(1,1);
            image3per3 = new Picture(3,3);
            image3per3.set(0, 0, Color.BLACK);
            image3per3.set(0, 1, Color.WHITE);
            image3per3.set(0, 2, Color.BLUE);
            image3per3.set(1, 0, Color.RED);
            image3per3.set(1, 1, Color.GREEN);
            image3per3.set(1, 2, Color.ORANGE);
            image3per3.set(2, 0, Color.GRAY);
            image3per3.set(2, 1, Color.YELLOW);
            image3per3.set(2, 2, Color.DARK_GRAY);
        }
    }
    
    @Test
    public void testConstructor() {
        Picture p = new Picture(Datasets.INSTANCE.image6per5);
        SeamCarver sc = new SeamCarver(p);
        Assert.assertTrue("The loaded image is not the same", p.equals(sc.picture()));
        Assert.assertEquals(p.width(), sc.width());
        Assert.assertEquals(p.height(), sc.height());
        
        //Check the defensive copy
        p.set(0, 0, Color.BLACK);
        Assert.assertFalse("The stored image is not a copy of the original", p.equals(sc.picture()));
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testEnergyOfNegativePixel() {
        SeamCarver sc = new SeamCarver(Datasets.INSTANCE.image6per5);
        sc.energy(-1, 0);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testEnergyOfOutOfBoundsPixel() {
        SeamCarver sc = new SeamCarver(Datasets.INSTANCE.image6per5);
        sc.energy(sc.width(), 0);
    }
    
    @Test
    public void testEnergyOfBorderPixel() {
        SeamCarver sc = new SeamCarver(Datasets.INSTANCE.image6per5);
        Assert.assertEquals(195075, sc.energy(1, 0), 0.1);
    }
    
    @Test
    public void testEnergyOfMiddlePixel() {
        SeamCarver sc = new SeamCarver(Datasets.INSTANCE.image6per5);
        Assert.assertEquals(23346, sc.energy(1, 1), 0.1);
        Assert.assertEquals(51304, sc.energy(2, 1), 0.1);
    }
    
    /*
    @Test
    public void testPixelConversion() {
        SeamCarver sc = new SeamCarver(Datasets.INSTANCE.image6per5);
        SeamCarver.Pixel p1 = sc.newPixel(2, 3, false);
        Assert.assertEquals(2, sc.newPixel(p1.idx(), false).x());
        Assert.assertEquals(3, sc.newPixel(p1.idx(), false).y());
        SeamCarver.Pixel p2 = sc.newPixel(3, 0, false);
        Assert.assertEquals(3, sc.newPixel(p2.idx(), false).x());
        Assert.assertEquals(0, sc.newPixel(p2.idx(), false).y());
    }*/
    
    @Test 
    public void testFindVerticalSeam() {
        SeamCarver sc = new SeamCarver(Datasets.INSTANCE.image6per5);
        int seam[] = sc.findVerticalSeam();
        Assert.assertEquals(sc.height(), seam.length);
        Assert.assertTrue("Does not belong to options", Math.abs(3 - seam[0]) <= 1);
        Assert.assertEquals(3, seam[1]);
        Assert.assertEquals(3, seam[2]);
        Assert.assertEquals(3, seam[3]);
        Assert.assertTrue("Does not belong to options", Math.abs(3 - seam[4]) <= 1);
    }
    
    @Test 
    public void testFindHorizontalSeam() {
        SeamCarver sc = new SeamCarver(Datasets.INSTANCE.image6per5);
        int seam[] = sc.findHorizontalSeam();
        Assert.assertEquals(sc.width(), seam.length);
        Assert.assertTrue("Does not belong to options", Math.abs(3 - seam[0]) <= 1);
        Assert.assertEquals(3, seam[1]);
        Assert.assertEquals(3, seam[2]);
        Assert.assertEquals(3, seam[3]);
        Assert.assertEquals(2, seam[4]);
        Assert.assertTrue("Does not belong to options", Math.abs(2 - seam[5]) <= 1);
    }
    
    
    @Test(expected = IllegalArgumentException.class)
    public void testremoveVerticalSeamWithImageTooNarrow() {
        SeamCarver sc = new SeamCarver(Datasets.INSTANCE.image1per1);
        sc.removeVerticalSeam(new int[0]);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testremoveVerticalSeamWithWrongLength() {
        SeamCarver sc = new SeamCarver(Datasets.INSTANCE.image6per5);
        sc.removeVerticalSeam(new int[]{0, 1, 2, 3});
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testremoveVerticalSeamOutOfBounds() {
        SeamCarver sc = new SeamCarver(Datasets.INSTANCE.image6per5);
        sc.removeVerticalSeam(new int[]{0, -1, 0, 1, 2});
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testremoveVerticalSeamWithAJump() {
        SeamCarver sc = new SeamCarver(Datasets.INSTANCE.image6per5);
        sc.removeVerticalSeam(new int[]{0, 1, 3, 4, 5});
    }
    
    @Test
    public void testremoveVerticalSeam() {
        SeamCarver sc = new SeamCarver(Datasets.INSTANCE.image3per3);
        sc.removeVerticalSeam(new int[]{1, 2, 1});
        Assert.assertEquals(2, sc.width());
        Assert.assertEquals(3, sc.height());
        Assert.assertEquals(Color.BLACK, sc.picture().get(0, 0));
        Assert.assertEquals(Color.GRAY, sc.picture().get(1, 0));
        Assert.assertEquals(Color.WHITE, sc.picture().get(0, 1));
        Assert.assertEquals(Color.GREEN, sc.picture().get(1, 1));
        Assert.assertEquals(Color.BLUE, sc.picture().get(0, 2));
        Assert.assertEquals(Color.DARK_GRAY, sc.picture().get(1, 2));
    }
    
    @Test
    public void testremoveHorizontalSeam() {
        SeamCarver sc = new SeamCarver(Datasets.INSTANCE.image3per3);
        sc.removeHorizontalSeam(new int[]{1, 2, 1});
        Assert.assertEquals(3, sc.width());
        Assert.assertEquals(2, sc.height());
        Assert.assertEquals(Color.BLACK, sc.picture().get(0, 0));
        Assert.assertEquals(Color.RED, sc.picture().get(1, 0));
        Assert.assertEquals(Color.GRAY, sc.picture().get(2, 0));
        Assert.assertEquals(Color.BLUE, sc.picture().get(0, 1));
        Assert.assertEquals(Color.GREEN, sc.picture().get(1, 1));
        Assert.assertEquals(Color.DARK_GRAY, sc.picture().get(2, 1));
    }
}
