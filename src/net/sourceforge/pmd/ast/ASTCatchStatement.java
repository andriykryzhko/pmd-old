/* Generated By:JJTree: Do not edit this line. ASTCatchStatement.java */

package net.sourceforge.pmd.ast;

public class ASTCatchStatement extends SimpleNode {
  public ASTCatchStatement(int id) {
    super(id);
  }

  public ASTCatchStatement(JavaParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(JavaParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
