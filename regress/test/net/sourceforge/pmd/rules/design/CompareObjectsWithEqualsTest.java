package test.net.sourceforge.pmd.rules.design;

import test.net.sourceforge.pmd.testframework.SimpleAggregatorTst;
import test.net.sourceforge.pmd.testframework.TestDescriptor;
import net.sourceforge.pmd.Rule;
import net.sourceforge.pmd.RuleSetNotFoundException;
import net.sourceforge.pmd.PMD;

public class CompareObjectsWithEqualsTest extends SimpleAggregatorTst{

    private Rule rule;

    public void setUp() throws RuleSetNotFoundException {
        rule = findRule("rulesets/design.xml", "CompareObjectsWithEquals");
    }

    public void testAll() {
       runTests(new TestDescriptor[] {
           new TestDescriptor(TEST1, "simple failure with method params", 1, rule),
           new TestDescriptor(TEST2, "primitives are ok", 0, rule),
           new TestDescriptor(TEST3, "skip nulls", 0, rule),
           new TestDescriptor(TEST4, "missed hit - qualified names.  that's ok, we can't resolve the types yet, so better to skip this for now", 0, rule),
       });
    }

    private static final String TEST1 =
    "public class Foo {" + PMD.EOL +
    " boolean bar(String a, String b) {" + PMD.EOL +
    "  return a == b;" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    private static final String TEST2 =
    "public class Foo {" + PMD.EOL +
    " boolean bar(int a, int b) {" + PMD.EOL +
    "  return a == b;" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    private static final String TEST3 =
    "public class Foo {" + PMD.EOL +
    " boolean bar(int a, int b) {" + PMD.EOL +
    "  return a == null || null == b;" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    private static final String TEST4 =
    "public class Foo {" + PMD.EOL +
    " boolean bar(Foo b) {" + PMD.EOL +
    "  return this.b == b.foo;" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

}
