/**
 * Modelates a percolation enviornment for a NxN grid.
 */
public class Percolation {
    
    private static final int OPEN = 1;
    private static final int BLOCKED = 0;
    private static final int FULL = 2;
    
    private boolean percolates = false;
    private int topIdx;
    private int bottomIdx;
    private int size;
    private int[] sites;
    private WeightedQuickUnionUF uf;

    /**
     * Creates a N*N grid with all sites blocked.
     */
    public Percolation(int N)
    {
        size = N;
        sites = new int[size * size + 2];               
        uf = new WeightedQuickUnionUF(size * size + 2);

        topIdx = size * size;
        bottomIdx = size * size + 1;
        sites[topIdx] = OPEN;
        sites[bottomIdx] = OPEN;
    }
    
    /**
     * Opens a site if it is not already
     * @param i The row [1..N]
     * @param j The column [1..N]
     */
    public void open(int i, int j)
    {
        validateSite(i, j);
        int siteIdx = siteToIdx(i, j);
        if (sites[siteIdx] == BLOCKED)
        { 
           sites[siteIdx] = OPEN;

           attemptUnion(siteIdx, i, j, i+1, j);
           attemptUnion(siteIdx, i, j, i, j+1);
           attemptUnion(siteIdx, i, j, i-1, j);
           attemptUnion(siteIdx, i, j, i, j-1);
        }
    }
    
    /**
     * Attempts an union between a site and another site.
     * @param siteIdx The first site index.
     * @param row The second site row [1..N]
     * @param column The second site column [1..N]
     */ 
    private void attemptUnion(int siteIdx, int i, int j, int row, int column)
    {
        int otherSiteIdx = siteToIdx(row, column);
        if (otherSiteIdx != -1           
           && (sites[otherSiteIdx] != BLOCKED)
           )
        {
            uf.union(siteIdx, otherSiteIdx);
            if (sites[otherSiteIdx] == FULL || otherSiteIdx == topIdx)
            {
                paintFull(i, j);
            }
        }
    }
    
    private void paintFull(int i, int j)
    {
        int idx = siteToIdx(i, j);
        if (idx != -1 &&  idx != topIdx && idx != bottomIdx && sites[idx] == OPEN)
        {
            sites[idx] = FULL;
            paintFull(i+1, j);
            paintFull(i, j+1);
            paintFull(i, j-1);
            paintFull(i-1, j);
        }
    }
    
    /**
     * Converts a site from row/column format to index format.
     * @param siteIdx The first site index.
     * @param rown The row [1..N]
     * @param column The column [1..N]
     * @return An unique index for the site or -1 if does not exist.
     */ 
    private int siteToIdx(int row, int column)
    {
        int idx = -1;
        if (row < 1)
        {
            idx = topIdx;
        } else if (row > size)
        {
            idx = bottomIdx;
        } else if (row >= 1 && column >= 1 && row <= size && column <= size)
        {
            idx = (row-1) * size + (column-1);
        }
        return idx;
    }
    
    /**
     * Validates that a site coordinates are valid.
     */
    private void validateSite(int row, int column)
    {
        if (row <= 0 || row > size) 
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (column <= 0 || column > size) 
            throw new IndexOutOfBoundsException("column index j out of bounds");
    }
   
    /**
     * Checks whether a site is open
     * @param i The row [1..N]
     * @param j The column [1..N]
     */
    public boolean isOpen(int i, int j)
    {
        validateSite(i, j);
        return sites[siteToIdx(i, j)] == OPEN || sites[siteToIdx(i, j)] == FULL;
    }
    
   /**
    * Checks whether a site is full
     * @param i The row [1..N]
     * @param j The column [1..N]
    */
    public boolean isFull(int i, int j)
    {
        validateSite(i, j);
        return sites[siteToIdx(i, j)] == FULL;
    }
    
    /**
     * Checks whether the system percolates
     */
    public boolean percolates()
    {
        if (!percolates)
        {
            percolates =  uf.connected(topIdx, bottomIdx);
        }
        
        return percolates;
    }
}