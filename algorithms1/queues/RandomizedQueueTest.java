import junit.framework.TestCase;
import junit.framework.Assert;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ArrayList;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class RandomizedQueueTest extends TestCase {
    
    /**
     * Tests an enqueue operation of several items
     */
    public void testEnqueue() 
    {
        //prepare
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
        
        //test
        q.enqueue(0);
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);
        
        //assert
        Assert.assertEquals(5, q.size());
    }
    
    public void testEnqueueNullElement()
    {
        //prepare
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
        
        //test
        try
        {
            q.enqueue(null);
            
            //assert
            Assert.fail("A null element has been inserted");
        }
        catch(NullPointerException e)
        {
            
        }        
    }
    
    public void testDequeueOnEmptyQueue()
    {
        //prepare
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
        
        //test
        try
        {
            q.dequeue();
            
            //assert
            Assert.fail("No elements should be available");
        }
        catch(NoSuchElementException e)
        {
            
        }        
    }
    
    public void testSampleOnEmptyQueue()
    {
        //prepare
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
        
        //test
        try
        {
            q.sample();
            
            //assert
            Assert.fail("No elements should be available");
        }
        catch(NoSuchElementException e)
        {
            
        }        
    }
    
    /**
     * Tests an enqueue operation of several items
     */
    public void testDequeue() 
    {
        //prepare
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        ArrayList<String> values = new ArrayList<String>();
        for (int i=0; i<100; i++)
        {
            String val = "val" + i;
            values.add(val);
            q.enqueue(val);
        }
        
        //test
        for (int i=0; i<100; i++)
        {
            String value = q.dequeue();
            Assert.assertTrue("Unable to remove " 
                                  + value, values.remove(value));
        }
        
        
        //assert
        Assert.assertEquals(0, values.size());
        Assert.assertEquals(0, q.size());
    }
    
    /**
     * Tests an sample operation of several items
     */
    public void testSample() 
    {
        //prepare
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        ArrayList<String> values = new ArrayList<String>();
        for (int i=0; i<100; i++)
        {
            String val = "val" + i;
            values.add(val);
            q.enqueue(val);
        }
        
        //test
        for (int i=0; i<100; i++)
        {
            String value = q.sample();
            Assert.assertTrue("Unable to check " 
                                  + value, values.contains(value));
        }
        
        
        //assert
        Assert.assertEquals(100, q.size());
    }
    
    /**
     * Tests an iterator of several items
     */
    public void testIterator() 
    {
        //prepare
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        ArrayList<String> values = new ArrayList<String>();
        for (int i=0; i<100; i++)
        {
            String val = "val" + i;
            values.add(val);
            q.enqueue(val);
        }
        
        //test
        for (String value: q)
        {
            Assert.assertTrue("Unable to check " 
                                  + value, values.contains(value));
        }
        
        
        //assert
        Assert.assertEquals(100, q.size());
    }
    
    /**
     * Tests an empty iterator.
     */
    public void testAnEmptyIteratorNextAndHasNext()
    {
        //prepare
        RandomizedQueue<Integer> d = new RandomizedQueue<Integer>();
        
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
        RandomizedQueue<Integer> d = new RandomizedQueue<Integer>();
        
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
    
}
