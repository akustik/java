import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.*;

public class WordNetTest {
    public enum Datasets {
        INSTANCE;

        private final WordNet _wn;
        private final WordNet _smoke;

        private Datasets() {
            _wn = new WordNet("synsets.txt", "hypernyms.txt");
            _smoke = new WordNet("synsets-smoke.txt", "hypernyms-smoke.txt");
        }

        public WordNet getReal() {
            return _wn;
        }

        public WordNet getSmoke() {
            return _smoke;
        }
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testWordNetConstructorWithCycle() {
        new WordNet("synsets.txt", "hypernyms-with-cycle.txt");
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testWordNetConstructorWithTwoRoots() {
        new WordNet("synsets.txt", "hypernyms-with-two-roots.txt");
    }

    @Test
    public void testWordNetNounsHasNoDuplicates() {
        Set<String> nouns = new HashSet<String>();
        for (String noun : Datasets.INSTANCE.getSmoke().nouns()) {
            if (nouns.contains(noun)) {
                fail(noun + " is duplicated");
            } else {
                nouns.add(noun);
            }
        }
    }

    @Test
    public void testWordNetNounsHasAllNouns() {
        Set<String> nouns = new HashSet<String>();
        for (String noun : Datasets.INSTANCE.getSmoke().nouns()) {
            nouns.add(noun);
        }
        assertEquals(5, nouns.size());
        assertTrue(nouns.contains("a") && nouns.contains("b")
                && nouns.contains("c") && nouns.contains("d")
                && nouns.contains("e"));
    }

    @Test
    public void testWordNetIsNounContainsNoun() {
        assertTrue(Datasets.INSTANCE.getSmoke().isNoun("a"));
    }

    @Test
    public void testWordNetIsNounNotContainsNoun() {
        assertFalse(Datasets.INSTANCE.getSmoke().isNoun("z"));
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testWordNetDistanceDoesNotContainFirstNoun() {
        Datasets.INSTANCE.getSmoke().distance("z", "a");
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testWordNetDistanceDoesNotContainSecondNoun() {
        Datasets.INSTANCE.getSmoke().distance("a", "z");
    }

    @Test
    public void testWordNetDistanceForTwoNouns() {
        assertEquals(1, Datasets.INSTANCE.getSmoke().distance("a", "c"));
    }

    @Test
    public void testWordNetAncestorForTwoNouns() {
        assertEquals("a", Datasets.INSTANCE.getSmoke().sap("a", "c"));
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testWordNetSapDoesNotContainFirstNoun() {
        Datasets.INSTANCE.getSmoke().sap("z", "a");
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testWordNetSapDoesNotContainSecondNoun() {
        Datasets.INSTANCE.getSmoke().sap("a", "z");
    }

    @Test
    public void testWordNetSapQueries() {
        assertEquals("abstraction abstract_entity", Datasets.INSTANCE.getReal()
                .sap("paper_trail", "infantile_amaurotic_idiocy"));
    }
    
    @Test
    public void testWordNetDistanceQueries() {
        assertEquals(14, Datasets.INSTANCE.getReal()
                .distance("paper_trail", "infantile_amaurotic_idiocy"));
    }    
}
