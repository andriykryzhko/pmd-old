/* Generated By:JJTree: Do not edit this line. ASTCompilationUnit.java */

package net.sourceforge.pmd.ast;

public class ASTCompilationUnit extends SimpleNode {
  public ASTCompilationUnit(int id) {
    super(id);
  }

  public ASTCompilationUnit(JavaParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(JavaParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
