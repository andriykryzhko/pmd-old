package net.sourceforge.pmd.ast;

import java.util.Iterator;
import java.util.List;

public class DiscardableNodeCleaner {

    private static final Class[] clazzes = new Class[]{
        ASTEqualityExpression.class,
        ASTAndExpression.class,
        ASTInstanceOfExpression.class,
        ASTUnaryExpression.class,
        ASTShiftExpression.class,
        ASTConditionalOrExpression.class,
        ASTInclusiveOrExpression.class,
        ASTExclusiveOrExpression.class,
        ASTConditionalExpression.class,
        ASTRelationalExpression.class,
        ASTUnaryExpression.class,
        ASTUnaryExpressionNotPlusMinus.class,
        ASTPostfixExpression.class,
        ASTMultiplicativeExpression.class,
        ASTAdditiveExpression.class,
        ASTConditionalAndExpression.class,
        ASTModifiers.class
    };

    public void clean(ASTCompilationUnit root) {
        for (int i = 0; i < clazzes.length; i++) {
            clean(root, clazzes[i]);
        }
    }

    private void clean(ASTCompilationUnit root, Class type) {
        List nodes = root.findChildrenOfType(type);
        for (Iterator i = nodes.iterator(); i.hasNext();) {
            ((SimpleNode) i.next()).discardIfNecessary();
        }
    }
}
