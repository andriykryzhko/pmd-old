/* Generated By:JJTree: Do not edit this line. ASTIfStatement.java */

package net.sourceforge.pmd.ast;

public class ASTIfStatement extends AbstractJavaNode {
    public ASTIfStatement(int id) {
        super(id);
    }

    public ASTIfStatement(JavaParser p, int id) {
        super(p, id);
    }

    private boolean hasElse;

    public void setHasElse() {
        this.hasElse = true;
    }

    public boolean hasElse() {
        return this.hasElse;
    }

    /**
     * Accept the visitor. *
     */
    public Object jjtAccept(JavaParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }
}
