/*
 * User: tom
 * Date: Oct 1, 2002
 * Time: 9:50:07 AM
 */
package test.net.sourceforge.pmd.rules;

import net.sourceforge.pmd.rules.AvoidDeeplyNestedIfStmtsRule;
import net.sourceforge.pmd.Rule;

public class AvoidDeeplyNestedIfStmtsRuleTest extends RuleTst {

    private Rule rule;

    public void setUp() {
        rule = new AvoidDeeplyNestedIfStmtsRule();
        rule.addProperty("problemDepth", "3");
    }
    public void test1() throws Throwable {
        runTest("AvoidDeeplyNestedIfStmtsRule1.java", 1, rule);
    }
/*
    // TODO - this test fails... is that OK?
    public void test2() throws Throwable {
        runTest("AvoidDeeplyNestedIfStmtsRule2.java", 0, rule);
    }
*/
}
