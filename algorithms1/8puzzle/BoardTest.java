import junit.framework.TestCase;
import junit.framework.Assert;
import java.util.*;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class BoardTest extends TestCase {
    
    public void testConstuctor() {
        Board b = new Board(new int[3][3]);
    }
    
    public void testDimension() {
        Board b = new Board(new int[3][3]);
        Assert.assertEquals(3, b.dimension());
    }
    
    public void testHamming() {
        Board b = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
        Assert.assertEquals(5, b.hamming());
        
        Board c = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
        Assert.assertEquals(0, c.hamming());
    }
    
    public void testManhattan() {
        Board b = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
        Assert.assertEquals(10, b.manhattan());
        
        Board c = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
        Assert.assertEquals(0, c.manhattan());
    }
    
    public void testIsNotGoal() {
        Board b = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
        Assert.assertFalse(b.isGoal());
    }
    
    public void testIsGoal() {
        Board b = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
        Assert.assertTrue(b.isGoal());
    }
    
    public void testIsGoal2x2a() {
        Board b = new Board(new int[][]{{1, 2}, {3, 0}});
        Assert.assertTrue(b.isGoal());
    }
    
    public void testIsGoal2x2b() {
        Board b = new Board(new int[][]{{1, 2}, {0, 3}});
        Assert.assertTrue(!b.isGoal());
    }
    
    public void testIsGoal9x9() {
        Board b = new Board(new int[][]{
            {1, 2, 3, 4, 5, 6, 7, 8, 9},
            {10, 11, 12, 13, 14, 15, 16, 17, 18},
            {19, 20, 21, 22, 23, 24, 25, 26, 27},
            {28, 29, 30, 31, 32, 33, 34, 35, 36},
            {37, 38, 39, 40, 41, 42, 43, 44, 45},
            {46, 47, 48, 49, 50, 51, 52, 53, 54},
            {55, 56, 57, 58, 59, 60, 61, 62, 63},
            {64, 65, 66, 67, 68, 69, 70, 71, 72},       
            {73, 74, 75, 76, 77, 78, 79, 80, 0},
        });
        Assert.assertTrue(b.isGoal());
    }
    
    public void testTwin() {
        Board b = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
        Assert.assertTrue(b.twin().manhattan() > 0);
    }
    
    public void testToString() {
        Board b = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
        Assert.assertTrue(b.toString().compareTo("3\n 1  2  3  \n 4  5  6  \n 7  8  0  \n") == 0);
    }
    
    public void testEquals() {
        Board b = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
        Assert.assertTrue(b.equals(b));
        Assert.assertFalse(b.equals(null));
        Assert.assertFalse(b.equals(b.twin()));
    }
    
    public void testNeighborsBottomRight() {
        Board b = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
        List<Board> eb = new ArrayList<Board>();
        eb.add(new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 0, 8}}));
        eb.add(new Board(new int[][]{{1, 2, 3}, {4, 5, 0}, {7, 8, 6}}));
        
        int idx = 0;
        for(Board board: b.neighbors()){
            Assert.assertEquals(board.toString(), eb.get(idx++).toString());
        } 
    }
    
    public void testNeighborsUpperLeft() {
        Board b = new Board(new int[][]{{0, 2, 3}, {4, 5, 6}, {7, 8, 1}});
        List<Board> eb = new ArrayList<Board>();
        eb.add(new Board(new int[][]{{2, 0, 3}, {4, 5, 6}, {7, 8, 1}}));
        eb.add(new Board(new int[][]{{4, 2, 3}, {0, 5, 6}, {7, 8, 1}}));

        int idx = 0;
        for(Board board: b.neighbors()){
            Assert.assertEquals(board.toString(), eb.get(idx++).toString());
        } 
    }
    
    public void testNeighborsTop() {
        Board b = new Board(new int[][]{{1, 0, 3}, {4, 2, 5}, {7, 8, 6}});
        List<Board> eb = new ArrayList<Board>();
        eb.add(new Board(new int[][]{{1, 3, 0}, {4, 2, 5}, {7, 8, 6}}));
        eb.add(new Board(new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}}));
        eb.add(new Board(new int[][]{{1, 2, 3}, {4, 0, 5}, {7, 8, 6}}));

        int idx = 0;
        for(Board board: b.neighbors()){
            Assert.assertEquals(eb.get(idx++).toString(), board.toString());
        } 
    }
    
    public void testNeighborsCenter() {
        Board b = new Board(new int[][]{{1, 2, 3}, {4, 0, 5}, {7, 8, 6}});
        List<Board> eb = new ArrayList<Board>();
        eb.add(new Board(new int[][]{{1, 2, 3}, {4, 5, 0}, {7, 8, 6}}));
        eb.add(new Board(new int[][]{{1, 2, 3}, {0, 4, 5}, {7, 8, 6}}));
        eb.add(new Board(new int[][]{{1, 2, 3}, {4, 8, 5}, {7, 0, 6}}));
        eb.add(new Board(new int[][]{{1, 0, 3}, {4, 2, 5}, {7, 8, 6}}));

        int idx = 0;
        for(Board board: b.neighbors()){
            Assert.assertEquals(eb.get(idx++).toString(), board.toString());
        } 
    }
    
}
