/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
*/
package test.net.sourceforge.pmd.rules;

import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.Rule;
import net.sourceforge.pmd.RuleSetNotFoundException;
import test.net.sourceforge.pmd.testframework.SimpleAggregatorTst;
import test.net.sourceforge.pmd.testframework.TestDescriptor;

public class UnusedPrivateMethodRuleTest extends SimpleAggregatorTst {

    private Rule rule;

    public void setUp() throws RuleSetNotFoundException {
        rule = findRule("unusedcode", "UnusedPrivateMethod");
    }

    public void testAll() {
       runTests(new TestDescriptor[] {
           new TestDescriptor(TEST1, "private method called by public method", 0, rule),
           new TestDescriptor(TEST2, "simple unused private method", 1, rule),
           new TestDescriptor(TEST3, "anonymous inner class calls private method", 0, rule),
           new TestDescriptor(TEST4, "two private methods with same name but different parameters", 1, rule),
           new TestDescriptor(TEST5, "calling private method after instantiating new copy of myself", 0, rule),
           new TestDescriptor(TEST6, "calling private method using 'this' modifier", 0, rule),
           new TestDescriptor(TEST7, "simple unused private static method", 1, rule),
           new TestDescriptor(TEST8, "readResolve/writeReplace/etc are OK", 0, rule),
           new TestDescriptor(TEST9, "Private methods called only by themselves, BUG 1038229", 1, rule),
           //new TestDescriptor(TEST10, "private with same name as public, different method signature", 0, rule),
           //new TestDescriptor(BUG_1114754, "False +, BUG 1114754", 0, rule), //FIXME
       });
    }

    private static final String TEST1 =
    "public class Foo {" + PMD.EOL +
    " public void bar() {" + PMD.EOL +
    "  foo();" + PMD.EOL +
    " }" + PMD.EOL +
    " private void foo() {}" + PMD.EOL +
    "}";

    private static final String TEST2 =
    "public class Foo {" + PMD.EOL +
    " private void foo() {}" + PMD.EOL +
    "}";

    private static final String TEST3 =
    "public class Foo {" + PMD.EOL +
    " public void bar() {" + PMD.EOL +
    "  new Runnable() {" + PMD.EOL +
    "   public void run() {" + PMD.EOL +
    "    foo();" + PMD.EOL +
    "   }" + PMD.EOL +
    "  };" + PMD.EOL +
    " }" + PMD.EOL +
    "" + PMD.EOL +
    " private void foo() {}" + PMD.EOL +
    "}";

    private static final String TEST4 =
    "public class Foo {" + PMD.EOL +
    " private void foo() {}" + PMD.EOL +
    " private void foo(String baz) {}" + PMD.EOL +
    " public void bar() {" + PMD.EOL +
    "  foo();" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    private static final String TEST5 =
    "public class Foo {" + PMD.EOL +
    " private void foo(String[] args) {}" + PMD.EOL +
    " public static void main(String[] args) {" + PMD.EOL +
    "  Foo u = new Foo();" + PMD.EOL +
    "  u.foo(args); " + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    private static final String TEST6 =
    "public class Foo {" + PMD.EOL +
    " public void bar() {" + PMD.EOL +
    "  this.foo();" + PMD.EOL +
    " }" + PMD.EOL +
    " private void foo() {}" + PMD.EOL +
    "}";

    private static final String TEST7 =
    "public class Foo {" + PMD.EOL +
    " private static void foo() {}" + PMD.EOL +
    "}";

    private static final String TEST8 =
    "public class Foo {" + PMD.EOL +
    " private void readResolve() {}" + PMD.EOL +
    " private void writeReplace() {}" + PMD.EOL +
    " private void readObject() {}" + PMD.EOL +
    " private void writeObject() {}" + PMD.EOL +
    "}";

    private static final String TEST9 =
    "public class Foo {" + PMD.EOL +
    " private void bar() {" + PMD.EOL +
    "  bar(); " + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    private static final String TEST10 =
    "public class Foo {" + PMD.EOL +
    " public void bar(int x) {" + PMD.EOL +
    "  bar(); " + PMD.EOL +
    " }" + PMD.EOL +
    " private void bar() {}" + PMD.EOL +
    "}";

    private static final String BUG_1114754 =
        "public class Foo {" + PMD.EOL +
        "   public void methodFlagged(Object[] arrayObj) {" + PMD.EOL + 
        "       for(int i=0; i<arrayObj.length; i++) {" + PMD.EOL + 
        "           methodFlagged(arrayObj[i]);" + PMD.EOL + 
        "       }" + PMD.EOL + 
        "   }" + PMD.EOL + 
        "   private void methodFlagged(Object a) {" + PMD.EOL + 
        "       // bla bla bla" + PMD.EOL + 
        "       a.toString();" + PMD.EOL + 
        "   }" + PMD.EOL + 
        "}";

}
