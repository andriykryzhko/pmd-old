package net.sourceforge.pmd.rules.design;

import net.sourceforge.pmd.stat.*;
import net.sourceforge.pmd.AbstractRule;
import net.sourceforge.pmd.RuleContext;

import net.sourceforge.pmd.ast.*;

/**
 * This rule detects an abnormally long parameter list.
 * Note:  This counts Nodes, and not necessarily parameters,
 * so the numbers may not match up.  (But topcount and sigma
 * should work.)
 */
public class LongParameterListRule
    extends ExcessiveNodeCountRule
{
    public LongParameterListRule() {
	super( ASTFormalParameters.class );
    }

    // Count these nodes, but no others.
    public Object visit(ASTFormalParameter node, Object data ) {
	return new Integer( 1 );
    }
}
