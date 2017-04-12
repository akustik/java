import java.util.ArrayList;
import java.util.List;

public class SAP {

    private final Digraph digraph;
    private final ShortestAncestralPathSearch sapAlg;

    /**
     * constructor takes a digraph (not necessarily a DAG)
     * 
     * @param G
     */
    public SAP(Digraph G) {
        digraph = new Digraph(G);
        sapAlg = new ShortestAncestralPathSearch(digraph);
    }

    /**
     * Immutable bean with a shortest ancestral search result
     */
    private class ShortestAncestralPathResult implements
            Comparable<ShortestAncestralPathResult> {

        private final int length;
        private final int ancestor;

        public ShortestAncestralPathResult(int length, int ancestor) {
            this.length = length;
            this.ancestor = ancestor;
        }

        public int length() {
            return length;
        }

        public int ancestor() {
            return ancestor;
        }

        public String toString() {
            return "length: " + length + ", ancestor: " + ancestor;
        }

        public int compareTo(ShortestAncestralPathResult that) {
            int res = 0;
            if (length != -1 && that.length() != -1) {
                res = length - that.length();
            } else if (length != -1) {
                res = -1;
            } else if (that.length() != -1) {
                res = 1;
            } else {
                res = 0;
            }

            return res;
        }
    }

    /**
     * Search status for a given BFS operation to obtain a shortest ancestral
     * path result.
     */

    private class SearchStatus {

        private ShortestAncestralPathResult result;
        private final boolean[] marked;
        private final int[] distTo;
        private final List<Integer> vertexs;
        private final String name;
        
        public SearchStatus(String label, Digraph G) {
            name = label;
            marked = new boolean[G.V()];
            distTo = new int[G.V()];
            vertexs = new ArrayList<Integer>();
        }

        public void init(Iterable<Integer> s) {
            result = new ShortestAncestralPathResult(-1, -1);
            vertexs.clear();

            for (int i = 0; i < distTo.length; i++) {
                distTo[i] = Integer.MAX_VALUE;
                marked[i] = false;
            }

            for (int si : s) {
                marked[si] = true;
                distTo[si] = 0;
                vertexs.add(si);
            }
        }

        public boolean isMarked(int i) {
            return marked[i];
        }

        public int distTo(int i) {
            return distTo[i];
        }

        public ShortestAncestralPathResult result() {
            return result;
        }
        
        private void log(String message) {
            //System.out.println(name + " => " + message);            
        }

        /**
         * Performs a single iteration looking up for common ancestors.
         * 
         * @param that
         * @return True if there are no more iterations, false otherwise
         */
        public boolean iterate(SearchStatus that) {
            
            List<Integer> nextIterationVertexs = new ArrayList<Integer>();
            
            log("Iterate: " + vertexs);
            for (int s : vertexs) {
                log("Take: " + s);
                for (int j : digraph.adj(s)) {
                    log("Inspecting adjacency: " + j);
                    if (!marked[j]) {
                        log("Marking adjacency: " + j);
                        // If its not marked by this path, mark it
                        // and update the distance.
                        marked[j] = true;
                        distTo[j] = distTo[s] + 1;

                        // Check if the other path is marked, so that
                        // a result can be updated.
                        if (that.isMarked(j)) {                            
                            // Compute the total distance between both paths
                            int d = distTo(j) + that.distTo(j);
                            log("Makes a path: " + j + " of length " + d);
                            ShortestAncestralPathResult improvedResult = 
                              new ShortestAncestralPathResult(d, j);
                            if (improvedResult.compareTo(result) < 0) {
                                log("Improved result: " + j + " of length " + d);
                                result = improvedResult;
                            }
                        } 
                        
                        // enqueue j to further inspection in the next iteration
                        // if this path is still eligible to succeed
                        int d1 = result.length();
                        int d2 = that.result().length();                        
                        if ((d1 == -1 || distTo[j] < d1)
                          && (d2 == -1 || distTo[j] < d2))
                        {    
                            log("Still eligible to better path, " 
                               + "adding for later inspection: " + j);
                            nextIterationVertexs.add(j);
                        }                      
                    }
                }
            }

            // Update the list of source vertexs for the next
            // iteration
            vertexs.clear();
            if (nextIterationVertexs.size() != 0) {
                vertexs.addAll(nextIterationVertexs);
            }
            
            log("Remain for next iteration " + vertexs + "==\n\n");
            return vertexs.size() == 0;
        }
    }

    /**
     * BFS-based algorithm to find the shortest ancestral related info.
     */

    private class ShortestAncestralPathSearch {

        private ShortestAncestralPathResult result;
        private SearchStatus sv, sw;

        public ShortestAncestralPathSearch(Digraph G) {
            sv = new SearchStatus("sv", G);
            sw = new SearchStatus("sw", G);
        }

        public ShortestAncestralPathResult search(Iterable<Integer> v,
                Iterable<Integer> w) {

            // Initialize the result
            result = new ShortestAncestralPathResult(-1, -1);

            // check degenerated case
            for (int i : v)
                for (int j : w)
                    if (i == j) {
                        result = new ShortestAncestralPathResult(0, i);
                    }

            if (result.length() != 0) {

                sv.init(v);
                sw.init(w);

                // Keep iteration until both paths are unable to
                // improve the result
                boolean ended = false;
                while (!ended) {
                    boolean svEnded = sv.iterate(sw);
                    boolean swEnded = sw.iterate(sv);
                    ended = svEnded && swEnded;
                }

                // Choose the better result
                if (sv.result().compareTo(sw.result()) < 0)
                    result = sv.result();
                else
                    result = sw.result();
            }

            return result;
        }

    }

    /**
     * length of shortest ancestral path between v and w; -1 if no such path
     * 
     * @param v
     * @param w
     * @return
     */
    public int length(int v, int w) {
        if (v >= digraph.V() || w >= digraph.V())
            throw new IndexOutOfBoundsException("Vertex does not exist");
        List<Integer> s1 = new ArrayList<Integer>();
        s1.add(v);
        List<Integer> s2 = new ArrayList<Integer>();
        s2.add(w);
        return sapAlg.search(s1, s2).length();
    }

    /**
     * a common ancestor of v and w that participates in a shortest ancestral
     * path; -1 if no such path
     * 
     * @param v
     * @param w
     * @return
     */
    public int ancestor(int v, int w) {
        if (v >= digraph.V() || w >= digraph.V())
            throw new IndexOutOfBoundsException("Vertex does not exist");
        List<Integer> s1 = new ArrayList<Integer>();
        s1.add(v);
        List<Integer> s2 = new ArrayList<Integer>();
        s2.add(w);
        return sapAlg.search(s1, s2).ancestor();
    }

    /**
     * length of shortest ancestral path between any vertex in v and any vertex
     * in w; -1 if no such path
     * 
     * @param v
     * @param w
     * @return
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        for (int s : v)
            if (s >= digraph.V())
                throw new IndexOutOfBoundsException("Vertex does not exist");

        for (int s : w)
            if (s >= digraph.V())
                throw new IndexOutOfBoundsException("Vertex does not exist");

        return sapAlg.search(v, w).length();
    }

    /**
     * a common ancestor that participates in shortest ancestral path; -1 if no
     * such path
     * 
     * @param v
     * @param w
     * @return
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        for (int s : v)
            if (s >= digraph.V())
                throw new IndexOutOfBoundsException("Vertex does not exist");

        for (int s : w)
            if (s >= digraph.V())
                throw new IndexOutOfBoundsException("Vertex does not exist");

        return sapAlg.search(v, w).ancestor();
    }

    /**
     * for unit testing of this class (such as the one below)
     * 
     * @param args
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
