import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A generalization of a stack and a queue that supports inserting and 
 * removing items from either the front or the back of the data structure
 */
public class Deque<Item> implements Iterable<Item> 
{
    private int size;
    private Node last;
    private Node first;
    
    // helper linked list class
    private class Node {
        private Item item;
        private Node previous;
        private Node next;
        
        public void dispose()
        {
            item = null;
            previous = null;
            next = null;
        }
    }
    
    // helper iterator
    private class DequeIterator implements Iterator<Item> {
        
        private Node current;
        
        public DequeIterator(Node n)
        {
            current = n;
        }
        
        public void remove()
        {        
            throw new UnsupportedOperationException("Cannot remove elements");
        }
        
        public Item next()
        {
            if (current == null)
            {
               throw new NoSuchElementException("There are no more elements"); 
            }
            
            Item i = current.item;
            current = current.next;
            return i;
        }
        
        public boolean hasNext()
        {
            return current != null;
        }        
    }
    
    /**
     * construct an empty deque 
     */
    public Deque()
    {
        last = null;
        first = null;
    }                   
    
    /**
     * is the deque empty?
     */
    public boolean isEmpty()
    {
        return size == 0;
    }          
    
    /**
     * return the number of items on the deque
     */
    public int size()
    {
        return size;
    }          
    
    /**
     * insert the item at the front
     */
    public void addFirst(Item item)
    {
        if (item == null)
        {
            throw new NullPointerException("Null items are not allowed");
        }
        
        Node next = first;
        first = new Node();
        first.item = item;
        first.next = next;
        
        if (last == null)
        {
            last = first;
        }
        
        if (next != null)
        {
            next.previous = first;        
        }

        updateSize(+1);
    }
    
    /**
     * insert the item at the end
     */    
    public void addLast(Item item)
    {
        if (item == null)
        {
            throw new NullPointerException("Null items are not allowed");
        }
        
        Node previous = last;
        last = new Node();
        last.item = item;
        last.previous = previous;
        
        if (first == null)
        {
            first = last;
        }
        
        if (previous != null)
        {
            previous.next = last;
        }
                
        updateSize(+1);
    }
    
    /**
     * delete and return the item at the front
     */
    public Item removeFirst()
    {
        if (isEmpty())
        {
            throw new java.util.NoSuchElementException("The deque is empty");
        }
                
        Node oldFirst = first;
        Item i = oldFirst.item;
        first = oldFirst.next;
        
        oldFirst.dispose();
        
        if (first != null)
        {
           first.previous = null;
        }
        
        updateSize(-1);
        return i;
    }    
    
    /**
     * delete and return the item at the end
     */
    public Item removeLast()
    {
        if (isEmpty())
        {
            throw new java.util.NoSuchElementException("The deque is empty");
        }
      
        Node oldLast = last;
        Item i = oldLast.item;
        last = oldLast.previous;
        
        oldLast.dispose();
        
        if (last != null)
        {
           last.next = null;                     
        }
                
        updateSize(-1);
        return i;
    }
    
    private void updateSize(int n)
    {
        size += n;
        if (size == 0)
        {
            first = null;
            last = null;
        }
    }
    
    /**
     * return an iterator over items in order from front to end
     */    
    public Iterator<Item> iterator()
    {
        return new DequeIterator(first);
    }   
}



