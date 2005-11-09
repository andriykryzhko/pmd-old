/* Generated By:JJTree: Do not edit this line. ASTBlock.java */

package net.sourceforge.pmd.ast;

public class ASTBlock extends SimpleNode {
    public ASTBlock(int id) {
        super(id);
    }

    public ASTBlock(JavaParser p, int id) {
        super(p, id);
    }


    /**
     * Accept the visitor. *
     */
    public Object jjtAccept(JavaParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }

    private boolean containsComment;

    public boolean containsComment() {
        return this.containsComment;
    }

    public void setContainsComment() {
        this.containsComment = true;
    }

}
