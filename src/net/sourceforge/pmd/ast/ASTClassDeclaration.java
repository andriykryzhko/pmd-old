package net.sourceforge.pmd.ast;

public class ASTClassDeclaration extends AccessNode {
    public ASTClassDeclaration(int id) {
        super(id);
    }

    public ASTClassDeclaration(JavaParser p, int id) {
        super(p, id);
    }


    /**
     * Accept the visitor. *
     */
    public Object jjtAccept(JavaParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }

    public void dump(String prefix) {
        System.out.println(collectDumpedModifiers(prefix));
        dumpChildren(prefix);
    }
}