package net.sourceforge.pmd.rules;

import net.sourceforge.pmd.AbstractRule;
import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.RuleViolation;

import net.sourceforge.pmd.ast.*;

import java.text.MessageFormat;

public class ShortMethodNameRule
    extends AbstractRule
{
    public ShortMethodNameRule() { }

    public Object visit(ASTMethodDeclarator decl, Object data) {
	RuleContext ctx = (RuleContext) data;
	String image = decl.getImage();

	if (image.length() <= 3) {
        ctx.getReport().addRuleViolation(createRuleViolation(ctx, decl.getBeginLine(), MessageFormat.format(getMessage(), new Object[] {decl.getImage()})));
	}

	return null;
    }
}
