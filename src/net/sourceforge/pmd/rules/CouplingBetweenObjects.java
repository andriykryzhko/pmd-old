/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd.rules;

import net.sourceforge.pmd.AbstractRule;
import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.ast.ASTCompilationUnit;
import net.sourceforge.pmd.ast.ASTFieldDeclaration;
import net.sourceforge.pmd.ast.ASTFormalParameter;
import net.sourceforge.pmd.ast.ASTLocalVariableDeclaration;
import net.sourceforge.pmd.ast.ASTReferenceType;
import net.sourceforge.pmd.ast.ASTResultType;
import net.sourceforge.pmd.ast.ASTType;
import net.sourceforge.pmd.ast.SimpleNode;

import java.util.HashSet;
import java.util.Set;


/**
 * CouplingBetweenObjects attempts to capture all unique Class attributes,
 * local variables, and return types to determine how many objects a class is
 * coupled to. This is only a guage and isn't a hard and fast rule. The threshold
 * value is configurable and should be determined accordingly
 *
 * @author aglover
 * @since Feb 20, 2003
 */
public class CouplingBetweenObjects extends AbstractRule {

    private int couplingCount;
    private Set typesFoundSoFar;

    public Object visit(ASTCompilationUnit cu, Object data) {
        this.typesFoundSoFar = new HashSet();
        this.couplingCount = 0;

        Object returnObj = cu.childrenAccept(this, data);

        if (this.couplingCount > getIntProperty("threshold")) {
            RuleContext ctx = (RuleContext) data;
            ctx.getReport().addRuleViolation(createRuleViolation(ctx, cu, "A value of " + this.couplingCount + " may denote a high amount of coupling within the class"));
        }

        return returnObj;
    }

    public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
        if (node.isInterface()) {
            return data;
        }
        return super.visit(node, data);
    }

    public Object visit(ASTResultType node, Object data) {
        for (int x = 0; x < node.jjtGetNumChildren(); x++) {
            SimpleNode tNode = (SimpleNode) node.jjtGetChild(x);
            if (tNode instanceof ASTType) {
                SimpleNode reftypeNode = (SimpleNode) tNode.jjtGetChild(0);
                if (reftypeNode instanceof ASTReferenceType) {
                    SimpleNode classOrIntType = (SimpleNode)reftypeNode.jjtGetChild(0);
                    if (classOrIntType instanceof ASTClassOrInterfaceType) {
                        SimpleNode nameNode = (ASTClassOrInterfaceType)classOrIntType;
                        this.checkVariableType(nameNode, nameNode.getImage());
                    }
                }
            }
        }
        return super.visit(node, data);
    }

    public Object visit(ASTLocalVariableDeclaration node, Object data) {
        this.handleASTTypeChildren(node);
        return super.visit(node, data);
    }

    public Object visit(ASTFormalParameter node, Object data) {
        this.handleASTTypeChildren(node);
        return super.visit(node, data);
    }

    public Object visit(ASTFieldDeclaration node, Object data) {
        for (int x = 0; x < node.jjtGetNumChildren(); ++x) {
            SimpleNode firstStmt = (SimpleNode) node.jjtGetChild(x);
            if (firstStmt instanceof ASTType) {
                ASTType tp = (ASTType) firstStmt;
                SimpleNode nd = (SimpleNode) tp.jjtGetChild(0);
                this.checkVariableType(nd, nd.getImage());
            }
        }

        return super.visit(node, data);
    }

    /**
     * convience method to handle hierarchy. This is probably too much
     * work and will go away once I figure out the framework
     */
    private void handleASTTypeChildren(SimpleNode node) {
        for (int x = 0; x < node.jjtGetNumChildren(); x++) {
            SimpleNode sNode = (SimpleNode) node.jjtGetChild(x);
            if (sNode instanceof ASTType) {
                SimpleNode nameNode = (SimpleNode) sNode.jjtGetChild(0);
                this.checkVariableType(nameNode, nameNode.getImage());
            }
        }
    }

    /**
     * performs a check on the variable and updates the couter. Counter is
     * instance for a class and is reset upon new class scan.
     *
     * @param String variableType
     */
    private void checkVariableType(SimpleNode nameNode, String variableType) {
        //if the field is of any type other than the class type
        //increment the count
        if (!nameNode.getScope().getEnclosingClassScope().getClassName().equals(variableType) && (!this.filterTypes(variableType)) && !this.typesFoundSoFar.contains(variableType)) {
            this.couplingCount++;
            this.typesFoundSoFar.add(variableType);
        }
    }

    /**
     * Filters variable type - we don't want primatives, wrappers, strings, etc.
     * This needs more work. I'd like to filter out super types and perhaps interfaces
     *
     * @param String variableType
     * @return boolean true if variableType is not what we care about
     */
    private boolean filterTypes(String variableType) {
        return variableType != null && (variableType.startsWith("java.lang.") || (variableType.equals("String")) || filterPrimitivesAndWrappers(variableType));
    }

    /**
     * @param String variableType
     * @return boolean true if variableType is a primative or wrapper
     */
    private boolean filterPrimitivesAndWrappers(String variableType) {
        return (variableType.equals("int") || variableType.equals("Integer") || variableType.equals("char") || variableType.equals("Character") || variableType.equalsIgnoreCase("double") || variableType.equalsIgnoreCase("long") || variableType.equalsIgnoreCase("short") || variableType.equalsIgnoreCase("float") || variableType.equalsIgnoreCase("byte") || variableType.equalsIgnoreCase("boolean"));
    }
}
