/*
 * Created on Jan 11, 2005 
 *
 * $Id$
 */
package net.sourceforge.pmd.rules.optimization;

import java.util.Iterator;
import java.util.List;

import net.sourceforge.pmd.AbstractRule;
import net.sourceforge.pmd.Rule;
import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.ast.ASTAssignmentOperator;
import net.sourceforge.pmd.ast.ASTLocalVariableDeclaration;
import net.sourceforge.pmd.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.ast.ASTName;
import net.sourceforge.pmd.ast.ASTPostfixExpression;
import net.sourceforge.pmd.ast.ASTPreDecrementExpression;
import net.sourceforge.pmd.ast.ASTPreIncrementExpression;
import net.sourceforge.pmd.ast.ASTVariableDeclaratorId;

/**
 * Base class with utility methods for optimization rules
 * 
 * @author mgriffa
 */
public class AbstractOptimizationRule extends AbstractRule implements Rule {

    /**
     * Check constructions like
     * int i; 
     * ++i;
     * --i;
     * i++;
     * i*=1;
     * i+=1;
     */
    private final boolean numericWithPrePost(ASTMethodDeclaration md, String varName) {
        // ++i
        List preinc = md.findChildrenOfType(ASTPreIncrementExpression.class);
        if (preinc!=null && !preinc.isEmpty()) {
            for (Iterator it = preinc.iterator() ; it.hasNext() ; ) {
                ASTPreIncrementExpression ie = (ASTPreIncrementExpression) it.next();
                if (((ASTName)ie.jjtGetChild(0).jjtGetChild(0).jjtGetChild(0)).getImage().equals(varName)) {
                    return true;
                }
            }
        }
        
        // --i
        List predec = md.findChildrenOfType(ASTPreDecrementExpression.class);
        if (predec!=null && !predec.isEmpty()) {
            for (Iterator it = predec.iterator() ; it.hasNext() ; ) {
                ASTPreDecrementExpression de = (ASTPreDecrementExpression) it.next();
                if (((ASTName)de.jjtGetChild(0).jjtGetChild(0).jjtGetChild(0)).getImage().equals(varName)) {
                    return true;
                }
            }
        }
        
        List pf = md.findChildrenOfType(ASTPostfixExpression.class);
        if (pf!=null && !pf.isEmpty()) {
            for (Iterator it = pf.iterator() ; it.hasNext() ; ) {
                ASTPostfixExpression pe = (ASTPostfixExpression) it.next();
                
                if (( pe.getImage().equals("++") || pe.getImage().equals("--") ) 
                        && ((ASTName)pe.jjtGetChild(0).jjtGetChild(0).jjtGetChild(0)).getImage().equals(varName)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected final String getVarName(ASTLocalVariableDeclaration node) {
        List l = node.findChildrenOfType(ASTVariableDeclaratorId.class);
        if (l != null && l.size()>0) {
            ASTVariableDeclaratorId vd = (ASTVariableDeclaratorId) l.get(0);
            return vd.getImage();
        }
        return null;
    }


    protected final void addViolation(RuleContext context, int beginLine) {
        context.getReport().addRuleViolation(createRuleViolation(context, beginLine));
    }


    private final boolean variableAssigned(final String varName, final List assignements) {
        if (assignements==null || assignements.isEmpty()) {
            return false;
        }
        for (Iterator it = assignements.iterator() ; it.hasNext() ; ) {
            final ASTAssignmentOperator a = (ASTAssignmentOperator) it.next();
            // if node is assigned return true
            ASTName n = (ASTName) a.jjtGetParent().jjtGetChild(0).jjtGetChild(0).jjtGetChild(0);
            if (n.getImage().equals(varName)) {
                return true;
            }
        }
        
        return false;
    }
    
    protected final boolean isVarWritterInMethod(String varName, ASTMethodDeclaration md) {
        List assignements = md.findChildrenOfType(ASTAssignmentOperator.class);

        return (variableAssigned(varName, assignements)
                || numericWithPrePost(md, varName));
    }
}
