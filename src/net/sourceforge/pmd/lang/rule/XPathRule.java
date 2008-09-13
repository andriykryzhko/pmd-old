/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd.lang.rule;

import java.util.List;

import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.rule.properties.EnumeratedProperty;
import net.sourceforge.pmd.lang.rule.properties.StringProperty;
import net.sourceforge.pmd.lang.rule.xpath.JaxenXPathRuleQuery;
import net.sourceforge.pmd.lang.rule.xpath.SaxonXPathRuleQuery;
import net.sourceforge.pmd.lang.rule.xpath.XPathRuleQuery;

/**
 * Rule that tries to match an XPath expression against a DOM view of an AST.
 * <p/>
 * This rule needs a property "xpath".
 */
public class XPathRule extends AbstractRule {

    public static final StringProperty XPATH_DESCRIPTOR = new StringProperty("xpath", "XPATH value", "", 1.0f);
    public static final EnumeratedProperty<String> VERSION_DESCRIPTOR = new EnumeratedProperty<String>("version",
	    "The XPath specification version.", new String[] { "1.0", "1.0 compatibility", "2.0" }, new String[] {
		    "XPath 1.0", "XPath 2.0 in XPath 1.0 compatability mode", "XPath 2.0" }, 0, 2.0f);

    private XPathRuleQuery xpathRuleQuery;

    public XPathRule() {
	definePropertyDescriptor(XPATH_DESCRIPTOR);
	definePropertyDescriptor(VERSION_DESCRIPTOR);
    }

    /**
     * Apply the rule to all nodes.
     */
    public void apply(List<? extends Node> nodes, RuleContext ctx) {
	for (Node node : nodes) {
	    evaluate(node, ctx);
	}
    }

    /**
     * Evaluate the XPath query with the AST node.
     * All matches are reported as violations.
     *
     * @param node The Node that to be checked.
     * @param data The RuleContext.
     */
    public void evaluate(Node node, RuleContext data) {
	init();
	List<Node> nodes = xpathRuleQuery.evaluate(node, data);
	if (nodes != null) {
	    for (Node n : nodes) {
		addViolation(data, n, n.getImage());
	    }
	}

    }

    @Override
    public List<String> getRuleChainVisits() {
	if (init()) {
	    for (String nodeName : xpathRuleQuery.getRuleChainVisits()) {
		super.addRuleChainVisit(nodeName);
	    }
	}
	return super.getRuleChainVisits();
    }

    private boolean init() {
	if (xpathRuleQuery == null) {
	    String xpath = getProperty(XPATH_DESCRIPTOR);
	    String version = (String) getProperty(VERSION_DESCRIPTOR);
	    if ("1.0".equals(version)) {
		xpathRuleQuery = new JaxenXPathRuleQuery();
	    } else {
		xpathRuleQuery = new SaxonXPathRuleQuery();
	    }
	    xpathRuleQuery.setXPath(xpath);
	    xpathRuleQuery.setVersion(version);
	    xpathRuleQuery.setProperties(this.getPropertiesByPropertyDescriptor());
	    return true;
	}
	return false;
    }
}
