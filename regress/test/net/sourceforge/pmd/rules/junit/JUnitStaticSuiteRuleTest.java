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
package test.net.sourceforge.pmd.rules.junit;

import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.Rule;
import net.sourceforge.pmd.rules.XPathRule;
import test.net.sourceforge.pmd.testframework.RuleTst;

public class JUnitStaticSuiteRuleTest extends RuleTst {

    private static final String TEST1 =
    "public class JUnitStaticSuite1 {" + PMD.EOL +
    " public TestSuite suite() {}" + PMD.EOL +
    "}";

    private static final String TEST2 =
    "public class JUnitStaticSuite2 {" + PMD.EOL +
    " public static TestSuite suite() {}" + PMD.EOL +
    "}";

    private static final String TEST3 =
    "public class JUnitStaticSuite3 {" + PMD.EOL +
    " private static TestSuite suite() {}" + PMD.EOL +
    "}";

    private Rule rule;

    public void setUp() {
        rule = new XPathRule();
        rule.addProperty("xpath", "//MethodDeclaration[not(@Static='true') or not(@Public='true')][MethodDeclarator/@Image='suite']");
    }
    public void testNonstatic() throws Throwable {
        runTestFromString(TEST1, 1, rule);
    }
    public void testGoodOK() throws Throwable {
        runTestFromString(TEST2, 0, rule);
    }
    public void testPrivateSuite() throws Throwable {
        runTestFromString(TEST3, 1, rule);
    }
}
