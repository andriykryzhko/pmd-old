/* Generated By:JJTree: Do not edit this line. ASTClassOrInterfaceBodyDeclaration.java */

package net.sourceforge.pmd.ast;

import net.sourceforge.pmd.Rule;

public class ASTClassOrInterfaceBodyDeclaration extends SimpleNode  implements CanSuppressWarnings{
    
  public ASTClassOrInterfaceBodyDeclaration(int id) {
    super(id);
  }

  public ASTClassOrInterfaceBodyDeclaration(JavaParser p, int id) {
    super(p, id);
  }


    public boolean hasSuppressWarningsAnnotationFor(Rule rule) {
        for (int i=0;i<jjtGetNumChildren(); i++) {
            if (jjtGetChild(i) instanceof ASTAnnotation) {
                ASTAnnotation a = (ASTAnnotation)jjtGetChild(i);
                if (a.suppresses(rule)) {
                    return true;
                }
            }
        }
        return false;
    }

  /** Accept the visitor. **/
  public Object jjtAccept(JavaParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

    public boolean isAnonymousInnerClass() {
        return jjtGetParent().jjtGetParent() instanceof ASTAllocationExpression;
    }
}
