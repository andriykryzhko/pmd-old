/*
 * User: tom
 * Date: Jun 28, 2002
 * Time: 1:56:19 PM
 */
package test.net.sourceforge.pmd.rules;

import net.sourceforge.pmd.rules.EmptyCatchBlockRule;

public class EmptyCatchBlockRuleTest extends RuleTst {

    public void testSimple() throws Throwable {
        runTest("EmptyCatchBlock1.java", 1, new EmptyCatchBlockRule());
    }

    public void testNotEmpty() throws Throwable {
        runTest("EmptyCatchBlock2.java", 0, new EmptyCatchBlockRule());
    }

    public void testNoCatchWithNestedCatchInFinally() throws Throwable {
        runTest("EmptyCatchBlock3.java", 1, new EmptyCatchBlockRule());
    }

    public void testMultipleCatchBlocks() throws Throwable {
        runTest("EmptyCatchBlock4.java", 2, new EmptyCatchBlockRule());
    }

    public void testEmptyTryAndFinally() throws Throwable {
        runTest("EmptyCatchBlock5.java", 0, new EmptyCatchBlockRule());
    }

}

