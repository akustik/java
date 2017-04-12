import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SAPTest {
    enum Datasets {
        INSTANCE;

        private final SAP s1, s2, s3, s4, s5;

        public static Digraph read(String path) {
            In in = new In(path);
            Digraph d = new Digraph(in);
            in.close();
            return d;
        }

        private Datasets() {
            s1 = new SAP(read(this.getClass()
                    .getResource("/wordnet/digraph1.txt").getPath()));
            s2 = new SAP(read(this.getClass()
                    .getResource("/digraph-disconnected-component.txt").getPath()));
            s3 = new SAP(read(this.getClass()
                    .getResource("/wordnet/digraph3.txt").getPath()));
            s4 = new SAP(read(this.getClass()
                    .getResource("/wordnet/digraph4.txt").getPath()));
            s5 = new SAP(read(this.getClass()
                    .getResource("/wordnet/digraph5.txt").getPath()));
        }

        public SAP getSAP1() {
            return s1;
        }
        
        public SAP getSAP2() {
            return s2;
        }
        
        public SAP getSAP3() {
            return s3;
        }
        
        public SAP getSAP4() {
            return s4;
        }
        
        public SAP getSAP5() {
            return s5;
        }
    }

    @Test
    public void testSAPConstructor() {
        Datasets.INSTANCE.getSAP1();
    }

    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testSAPLengthForANonExistentFirstVertex(){
        Datasets.INSTANCE.getSAP1().length(13, 0);
    }
    
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void testSAPLengthForANonExistentSecondVertex(){
        Datasets.INSTANCE.getSAP1().length(0, 13);
    }
    
    @Test
    public void testSAPLengthForSameVertex() {
        assertEquals(0, Datasets.INSTANCE.getSAP1().length(0, 0));
    }
    
    @Test
    public void testSAPAncestorForSameVertex() {
        assertEquals(0, Datasets.INSTANCE.getSAP1().ancestor(0, 0));
    }
    
    @Test
    public void testSAPLengthForVertexsWithCommonAncestor() {
        assertEquals(4, Datasets.INSTANCE.getSAP1().length(3, 11));
    }
    
    @Test
    public void testSAPAncestorForVertexsWithCommonAncestor() {
        assertEquals(1, Datasets.INSTANCE.getSAP1().ancestor(3, 11));
    }
    
    @Test
    public void testSAPLengthForVertexsOneAncestorOfTheOther() {
        assertEquals(1, Datasets.INSTANCE.getSAP1().length(3, 1));
    }
    
    @Test
    public void testSAPAncestorForVertexsOneAncestorOfTheOther() {
        assertEquals(1, Datasets.INSTANCE.getSAP1().ancestor(3, 1));
    }
    
    @Test
    public void testSAPLengthForVertexsDisconnectedVertexs() {
        assertEquals(-1, Datasets.INSTANCE.getSAP2().length(4, 3));
    }
    
    @Test
    public void testSAPAncestorForVertexsDisconnectedVertexs() {
        assertEquals(-1, Datasets.INSTANCE.getSAP2().length(4, 3));
    }
    
    @Test
    public void testSAPLengthAndAncestorForVertexGroupsWithCommonAncestor() {
        List<Integer> sv = new ArrayList<Integer>();
        sv.add(12);
        sv.add(10);
        sv.add(5);
        List<Integer> sw = new ArrayList<Integer>();
        sw.add(8);
        assertEquals(1, Datasets.INSTANCE.getSAP1().ancestor(sv, sw));
        assertEquals(3, Datasets.INSTANCE.getSAP1().length(sv, sw));
    }
    
    @Test
    public void testSAPLengthAndAncestorForVertexGroupsOneAncestorOfTheOther() {
        List<Integer> sv = new ArrayList<Integer>();
        sv.add(12);
        sv.add(10);
        sv.add(5);
        List<Integer> sw = new ArrayList<Integer>();
        sw.add(9);
        assertEquals(5, Datasets.INSTANCE.getSAP1().ancestor(sv, sw));
        assertEquals(1, Datasets.INSTANCE.getSAP1().length(sv, sw));
    }
    
    @Test
    public void testSAPLengthAndAncestorForVertexGroupsSameVertex() {
        List<Integer> sv = new ArrayList<Integer>();
        sv.add(12);
        sv.add(10);
        sv.add(9);
        List<Integer> sw = new ArrayList<Integer>();
        sw.add(4);
        sw.add(9);
        assertEquals(9, Datasets.INSTANCE.getSAP1().ancestor(sv, sw));
        assertEquals(0, Datasets.INSTANCE.getSAP1().length(sv, sw));
    }
    
    @Test
    public void testSAPLengthOnDigraph3WhenBetterOptionArrivesLaterWithDifferntSources() {
        assertEquals(3, Datasets.INSTANCE.getSAP3().length(10, 7));
    }
    
    @Test
    public void testSAPLengthOnDigraph4WhenBetterOptionArrivesLaterWithSameSource() {
        assertEquals(3, Datasets.INSTANCE.getSAP4().length(1, 4));
    } 

    @Test
    public void testSAPLengthOnDigraph5() {
        assertEquals(5, Datasets.INSTANCE.getSAP5().length(17, 21));
    } 
    
    @Test
    public void testSAPLengthOnDigraph3WhenThereIsASecondBetterOptionWithSamePath() {
        assertEquals(5, Datasets.INSTANCE.getSAP3().length(8, 13));
    } 
}
