package test.net.sourceforge.pmd.ast;

import java.io.StringReader;

import java.util.Set;
import java.util.HashSet;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import net.sourceforge.pmd.ast.*;

public class MethodDeclTest
    extends ParserTst
{
    public MethodDeclTest( String name ) {
	super( name );
    }

    public void testPublic() 
	throws Throwable
    {
	String access[] = { "public" };
	ASTMethodDeclaration amd = getMethodDecl( access );
	assertTrue( "Expecting method to be public.",
		    amd.isPublic() );
    }

    public void testPrivate() 
	throws Throwable
    {
	String access[] = { "private" };
	ASTMethodDeclaration amd = getMethodDecl( access );
	assertTrue( "Expecting method to be private.",
		    amd.isPrivate() );
    }

    public void testProtected() 
	throws Throwable
    {
	String access[] = { "protected" };
	ASTMethodDeclaration amd = getMethodDecl( access );
	assertTrue( "Expecting method to be protected.",
		    amd.isProtected() );
    }

    public void testFinal() 
	throws Throwable
    {
	String access[] = { "public", "final" };
	ASTMethodDeclaration amd = getMethodDecl( access );
	assertTrue( "Expecting method to be final.",
		    amd.isFinal() );
	assertTrue( "Expecting method to be public.",
		    amd.isPublic() );
    }

    public void testSynchronized() 
	throws Throwable
    {
	String access[] = { "public", "synchronized" };
	ASTMethodDeclaration amd = getMethodDecl( access );
	assertTrue( "Expecting method to be synchronized.",
		    amd.isSynchronized() );
	assertTrue( "Expecting method to be public.",
		    amd.isPublic() );
    }

    public void testAbstract() 
	throws Throwable
    {
	String access[] = { "public", "abstract" };
	ASTMethodDeclaration amd = getMethodDecl( access );
	assertTrue( "Expecting method to be abstract.",
		    amd.isAbstract() );
	assertTrue( "Expecting method to be public.",
		    amd.isPublic() );
    }

    public void testNative() 
	throws Throwable
    {
	String access[] = { "private", "native" };
	ASTMethodDeclaration amd = getMethodDecl( access );
	assertTrue( "Expecting method to be native.",
		    amd.isNative() );
	assertTrue( "Expecting method to be private.",
		    amd.isPrivate() );
    }

    public void testStrict() 
	throws Throwable
    {
	String access[] = { "public", "strictfp" };
	ASTMethodDeclaration amd = getMethodDecl( access );
	assertTrue( "Expecting method to be strict.",
		    amd.isStrict() );
	assertTrue( "Expecting method to be public.",
		    amd.isPublic() );
    }

    public ASTMethodDeclaration getMethodDecl( String access[] )
	throws Throwable
    {
	String javaCode =
	    "public class Test { ";
	for (int i = 0; i < access.length; i++) {
	    javaCode += access[i] + " ";
	} 

	javaCode +=
	    " void stuff() { } }";

	Set methods = getNodes( ASTMethodDeclaration.class,
				javaCode );

	assertEquals( "Wrong number of methods",
		      1, methods.size());

	Iterator i = methods.iterator();
	return (ASTMethodDeclaration) i.next();
    }
}
