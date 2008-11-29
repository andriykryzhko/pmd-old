/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd.lang.ecmascript.ast;

import org.mozilla.javascript.ast.NewExpression;

public class ASTNewExpression extends AbstractEcmascriptNode<NewExpression> {
    public ASTNewExpression(NewExpression newExpression) {
	super(newExpression);
    }

    public EcmascriptNode getTarget() {
	return (EcmascriptNode) jjtGetChild(0);
    }

    public int getNumArguments() {
	return node.getArguments().size();
    }

    public EcmascriptNode getArgument(int index) {
	return (EcmascriptNode) jjtGetChild(index + 1);
    }

    public boolean isHasArguments() {
	return getNumArguments() != 0;
    }

    public boolean isHasInitializer() {
	return node.getInitializer() != null;
    }

    public ASTObjectLiteral getInitializer() {
	return (ASTObjectLiteral) jjtGetChild(jjtGetNumChildren() - 1);
    }
}
