/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd.rules;

import net.sourceforge.pmd.AbstractRule;
import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.ast.ASTCompilationUnit;
import net.sourceforge.pmd.ast.ASTImportDeclaration;
import net.sourceforge.pmd.ast.ASTName;
import net.sourceforge.pmd.ast.SimpleNode;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class UnusedImportsRule extends AbstractRule {

    private Set imports = new HashSet();

    public Object visit(ASTCompilationUnit node, Object data) {
        imports.clear();

        super.visit(node, data);

        RuleContext ctx = (RuleContext) data;
        for (Iterator i = imports.iterator(); i.hasNext();) {
            ImportWrapper wrapper = (ImportWrapper) i.next();
            String msg = MessageFormat.format(getMessage(), new Object[]{wrapper.getFullName()});
            ctx.getReport().addRuleViolation(createRuleViolation(ctx, wrapper.getPositionProvider(), msg));
        }
        return data;
    }

    public Object visit(ASTImportDeclaration node, Object data) {
        if (!node.isImportOnDemand()) {
            ASTName importedType = (ASTName) node.jjtGetChild(0);
            String className;
            if (importedType.getImage().indexOf('.') != -1) {
                int lastDot = importedType.getImage().lastIndexOf('.') + 1;
                className = importedType.getImage().substring(lastDot);
            } else {
                className = importedType.getImage();
            }
            ImportWrapper wrapper = new ImportWrapper(importedType.getImage(), className, node);
            imports.add(wrapper);
        }

        return data;
    }

    public Object visit(ASTClassOrInterfaceType node, Object data) {
        check(node);
        return super.visit(node, data);
    }

    public Object visit(ASTName node, Object data) {
        check(node);
        return data;
    }

    private void check(SimpleNode node) {
        String name;
        if (node.getImage().indexOf('.') == -1) {
            name = node.getImage();
        } else {
            name = node.getImage().substring(0, node.getImage().indexOf('.'));
        }
        ImportWrapper candidate = new ImportWrapper(node.getImage(), name, new SimpleNode(-1));
        if (imports.contains(candidate)) {
            imports.remove(candidate);
        }
    }
}
