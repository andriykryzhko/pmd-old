/* Generated By:JJTree: Do not edit this line. ASTValueBinding.java */

package net.sourceforge.pmd.jsp.ast;

public class ASTValueBinding extends AbstractJspNode {
    public ASTValueBinding(int id) {
        super(id);
    }

    public ASTValueBinding(JspParser p, int id) {
        super(p, id);
    }


    /**
     * Accept the visitor. *
     */
    public Object jjtAccept(JspParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }
}
