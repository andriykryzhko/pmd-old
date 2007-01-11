package net.sourceforge.pmd;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sourceforge.pmd.ast.JavaRuleChainVisitorByRule;
import net.sourceforge.pmd.jsp.ast.JspRuleChainVisitorByRule;

/**
 * The RuleChain is a means by which Rules can participate in a uniform
 * visitation of the AST, and not need perform their own independant visitation.
 * The RuleChain exists as a means to improve the speed of PMD when there are
 * many Rules.
 */
public class RuleChain {
    // Mapping from Language to RuleChainVisitor
    private final Map languageToRuleChainVisitor = new HashMap();

    /**
     * Add all Rules from the given RuleSet which want to participate in the
     * RuleChain.
     * 
     * @param ruleSet
     *            The RuleSet to add Rules from.
     */
    public void add(RuleSet ruleSet) {
        Language language = ruleSet.getLanguage();
        Iterator iter = ruleSet.getRules().iterator();
        while (iter.hasNext()) {
            add(language, (Rule) iter.next());
        }
    }

    /**
     * Add the given Rule if it wants to participate in the RuleChain.
     * 
     * @param language
     *            The Language used by the Rule.
     * @param rule
     *            The Rule to add.
     */
    public void add(Language language, Rule rule) {
        RuleChainVisitor visitor = getRuleChainVisitor(language);
        if (visitor != null) {
            visitor.add(rule);
        }
    }

    /**
     * Apply the RuleChain to the given ASTCompilationUnits using the given
     * RuleContext, for those rules using the given Language.
     * 
     * @param astCompilationUnits
     *            The ASTCompilationUnits.
     * @param ctx
     *            The RuleContext.
     * @param language
     *            The Language.
     */
    public void apply(List astCompilationUnits, RuleContext ctx,
            Language language) {
        RuleChainVisitor visitor = getRuleChainVisitor(language);
        if (visitor != null) {
            visitor.visitAll(astCompilationUnits, ctx);
        }
    }

    // Get the RuleChainVisitor for the appropriate Language.
    private RuleChainVisitor getRuleChainVisitor(Language language) {
        if (language == null) {
            language = Language.JAVA;
        }
        RuleChainVisitor visitor = (RuleChainVisitor) languageToRuleChainVisitor
                .get(language);
        if (visitor == null) {
            if (Language.JAVA.equals(language)) {
                visitor = new JavaRuleChainVisitorByRule();
            } else if (Language.JSP.equals(language)) {
                visitor = new JspRuleChainVisitorByRule();
            } else {
                throw new IllegalArgumentException("Unknown language: "
                        + language);
            }
            languageToRuleChainVisitor.put(language, visitor);
        }
        return visitor;
    }
}
