package test.net.sourceforge.pmd.renderers;

import junit.framework.TestCase;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

import test.net.sourceforge.pmd.MockRule;

import net.sourceforge.pmd.renderers.*;
import net.sourceforge.pmd.*;

public class XMLRendererTest
    extends TestCase
{
    private XMLRenderer IUT = null;
    private MockRule RULE1 = new MockRule( "RULE1", "RULE1" );
    private MockRule RULE2 = new MockRule( "RULE2", "RULE2" );

    public XMLRendererTest( String name ) {
	super( name );
    }

    public void setUp() {
	IUT = new XMLRenderer();
    }

    public void testEmptyReport() 
	throws Throwable
    {
	String rendered = IUT.render( new Report() );
	assertTrue("Expected empty PMD tag.",
		   rendered.indexOf("violation") < 0);
    }

    public void testSingleReport() 
	throws Throwable
    {
	Report report = new Report();
	report.addRuleViolation( new RuleViolation( RULE1, 
				 1, "Rule1",
				 "testSingleReport") );

	String rendered = IUT.render( report );

	// <?xml version="1.0"?>
	// <pmd>
	//   <file name="testSingleReport">
	//     <violation line="1" rule="RULE1">
        // Rule1
        //     </violation>
        //   </file>
        // </pmd>

	List expectedStrings = new ArrayList();
	expectedStrings.add("<pmd>");
	expectedStrings.add("<file name=\"testSingleReport\">" );
	expectedStrings.add("<violation line=\"1\" rule=\"RULE1\">");
	expectedStrings.add("Rule1");
	expectedStrings.add("</violation>");
	expectedStrings.add("</file>");
	expectedStrings.add("</pmd>");

	verifyPositions( rendered, expectedStrings );
    }

    public void testDoubleReport() 
	throws Throwable
    {
	Report report = new Report();
	report.addRuleViolation( new RuleViolation( RULE1, 
				 1, "Rule1",
				 "testDoubleReport") );

	report.addRuleViolation( new RuleViolation( RULE2, 
				 2, "Rule2",
				 "testDoubleReport") );

	String rendered = IUT.render( report );

	// <?xml version="1.0"?>
	// <pmd>
	//   <file name="testSingleReport">
	//     <violation line="1" rule="RULE1">
        // Rule1
        //     </violation>
	//     <violation line="2" rule="RULE2">
	// Rule2
	//     </violation>
        //   </file>
        // </pmd>

	List expectedStrings = new ArrayList();
	expectedStrings.add("<pmd>");
	expectedStrings.add("<file name=\"testDoubleReport\">" );
	expectedStrings.add("<violation line=\"1\" rule=\"RULE1\">");
	expectedStrings.add("Rule1");
	expectedStrings.add("</violation>");
	expectedStrings.add("<violation line=\"2\" rule=\"RULE2\">");
	expectedStrings.add("Rule2");
	expectedStrings.add("</violation>");
	expectedStrings.add("</file>");
	expectedStrings.add("</pmd>");

	verifyPositions( rendered, expectedStrings );
    }

    public void testTwoFiles() 
	throws Throwable
    {
	Report report = new Report();
	report.addRuleViolation( new RuleViolation( RULE1, 
				 1, "Rule1",
				 "testTwoFiles_0") );

	report.addRuleViolation( new RuleViolation( RULE1, 
				 1, "Rule1",
				 "testTwoFiles_1") );

	String rendered = IUT.render( report );

	// <?xml version="1.0"?>
	// <pmd>
	//   <file name="testTwoFiles_0">
	//     <violation line="1" rule="RULE1">
        // Rule1
        //     </violation>
	//   </file>
	//   <file name="testTwoFiles_1">
	//     <violation line="1" rule="RULE1">
	// Rule1
	//     </violation>
        //   </file>
        // </pmd>

	List expectedStrings = new ArrayList();
	expectedStrings.add("<pmd>");
	expectedStrings.add("<file name=\"testTwoFiles_0\">" );
	expectedStrings.add("<violation line=\"1\" rule=\"RULE1\">");
	expectedStrings.add("Rule1");
	expectedStrings.add("</violation>");
	expectedStrings.add("</file>");
	expectedStrings.add("<file name=\"testTwoFiles_1\">" );
	expectedStrings.add("<violation line=\"1\" rule=\"RULE1\">");
	expectedStrings.add("Rule1");
	expectedStrings.add("</violation>");
	expectedStrings.add("</file>");
	expectedStrings.add("</pmd>");

	verifyPositions( rendered, expectedStrings );
    }

    public void testUnorderedFiles() 
	throws Throwable
    {
	Report report = new Report();
	report.addRuleViolation( new RuleViolation( RULE1, 
				 1, "Rule1",
				 "testTwoFiles_0") );

	report.addRuleViolation( new RuleViolation( RULE1, 
				 1, "Rule1",
				 "testTwoFiles_1") );

	report.addRuleViolation( new RuleViolation( RULE2, 
				 2, "Rule2",
				 "testTwoFiles_0") );

	String rendered = IUT.render( report );

	// <?xml version="1.0"?>
	// <pmd>
	//   <file name="testTwoFiles_0">
	//     <violation line="1" rule="RULE1">
        // Rule1
        //     </violation>
	//   </file>
	//   <file name="testTwoFiles_1">
	//     <violation line="1" rule="RULE1">
	// Rule1
	//     </violation>
        //   </file>
        // </pmd>

	System.err.println( rendered );

	List expectedStrings = new ArrayList();
	expectedStrings.add("<pmd>");
	expectedStrings.add("<file name=\"testTwoFiles_0\">" );
	expectedStrings.add("<violation line=\"1\" rule=\"RULE1\">");
	expectedStrings.add("Rule1");
	expectedStrings.add("</violation>");
	expectedStrings.add("<violation line=\"2\" rule=\"RULE2\">");
	expectedStrings.add("Rule2");
	expectedStrings.add("</violation>");
	expectedStrings.add("</file>");
	expectedStrings.add("<file name=\"testTwoFiles_1\">" );
	expectedStrings.add("<violation line=\"1\" rule=\"RULE1\">");
	expectedStrings.add("Rule1");
	expectedStrings.add("</violation>");
	expectedStrings.add("</file>");
	expectedStrings.add("</pmd>");

	verifyPositions( rendered, expectedStrings );
    }

    public void verifyPositions( String rendered, 
				 List strings ) {
	Iterator i = strings.iterator();
	int currPos = 0;
	String lastString = "<?xml version=\"1.0\"?>";

	while (i.hasNext()) {
	    String str = (String) i.next();

	    int strPos = rendered.indexOf( str, currPos );
	    assertTrue("Expecting: " + str + " after " + lastString,
		       strPos > currPos );
	    currPos = strPos;
	    lastString = str;
	}
    }
}
