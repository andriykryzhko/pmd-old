/* Generated By:JJTree: Do not edit this line. ASTClassOrInterfaceType.java */

package net.sourceforge.pmd.ast;

public class ASTClassOrInterfaceType extends SimpleJavaNode {
  public ASTClassOrInterfaceType(int id) {
    super(id);
  }

  public ASTClassOrInterfaceType(JavaParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(JavaParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

}
