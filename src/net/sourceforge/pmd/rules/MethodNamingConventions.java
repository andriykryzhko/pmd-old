/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd.rules;

import net.sourceforge.pmd.AbstractRule;
import net.sourceforge.pmd.ast.ASTMethodDeclarator;

public class MethodNamingConventions extends AbstractRule {

    public Object visit(ASTMethodDeclarator node, Object data) {
        if (Character.isUpperCase(node.getImage().charAt(0))) {
            addViolation(data, node);
        }
        if (node.getImage().indexOf("_") >= 0) {
            addViolationWithMessage(data, node, "Method names should not contain underscores");
        }
        return data;
    }

}
