/*
 * User: tom
 * Date: Aug 6, 2002
 * Time: 9:05:19 AM
 */
package test.net.sourceforge.pmd.cpd;

import junit.framework.TestCase;
import net.sourceforge.pmd.cpd.JavaTokenizer;
import net.sourceforge.pmd.cpd.SourceCode;
import net.sourceforge.pmd.cpd.Tokenizer;
import net.sourceforge.pmd.cpd.Tokens;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class JavaTokensTokenizerTest extends TestCase {

    private static final String EOL = System.getProperty("line.separator", "\n");

    public void test1() throws Throwable {
        Tokenizer tokenizer = new JavaTokenizer();
        SourceCode sourceCode = new SourceCode("1");
        String data = "public class Foo {}";
        Tokens tokens = new Tokens();
        tokenizer.tokenize(sourceCode, tokens, new StringReader(data));
        assertEquals(6, tokens.size());
        assertEquals("public class Foo {}", sourceCode.getSlice(0, 0));
    }

    public void test2() throws Throwable {
        Tokenizer t = new JavaTokenizer();
        SourceCode sourceCode = new SourceCode("1");
        String data = "public class Foo {" + EOL + "public void bar() {}" + EOL + "public void buz() {}" + EOL + "}";
        Tokens tokens = new Tokens();
        t.tokenize(sourceCode, tokens, new StringReader(data));
        assertEquals("public class Foo {" + EOL + "public void bar() {}", sourceCode.getSlice(0, 1));
    }

    public void testDiscardSemicolons() throws Throwable {
        Tokenizer t = new JavaTokenizer();
        SourceCode sourceCode = new SourceCode("1");
        String data = "public class Foo {private int x;}";
        Tokens tokens = new Tokens();
        t.tokenize(sourceCode, tokens, new StringReader(data));
        assertEquals(9, tokens.size());
    }

    public void testDiscardImports() throws Throwable {
        Tokenizer t = new JavaTokenizer();
        SourceCode sourceCode = new SourceCode("1");
        String data = "import java.io.File;" + EOL + "public class Foo {}";
        Tokens tokens = new Tokens();
        t.tokenize(sourceCode, tokens, new StringReader(data));
        assertEquals(6, tokens.size());
    }

    public void testDiscardPkgStmts() throws Throwable {
        Tokenizer t = new JavaTokenizer();
        SourceCode sourceCode = new SourceCode("1");
        String data = "package foo.bar.baz;" + EOL + "public class Foo {}";
        Tokens tokens = new Tokens();
        t.tokenize(sourceCode, tokens, new StringReader(data));
        assertEquals(6, tokens.size());
    }
}


