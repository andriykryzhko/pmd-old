/* Generated By:JJTree: Do not edit this line. ASTAnnotationTypeBody.java */

package net.sourceforge.pmd.ast;

public class ASTAnnotationTypeBody extends SimpleNode {
  public ASTAnnotationTypeBody(int id) {
    super(id);
  }

  public ASTAnnotationTypeBody(JavaParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(JavaParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
