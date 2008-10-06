/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd.renderers;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import net.sourceforge.pmd.Report;
import net.sourceforge.pmd.RuleViolation;

/**
 * Abstract base class for {@link Renderer} implementations which can produce
 * output incrementally for {@link RuleViolation}s as source files are
 * processed.  Such {@link Renderer}s are able to produce large reports with
 * significantly less working memory at any given time.  Variations in the
 * delivery of source file reports are reflected in the output of the
 * {@link Renderer}, so report output can be different between runs.
 * 
 * Only processing errors and suppressed violations are accumulated across all
 * files.  These are intended to be processed in the {@link #end()} method.
 */
public abstract class AbstractIncrementalRenderer extends AbstractRenderer {

    /**
     * Accumulated processing errors.
     */
    protected List<Report.ProcessingError> errors = new LinkedList<Report.ProcessingError>();

    /**
     * Accumulated suppressed violations.
     */
    protected List<Report.SuppressedViolation> suppressed = new LinkedList<Report.SuppressedViolation>();

    public AbstractIncrementalRenderer(String name, String description, Properties properties) {
	super(name, description, properties);
    }

    /**
     * {@inheritDoc}
     */
    public void render(Writer writer, Report report) throws IOException {
	setWriter(writer);
	start();
	renderFileReport(report);
	end();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renderFileReport(Report report) throws IOException {
	Iterator<RuleViolation> violations = report.iterator();
	if (violations.hasNext()) {
	    renderFileViolations(violations);
	    getWriter().flush();
	}

	for (Iterator<Report.ProcessingError> i = report.errors(); i.hasNext();) {
	    errors.add(i.next());
	}

	if (showSuppressedViolations) {
	    suppressed.addAll(report.getSuppressedRuleViolations());
	}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() throws IOException {
    }

    /**
     * Render a series of {@link RuleViolation}s.
     * @param violations The iterator of violations to render.
     * @throws IOException
     */
    public abstract void renderFileViolations(Iterator<RuleViolation> violations) throws IOException;

    /**
     * {@inheritDoc}
     */
    @Override
    public void end() throws IOException {
    }
}
