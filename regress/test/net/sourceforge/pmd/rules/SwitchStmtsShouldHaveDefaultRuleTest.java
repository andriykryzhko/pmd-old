package test.net.sourceforge.pmd.rules;

import net.sourceforge.pmd.Rule;
import net.sourceforge.pmd.cpd.CPD;
import net.sourceforge.pmd.rules.XPathRule;

public class SwitchStmtsShouldHaveDefaultRuleTest extends SimpleAggregatorTst {

    private Rule rule;

    public void setUp() {
        rule = new XPathRule();
        rule.addProperty("xpath", "//SwitchStatement[not(SwitchLabel[count(*) = 0])]");
    }

    public void testAll() {
       runTests(new TestDescriptor[] {
           new TestDescriptor(TEST1, "simple failure case", 1, rule),
           new TestDescriptor(TEST2, "simple ok case", 0, rule)
       });
    }

    private static final String TEST1 =
    "public class SwitchStmtsShouldHaveDefault1 {" + CPD.EOL +
    " public void bar() {" + CPD.EOL +
    "  int x = 2;" + CPD.EOL +
    "  switch (x) {" + CPD.EOL +
    "   case 2: int y=8;" + CPD.EOL +
    "  }" + CPD.EOL +
    " }" + CPD.EOL +
    "}";

    private static final String TEST2 =
    "public class SwitchStmtsShouldHaveDefault2 {" + CPD.EOL +
    " public void bar() {" + CPD.EOL +
    "  int x = 2;" + CPD.EOL +
    "  switch (x) {" + CPD.EOL +
    "   case 2: int y=8;" + CPD.EOL +
    "   default: int j=8;" + CPD.EOL +
    "  }" + CPD.EOL +
    " }" + CPD.EOL +
    "}";


}
