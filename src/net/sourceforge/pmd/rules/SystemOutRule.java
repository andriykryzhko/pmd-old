package net.sourceforge.pmd.rules;

import net.sourceforge.pmd.ast.JavaParserVisitorAdapter;
import net.sourceforge.pmd.ast.ASTName;
import net.sourceforge.pmd.AbstractRule;
import net.sourceforge.pmd.Rule;
import net.sourceforge.pmd.Report;
import net.sourceforge.pmd.RuleViolation;

import java.util.*;

public class SystemOutRule extends AbstractRule implements Rule {

    public String getDescription() {return "Don't use System.out/in/err, use the Cougaar logging service instead";}

    public Object visit(ASTName node, Object data){
        if (node.getImage() != null && (node.getImage().startsWith("System.out") || node.getImage().startsWith("System.err") || node.getImage().startsWith("System.in"))) {
            ((Report)data).addRuleViolation(new RuleViolation(this, node.getBeginLine()));
        }
        return super.visit(node,data);
    }
}
