/*
 * User: tom
 * Date: Jun 18, 2002
 * Time: 11:02:09 AM
 */
package net.sourceforge.pmd.rules;

import java.util.Iterator;
import java.util.Stack;
import java.text.MessageFormat;

import net.sourceforge.pmd.ast.*;
import net.sourceforge.pmd.*;
import net.sourceforge.pmd.symboltable.NameDeclaration;
import net.sourceforge.pmd.symboltable.Namespace;
import net.sourceforge.pmd.symboltable.SymbolTable;
import net.sourceforge.pmd.symboltable.Scope;

public class UnusedLocalVariableRule extends AbstractRule {
    public Object visit(ASTVariableDeclaratorId node, Object data) {
        if (node.jjtGetParent().jjtGetParent() instanceof ASTLocalVariableDeclaration) {
            RuleContext ctx = (RuleContext)data;
            Scope scope = ((SimpleNode)node).getScope();
            for (Iterator i = scope.getUnusedDeclarations(); i.hasNext();) {
                NameDeclaration symbol = (NameDeclaration)i.next();
                ctx.getReport().addRuleViolation(createRuleViolation(ctx, symbol.getLine(), MessageFormat.format(getMessage(), new Object[] {symbol.getImage()})));
            }
        }
        return data;
    }
}
