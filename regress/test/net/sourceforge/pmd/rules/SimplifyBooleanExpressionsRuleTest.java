/**
 * <copyright>
 *  Copyright 1997-2002 InfoEther, LLC
 *  under sponsorship of the Defense Advanced Research Projects Agency
(DARPA).
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the Cougaar Open Source License as published
by
 *  DARPA on the Cougaar Open Source Website (www.cougaar.org).
 *
 *  THE COUGAAR SOFTWARE AND ANY DERIVATIVE SUPPLIED BY LICENSOR IS
 *  PROVIDED 'AS IS' WITHOUT WARRANTIES OF ANY KIND, WHETHER EXPRESS OR
 *  IMPLIED, INCLUDING (BUT NOT LIMITED TO) ALL IMPLIED WARRANTIES OF
 *  MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE, AND WITHOUT
 *  ANY WARRANTIES AS TO NON-INFRINGEMENT.  IN NO EVENT SHALL COPYRIGHT
 *  HOLDER BE LIABLE FOR ANY DIRECT, SPECIAL, INDIRECT OR CONSEQUENTIAL
 *  DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE OF DATA OR PROFITS,
 *  TORTIOUS CONDUCT, ARISING OUT OF OR IN CONNECTION WITH THE USE OR
 *  PERFORMANCE OF THE COUGAAR SOFTWARE.
 * </copyright>
 */
package test.net.sourceforge.pmd.rules;

import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.Rule;
import net.sourceforge.pmd.rules.XPathRule;
import test.net.sourceforge.pmd.testframework.RuleTst;

public class SimplifyBooleanExpressionsRuleTest extends RuleTst {
    private static final String TEST1 =
    "public class SimplifyBooleanExpressions1 {" + PMD.EOL +
    " private boolean foo = (isFoo() == true);" + PMD.EOL +
    " public boolean isFoo() {return foo;}" + PMD.EOL +
    "}";

    private static final String TEST2 =
    "public class SimplifyBooleanExpressions2 {" + PMD.EOL +
    " public void foo() {" + PMD.EOL +
    "  boolean bar = (new String().length() >2) == false;" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    private static final String TEST3 =
    "public class SimplifyBooleanExpressions3 {" + PMD.EOL +
    " boolean bar = true;" + PMD.EOL +
    "}";

    private Rule rule;

    public void setUp() {
        rule = new XPathRule();
        rule.addProperty("xpath", "//Expression/ConditionalAndExpression/InstanceOfExpression[position()>1]/PrimaryExpression/PrimaryPrefix/Literal/BooleanLiteral");
    }

    public void testInFieldAssignment() throws Throwable {
        runTestFromString(TEST1, 1, rule);
    }
    public void testInMethodBody() throws Throwable {
        runTestFromString(TEST2, 1, rule);
    }
    public void testOK() throws Throwable {
        runTestFromString(TEST3, 0, rule);
    }
}
