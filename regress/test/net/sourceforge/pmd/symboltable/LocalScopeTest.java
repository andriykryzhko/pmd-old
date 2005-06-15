/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
*/
package test.net.sourceforge.pmd.symboltable;

import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.ast.ASTName;
import net.sourceforge.pmd.ast.ASTPrimaryPrefix;
import net.sourceforge.pmd.ast.ASTVariableDeclaratorId;
import net.sourceforge.pmd.symboltable.LocalScope;
import net.sourceforge.pmd.symboltable.NameDeclaration;
import net.sourceforge.pmd.symboltable.NameOccurrence;
import net.sourceforge.pmd.symboltable.VariableNameDeclaration;

import java.util.List;
import java.util.Map;

public class LocalScopeTest extends STBBaseTst {

    public void testNameWithThisOrSuperIsNotFlaggedAsUnused() {
        LocalScope scope = new LocalScope();
        ASTName name = new ASTName(1);
        name.setImage("foo");
        ASTPrimaryPrefix prefix = new ASTPrimaryPrefix(2);
        prefix.setUsesThisModifier();
        name.jjtAddChild(prefix, 1);
        NameOccurrence occ = new NameOccurrence(name, "foo");
        scope.addVariableNameOccurrence(occ);
        assertTrue(!scope.getVariableDeclarations().keySet().iterator().hasNext());
    }

    public void testNameWithSuperIsNotFlaggedAsUnused() {
        LocalScope scope = new LocalScope();
        ASTName name = new ASTName(1);
        name.setImage("foo");
        ASTPrimaryPrefix prefix = new ASTPrimaryPrefix(2);
        prefix.setUsesSuperModifier();
        name.jjtAddChild(prefix, 1);
        NameOccurrence occ = new NameOccurrence(name, "foo");
        scope.addVariableNameOccurrence(occ);
        assertTrue(!scope.getVariableDeclarations().keySet().iterator().hasNext());
    }

    public void testLocalVariableDeclarationFound() {
        parseCode(TEST1);
        List nodes = acu.findChildrenOfType(ASTVariableDeclaratorId.class);
        ASTVariableDeclaratorId node = (ASTVariableDeclaratorId)nodes.get(0);
        Map vars = node.getScope().getVariableDeclarations();
        assertEquals(1, vars.size());
        NameDeclaration decl = (NameDeclaration)vars.keySet().iterator().next();
        assertEquals("b", decl.getImage());
    }

    public void testQualifiedNameOccurrence() {
        parseCode(TEST2);
        List nodes = acu.findChildrenOfType(ASTVariableDeclaratorId.class);
        ASTVariableDeclaratorId node = (ASTVariableDeclaratorId)nodes.get(0);
        Map vars = node.getScope().getVariableDeclarations();
        NameDeclaration decl = (NameDeclaration)vars.keySet().iterator().next();
        NameOccurrence occ = (NameOccurrence)((List)vars.get(decl)).get(0);
        assertEquals("b", occ.getImage());
    }

    public void testPostfixUsageIsRecorded() {
        parseCode(TEST3);
        List nodes = acu.findChildrenOfType(ASTVariableDeclaratorId.class);
        ASTVariableDeclaratorId node = (ASTVariableDeclaratorId)nodes.get(0);
        Map vars = node.getScope().getVariableDeclarations();
        NameDeclaration decl = (NameDeclaration)vars.keySet().iterator().next();
        List usages = (List)vars.get(decl);
        NameOccurrence occ = (NameOccurrence)usages.get(0);
        assertEquals(4, occ.getLocation().getBeginLine());
    }

    public void testTypesAreRecorded() {
        parseCode(TEST1);
        List nodes = acu.findChildrenOfType(ASTVariableDeclaratorId.class);
        Map vars = ((ASTVariableDeclaratorId)nodes.get(0)).getScope().getVariableDeclarations();
        VariableNameDeclaration decl = (VariableNameDeclaration)vars.keySet().iterator().next();
        assertEquals("Bar", decl.getTypeImage());
    }

    public static final String TEST1 =
    "public class Foo {" + PMD.EOL +
    " void foo() {" + PMD.EOL +
    "  Bar b = new Bar();" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    public static final String TEST2 =
    "public class Foo {" + PMD.EOL +
    " void foo() {" + PMD.EOL +
    "  Bar b = new Bar();" + PMD.EOL +
    "  b.buz = 2;" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    public static final String TEST3 =
    "public class Foo {" + PMD.EOL +
    " void foo() {" + PMD.EOL +
    "  int x = 2;" + PMD.EOL +
    "  x++;" + PMD.EOL +
    " }" + PMD.EOL +
    "}";
}
