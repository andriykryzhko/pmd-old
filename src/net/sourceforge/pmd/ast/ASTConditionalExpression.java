/* Generated By:JJTree: Do not edit this line. ASTConditionalExpression.java */

package net.sourceforge.pmd.ast;

public class ASTConditionalExpression extends SimpleNode {
    public ASTConditionalExpression(int id) {
        super(id);
        setDiscardable();
    }

    public ASTConditionalExpression(JavaParser p, int id) {
        super(p, id);
        setDiscardable();
    }

    /**
     * Accept the visitor. *
     */
    public Object jjtAccept(JavaParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }
}
