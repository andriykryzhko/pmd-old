/* Generated By:JJTree: Do not edit this line. ASTMemberValuePair.java */

package net.sourceforge.pmd.ast;

public class ASTMemberValuePair extends SimpleJavaNode {
  public ASTMemberValuePair(int id) {
    super(id);
  }

  public ASTMemberValuePair(JavaParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(JavaParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
