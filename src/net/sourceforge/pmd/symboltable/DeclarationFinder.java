/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd.symboltable;

import net.sourceforge.pmd.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.ast.ASTMethodDeclarator;
import net.sourceforge.pmd.ast.ASTVariableDeclaratorId;
import net.sourceforge.pmd.ast.JavaParserVisitorAdapter;
import net.sourceforge.pmd.ast.SimpleNode;

public class DeclarationFinder extends JavaParserVisitorAdapter {

    public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
        // FIXME - this adds it to the wrong scope for top level classes
        Scope s = ((SimpleNode)node.jjtGetParent()).getScope();
        s.addDeclaration(new ClassNameDeclaration(node));
        return super.visit(node, data);
    }

    public Object visit(ASTVariableDeclaratorId node, Object data) {
        node.getScope().addDeclaration(new VariableNameDeclaration(node));
        return super.visit(node, data);
    }

    public Object visit(ASTMethodDeclarator node, Object data) {
        node.getScope().getEnclosingClassScope().addDeclaration(new MethodNameDeclaration(node));
        return super.visit(node, data);
    }
}
