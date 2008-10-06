/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd.renderers;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Properties;

import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.Report;
import net.sourceforge.pmd.RuleViolation;

/**
 * Renderer to simple text format.
 */
public class TextRenderer extends OnTheFlyRenderer {

    public static final String NAME = "text";

    private boolean empty;

    public TextRenderer(Properties properties) {
	super(NAME, "Text format.", properties);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() throws IOException {
	empty = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renderFileViolations(Iterator<RuleViolation> violations) throws IOException {
	Writer writer = getWriter();
	StringBuffer buf = new StringBuffer();

	empty = !violations.hasNext();
	while (violations.hasNext()) {
	    buf.setLength(0);
	    RuleViolation rv = violations.next();
	    buf.append(PMD.EOL).append(rv.getFilename());
	    buf.append(':').append(Integer.toString(rv.getBeginLine()));
	    buf.append('\t').append(rv.getDescription());
	    writer.write(buf.toString());
	}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void end() throws IOException {
	Writer writer = getWriter();
	StringBuffer buf = new StringBuffer(500);
	if (!errors.isEmpty()) {
	    empty = false;

	    for (Report.ProcessingError error : errors) {
		buf.setLength(0);
		buf.append(PMD.EOL).append(error.getFile());
		buf.append("\t-\t").append(error.getMsg());
		writer.write(buf.toString());
	    }
	}

	for (Report.SuppressedViolation excluded : suppressed) {
	    buf.setLength(0);
	    buf.append(PMD.EOL);
	    buf.append(excluded.getRuleViolation().getRule().getName());
	    buf.append(" rule violation suppressed by ");
	    buf.append(excluded.suppressedByNOPMD() ? "//NOPMD" : "Annotation");
	    buf.append(" in ").append(excluded.getRuleViolation().getFilename());
	    writer.write(buf.toString());
	}

	if (empty) {
	    getWriter().write("No problems found!");
	}
    }

}
