/* Generated By:JJTree: Do not edit this line. ASTUnmodifiedClassDeclaration.java */

package net.sourceforge.pmd.ast;

public class ASTUnmodifiedClassDeclaration extends SimpleNode {

    public ASTUnmodifiedClassDeclaration(int id) {
        super(id);
    }

    public ASTUnmodifiedClassDeclaration(JavaParser p, int id) {
        super(p, id);
    }

    /**
     * Accept the visitor. *
     */
    public Object jjtAccept(JavaParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }

    private boolean hasExplicitExtends;

    public void setHasExplicitExtends() {
        this.hasExplicitExtends = true;
    }

    public boolean hasExplicitExtends() {
        return hasExplicitExtends;
    }

    private boolean hasExplicitImplements;

    public void setHasExplicitImplements() {
        this.hasExplicitImplements = true;
    }

    public boolean hasExplicitImplements() {
        return hasExplicitImplements;
    }

    public void dump(String prefix) {
        System.out.println(toString(prefix) + "(" + getImage() + ")");
        dumpChildren(prefix);
    }

}
