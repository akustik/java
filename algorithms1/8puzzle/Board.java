import java.util.ArrayList;
import java.util.List;

public class Board {
    
    private int[][] blocks;
    
    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks)           
    {
        this.blocks = new BoardUtils(blocks).cloneBlocks();
    }
    

    
    // board dimension N
    public int dimension()                 
    {
        return this.blocks.length;
    }
    
    // number of blocks out of place
    public int hamming()                   
    {
        int distance = 0;
        int expected = 1;
        for (int i = 0; i < dimension(); i++)
        {
            for (int j = 0; j < dimension(); j++)
            {
                int value = blocks[i][j];
                if (value != expected++ && value != 0)
                {
                    distance++;
                }
            }
        }
        
        return distance;
    }
    
    // sum of Manhattan distances between blocks and goal
    public int manhattan()                 
    {
        int distance = 0;
        int expected = 1;
        for (int i = 0; i < dimension(); i++)
        {
            for (int j = 0; j < dimension(); j++)
            {
                int value = blocks[i][j];
                if (value != expected && value != 0)
                {
                    Position p = new Position(blocks[i][j], dimension());
                    distance += Math.abs(i  - p.y) + Math.abs(j - p.x);
                }
                expected++;
            }
        }
        
        return distance;
    }
    
    private class Position {
        
        private final int x;
        private final int y;
        
        public Position(int n, int dimension)
        {
            x = (n - 1) % dimension;
            y = (n - 1) / dimension;
        }
        
        public String toString()
        {
            return "(" + x + ", " + y + ")";
        }
        
        public int x()
        {
            return x;
        }
        
        public int y()
        {
            return y;
        }
    }
    
    // is this board the goal board?
    public boolean isGoal()                
    {
        for (int i = 1; i <= dimension() * dimension() - 1;  i++)
        {
            Position p = new Position(i, dimension());
            if (i != blocks[p.y][p.x])
            {
                return false;
            }
        }
        
        return true;
    }
    
    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin()                    
    {
        //Copy the matrix
        int[][] excBlocks = new BoardUtils(blocks).cloneBlocks();
        for (int i = 0; i < dimension(); i++)
        {
            for (int j = 0; j < dimension(); j++)
            {
                excBlocks[i][j] = blocks[i][j];
            }
        }
        
        // Change adjacent blocks
        boolean changed = false;
        for (int i = 0; i < dimension(); i++)
        {
            for (int j = 0; j < dimension() - 1; j++)
            {
                int a = excBlocks[i][j];
                int b = excBlocks[i][j+1];
                if (a != 0 && b != 0)
                {
                    excBlocks[i][j] = b;
                    excBlocks[i][j+1] = a;
                    changed = true;
                    break;
                }
            }
            
            if (changed)
                break;
        }
        
        return new Board(excBlocks);
    }
    
    // does this board equal y?
    public boolean equals(Object y)        
    {
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        
        return toString().compareTo(y.toString()) == 0;
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors()     
    {
        int x = 0;
        int y = 0;
        boolean found = false;
        for (int i = 0; i < dimension(); i++)
        {
            for (int j = 0; j < dimension(); j++)
            {
                if (blocks[i][j] == 0)
                {
                    x = j;
                    y = i;
                }
            }       
        }
        
        BoardUtils utils = new BoardUtils(blocks);
        List<Board> neighbors = new ArrayList<Board>(2);
        
        if (x < dimension() - 1)
        {
            utils.exch(x, y, x + 1, y);
            neighbors.add(new Board(utils.cloneBlocks()));
            utils.exch(x, y, x + 1, y);
        }
        
        if (x > 0)
        {
            utils.exch(x, y, x - 1, y);
            neighbors.add(new Board(utils.cloneBlocks()));
            utils.exch(x, y, x - 1, y);
        }
        
        if (y < dimension() - 1)
        {
            utils.exch(x, y, x, y + 1);
            neighbors.add(new Board(utils.cloneBlocks()));
            utils.exch(x, y, x, y + 1);
        }
        
        if (y > 0)
        {
            utils.exch(x, y, x, y - 1);
            neighbors.add(new Board(utils.cloneBlocks()));
            utils.exch(x, y, x, y - 1);
        }
        
        return neighbors;
        
    }
    
    private class BoardUtils {
        
        private final int[][] b;
        private int swap = 0;
        
        public BoardUtils(int[][] b)
        {
            this.b = b;
        }
        
        public int[][] cloneBlocks()
        {
            int[][] cb = new int[b.length][b.length];
            for (int i = 0; i < b.length; i++)
            {
                for (int j = 0; j < b.length; j++)
                {
                    cb[i][j] = b[i][j];
                }
            }
            
            return cb;
        }
        
        public void exch(int x1, int y1, int x2, int y2)
        {
            swap = b[y1][x1];
            b[y1][x1] = b[y2][x2];
            b[y2][x2] = swap;
        }
        
        public String toString()
        {
            return new Board(b).toString();
        }
    }
    
    // string representation of the board (in the output format specified below)
    public String toString()               
    {
        StringBuilder builder = new StringBuilder();
        builder.append(dimension());
        builder.append("\n");
        for (int i = 0; i < dimension(); i++)
        {
            builder.append(" ");
            for (int j = 0; j < dimension(); j++)
            {
                builder.append(blocks[i][j]);
                builder.append("  ");
            }
            builder.append("\n");
        }
        
        return builder.toString();
    }
}