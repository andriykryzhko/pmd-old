/* Generated By:JJTree: Do not edit this line. ASTResultType.java */

package net.sourceforge.pmd.ast;

public class ASTResultType extends SimpleNode {
    public ASTResultType(int id) {
        super(id);
    }

    public ASTResultType(JavaParser p, int id) {
        super(p, id);
    }

    public boolean isVoid() {
        return jjtGetNumChildren() == 0;
    }

    /**
     * Accept the visitor. *
     */
    public Object jjtAccept(JavaParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }
}
