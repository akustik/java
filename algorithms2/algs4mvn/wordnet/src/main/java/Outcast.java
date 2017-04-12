import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Outcast {

    private final WordNet wordnet;

    /**
     * constructor takes a WordNet object
     * 
     * @param wordnet
     */
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    private class NounAndDistance implements Comparable<NounAndDistance> {

        private final String noun;
        private int distance;

        public NounAndDistance(String noun) {
            this.noun = noun;
            this.distance = 0;
        }

        public void add(int d) {
            this.distance += d;
        }

        public int distance() {
            return distance;
        }

        public String noun() {
            return noun;
        }

        public int compareTo(NounAndDistance that) {
            return distance - that.distance();
        }

        public String toString() {
            return "noun: " + noun + " distance: " + distance;
        }
    }

    /**
     * given an array of WordNet nouns, return an outcast
     * 
     * @param nouns
     * @return
     */
    public String outcast(String[] nouns) {
        // Create a list of nouns and distances
        List<NounAndDistance> nounsAndDistances = new ArrayList<NounAndDistance>(
                nouns.length);
        for (String noun : nouns) {
            nounsAndDistances.add(0, new NounAndDistance(noun));
        }

        // Calculate the distance for each noun
        for (NounAndDistance n : nounsAndDistances) {
            for (String other : nouns) {
                n.add(wordnet.distance(n.noun(), other));
            }
        }

        // Sort the array and return the maximum
        Collections.sort(nounsAndDistances);

        return nounsAndDistances.get(nounsAndDistances.size() - 1).noun();
    }

    /**
     * for unit testing of this class (such as the one below)
     * 
     * @param args
     */
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            String[] nouns = In.readStrings(args[t]);
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
