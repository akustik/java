import junit.framework.TestCase;
import junit.framework.Assert;
import java.util.NoSuchElementException;
import java.util.Iterator;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class DequeTest extends TestCase {
            
    /**
     * Tests that the class may be used as a stack on the back
     */
    public void testAddLast() 
    {
        //prepare
        Deque<String> d = new Deque<String>();
        
        //test
        int size0 = d.size();
        d.addLast("First addition");
        int size1 = d.size();
        d.addLast("Second addition");
        int size2 = d.size();
        
        //assert
        Assert.assertEquals(d.removeLast(), "Second addition");
        Assert.assertEquals(d.removeLast(), "First addition");
        Assert.assertEquals(0, size0);
        Assert.assertEquals(1, size1);
        Assert.assertEquals(2, size2);
    }
    
    /**
     * Tests that the class may be used as a stack on the front
     */
    public void testAddFirst() 
    {
        //prepare
        Deque<String> d = new Deque<String>();
        
        //test
        int size0 = d.size();
        d.addFirst("First addition");
        int size1 = d.size();
        d.addFirst("Second addition");
        int size2 = d.size();
        
        //assert
        Assert.assertEquals(d.removeFirst(), "Second addition");
        Assert.assertEquals(d.removeFirst(), "First addition");
        Assert.assertEquals(0, size0);
        Assert.assertEquals(1, size1);
        Assert.assertEquals(2, size2);
        
    }
    
    /**
     * Tests adding a null item on the front
     */
    public void testAddFirstNull() 
    {        
        //prepare
        Deque<String> d = new Deque<String>();
        
        //test
        try 
        {
            d.addFirst(null);
            
            //assert
            Assert.fail("A null element has been inserted");
        }
        catch (NullPointerException e)
        {   
        }
    }
    
    /**
     * Tests adding a null item on the back
     */
    public void testAddLastNull() 
    {        
        //prepare
        Deque<String> d = new Deque<String>();
        
        //test
        try 
        {
            d.addLast(null);
            
            //assert
            Assert.fail("A null element has been inserted");
        }
        catch (NullPointerException e)
        {   
        }
    }
    
    /**
     * Tests that an element added on the front can be retrieved on the back
     */
    public void testAddFirstRemoveLast()
    {
        //prepare
        Deque<String> d = new Deque<String>();
        
        //test
        d.addFirst("First addition");
        d.addFirst("Second addition");
        String retrieved = d.removeLast();
        String retrieved2 = d.removeLast();
        
        //assert
        Assert.assertEquals("First addition", retrieved);
        Assert.assertEquals("Second addition", retrieved2);
        Assert.assertEquals(0, d.size());
    }
    
    /**
     * Tests that an element added on the back can be retrieved on the front
     */
    public void testAddLastRemoveFirst()
    {
        //prepare
        Deque<String> d = new Deque<String>();
        
        //test
        d.addLast("First addition");
        String retrieved = d.removeFirst();
        
        //assert
        Assert.assertEquals("First addition", retrieved);
        Assert.assertEquals(0, d.size());
    }   
    
    /**
     * Tests that an element added on the back is effectively removed when
     * retrieved from the front.
     */
    public void testAddLastRemoveFirstThenRemoved()
    {
        //prepare
        Deque<String> d = new Deque<String>();
        
        //test
        d.addLast("First addition");
        String retrieved = d.removeFirst();
        
        //assert
        try 
        {
            d.removeLast();
            Assert.fail("The are still elements in the stack");
        } 
        catch (NoSuchElementException e){
            //Nothing
        }
    } 
    
    /**
     * Tests that an element added on the front is effectively removed when
     * retrieved from the back.
     */
    public void testAddFirstRemoveLastThenRemoved()
    {
        //prepare
        Deque<String> d = new Deque<String>();
        
        //test
        d.addFirst("First addition");
        String retrieved = d.removeLast();
        
        //assert
        try 
        {
            d.removeFirst();
            Assert.fail("The are still elements in the stack");
        } 
        catch (NoSuchElementException e){
            //Nothing
        }
    }
    
    public void testAdditionsAndRemovalsInARandomWay()
    {
         //prepare
        Deque<String> d = new Deque<String>();
        
        //test
        int expectedSize = 0;
        for (int i=0; i<100; i++)
        {
            int type = StdRandom.uniform(4);
            switch(type)
            {
                case 0: 
                    System.out.println("AddFirst " + i);
                    d.addFirst("AddFirst" + i);
                    expectedSize++;
                    break;
                case 1: 
                    System.out.println("AddLast " + i);
                    d.addLast("AddLast" + i);
                    expectedSize++;
                    break;
                case 2:
                    if (expectedSize > 0 ){
                         System.out.println("RemoveLast " + d.removeLast());
                         expectedSize--;
                    }                    
                    break; 
                case 3:
                    if (expectedSize > 0 ){
                         System.out.println("RemoveFirst " + d.removeFirst());
                         expectedSize--;                   
                    }                    
                    break;                 
            }
                        

            
            //assert
            Assert.assertEquals(expectedSize, d.size());
            Assert.assertEquals(expectedSize, countElements(d));
        }
    }
    
    /**
     * Tests an iterator with front inserted items.
     */
    public void testIteratorWithFrontInsertedItems()
    {
        //prepare
        Deque<Integer> d = new Deque<Integer>();
        
        //test
        d.addFirst(2);
        d.addFirst(1);
        d.addLast(3);
        d.addLast(4);
        d.addFirst(0);
        
        //assert
        int idx = 0;
        for (int i: d)
        {
            Assert.assertEquals(idx, i);            
            idx++;
        }
        
        Assert.assertEquals(5, idx);
    }
    
    /**
     * Tests the iterator.
     */
    public void testIterator1()
    {
        //prepare
        Deque<Integer> d = new Deque<Integer>();
        
        //test
        d.addLast(2);
        d.addFirst(1);
        int last = d.removeLast();
        
        //assert
        Assert.assertEquals(2, last);
        Assert.assertEquals(1, d.size());
        Assert.assertEquals(1, countElements(d));
    }
    
    /**
     * Tests an empty iterator.
     */
    public void testAnEmptyIteratorNextAndHasNext()
    {
        //prepare
        Deque<Integer> d = new Deque<Integer>();
        
        //test
        Iterator<Integer> it = d.iterator();
        boolean hasNext = it.hasNext();
        try 
        {
            it.next();
            Assert.fail("Next call should throw an exception");
        }
        catch(NoSuchElementException e)
        {
        }
        
        //assert   
        Assert.assertFalse(hasNext);
    }
    
   /**
     * Tests the remove operation of an iterator.
     */
    public void testIteratorRemove()
    {
        //prepare
        Deque<Integer> d = new Deque<Integer>();
        
        //test
        Iterator<Integer> it = d.iterator();
        try 
        {
            it.remove();
            Assert.fail("Next call should throw an exception");
        }
        catch(UnsupportedOperationException  e)
        {
        }
        
    }
    
    private int countElements(Deque d)
    {
        int itElements = 0;
        System.out.print("[Queue: ");
        for( Object str: d )
        {
            itElements++;
            System.out.print(str + " ");
        }
        System.out.println("]");
        return itElements;
    }
}
