/* Generated By:JJTree: Do not edit this line. ASTPrimitiveType.java */

package net.sourceforge.pmd.ast;

public class ASTPrimitiveType extends SimpleNode {
    public ASTPrimitiveType(int id) {
        super(id);
    }

    public ASTPrimitiveType(JavaParser p, int id) {
        super(p, id);
    }

    public boolean isBoolean() {
        return getImage().equals("boolean");
    }

    /**
     * Accept the visitor. *
     */
    public Object jjtAccept(JavaParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }

    public void dump(String prefix) {
        System.out.println(toString(prefix) + "(" + getImage() + ")");
        dumpChildren(prefix);
    }
}
