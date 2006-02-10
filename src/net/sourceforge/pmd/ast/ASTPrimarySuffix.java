/* Generated By:JJTree: Do not edit this line. ASTPrimarySuffix.java */

package net.sourceforge.pmd.ast;

public class ASTPrimarySuffix extends SimpleJavaNode {
    public ASTPrimarySuffix(int id) {
        super(id);
    }

    public ASTPrimarySuffix(JavaParser p, int id) {
        super(p, id);
    }

    private boolean isArguments;
    private boolean isArrayDereference;

    public void setIsArrayDereference() {
        isArrayDereference = true;
    }

    public boolean isArrayDereference() {
        return isArrayDereference;
    }

    public void setIsArguments() {
        this.isArguments = true;
    }

    public boolean isArguments() {
        return this.isArguments;
    }

    public void dump(String prefix) {
        String out = "";
        if (isArrayDereference()) {
            out += ":[";
        }
        if (this.getImage() != null) {
            out += out.length() == 0 ? ":" + this.getImage() : this.getImage();
        }
        System.out.println(toString(prefix) + out);
        dumpChildren(prefix);
    }

    /**
     * Accept the visitor. *
     */
    public Object jjtAccept(JavaParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }
}
