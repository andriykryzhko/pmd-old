package test.net.sourceforge.pmd.rules.strictexception;

import junit.framework.Test;
import junit.framework.TestSuite;


/**
 * tests for the net.sourceforge.pmd.rules.strictexception package
 *
 * @author Boris Gruschko ( boris at gruschko.org )
 * @version $Id$
 */
public class StrictExceptionRulesTests
{
  /**
   * test suite
   *
   * @return test suite
   */
  public static Test suite(  )
  {
    TestSuite suite =
      new TestSuite( "Test for test.net.sourceforge.pmd.rules.strictexception" );

    //$JUnit-BEGIN$
    suite.addTestSuite( AvoidCatchingThrowableRuleTest.class );
    suite.addTestSuite( ExceptionSignatureDeclarationRuleTest.class );
    suite.addTestSuite( ExceptionTypeCheckingRuleTest.class );

    //$JUnit-END$
    return suite;
  }
}


/*
 * $Log$
 * Revision 1.1  2003/09/29 14:32:32  tomcopeland
 * Committed regression test suites, thanks to Boris Gruschko
 *
 */
