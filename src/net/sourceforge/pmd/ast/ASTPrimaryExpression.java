/* Generated By:JJTree: Do not edit this line. ASTPrimaryExpression.java */

package net.sourceforge.pmd.ast;

public class ASTPrimaryExpression extends SimpleNode {
    public ASTPrimaryExpression(int id) {
        super(id);
    }

    public ASTPrimaryExpression(JavaParser p, int id) {
        super(p, id);
    }


    /**
     * Accept the visitor. *
     */
    public Object jjtAccept(JavaParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }

}
