/*
 * User: tom
 * Date: Jun 28, 2002
 * Time: 1:43:05 PM
 */
package test.net.sourceforge.pmd.rules;

import junit.framework.TestCase;
import net.sourceforge.pmd.*;
import net.sourceforge.pmd.reports.Report;
import net.sourceforge.pmd.rules.UnusedLocalVariableRule;
import org.cougaar.util.pmd.SystemPropsRule;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.io.FileNotFoundException;

public class UnusedLocalVariableTest extends RuleTst {
    public UnusedLocalVariableTest(String name) {
        super(name);
    }

    public void testUnusedLocal1() throws Throwable {
        Report report = process("Unused1.java", new UnusedLocalVariableRule());
        assertEquals(1, report.size());
        assertEquals(new UnusedLocalVariableRule(), ((RuleViolation)report.iterator().next()).getRule());
    }

    public void testUnusedLocal2() throws Throwable {
        Report report = process("Unused2.java", new UnusedLocalVariableRule());
        assertEquals(1, report.size());
        assertEquals(new UnusedLocalVariableRule(), ((RuleViolation)report.iterator().next()).getRule());
    }

    public void testUnusedLocal3() throws Throwable {
        Report report = process("Unused3.java", new UnusedLocalVariableRule());
        assertEquals(1, report.size());
        assertEquals(new UnusedLocalVariableRule(), ((RuleViolation)report.iterator().next()).getRule());
    }

    public void testUnusedLocal4() throws Throwable {
        Report report = process("Unused4.java", new UnusedLocalVariableRule());
        assertTrue(report.isEmpty());
    }

    public void testUnusedLocal5() throws Throwable {
        Report report = process("Unused5.java", new UnusedLocalVariableRule());
        assertEquals(1, report.size());
        assertEquals(new UnusedLocalVariableRule(), ((RuleViolation)report.iterator().next()).getRule());
    }

    public void testUnusedLocal6() throws Throwable {
        Report report = process("Unused6.java", new UnusedLocalVariableRule());
        assertTrue(report.isEmpty());
    }

    public void testUnusedLocal7() throws Throwable {
        Report report = process("Unused7.java", new UnusedLocalVariableRule());
        assertTrue(report.isEmpty());
    }

    public void testUnusedLocal8() throws Throwable {
        Report report = process("Unused8.java", new UnusedLocalVariableRule());
        assertTrue(report.isEmpty());
    }

    public void testUnusedLocal9() throws Throwable {
        Report report = process("Unused9.java", new UnusedLocalVariableRule());
        assertEquals(2, report.size());
        Iterator i =  report.iterator();
        assertEquals(new UnusedLocalVariableRule(), ((RuleViolation)i.next()).getRule());
        assertEquals(new UnusedLocalVariableRule(), ((RuleViolation)i.next()).getRule());
    }

    public void testUnusedLocal10() throws Throwable {
        Report report = process("Unused10.java", new UnusedLocalVariableRule());
        assertTrue(report.isEmpty());
    }

}
