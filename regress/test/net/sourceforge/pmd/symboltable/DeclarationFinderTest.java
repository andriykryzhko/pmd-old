/*
 * User: tom
 * Date: Oct 10, 2002
 * Time: 8:20:05 AM
 */
package test.net.sourceforge.pmd.symboltable;

import junit.framework.TestCase;
import net.sourceforge.pmd.symboltable.DeclarationFinder;
import net.sourceforge.pmd.symboltable.LocalScope;
import net.sourceforge.pmd.symboltable.NameOccurrence;
import net.sourceforge.pmd.ast.ASTVariableDeclaratorId;
import net.sourceforge.pmd.ast.ASTVariableDeclarator;
import net.sourceforge.pmd.ast.ASTLocalVariableDeclaration;

public class DeclarationFinderTest extends TestCase {

/*
    public void testDeclarationsAreFound() {
        DeclarationFinder df = new DeclarationFinder();

        ASTVariableDeclaratorId node = new ASTVariableDeclaratorId(1);
        node.setImage("foo");

        ASTVariableDeclarator parent = new ASTVariableDeclarator(2);
        node.jjtSetParent(parent);

        ASTLocalVariableDeclaration gparent = new ASTLocalVariableDeclaration(3);
        parent.jjtSetParent(gparent);

        LocalScope scope = new LocalScope();
        node.setScope(scope);
        df.visit(node, null);

        assertTrue(scope.contains(new NameOccurrence(NameDeclarationTest.createNode("foo", 10))));
    }
*/

    public void test1() {}
}
