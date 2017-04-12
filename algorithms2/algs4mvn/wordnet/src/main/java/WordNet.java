import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordNet {

    private final Map<String, List<Integer>> dictionary;
    private final Map<Integer, String> inverseDictionary;
    private final Digraph digraph;
    private final SAP sap;

    /**
     * constructor takes the name of the two input files
     * 
     * @param synsets
     * @param hypernyms
     */
    public WordNet(String synsets, String hypernyms) {

        dictionary = new HashMap<String, List<Integer>>();
        inverseDictionary = new HashMap<Integer, String>();
        
        In vertexIn = new In(synsets);
        String synset = null;
        int nVertexs = 0;
        while ((synset = vertexIn.readLine()) != null) {
            String[] fields = synset.split(",");
            int v = Integer.parseInt(fields[0]);
            String[] nouns = fields[1].split(" ");
            
            inverseDictionary.put(v, fields[1]);
            for (int i = 0; i < nouns.length; i++) {
                List<Integer> ids = dictionary.get(nouns[i]);
                if (ids == null) {
                    ids = new ArrayList<Integer>();
                    dictionary.put(nouns[i], ids);
                }
                ids.add(v);
            }

            nVertexs++;
        }
        vertexIn.close();

        digraph = new Digraph(nVertexs);

        String hypernym = null;
        In edgesIn = new In(hypernyms);
        while ((hypernym = edgesIn.readLine()) != null) {
            String[] fields = hypernym.split(",");
            int v = Integer.parseInt(fields[0]);
            for (int i = 1; i < fields.length; i++) {
                digraph.addEdge(v, Integer.parseInt(fields[i]));
            }

        }
        edgesIn.close();

        // Check that there are no more than one connected component
        // KosarajuSharirSCC scc = new KosarajuSharirSCC(_digraph);
        // if(scc.count() != _digraph.V()){
        // throw new java.lang.IllegalArgumentException("Graph is not DAG");
        // }

        // Check that there are no cycles
        DirectedCycle dc = new DirectedCycle(digraph);
        if (dc.cycle() != null) {
            throw new java.lang.IllegalArgumentException("Graph is not DAG");
        }

        // Count the number of root elements
        int nRoots = 0;
        for (int v = 0; v < digraph.V(); v++) {
            if (!digraph.adj(v).iterator().hasNext()) {
                nRoots++;
            }
        }

        if (nRoots != 1) {
            throw new java.lang.IllegalArgumentException(
                    "Graph has not a single Root");
        }

        // Create the sap
        sap = new SAP(digraph);
    }

    /**
     * the set of nouns (no dusplicates), returned as an Iterable
     * 
     * @return
     */
    public Iterable<String> nouns() {
        return dictionary.keySet();
    }

    /**
     * is the word a WordNet noun?
     * 
     * @param word
     * @return
     */
    public boolean isNoun(String word) {
        return dictionary.containsKey(word);
    }

    /**
     * distance between nounA and nounB (defined below)
     * 
     * @param nounA
     * @param nounB
     * @return
     */
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new java.lang.IllegalArgumentException("Noun not existent");
        List<Integer> v = dictionary.get(nounA);
        List<Integer> w = dictionary.get(nounB);
        return sap.length(v, w);        
    }

    /**
     * a synset (second field of synsets.txt) that is the common ancestor of
     * nounA and nounB in a shortest ancestral path (defined below)
     * 
     * @param nounA
     * @param nounB
     * @return
     */
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new java.lang.IllegalArgumentException("Noun not existent");
        List<Integer> v = dictionary.get(nounA);
        List<Integer> w = dictionary.get(nounB);
        return inverseDictionary.get(sap.ancestor(v, w));        
    }

    /**
     * for unit testing of this class
     * 
     * @param args
     */
    public static void main(String[] args) {
        WordNet wn = new WordNet(args[0], args[1]);
        while (!StdIn.isEmpty()) {
            String v = StdIn.readString();
            String w = StdIn.readString();
            int length = wn.distance(v, w);
            String ancestor = wn.sap(v, w);
            StdOut.printf("length = %d, ancestor = %s\n", length, ancestor);
        }
    }
}
