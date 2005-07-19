/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd.renderers;

import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.Report;
import net.sourceforge.pmd.RuleViolation;
import net.sourceforge.pmd.util.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class XMLRenderer implements Renderer {

    public String render(Report report) {

        StringBuffer buf = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + PMD.EOL + createVersionAttr() + createTimestampAttr() + createTimeElapsedAttr(report) + ">" + PMD.EOL);
        String filename = null;

        // rule violations
        for (Iterator i = report.iterator(); i.hasNext();) {
            RuleViolation rv = (RuleViolation) i.next();
            if (!rv.getFilename().equals(filename)) { // New File
                if (filename != null) {// Not first file ?
                    buf.append("</file>"+PMD.EOL);
                }
                filename = rv.getFilename();
                buf.append("<file name=\"");
                StringUtil.appendXmlEscaped(buf, filename);
                buf.append("\">").append(PMD.EOL);
            }

            buf.append("<violation line=\"").append(rv.getLine()).append("\"");
            buf.append(" rule=\"");
            StringUtil.appendXmlEscaped(buf, rv.getRule().getName());
            buf.append("\"");
            buf.append(" ruleset=\"");
            StringUtil.appendXmlEscaped(buf, rv.getRule().getRuleSetName());
            buf.append("\"");
            maybeAdd("package", rv.getPackageName(), buf);
            maybeAdd("class", rv.getClassName(), buf);
            maybeAdd("method", rv.getMethodName(), buf);
            maybeAdd("externalInfoUrl", rv.getRule().getExternalInfoUrl(), buf);
            buf.append(" priority=\"");
            buf.append(rv.getRule().getPriority());
            buf.append("\">");
            buf.append(PMD.EOL);
            StringUtil.appendXmlEscaped(buf, rv.getDescription());

            buf.append(PMD.EOL);
            buf.append("</violation>");
            buf.append(PMD.EOL);
        }
        if (filename != null) { // Not first file ?
            buf.append("</file>"+PMD.EOL);
        }

        // errors
        for (Iterator i = report.errors(); i.hasNext();) {
            Report.ProcessingError pe = (Report.ProcessingError) i.next();
            buf.append("<error ").append("filename=\"");
            StringUtil.appendXmlEscaped(buf, pe.getFile());
            buf.append("\" msg=\"");
            StringUtil.appendXmlEscaped(buf, pe.getMsg());
            buf.append("\"/>").append(PMD.EOL);
        }

        buf.append("</pmd>");
        return buf.toString();
    }

    private void maybeAdd(String attr, String value, StringBuffer buf) {
        if (value != null && value.length() > 0) {
            buf.append(" " + attr +"=\"");
            StringUtil.appendXmlEscaped(buf, value);
            buf.append("\"");
        }
    }

    private String createVersionAttr() {
        return "<pmd version=\"" + PMD.VERSION + "\"";
    }

    private String createTimestampAttr() {
        return " timestamp=\"" + new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(new Date()) + "\"";
    }

    private String createTimeElapsedAttr(Report rpt) {
        Report.ReadableDuration d = new Report.ReadableDuration(rpt.getElapsedTimeInMillis());
        return " elapsedTime=\"" + d.getTime() + "\"";
    }

}
