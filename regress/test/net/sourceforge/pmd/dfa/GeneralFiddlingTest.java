package test.net.sourceforge.pmd.dfa;

import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.ast.ASTMethodDeclarator;
import net.sourceforge.pmd.ast.ASTCompilationUnit;
import net.sourceforge.pmd.dfa.IDataFlowNode;
import net.sourceforge.pmd.dfa.DataFlowNode;
import test.net.sourceforge.pmd.testframework.ParserTst;

import java.util.Iterator;
import java.util.List;

public class GeneralFiddlingTest extends ParserTst {

    public void test1() throws Throwable {
        ASTCompilationUnit acu = buildDFA(TEST1);
        ASTMethodDeclarator meth = (ASTMethodDeclarator)acu.findChildrenOfType(ASTMethodDeclarator.class).get(0);
        IDataFlowNode n = meth.getDataFlowNode();
        List f = n.getFlow();
        for (Iterator i = f.iterator(); i.hasNext();) {
            DataFlowNode dfan = (DataFlowNode)i.next();
            System.out.println(dfan);
        }
    }

    private static final String TEST1 =
        "class Foo {" + PMD.EOL +
        " void bar() {" + PMD.EOL +
        "  for (int i=0; i<10; i++) {" + PMD.EOL +
        "   int j;" + PMD.EOL +
        "  }" + PMD.EOL +
        " }"  + PMD.EOL +
        "}";
}
