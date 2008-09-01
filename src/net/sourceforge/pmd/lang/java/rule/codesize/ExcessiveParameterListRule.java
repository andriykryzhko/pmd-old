/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd.lang.java.rule.codesize;

import net.sourceforge.pmd.lang.java.ast.ASTFormalParameter;
import net.sourceforge.pmd.lang.java.ast.ASTFormalParameters;
import net.sourceforge.pmd.lang.java.rule.design.ExcessiveNodeCountRule;
import net.sourceforge.pmd.util.NumericConstants;

/**
 * This rule detects an abnormally long parameter list.
 * Note:  This counts Nodes, and not necessarily parameters,
 * so the numbers may not match up.  (But topcount and sigma
 * should work.)
 */
// FUTURE Rename to ExcessiveParameterListRule
public class ExcessiveParameterListRule extends ExcessiveNodeCountRule {
    public ExcessiveParameterListRule() {
        super(ASTFormalParameters.class);
        setProperty(MINIMUM_DESCRIPTOR, 10d);
    }

    // Count these nodes, but no others.
    public Object visit(ASTFormalParameter node, Object data) {
        return NumericConstants.ONE;
    }
}
