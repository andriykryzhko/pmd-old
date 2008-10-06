/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package test.net.sourceforge.pmd.renderers;

import java.util.Properties;

import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.renderers.Renderer;
import net.sourceforge.pmd.renderers.TextPadRenderer;


public class TextPadRendererTest extends AbstractRendererTst{

    public Renderer getRenderer() {
        return new TextPadRenderer(new Properties());
    }

    public String getExpected() {
        return PMD.EOL + "n/a(1,  Foo):  msg";
    }

    public String getExpectedEmpty() {
        return "";
    }
    
    public String getExpectedMultiple() {
        return PMD.EOL + "n/a(1,  Foo):  msg" + PMD.EOL + "n/a(1,  Foo):  msg";
    }

    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(TextPadRendererTest.class);
    }
}









