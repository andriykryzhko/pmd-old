/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
*/
package test.net.sourceforge.pmd.symboltable;

import junit.framework.TestCase;
import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.ast.ASTCompilationUnit;
import net.sourceforge.pmd.ast.ASTInitializer;
import net.sourceforge.pmd.ast.JavaParser;
import net.sourceforge.pmd.symboltable.SymbolFacade;

import java.io.StringReader;

public class AcceptanceTest extends TestCase {

    public void testClashingSymbols() {
        JavaParser parser = new JavaParser(new StringReader(TEST1));
        ASTCompilationUnit c = parser.CompilationUnit();
        SymbolFacade stb = new SymbolFacade();
        stb.initializeWith(c);
    }

    public void testInitializer() {
        JavaParser parser = new JavaParser(new StringReader(TEST2));
        ASTCompilationUnit c = parser.CompilationUnit();
        ASTInitializer a = (ASTInitializer)(c.findChildrenOfType(ASTInitializer.class)).get(0);
        assertFalse(a.isStatic());
    }

    public void testStaticInitializer() {
        JavaParser parser = new JavaParser(new StringReader(TEST3));
        ASTCompilationUnit c = parser.CompilationUnit();
        ASTInitializer a = (ASTInitializer)(c.findChildrenOfType(ASTInitializer.class)).get(0);
        assertTrue(a.isStatic());
    }

    private static final String TEST1 =
    "import java.io.*;" + PMD.EOL +
    "public class Foo  {" + PMD.EOL +
    " void buz( ) {" + PMD.EOL +
    "  Object o = new Serializable() { int x; };" + PMD.EOL +
    "  Object o1 = new Serializable() { int x; };" + PMD.EOL +
    " }" + PMD.EOL  +
    "}" + PMD.EOL;

    private static final String TEST2 =
    "public class Foo  {" + PMD.EOL +
    " {} " + PMD.EOL +
    "}" + PMD.EOL;

    private static final String TEST3 =
    "public class Foo  {" + PMD.EOL +
    " static {} " + PMD.EOL +
    "}" + PMD.EOL;

}
