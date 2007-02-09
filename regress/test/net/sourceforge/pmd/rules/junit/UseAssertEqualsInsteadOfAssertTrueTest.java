
 /**
  * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
  */
 package test.net.sourceforge.pmd.rules.junit;
 
 import org.junit.Before;
import org.junit.Test;

import net.sourceforge.pmd.Rule;
import test.net.sourceforge.pmd.testframework.SimpleAggregatorTst;
 
 public class UseAssertEqualsInsteadOfAssertTrueTest extends SimpleAggregatorTst {
     private Rule rule;
 
     @Before
     public void setUp() {
         rule = findRule("junit", "UseAssertEqualsInsteadOfAssertTrue");
     }
 
     @Test
     public void testAll() throws Throwable {
         runTests(rule);
     }

     public static junit.framework.Test suite() {
         return new junit.framework.JUnit4TestAdapter(UseAssertEqualsInsteadOfAssertTrueTest.class);
     }
 }
