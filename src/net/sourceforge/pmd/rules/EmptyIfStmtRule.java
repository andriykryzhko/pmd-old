/*
 * User: tom
 * Date: Jun 17, 2002
 * Time: 4:23:30 PM
 */
package net.sourceforge.pmd.rules;

import net.sourceforge.pmd.ast.JavaParserVisitorAdapter;
import net.sourceforge.pmd.ast.ASTBlock;
import net.sourceforge.pmd.ast.ASTTryStatement;
import net.sourceforge.pmd.ast.ASTIfStatement;
import net.sourceforge.pmd.*;

public class EmptyIfStmtRule extends AbstractRule implements Rule {
    public String getDescription() {return "Avoid empty IF statements";}

   public Object visit(ASTBlock node, Object data){
       if ((node.jjtGetParent().jjtGetParent() instanceof ASTIfStatement) && node.jjtGetNumChildren()==0) {
           (((RuleContext)data).getReport()).addRuleViolation(new RuleViolation(this, node.getBeginLine()));
       }

        return super.visit(node, data);
    }
}
