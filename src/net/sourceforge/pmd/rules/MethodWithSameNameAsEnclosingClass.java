package net.sourceforge.pmd.rules;

import net.sourceforge.pmd.AbstractRule;
import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.ast.ASTMethodDeclarator;

import java.util.List;
import java.util.Iterator;

public class MethodWithSameNameAsEnclosingClass extends AbstractRule {

    public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
        List methods = node.findChildrenOfType(ASTMethodDeclarator.class);
        for (Iterator i = methods.iterator(); i.hasNext();) {
            ASTMethodDeclarator m = (ASTMethodDeclarator)i.next();
            if (m.getImage().equals(node.getImage())) {
                RuleContext ctx = (RuleContext)data;
                ctx.getReport().addRuleViolation(createRuleViolation(ctx, m));
            }
        }
        return super.visit(node, data);
    }
}
