/*
 * User: tom
 * Date: Sep 13, 2002
 * Time: 11:56:24 AM
 */
package net.sourceforge.pmd.rules;

import net.sourceforge.pmd.AbstractRule;
import net.sourceforge.pmd.ast.ASTBlockStatement;
import net.sourceforge.pmd.ast.ASTForStatement;
import net.sourceforge.pmd.ast.Node;

public class StringConcatenationRule extends AbstractRule {

    public Object visit(ASTForStatement node, Object data) {
        Node forLoopStmt = null;
        for (int i=0;i<4;i++) {
            forLoopStmt = node.jjtGetChild(i);
            if (forLoopStmt instanceof ASTBlockStatement) {
                break;
            }
        }
        if (forLoopStmt == null) {
            return data;
        }



        return data;
    }
}
