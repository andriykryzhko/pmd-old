/* Generated By:JJTree: Do not edit this line. ASTReturnStatement.java */

package com.infoether.pmd.ast;

public class ASTReturnStatement extends SimpleNode {
  public ASTReturnStatement(int id) {
    super(id);
  }

  public ASTReturnStatement(JavaParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(JavaParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
