/*
 * User: tom
 * Date: Sep 30, 2002
 * Time: 11:09:24 AM
 */
package net.sourceforge.pmd.symboltable;

import net.sourceforge.pmd.ast.*;


public class SymbolFacade extends JavaParserVisitorAdapter {

    private class ScopeCreator extends JavaParserVisitorAdapter {

        private ContextManager contextManager = new ContextManager(new SymbolTable());
        private ScopeFactory sf = new ScopeFactory();

        public Object visit(ASTCompilationUnit node, Object data){openScope(node);return data;}
        public Object visit(ASTUnmodifiedClassDeclaration node, Object data){openScope(node);return data;}
        public Object visit(ASTBlock node, Object data){openScope(node);return data;}
        public Object visit(ASTConstructorDeclaration node, Object data){openScope(node);return data;}
        public Object visit(ASTMethodDeclaration node, Object data){openScope(node);return data;}
        public Object visit(ASTTryStatement node, Object data){openScope(node);return data;}
        public Object visit(ASTForStatement node, Object data){openScope(node);return data;}
        public Object visit(ASTIfStatement node, Object data){openScope(node);return data;}

        private void openScope(SimpleNode node) {
            Scope scope = sf.createScope(node);
            if (scope instanceof NullScope) {
                super.visit(node, null);
            } else {
                contextManager.openScope(scope);
                node.setScope(contextManager.getCurrentScope());
                super.visit(node, null);
                contextManager.leaveScope();
            }
        }
    }

    private static class DeclFinder extends JavaParserVisitorAdapter {
        public Object visit(ASTVariableDeclaratorId node, Object data) {
            node.getScope().addDeclaration(new NameDeclaration(node));
            return super.visit(node, data);
        }
    }

    public void initializeWith(ASTCompilationUnit node) {
        // first, traverse the AST and create all the scopes
        ScopeCreator sc = new ScopeCreator();
        node.jjtAccept(sc, null);

        // traverse the AST and pick up all the declarations
        // this takes care of forward references
        DeclFinder df = new DeclFinder();
        node.jjtAccept(df, null);

        // finally, traverse the AST and pick up all the usages
        node.jjtAccept(this, null);
    }

    public Object visit(ASTPrimaryPrefix node, Object data) {
        LookupController lookupController = new LookupController();
        if (node.jjtGetNumChildren() > 0 && node.jjtGetChild(0) instanceof ASTName) {
            SimpleNode child = (SimpleNode)node.jjtGetChild(0);
            lookupController.lookup(new NameOccurrence(child), child.getScope());
        } else {
            if (node.jjtGetParent() instanceof ASTPrimaryExpression) {
                SimpleNode parent = (SimpleNode)node.jjtGetParent();
                if (parent.jjtGetNumChildren() > 1 && parent.jjtGetChild(1) instanceof ASTPrimarySuffix) {
                    ASTPrimarySuffix suffix = (ASTPrimarySuffix)parent.jjtGetChild(1);

                    // TODO - make this nicer
                    NameOccurrence occ = new NameOccurrence(suffix);
                    if (node.usesSuperModifier()) {
                        occ.setQualifier(Qualifier.SUPER);
                    } else if (node.usesThisModifier()) {
                        occ.setQualifier(Qualifier.THIS);
                    }

                    lookupController.lookup(occ, suffix.getScope());
                }
            }
        }
        return super.visit(node, data);
    }
}
