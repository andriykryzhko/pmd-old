/* Generated By:JJTree: Do not edit this line. ASTTypeArguments.java */

package net.sourceforge.pmd.ast;

public class ASTTypeArguments extends SimpleJavaNode {
  public ASTTypeArguments(int id) {
    super(id);
  }

  public ASTTypeArguments(JavaParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(JavaParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
