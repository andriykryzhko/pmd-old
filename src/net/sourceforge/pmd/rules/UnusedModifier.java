package net.sourceforge.pmd.rules;

import net.sourceforge.pmd.AbstractRule;
import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.ast.Node;
import net.sourceforge.pmd.ast.ASTFieldDeclaration;
import net.sourceforge.pmd.ast.SimpleNode;

public class UnusedModifier extends AbstractRule {

    public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
        if (!node.isInterface() && node.isNested() && (node.isPublic() || node.isStatic())) {
            ASTClassOrInterfaceDeclaration parent = (ASTClassOrInterfaceDeclaration)node.getFirstParentOfType(ASTClassOrInterfaceDeclaration.class);
            if (parent.isInterface()) {
                flag(data, node);
            }
        } else if (node.isInterface() && node.isNested() && (node.isPublic() || node.isStatic())) {
            ASTClassOrInterfaceDeclaration parent = (ASTClassOrInterfaceDeclaration)node.getFirstParentOfType(ASTClassOrInterfaceDeclaration.class);
            if (parent.isInterface()) {
                flag(data, node);
            } else {
                if (node.isStatic()) {
                    flag(data, node);
                }
            }
        }
        return super.visit(node, data);
    }

    public Object visit(ASTMethodDeclaration node, Object data) {
        if (node.isPublic() || node.isAbstract()) {
            check(node, data);
        }
        return super.visit(node, data);
    }

    public Object visit(ASTFieldDeclaration node, Object data) {
        if (node.isPublic() || node.isStatic() || node.isFinal()) {
            check(node, data);
        }
        return super.visit(node, data);
    }

    private void check(SimpleNode fieldOrMethod, Object data) {
        // third ancestor could be an AllocationExpression
        // if this is a method in an anonymous inner class
        Node parent = fieldOrMethod.jjtGetParent().jjtGetParent().jjtGetParent();
        if (parent instanceof ASTClassOrInterfaceDeclaration && ((ASTClassOrInterfaceDeclaration)parent).isInterface()) {
            ((RuleContext) data).getReport().addRuleViolation(createRuleViolation((RuleContext) data, fieldOrMethod, getMessage()));
        }
    }

    private void flag(Object data, ASTClassOrInterfaceDeclaration node) {
        ((RuleContext) data).getReport().addRuleViolation(createRuleViolation((RuleContext) data, node, getMessage()));
    }
}
