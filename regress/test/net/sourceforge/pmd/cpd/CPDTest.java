/*
 * User: tom
 * Date: Jul 31, 2002
 * Time: 10:41:25 AM
 */
package test.net.sourceforge.pmd.cpd;

import junit.framework.TestCase;
import net.sourceforge.pmd.cpd.*;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class CPDTest extends TestCase{
    public CPDTest(String name) {
        super(name);
    }

    public void testBasic() throws Throwable {
        CPD cpd = new CPD();
        cpd.add("1", "helloworld");
        cpd.add("2", "hellothere");
        cpd.go(5);
        Occurrences occs = cpd.getResults();
        Iterator i = occs.getOccurrences(new Tile(getHelloTokens()));
        assertTrue(i.hasNext());
        Occurrence occ = (Occurrence)i.next();
        if (occ.getTokenSetID().equals("1")) {
            assertEquals(0, occ.getIndex());
        } else {
            assertEquals("2", occ.getTokenSetID());
            assertEquals(0, occ.getIndex());
        }
    }

    private List getHelloTokens() {
        List tokens = new ArrayList();
        Token tok = new Token('h', 0, "1");
        tokens.add(tok);
        Token tok1 = new Token('e', 1, "1");
        tokens.add(tok1);
        Token tok3 = new Token('l', 2, "1");
        tokens.add(tok3);
        Token tok4 = new Token('l', 3, "1");
        tokens.add(tok4);
        Token tok5 = new Token('o', 4, "1");
        tokens.add(tok5);
        return tokens;
    }

}
