package test.net.sourceforge.pmd.jaxen;

import junit.framework.TestCase;
import net.sourceforge.pmd.ast.SimpleNode;
import net.sourceforge.pmd.jaxen.AttributeAxisIterator;

public class AttributeAxisIteratorTest extends TestCase {

    public void testRemove() {
        SimpleNode n = new SimpleNode(0);
        n.testingOnly__setBeginColumn(1);
        n.testingOnly__setBeginLine(1);
        AttributeAxisIterator iter = new AttributeAxisIterator(n);
        try {
            iter.remove();
            fail("Should have thrown an exception!");
        } catch (UnsupportedOperationException e) {
            // cool
        }
    }
}
