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
        cpd.add("1", "public class Foo {}");
        cpd.add("2", "public class Bar {}");
        cpd.go(2);
        Results results = cpd.getResults();
        Iterator i = results.getOccurrences(new Tile(getHelloTokens()));
        assertTrue(i.hasNext());
        TokenEntry tok = (TokenEntry)i.next();
        if (tok.getTokenSrcID().equals("1")) {
            assertEquals(0, tok.getIndex());
        } else {
            assertEquals("2", tok.getTokenSrcID());
            assertEquals(0, tok.getIndex());
        }
    }

    public void testBasic2() throws Throwable {
        CPD cpd = new CPD();
        cpd.add("1", "public class Foo {}");
        cpd.add("2", "public class Bar {}");
        cpd.go(2);
        Results results = cpd.getResults();
        Iterator i = results.getOccurrences(new Tile(getHelloTokens()));
        assertTrue(i.hasNext());
        TokenEntry tok = (TokenEntry)i.next();
        if (tok.getTokenSrcID().equals("1")) {
            assertEquals(0, tok.getIndex());
        } else {
            assertEquals("2", tok.getTokenSrcID());
            assertEquals(0, tok.getIndex());
        }
    }

    public static List getHelloTokens() {
        List tokens = new ArrayList();
        tokens.add(new TokenEntry("public", 0, "1", 5));
        tokens.add(new TokenEntry("class", 1, "1", 5));
        return tokens;
    }
}
