import java.util.List;
import java.util.Iterator;

public class Solver {
    
    private class BoardKey implements Comparable<BoardKey>, Iterable<Board> {
        private final int priority;
        private final Board board;
        private final BoardKey parent;
        private final int moves;
        
        public BoardKey(Board board, BoardKey parent)
        {            
            this.board = board;
            this.moves = parent.moves + 1;
            this.priority = board.manhattan() + moves;
            this.parent = parent;
            
        }
        
        public BoardKey(Board board)
        {            
            this.board = board;
            this.priority = board.manhattan();
            this.parent = null;
            this.moves = 0;
        }
        
        public String toString()
        {
            return String.format("[%d] %d - > %s", moves, priority, board);
        }
        
        public int compareTo(BoardKey key)
        {
            assert (key != null);
            return priority - key.priority;
        } 
        
        public Iterator<Board> iterator()
        {
            BoardKey current = this;
            Stack<Board> stack = new Stack<Board>();
            do {
                stack.push(current.board);
                current = current.parent;
            } while(current != null);
            
            return stack.iterator();
        }
    }
    
    private MinPQ<BoardKey> pq, tpq;
    private int moves;
    private List<Board> solution;
    private BoardKey pqs = null;
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial)            
    {
        pq = new MinPQ<BoardKey>();
        tpq = new MinPQ<BoardKey>();
        
        //Initialize queues
        pq.insert(new BoardKey(initial));
        tpq.insert(new BoardKey(initial.twin()));
        
        //Enter the A* algorithm
        BoardKey tpqs = null;
        do {
            pqs = pq.delMin();
            tpqs = tpq.delMin();
                        
            for (Board b: pqs.board.neighbors())
            {
                //Avoid reverting the last movement
                if (pqs.parent == null || !b.equals(pqs.parent.board))
                {
                    pq.insert(new BoardKey(b, pqs));
                }
            }
            
            for (Board b: tpqs.board.neighbors())
            {
                //Avoid reverting the last movement
                if (tpqs.parent == null || !b.equals(tpqs.parent.board))
                {
                    tpq.insert(new BoardKey(b, tpqs));
                }
            }
            
        } 
        while (!pqs.board.isGoal() && !tpqs.board.isGoal());
        
    }
    
    // is the initial board solvable?
    public boolean isSolvable()             
    {
        return pqs.board.isGoal();
    }
    
    // min number of moves to solve initial board; -1 if no solution
    public int moves()                      
    {
        if (isSolvable())
        {
            return pqs.moves;
        }
        
        return -1;
    }
    
    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution()       
    {
        if (isSolvable())
        {
            return pqs;
        }
        
        return null;
    }
    
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}