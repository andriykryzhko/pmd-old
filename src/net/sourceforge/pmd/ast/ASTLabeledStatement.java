/* Generated By:JJTree: Do not edit this line. ASTLabeledStatement.java */

package net.sourceforge.pmd.ast;

public class ASTLabeledStatement extends SimpleNode {
    public ASTLabeledStatement(int id) {
        super(id);
    }

    public ASTLabeledStatement(JavaParser p, int id) {
        super(p, id);
    }


    /** Accept the visitor. **/
    public Object jjtAccept(JavaParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }
}
