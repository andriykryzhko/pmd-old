/*
 * User: tom
 * Date: Oct 21, 2002
 * Time: 3:56:21 PM
 */
package test.net.sourceforge.pmd.symboltable;

import junit.framework.TestCase;
import net.sourceforge.pmd.symboltable.*;
import net.sourceforge.pmd.ast.SimpleNode;

import java.util.Iterator;

public class AbstractScopeTest extends TestCase {

    // A helper class to stub out AbstractScope's abstract stuff
    private class MyScope extends AbstractScope {
        protected NameDeclaration findHere(NameOccurrence occ) {
            for (Iterator i = names.keySet().iterator(); i.hasNext();) {
                NameDeclaration decl = (NameDeclaration)i.next();
                if (decl.getImage().equals(occ.getImage())) {
                    return decl;
                }
            }
            return null;
        }
    }

    // Another helper class to test the search for a class scope behavior
    private class IsEnclosingClassScope extends AbstractScope {
        protected NameDeclaration findHere(NameOccurrence occ) {return null;}
        public Scope getEnclosingClassScope() {
            return this;
        }
    }

    public void testAccessors() {
        Scope scope = new MyScope();
        MyScope parent = new MyScope();
        scope.setParent(parent);
        assertEquals(parent, scope.getParent());

        assertTrue(!scope.getUnusedDeclarations().hasNext());
        assertTrue(scope.getUsedDeclarations().isEmpty());
    }

    public void testEnclClassScopeGetsDelegatedRight() {
        Scope scope = new MyScope();
        Scope isEncl = new IsEnclosingClassScope();
        scope.setParent(isEncl);
        assertEquals(isEncl, scope.getEnclosingClassScope());
    }

    public void testAdd() {
        Scope scope = new MyScope();
        SimpleNode node = new SimpleNode(1);
        node.setImage("foo");
        NameDeclaration decl = new NameDeclaration(node);
        scope.addDeclaration(decl);
        assertTrue(scope.contains(new NameOccurrence(new SimpleNode(1), "foo")));
    }
}
