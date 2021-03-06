package test.net.sourceforge.pmd.stat;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


/**
 * tests for the net.sourceforge.pmd.stat package
 *
 * @author Boris Gruschko ( boris at gruschko.org )
 */
@RunWith(Suite.class)
@SuiteClasses({MetricTest.class, StatisticalRuleTest.class})
public class StatTests {
}
