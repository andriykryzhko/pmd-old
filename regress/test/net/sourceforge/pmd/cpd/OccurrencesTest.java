/*
 * User: tom
 * Date: Jul 31, 2002
 * Time: 12:25:01 PM
 */
package test.net.sourceforge.pmd.cpd;

import junit.framework.TestCase;
import net.sourceforge.pmd.cpd.*;

import java.util.Iterator;

public class OccurrencesTest  extends TestCase {
    public OccurrencesTest(String name) {
        super(name);
    }

    public void testBasic() {
        Occurrences occs = new Occurrences(new TokenSets());
        assertTrue(!occs.contains(new Token('h', 0, "foo")));
        assertTrue(!occs.getTiles().hasNext());
        assertTrue(occs.isEmpty());
        assertEquals(0, occs.size());

        occs = new Occurrences((new TokenSets(GSTTest.createHelloTokenSet("foo"))));
        assertEquals(4, occs.size());
        assertTrue(occs.contains(new Token('h', 0, "foo")));
        Iterator i = occs.getOccurrences(new Tile(new Token('h', 0, "foo")));
        assertTrue(i.hasNext());
        assertTrue(occs.getTiles().hasNext());
        int count = 0;
        for (Iterator foo = occs.getTiles(); foo.hasNext();) {
            foo.next();
            count++;
        }
        assertEquals(4, count);
    }

    public void testInitialFrequencyCount() {
        Occurrences occs = new Occurrences((new TokenSets(GSTTest.createHelloTokenSet("foo"))));

        Iterator i = occs.getOccurrences(new Tile(new Token('h', 0, "foo")));
        Token tok = (Token)i.next();
        assertEquals("foo", tok.getTokenSrcID());
        assertEquals(0,tok.getIndex());
    }

    public void testContains() {
        Occurrences occs = new Occurrences((new TokenSets(GSTTest.createHelloTokenSet("foo"))));
        assertTrue(occs.contains(new Token('h', 0, "foo")));
    }

    public void testDeleteSolo() {
        Occurrences occs = new Occurrences((new TokenSets(GSTTest.createHelloTokenSet("foo"))));
        occs.deleteSoloTiles();
        assertEquals(1, occs.size());
        assertTrue(!occs.contains(new Token('h', 0, "foo")));
        assertTrue(occs.contains(new Token('l', 2, "foo")));
    }
}
