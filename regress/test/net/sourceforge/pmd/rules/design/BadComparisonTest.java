
 package test.net.sourceforge.pmd.rules.design;
 
 import net.sourceforge.pmd.Rule;

import org.junit.Before;
import org.junit.Test;

import test.net.sourceforge.pmd.testframework.SimpleAggregatorTst;
 
 public class BadComparisonTest extends SimpleAggregatorTst {
 
     private Rule rule;

     @Before
     public void setUp() {
         rule = findRule("design", "BadComparison");
     }
 
     @Test
     public void testAll() {
         runTests(rule);
     }

     public static junit.framework.Test suite() {
         return new junit.framework.JUnit4TestAdapter(BadComparisonTest.class);
     }
 }
