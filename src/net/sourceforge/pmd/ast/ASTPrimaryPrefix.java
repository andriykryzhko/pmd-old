/* Generated By:JJTree: Do not edit this line. ASTPrimaryPrefix.java */

package net.sourceforge.pmd.ast;

public class ASTPrimaryPrefix extends SimpleJavaTypeNode {
    public ASTPrimaryPrefix(int id) {
        super(id);
    }

    public ASTPrimaryPrefix(JavaParser p, int id) {
        super(p, id);
    }

    private boolean usesThisModifier;
    private boolean usesSuperModifier;

    public void setUsesThisModifier() {
        usesThisModifier = true;
    }

    public boolean usesThisModifier() {
        return this.usesThisModifier;
    }

    public void setUsesSuperModifier() {
        usesSuperModifier = true;
    }

    public boolean usesSuperModifier() {
        return this.usesSuperModifier;
    }

    /**
     * Accept the visitor. *
     */
    public Object jjtAccept(JavaParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }
}
