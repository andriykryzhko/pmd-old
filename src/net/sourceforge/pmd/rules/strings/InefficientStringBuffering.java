/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd.rules.strings;

import net.sourceforge.pmd.AbstractRule;
import net.sourceforge.pmd.ast.ASTAdditiveExpression;
import net.sourceforge.pmd.ast.ASTAllocationExpression;
import net.sourceforge.pmd.ast.ASTBlockStatement;
import net.sourceforge.pmd.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.ast.ASTLiteral;
import net.sourceforge.pmd.ast.ASTName;
import net.sourceforge.pmd.ast.Node;
import net.sourceforge.pmd.ast.SimpleNode;
import net.sourceforge.pmd.symboltable.VariableNameDeclaration;

import java.util.Iterator;
import java.util.List;

/*
 * How this rule works:
 * find additive expressions: +
 * check that the addition is between anything other than two literals
 * if true and also the parent is StringBuffer constructor or append,
 * report a violation.
 * 
 * @author mgriffa
 */
public class InefficientStringBuffering extends AbstractRule {

    public Object visit(ASTAdditiveExpression node, Object data) {
        ASTBlockStatement bs = (ASTBlockStatement) node.getFirstParentOfType(ASTBlockStatement.class);
        if (bs == null) {
            return data;
        }

        int immediateLiterals = 0;
        List nodes = node.findChildrenOfType(ASTLiteral.class);
        for (Iterator i = nodes.iterator();i.hasNext();) {
            ASTLiteral literal = (ASTLiteral)i.next();
            if (literal.jjtGetParent().jjtGetParent().jjtGetParent() instanceof ASTAdditiveExpression) {
                immediateLiterals++;
            }
            try {
                Integer.parseInt(literal.getImage());
                return data;
            } catch (NumberFormatException nfe) {
                // NFE means new StringBuffer("a" + "b"), want to flag those
            }
        }

        if (immediateLiterals == 2) {
            return data;
        }

        // if literal + public static final, return
        List nameNodes = node.findChildrenOfType(ASTName.class);
        for (Iterator i = nameNodes.iterator(); i.hasNext();) {
            ASTName name = (ASTName)i.next();
            if (name.getNameDeclaration() != null && name.getNameDeclaration() instanceof VariableNameDeclaration) {
                VariableNameDeclaration vnd = (VariableNameDeclaration)name.getNameDeclaration();
                if (vnd.getAccessNodeParent().isFinal() && vnd.getAccessNodeParent().isStatic()) {
                    return data;
                }
            }
        }


        if (bs.isAllocation()) {
            if (isAllocatedStringBuffer(node)) {
                addViolation(data, node);
            }
        } else if (isInStringBufferAppend(node, 8)) {
            addViolation(data, node);
        }
        return data;
    }

    protected static boolean isInStringBufferAppend(SimpleNode node, int length) {
        if (!xParentIsBlockStatement(node, length)) {
            return false;
        }
        ASTBlockStatement s = (ASTBlockStatement) node.getFirstParentOfType(ASTBlockStatement.class);
        if (s == null) {
            return false;
        }
        ASTName n = (ASTName) s.getFirstChildOfType(ASTName.class);

        if (n == null || n.getImage().indexOf("append") == -1 || !(n.getNameDeclaration() instanceof VariableNameDeclaration)) {
            return false;
        }
        return ((VariableNameDeclaration)n.getNameDeclaration()).getTypeImage().equals("StringBuffer");
    }

    // TODO move this method to SimpleNode
    private static boolean xParentIsBlockStatement(SimpleNode node, int length) {
        Node curr = node;
        for (int i=0; i<length; i++) {
            if (node.jjtGetParent() == null) {
                return false;
            }
            curr = curr.jjtGetParent();
        }
        return curr instanceof ASTBlockStatement;
    }

    private boolean isAllocatedStringBuffer(ASTAdditiveExpression node) {
        ASTAllocationExpression ao = (ASTAllocationExpression) node.getFirstParentOfType(ASTAllocationExpression.class);
        if (ao == null) {
            return false;
        }
        // note that the child can be an ArrayDimsAndInits, for example, from java.lang.FloatingDecimal:  t = new int[ nWords+wordcount+1 ];
        ASTClassOrInterfaceType an = (ASTClassOrInterfaceType) ao.getFirstChildOfType(ASTClassOrInterfaceType.class);
        return an != null && (an.getImage().endsWith("StringBuffer") || an.getImage().endsWith("StringBuilder"));
    }
}

