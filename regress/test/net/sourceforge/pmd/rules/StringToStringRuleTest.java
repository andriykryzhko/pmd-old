package test.net.sourceforge.pmd.rules;

import net.sourceforge.pmd.cpd.CPD;
import net.sourceforge.pmd.rules.StringToStringRule;
import net.sourceforge.pmd.PMD;

public class StringToStringRuleTest extends SimpleAggregatorTst {

    public void testAll() {
       runTests(new TestDescriptor[] {
           new TestDescriptor(TEST1, "local var", 1, new StringToStringRule()),
           new TestDescriptor(TEST2, "parameter", 1, new StringToStringRule()),
           new TestDescriptor(TEST3, "field", 1, new StringToStringRule()),
           new TestDescriptor(TEST4, "primitive", 0, new StringToStringRule()),
           new TestDescriptor(TEST5, "multiple similar params", 0, new StringToStringRule()),
           new TestDescriptor(TEST6, "string array", 1, new StringToStringRule())
       });
    }

   private static final String TEST1 =
    "public class StringToString1 {" + PMD.EOL +
    " private String baz() {" + PMD.EOL +
    "  String bar = \"howdy\";" + PMD.EOL +
    "  return bar.toString();" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    private static final String TEST2 =
    "public class StringToString2 {" + PMD.EOL +
    " private String baz(String bar) {" + PMD.EOL +
    "  return bar.toString();" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    private static final String TEST3 =
    "public class StringToString3 {" + PMD.EOL +
    " private String baz;" + PMD.EOL +
    " private int getBaz() {" + PMD.EOL +
    "  return baz.toString();" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    private static final String TEST4 =
    "public class StringToString4 {" + PMD.EOL +
    " private int baz;" + PMD.EOL +
    " private int getBaz() {" + PMD.EOL +
    "  return baz;" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    private static final String TEST5 =
    "public class StringToString5 {" + PMD.EOL +
    " private String getBaz(String foo, StringBuffer buffer) {" + PMD.EOL +
    "  return buffer.toString();" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    private static final String TEST6 =
    "public class StringToString6 {" + PMD.EOL +
    " private String getBaz() {" + PMD.EOL +
    "  String[] foo = {\"hi\"};" + PMD.EOL +
    "  return foo[0].toString();" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

}
