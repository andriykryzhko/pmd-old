/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
*/
package test.net.sourceforge.pmd.rules;

import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.Rule;
import test.net.sourceforge.pmd.testframework.SimpleAggregatorTst;
import test.net.sourceforge.pmd.testframework.TestDescriptor;

public class ConstructorCallsOverridableMethodTest extends SimpleAggregatorTst {
	private Rule rule = null;
	public void setUp() {
		rule = findRule("rulesets/design.xml", "ConstructorCallsOverridableMethod");
	}
    public void testAll() {
       runTests(new TestDescriptor[] {
           new TestDescriptor(TEST1, "calling public method from constructor", 1, rule),
           new TestDescriptor(TEST2, "calling protected method from constructor", 1, rule),
           new TestDescriptor(TEST3, "calling package private method from constructor", 1, rule),
           new TestDescriptor(TEST4, "calling private method, ok", 0, rule),
           new TestDescriptor(TEST5, "overloaded constructors, calling public method", 1, rule),
           new TestDescriptor(TEST6, "calling method on literal bug", 0, rule),
           new TestDescriptor(TEST7, "method in anonymous inner class is ok", 0, rule),
           new TestDescriptor(TEST8, "bug report 975407", 0, rule),
		   new TestDescriptor(BUG_994400, "bug report 994400, reports violation on abstract method", 0, rule),
       });
    }

    private static final String TEST1 =
    "public class Foo {" + PMD.EOL +
    " public Foo() {" + PMD.EOL +
    "  bar();" + PMD.EOL +
    " }" + PMD.EOL +
    " public void bar() {}" + PMD.EOL +
    "}";

    private static final String TEST2 =
    "public class Foo {" + PMD.EOL +
    " public Foo() {" + PMD.EOL +
    "  bar();" + PMD.EOL +
    " }" + PMD.EOL +
    " protected void bar() {}" + PMD.EOL +
    "}";

    private static final String TEST3 =
    "public class Foo {" + PMD.EOL +
    " public Foo() {" + PMD.EOL +
    "  bar();" + PMD.EOL +
    " }" + PMD.EOL +
    " void bar() {}" + PMD.EOL +
    "}";

    private static final String TEST4 =
    "public class Foo {" + PMD.EOL +
    " public Foo() {" + PMD.EOL +
    "  bar();" + PMD.EOL +
    " }" + PMD.EOL +
    " private void bar() {}" + PMD.EOL +
    "}";

    private static final String TEST5 =
    "public class Foo {" + PMD.EOL +
    " public Foo() {" + PMD.EOL +
    "  this(\"Bar\");" + PMD.EOL +
    " }" + PMD.EOL +
    " private Foo(String bar) {" + PMD.EOL +
    "  bar();" + PMD.EOL +
    " }" + PMD.EOL +
    " public void bar() {}" + PMD.EOL +
    "}";

    private static final String TEST6 =
    "public class Foo {" + PMD.EOL +
    " public Foo(String s) {" + PMD.EOL +
    "  \"foo\".equals(s);" + PMD.EOL +
    " }" + PMD.EOL +
    " public void equals(String bar) {}" + PMD.EOL +
    "}";

    private static final String TEST7 =
    "public class Foo {" + PMD.EOL +
    " public Foo(String s) {" + PMD.EOL +
    "  addActionListener(new ActionListener() {" + PMD.EOL +
    "   public void actionPerformed(ActionEvent e) {bar();}" + PMD.EOL +
    "  });" + PMD.EOL +
    " }" + PMD.EOL +
    " public void bar() {}" + PMD.EOL +
    "}";

    private static final String TEST8 =
    "public class Foo {" + PMD.EOL +
    " public Foo() {" + PMD.EOL +
    "  bar();" + PMD.EOL +
    " }" + PMD.EOL +
    " private void bar() {}" + PMD.EOL +
    "}";

    private static final String BUG_994400 =
        "public class Foo {" + PMD.EOL +
        " public Foo() {" + PMD.EOL +
        "  bar();" + PMD.EOL +
        " }" + PMD.EOL +
        " abstract void bar() {}" + PMD.EOL +
        "}";
    
}
