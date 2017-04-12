import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Filters elements from a set.
 */
public class Subset {
        
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        try
        {
            for (;;)
            {
                queue.enqueue(StdIn.readString());
            }
        }
        catch (NoSuchElementException e)
        {
            //nothing
        }
        
        Iterator<String> it = queue.iterator();
        while (k > 0)
        {
            System.out.println(it.next());
            k--;
        }
                
    }
}
