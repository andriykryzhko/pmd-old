/* Generated By:JJTree: Do not edit this line. ASTLiteral.java */

package net.sourceforge.pmd.ast;

public class ASTLiteral extends SimpleNode {
  public ASTLiteral(int id) {
    super(id);
  }

  public ASTLiteral(JavaParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(JavaParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
