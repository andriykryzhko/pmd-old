/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
*/
package test.net.sourceforge.pmd.rules.junit;

import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.rules.junit.JUnitAssertionsShouldIncludeMessage;
import test.net.sourceforge.pmd.testframework.SimpleAggregatorTst;
import test.net.sourceforge.pmd.testframework.TestDescriptor;

public class JUnitAssertionsShouldIncludeMessageTest extends SimpleAggregatorTst {

    public void testAll() {
       runTests(new TestDescriptor[] {
           new TestDescriptor(TEST1, "assertEquals ok", 0, new JUnitAssertionsShouldIncludeMessage()),
           new TestDescriptor(TEST2, "assertEquals bad", 1, new JUnitAssertionsShouldIncludeMessage()),
           new TestDescriptor(TEST3, "assertTrue ok", 0, new JUnitAssertionsShouldIncludeMessage()),
           new TestDescriptor(TEST4, "assertTrue bad", 1, new JUnitAssertionsShouldIncludeMessage()),
           new TestDescriptor(TEST5, "assertNull OK", 0, new JUnitAssertionsShouldIncludeMessage()),
           new TestDescriptor(TEST6, "assertNull bad", 1, new JUnitAssertionsShouldIncludeMessage()),
           new TestDescriptor(TEST7, "assertSame OK", 0, new JUnitAssertionsShouldIncludeMessage()),
           new TestDescriptor(TEST8, "assertSame badd", 1, new JUnitAssertionsShouldIncludeMessage()),
           new TestDescriptor(TEST9, "assertNotNull OK", 0, new JUnitAssertionsShouldIncludeMessage()),
           new TestDescriptor(TEST10, "assertNotNull bad", 1, new JUnitAssertionsShouldIncludeMessage()),
           new TestDescriptor(TEST11, "find that pesky bug", 0, new JUnitAssertionsShouldIncludeMessage()),
       });
    }

    private static final String TEST1 =
    "public class Foo {" + PMD.EOL +
    " public void test1() {" + PMD.EOL +
    "  assertEquals(\"1 == 1\", 1, 1);" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    private static final String TEST2 =
    "public class Foo {" + PMD.EOL +
    " public void test1() {" + PMD.EOL +
    "  assertEquals(1, 1);" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    private static final String TEST3 =
    "public class Foo {" + PMD.EOL +
    " public void test1() {" + PMD.EOL +
    "  assertTrue(\"foo\", \"foo\".equals(\"foo\"));" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    private static final String TEST4 =
    "public class Foo {" + PMD.EOL +
    " public void test1() {" + PMD.EOL +
    "  assertTrue(\"foo\".equals(\"foo\"));" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    private static final String TEST5 =
    "public class Foo {" + PMD.EOL +
    " public void test1() {" + PMD.EOL +
    "  assertNull(\"it's not null\", null);" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    private static final String TEST6 =
    "public class Foo {" + PMD.EOL +
    " public void test1() {" + PMD.EOL +
    "  assertNull(null);" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    private static final String TEST7 =
    "public class Foo {" + PMD.EOL +
    " public void test1() {" + PMD.EOL +
    "  assertSame(\"not same!\", null, null);" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    private static final String TEST8 =
    "public class Foo {" + PMD.EOL +
    " public void test1() {" + PMD.EOL +
    "  assertSame(null, null);" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    private static final String TEST9 =
    "public class Foo {" + PMD.EOL +
    " public void test1() {" + PMD.EOL +
    "  assertNotNull(\"foo\", null);" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    private static final String TEST10 =
    "public class Foo {" + PMD.EOL +
    " public void test1() {" + PMD.EOL +
    "  assertNotNull(null);" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    private static final String TEST11 =
    "public class Foo {" + PMD.EOL +
    " public void test1() {" + PMD.EOL +
    "  this.test1(\"foo\");" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

}