package net.sourceforge.pmd.renderers;

import net.sourceforge.pmd.Report;
import net.sourceforge.pmd.RuleViolation;

import java.util.Iterator;

public class TextRenderer implements Renderer {

    protected String EOL = System.getProperty("line.separator", "\n");

    public String render(Report report) {
        StringBuffer buf = new StringBuffer();
        for (Iterator i = report.iterator(); i.hasNext();) {
            RuleViolation rv = (RuleViolation) i.next();
            buf.append(EOL + rv.getFilename());
            buf.append("\t" + Integer.toString(rv.getLine()));
            buf.append("\t" + rv.getDescription());
        }
        for (Iterator i = report.errors(); i.hasNext();) {
            Report.ProcessingError rv = (Report.ProcessingError) i.next();
            buf.append(EOL + rv.getFile());
            buf.append("\t-");
            buf.append("\t" + rv.getMsg());
        }
        return buf.toString();
    }
}
