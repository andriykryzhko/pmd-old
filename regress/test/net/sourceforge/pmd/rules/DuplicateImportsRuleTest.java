/*
 * User: tom
 * Date: Jul 12, 2002
 * Time: 10:51:59 PM
 */
package test.net.sourceforge.pmd.rules;

import net.sourceforge.pmd.Report;
import net.sourceforge.pmd.RuleViolation;
import net.sourceforge.pmd.rules.DuplicateImportsRule;
import net.sourceforge.pmd.rules.UnusedLocalVariableRule;
import org.cougaar.util.pmd.DontCreateTimersRule;

public class DuplicateImportsRuleTest extends RuleTst {
    public DuplicateImportsRuleTest(String name) {
        super(name);
    }

    private DuplicateImportsRule rule;

    public void setUp() {
        rule = new DuplicateImportsRule();
        rule.setMessage("Avoid this stuff -> ''{0}''");
    }

    public void test1() throws Throwable {
        Report report = process("DuplicateImports.java", rule);
        assertEquals(1, report.size());
    }
    public void test2() throws Throwable {
        Report report = process("DuplicateImports2.java", rule);
        assertEquals(1, report.size());
    }
}
