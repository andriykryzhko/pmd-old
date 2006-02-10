/* Generated By:JJTree: Do not edit this line. ASTModifiers.java */

package net.sourceforge.pmd.ast;

public class ASTModifiers extends SimpleJavaNode {
    public ASTModifiers(int id) {
        super(id);
    }

    public ASTModifiers(JavaParser p, int id) {
        super(p, id);
    }

    public void discardIfNecessary() {
        SimpleNode parent = (SimpleNode) jjtGetParent();
        if (allChildrenAreAnnotations()) {
            handleAnnotations(parent);
        } else if (parent.jjtGetNumChildren() == 2) {
            parent.children = new Node[]{parent.children[1]};
        } else if (parent.jjtGetNumChildren() == 3) {
            // AnnotationTypeMemberDeclaration with default value, like this:
            // String defaultValue() default "";
            parent.children = new Node[]{parent.children[1], parent.children[2]};
        } else if (parent.jjtGetNumChildren() == 4) {
            // JDK 1.5 forloop syntax
            parent.children = new Node[]{parent.children[1], parent.children[2], parent.children[3]};
        } else {
            throw new RuntimeException("ASTModifiers.discardIfNecessary didn't see expected children");
        }
    }

    private boolean allChildrenAreAnnotations() {
        if (jjtGetNumChildren() == 0) {
            return false;
        }
        for (int i = 0; i < jjtGetNumChildren(); i++) {
            if (!(jjtGetChild(i) instanceof ASTAnnotation)) {
                return false;
            }
        }
        return true;
    }

    private void handleAnnotations(SimpleNode parent) {
        SimpleNode kid = (SimpleNode) this.jjtGetChild(0);
        kid.jjtSetParent(parent);
        for (int i = 0; i < jjtGetNumChildren(); i++) {
            if (i == 0) {
                parent.jjtReplaceChild(this, kid);
            } else {
                parent.jjtAddChild(jjtGetChild(i), i);
            }
        }
    }


    /**
     * Accept the visitor. *
     */
    public Object jjtAccept(JavaParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }
}
