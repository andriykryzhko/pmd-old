/* Generated By:JJTree: Do not edit this line. ASTMethodDeclarator.java */

package net.sourceforge.pmd.ast;

public class ASTMethodDeclarator extends SimpleNode {
    public ASTMethodDeclarator(int id) {
        super(id);
    }

    public ASTMethodDeclarator(JavaParser p, int id) {
        super(p, id);
    }

    public int getParameterCount() {
        return this.jjtGetChild(0).jjtGetNumChildren();
    }


    /** Accept the visitor. **/
    public Object jjtAccept(JavaParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }

    public void dump(String prefix) {
        System.out.println(toString(prefix) + ":" + getImage());
        dumpChildren(prefix);
    }
}
