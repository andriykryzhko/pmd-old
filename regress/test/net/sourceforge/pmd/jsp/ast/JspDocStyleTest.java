package test.net.sourceforge.pmd.jsp.ast;

import java.util.Set;

import net.sourceforge.pmd.jsp.ast.ASTAttribute;
import net.sourceforge.pmd.jsp.ast.ASTCData;
import net.sourceforge.pmd.jsp.ast.ASTCommentTag;
import net.sourceforge.pmd.jsp.ast.ASTDoctypeDeclaration;
import net.sourceforge.pmd.jsp.ast.ASTDoctypeExternalId;
import net.sourceforge.pmd.jsp.ast.ASTElement;
import net.sourceforge.pmd.ast.ParseException;

/**
 * Test parsing of a JSP in document style, by checking the generated AST.
 * 
 * @author pieter_van_raemdonck - Application Engineers NV/SA - www.ae.be
 * 
 */
public class JspDocStyleTest extends AbstractJspNodesTst {

	/**
	 * Smoke test for JSP parser.
	 * 
	 * @throws Throwable
	 */
	public void testSimplestJsp() throws Throwable {
		assertNumberOfNodes(ASTElement.class, TEST_SIMPLEST_HTML, 1);
	}

	/**
	 * Test the information on a Element and Attribute.
	 * 
	 * @throws Throwable
	 */
	public void testElementAttributeAndNamespace() throws Throwable {
		Set nodes = getNodes(null, TEST_ELEMENT_AND_NAMESPACE);

		Set elementNodes = getNodesOfType(ASTElement.class, nodes);
		assertEquals("One element node expected!", 1, elementNodes.size());
		ASTElement element = (ASTElement) elementNodes.iterator().next();
		assertEquals("Correct name expected!", "h:html", element.getName());
		assertEquals("Has namespace prefix!", true, element.isHasNamespacePrefix());
		assertEquals("Element is empty!", true, element.isEmpty());
		assertEquals("Correct namespace prefix of element expected!", "h", element
				.getNamespacePrefix());
		assertEquals("Correct local name of element expected!", "html", element
				.getLocalName());

		Set attributeNodes = getNodesOfType(ASTAttribute.class, nodes);
		assertEquals("One attribute node expected!", 1, attributeNodes.size());
		ASTAttribute attribute = (ASTAttribute) attributeNodes.iterator().next();
		assertEquals("Correct name expected!", "MyNsPrefix:MyAttr", attribute
				.getName());
		assertEquals("Has namespace prefix!", true, attribute.isHasNamespacePrefix());
		assertEquals("Correct namespace prefix of element expected!", "MyNsPrefix",
				attribute.getNamespacePrefix());
		assertEquals("Correct local name of element expected!", "MyAttr", attribute
				.getLocalName());

	}

	/**
	 * Test correct parsing of CDATA.
	 */
	public void testCData() throws ParseException {
		Set cdataNodes = getNodes(ASTCData.class, TEST_CDATA);

		assertEquals("One CDATA node expected!", 1, cdataNodes.size());
		ASTCData cdata = (ASTCData) cdataNodes.iterator().next();
		assertEquals("Content incorrectly parsed!", " some <cdata> ]] ]> ", cdata.getImage());
	}

	/**
	 * Test parsing of Doctype declaration.
	 */
	public void testDoctype() throws ParseException {
		Set nodes = getNodes(null, TEST_DOCTYPE);

		Set docTypeDeclarations = getNodesOfType(ASTDoctypeDeclaration.class, nodes);
		assertEquals("One doctype declaration expected!", 1, docTypeDeclarations
				.size());
		ASTDoctypeDeclaration docTypeDecl = (ASTDoctypeDeclaration) docTypeDeclarations
				.iterator().next();
		assertEquals("Correct doctype-name expected!", "html", docTypeDecl.getName());
		
		Set externalIds = getNodesOfType(ASTDoctypeExternalId.class, nodes);
		assertEquals("One doctype external id expected!", 1, externalIds
				.size());
		ASTDoctypeExternalId externalId = (ASTDoctypeExternalId) externalIds
				.iterator().next();
		assertEquals("Correct external public id expected!", "-//W3C//DTD XHTML 1.1//EN", 
				externalId.getPublicId());
		assertEquals("Correct external uri expected!", "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd",
				externalId.getUri());
		
	}
	
	/**
	 * Test parsing of a XML comment.
	 *
	 */
	public void testComment() throws ParseException {
		Set comments = getNodes(ASTCommentTag.class, TEST_COMMENT);
		assertEquals("One comment expected!", 1, comments.size());
		ASTCommentTag comment = (ASTCommentTag) comments.iterator().next();
		assertEquals("Correct comment content expected!", "comment", comment.getImage());
	}

	private static final String TEST_SIMPLEST_HTML = "<html/>";

	private static final String TEST_ELEMENT_AND_NAMESPACE = "<h:html MyNsPrefix:MyAttr='MyValue'/>";

	private static final String TEST_CDATA = "<html><![CDATA[ some <cdata> ]] ]> ]]></html>";

	private static final String TEST_DOCTYPE = "<?xml version=\"1.0\" standalone='yes'?>\n"
			+ "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" "
			+ "\"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">\n"
			+ "<greeting>Hello, world!</greeting>";
	
	private static final String TEST_COMMENT = "<html><!-- comment --></html>";
}
