package net.sourceforge.pmd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sourceforge.pmd.ast.SimpleNode;
import net.sourceforge.pmd.util.Benchmark;

/**
 * This is a base class for RuleChainVisitor implementations which
 * extracts interesting nodes from an AST, and lets each Rule visit
 * the nodes it has expressed interest in.
 */
public abstract class AbstractRuleChainVisitor implements RuleChainVisitor {
    /**
     * These are all the rules participating in the RuleChain.
     */
    protected List rules = new ArrayList();

    /**
     * This is a mapping from node names to nodes instances for the current AST.
     */
    protected Map nodeNameToNodes;

    /**
     * @see RuleChainVisitor#add(Rule)
     */
    public void add(Rule rule) {
        rules.add(rule);
    }

    /**
     * @see RuleChainVisitor#visitAll(List, RuleContext)
     */
    public void visitAll(List astCompilationUnits, RuleContext ctx) {
        initialize();
        clear();

        // Perform a visitation of the AST to index nodes which need visiting by
        // type
        long start = Benchmark.nanoTime();
        indexNodes(astCompilationUnits, ctx);
        long end = Benchmark.nanoTime();
        Benchmark.mark(Benchmark.TYPE_RULE_CHAIN, "RuleChain Visit", end - start, 1);

        // For each rule, allow it to visit the nodes it desires
        int visits = 0;
        start = Benchmark.nanoTime();
        for (int i = 0; i < rules.size(); i++) {
            final Rule rule = (Rule) rules.get(i);
            final List nodeNames = rule.getRuleChainVisits();
            for (int j = 0; j < nodeNames.size(); j++) {
                List nodes = (List) nodeNameToNodes.get((String) nodeNames.get(j));
                for (int k = 0; k < nodes.size(); k++) {
                    visit(rule, (SimpleNode) nodes.get(k), ctx);
                }
                visits += nodes.size();
            }
            end = Benchmark.nanoTime();
            Benchmark.mark(Benchmark.TYPE_RULE_CHAIN_RULE, rule.getName(), end - start, visits);
            start = end;
        }
    }

    /**
     * Visit the given rule to the given node.
     */
    protected abstract void visit(Rule rule, SimpleNode node, RuleContext ctx);

    /**
     * Index all nodes for visitation by rules.
     */
    protected abstract void indexNodes(List astCompilationUnits, RuleContext ctx);

    /**
     * Index a single node for visitation by rules.
     */
    protected void indexNode(SimpleNode node) {
        List nodes = (List) nodeNameToNodes.get(node.toString());
        if (nodes != null) {
            nodes.add(node);
        }
    }

    /**
     * Initialize the RuleChainVisitor to be ready to perform visitations. This
     * method should not be called until it is know that all Rules participating
     * in the RuleChain are ready to be initialized themselves.  Some rules
     * may require full initialization to determine if they will participate in
     * the RuleChain, so this has been delayed as long as possible to ensure
     * that manipulation of the Rules is no longer occuring.
     */
    protected void initialize() {
        if (nodeNameToNodes != null) {
            return;
        }

        // Determine all node types that need visiting
        Set visitedNodes = new HashSet();
        for (Iterator i = rules.iterator(); i.hasNext();) {
            Rule rule = (Rule) i.next();
            if (rule.usesRuleChain()) {
                visitedNodes.addAll(rule.getRuleChainVisits());
            }
            else {
                // Drop rules which do not participate in the rule chain.
                i.remove();
            }
        }

        // Setup the data structure to manage mapping node names to node
        // instances.  We intend to reuse this data structure between
        // visits to different ASTs.
        nodeNameToNodes = new HashMap();
        for (Iterator i = visitedNodes.iterator(); i.hasNext();) {
            List nodes = new ArrayList(100);
            nodeNameToNodes.put((String) i.next(), nodes);
        }
    }

    /**
     * Clears the internal data structure used to manage the nodes visited
     * between visiting different ASTs.
     */
    protected void clear() {
        for (Iterator i = nodeNameToNodes.values().iterator(); i.hasNext();) {
            ((List) i.next()).clear();
        }
    }
}
