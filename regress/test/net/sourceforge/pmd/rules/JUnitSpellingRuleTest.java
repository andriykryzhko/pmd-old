/*
 * User: tom
 * Date: Sep 10, 2002
 * Time: 1:15:05 PM
 */
package test.net.sourceforge.pmd.rules;

import net.sourceforge.pmd.rules.JUnitSpellingRule;
import net.sourceforge.pmd.Rule;

public class JUnitSpellingRuleTest extends RuleTst {

    private Rule rule;


    public void setUp() {
        rule = new JUnitSpellingRule();
        rule.setMessage("when you mean ''{0}'', don't do ''{1}''");
    }


    public void test1() throws Throwable {
        runTest("JUnitSpelling1.java", 2, rule);
    }

    public void test2() throws Throwable {
        runTest("JUnitSpelling2.java", 2, rule);
    }

    public void test3() throws Throwable {
        runTest("JUnitSpelling3.java", 0, rule);
    }

    public void test4() throws Throwable {
        runTest("JUnitSpelling4.java", 0, rule);
    }
}
