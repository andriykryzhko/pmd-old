/*
 * User: tom
 * Date: Jul 22, 2002
 * Time: 11:35:50 AM
 */
package net.sourceforge.pmd.rules.design;

import net.sourceforge.pmd.AbstractRule;
import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.ast.ASTResultType;
import net.sourceforge.pmd.ast.SimpleNode;
import net.sourceforge.pmd.ast.ASTFieldDeclaration;
import net.sourceforge.pmd.ast.ASTFormalParameter;

import java.util.Set;
import java.util.HashSet;
import java.text.MessageFormat;

public class LooseCouplingRule extends AbstractRule {

    private Set implClassNames = new HashSet();

    public LooseCouplingRule() {
        super();
        implClassNames.add("HashSet");
        implClassNames.add("HashMap");
        implClassNames.add("ArrayList");
        implClassNames.add("LinkedList");
        implClassNames.add("LinkedHashMap");
        implClassNames.add("LinkedHashSet");
        implClassNames.add("TreeSet");
        implClassNames.add("TreeMap");
        implClassNames.add("java.util.HashSet");
        implClassNames.add("java.util.HashMap");
        implClassNames.add("java.util.ArrayList");
        implClassNames.add("java.util.LinkedList");
        implClassNames.add("java.util.LinkedHashMap");
        implClassNames.add("java.util.LinkedHashSet");
        implClassNames.add("java.util.TreeSet");
        implClassNames.add("java.util.TreeMap");
    }

    public Object visit(ASTResultType node, Object data) {
        if (node.isVoid()) {
            return data;
        }
        return checkType(node, data);
    }

    public Object visit(ASTFieldDeclaration node, Object data) {
        return checkType(node, data);
    }

    public Object visit(ASTFormalParameter node, Object data) {
        return checkType(node, data);
    }

    private Object checkType(SimpleNode node, Object data) {
        SimpleNode name = (SimpleNode)node.jjtGetChild(0).jjtGetChild(0);
        if (implClassNames.contains(name.getImage())) {
            RuleContext ctx = (RuleContext)data;
            ctx.getReport().addRuleViolation(createRuleViolation(ctx, name.getBeginLine(), MessageFormat.format(getMessage(), new Object[] {name.getImage()})));
        }
        return data;
    }
}
