import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A queue implementation that deques elements in a random manner.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private Item[] items;
    private int size;
    
    // helper iterator
    private class RandomizedQueueIterator implements Iterator<Item> {
        
        private int current;
        private Item[] shuffledItems;
        
        public RandomizedQueueIterator()
        {
            shuffledItems = (Item[]) new Object[size];
            for (int i = 0; i < shuffledItems.length; i++)
            {
                shuffledItems[i] = items[i];
            }
            StdRandom.shuffle(shuffledItems);
            current = 0;
        }
        
        public void remove()
        {        
            throw new UnsupportedOperationException("Cannot remove elements");
        }
        
        public Item next()
        {
            if (current == shuffledItems.length)
            {
               throw new NoSuchElementException("There are no more elements"); 
            }
            
            return shuffledItems[current++];
        }
        
        public boolean hasNext()
        {
            return current < shuffledItems.length;
        }        
    }
    
    public RandomizedQueue()
    {
        items = (Item[]) new Object[2];
        size = 0;
    }
    
    /**
     * is the queue empty?
     */
    public boolean isEmpty()
    {
        return size == 0;
    }
    
    /**
     * return the number of items on the queue
     */
    public int size() 
    {
        return size;
    }
    
    /**
     * add the item
     */
    public void enqueue(Item item)
    {
       if (item == null)
       {
           throw new NullPointerException();
       }
       
       if (items.length == size) resize(2*items.length);
       items[size++] = item;
       assert checkReferences();
    }
    
    /**
     * delete and return a random item
     */
    public Item dequeue()
    {
        Item i;
        if (size > 1)
        {
            int idx = StdRandom.uniform(size);
            i = items[idx];
            items[idx] = items[size - 1];
            items[size - 1] = null;
        } 
        else if (size == 1)
        {
            i = items[0];
            items[0] = null;
        }
        else
        {
            throw new NoSuchElementException("There are no elements");
        }

        size--;
        
        if (size > 0 && size == items.length/4) resize(items.length/2);
        assert checkReferences();
        return i;
    }
    
    private boolean checkReferences()
    {
        boolean valid = true;
        for (int i = 0; i < size; i++)
        {
            if (items[i] == null)
            {
                System.out.println("Items " + i + " is null");
                valid = false;
            }
        }
        
        for (int i = size; i < items.length; i++)
        {
            if (items[i] != null)
            {
                System.out.println("Items " + i + " is not null");
                valid = false;
            }
        }
        
        return valid;
    }
    
    /**
     * return (but do not delete) a random item
     */
    public Item sample()
    {
        if (size > 0)
        {
            int idx = StdRandom.uniform(size);
            return items[idx];
        }
        else
        {
            throw new NoSuchElementException("There are no elements");
        }
    }
    
    /**
     * return an independent iterator over items in random order
     */
    public Iterator<Item> iterator()  
    {
        return new RandomizedQueueIterator();
    }
    
    private void resize(int capacity) {
        assert capacity >= size;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            temp[i] = items[i];
        }
        items = temp;
    }
}