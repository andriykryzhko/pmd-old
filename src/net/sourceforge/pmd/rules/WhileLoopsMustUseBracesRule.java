/*
 * User: tom
 * Date: Jul 19, 2002
 * Time: 11:13:17 PM
 */
package net.sourceforge.pmd.rules;

import net.sourceforge.pmd.AbstractRule;
import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.ast.ASTIfStatement;
import net.sourceforge.pmd.ast.SimpleNode;
import net.sourceforge.pmd.ast.ASTBlock;
import net.sourceforge.pmd.ast.ASTWhileStatement;

public class WhileLoopsMustUseBracesRule extends BracesRule {

    public Object visit(ASTWhileStatement node, Object data) {
        RuleContext ctx = (RuleContext)data;
        SimpleNode firstStmt = (SimpleNode)node.jjtGetChild(1);

        if (!hasBlockAsFirstChild(firstStmt)) {
            ctx.getReport().addRuleViolation(createRuleViolation(ctx, node.getBeginLine()));
        }
        return super.visit(node,data);
    }

}
