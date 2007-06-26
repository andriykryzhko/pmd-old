package test.net.sourceforge.pmd.rules.basic;

import net.sourceforge.pmd.Rule;

import org.junit.Before;
import org.junit.Test;

import test.net.sourceforge.pmd.testframework.SimpleAggregatorTst;

import java.util.List;
import java.util.ArrayList;

public class BasicRulesTest extends SimpleAggregatorTst {
    private List<Rule> rules = new ArrayList<Rule>();

    @Before
    public void setUp() {
        rules.add(findRule("basic", "AvoidThreadGroup"));
        rules.add(findRule("basic", "AvoidUsingOctalValues"));
        rules.add(findRule("basic", "AvoidDecimalLiteralsInBigDecimalConstructor"));
        rules.add(findRule("basic", "BigIntegerInstantiation"));
        rules.add(findRule("basic", "BooleanInstantiation"));
        rules.add(findRule("basic", "BrokenNullCheck"));
        rules.add(findRule("basic", "ClassCastExceptionWithToArray"));
        rules.add(findRule("basic", "CollapsibleIfStatements"));
        rules.add(findRule("basic", "DoubleCheckedLocking"));
        rules.add(findRule("basic", "EmptyCatchBlock"));
        rules.add(findRule("basic", "EmptyFinallyBlock"));
        rules.add(findRule("basic", "EmptyIfStmt"));
    }

    @Test
    public void testAll() {
        for (Rule r : rules) {
            runTests(r);
        }
    }

    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(BasicRulesTest.class);
    }
}
