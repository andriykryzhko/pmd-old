package test.net.sourceforge.pmd.jaxen;

import junit.framework.TestCase;
import net.sourceforge.pmd.jaxen.RegexpFunction;
import net.sourceforge.pmd.jaxen.Attribute;
import net.sourceforge.pmd.ast.Node;
import net.sourceforge.pmd.ast.JavaParserVisitor;

import java.util.ArrayList;
import java.util.List;

import org.jaxen.Context;
import org.jaxen.FunctionCallException;

public class RegexFunctionTest extends TestCase implements Node {

    public void jjtOpen() {
    }
    public void jjtClose() {
    }
    public void jjtSetParent(Node n) {
    }
    public Node jjtGetParent() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
    public void jjtAddChild(Node n, int i) {
    }
    public Node jjtGetChild(int i) {
        return null;
    }
    public int jjtGetNumChildren() {
        return 0;
    }
    public Object jjtAccept(JavaParserVisitor visitor, Object data) {
        return null;
    }

    private String className;

    public String getValue() {
        return className;
    }

    public void testMatch() throws FunctionCallException {
        className = "Foo";
        assertTrue(tryRegexp("/Foo/") instanceof List);
    }

    public void testNoMatch() throws FunctionCallException {
        className = "bar";
        assertTrue(tryRegexp("/Foo/") instanceof Boolean);
        className = "FobboBar";
        assertTrue(tryRegexp("/Foo/") instanceof Boolean);
    }

    private Object tryRegexp(String exp) throws FunctionCallException {
        RegexpFunction function = new RegexpFunction();
        List list = new ArrayList();
        List attrs = new ArrayList();
        attrs.add(new Attribute(this, "regexp", getClass().getMethods()[0]));
        list.add(attrs);
        list.add(exp);
        Context c = new Context(null);
        c.setNodeSet(new ArrayList());
        return function.call(c, list);
    }
}
