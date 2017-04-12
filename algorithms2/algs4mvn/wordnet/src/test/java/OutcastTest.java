import static org.junit.Assert.*;

import org.junit.Test;

public class OutcastTest {

    @Test
    public void testSAPConstructor() {
        Outcast outcast = new Outcast(WordNetTest.Datasets.INSTANCE.getReal());
        String o = outcast.outcast(new String[]{"Turing",
                "von_Neumann"});
        assertEquals("Turing", o);
    }
}
