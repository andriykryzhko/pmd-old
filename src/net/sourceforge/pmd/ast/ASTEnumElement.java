/* Generated By:JJTree: Do not edit this line. ASTEnumElement.java */

package net.sourceforge.pmd.ast;

public class ASTEnumElement extends SimpleJavaNode {
    public ASTEnumElement(int id) {
        super(id);
    }

    public ASTEnumElement(JavaParser p, int id) {
        super(p, id);
    }


    /**
     * Accept the visitor. *
     */
    public Object jjtAccept(JavaParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }
}
