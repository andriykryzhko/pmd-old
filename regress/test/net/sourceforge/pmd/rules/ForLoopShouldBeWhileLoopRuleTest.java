/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
*/
package test.net.sourceforge.pmd.rules;

import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.Rule;
import net.sourceforge.pmd.RuleSetNotFoundException;
import net.sourceforge.pmd.Report;
import test.net.sourceforge.pmd.testframework.SimpleAggregatorTst;
import test.net.sourceforge.pmd.testframework.TestDescriptor;

public class ForLoopShouldBeWhileLoopRuleTest extends SimpleAggregatorTst {

    private Rule rule;

    public void setUp() throws RuleSetNotFoundException {
        rule = findRule("rulesets/basic.xml", "ForLoopShouldBeWhileLoop");
    }

    public void testAll() {
       runTests(new TestDescriptor[] {
           new TestDescriptor(TEST1, "simple failure case", 1, rule),
           new TestDescriptor(TEST2, "ok", 0, rule),
           new TestDescriptor(TEST3, "for loop like this: for (;;) {} ", 0, rule),
       });
    }

    public void testJDK15ForLoop() throws Throwable {
        Report rpt = new Report();
        runTestFromString15(TEST4, rule, rpt);
        assertEquals(0, rpt.size());
    }


    private static final String TEST1 =
    "public class Foo {" + PMD.EOL +
    " void foo() {" + PMD.EOL +
    "  int x = 2;" + PMD.EOL +
    "  for (;x<5;) { " + PMD.EOL +
    "   x++;" + PMD.EOL +
    "  }" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    private static final String TEST2 =
    "public class Foo {" + PMD.EOL +
    " void foo() {" + PMD.EOL +
    "  for (int x=2;x<5;) { " + PMD.EOL +
    "   x++;" + PMD.EOL +
    "  }" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    private static final String TEST3 =
    "public class Foo {" + PMD.EOL +
    " void foo() {" + PMD.EOL +
    "  for (;;) {}" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    private static final String TEST4 =
    "public class Foo {" + PMD.EOL +
    " void foo() {" + PMD.EOL +
    "  for (String x : mylist) {}" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

}
