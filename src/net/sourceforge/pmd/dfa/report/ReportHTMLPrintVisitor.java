package net.sourceforge.pmd.dfa.report;

import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.RuleViolation;
import org.apache.tools.ant.BuildException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * + * @author raik
 * + *
 * + * Uses the generated result tree instead of the result list. The visitor
 * + * traverses the tree and creates several html files. The "package view" file
 * + * (index.html) displays an overview of packgages, classes and the number of
 * + * rule violations they contain. All the other html files represent a class
 * + * and show detailed information about the violations.
 * +
 */
public class ReportHTMLPrintVisitor extends ReportVisitor {

    private StringBuffer packageBuf = new StringBuffer();
    private StringBuffer classBuf = new StringBuffer();
    private int lenght;

// ----------------------------------------------------------------------------

    /**
     * Writes the buffer to file.
     */
    private void write(String filename, StringBuffer buf) {
        String fs = System.getProperty("file.separator");
        String baseDir = ".." + fs; // TODO output destination

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(baseDir + fs + filename)));
            bw.write(buf.toString(), 0, buf.length());
            bw.close();
        } catch (IOException ioe) {
            throw new BuildException(ioe.getMessage());
        }
    }

    /**
     * Generates a html table with violation information.
     */
    private String displayRuleViolation(RuleViolation vio) {
        String ret = "<table border=\"0\">";

        ret += "<tr><td><b>Rule:</b></td><td>" + vio.getRule().getName() + "</td></tr>";

        ret += "<tr><td><b>Description:</b></td><td>" + vio.getDescription() + "</td></tr>";

        if (vio.getVariableName().length() > 0) {
            ret += "<tr><td><b>Variable:</b></td><td>" + vio.getVariableName() + "</td></tr>";
        }

        if (vio.getLine2() > 0) {
            ret += "<tr><td><b>Line:</b></td><td>" + vio.getLine2() + " and " + vio.getLine() + "</td></tr>";
        } else {
            ret += "<tr><td><b>Line:</b></td><td>" + vio.getLine() + "</td></tr>";
        }

        ret += "</table>";
        return ret;
    }

    /**
     * The visit method (Visitor Pattern). There are 3 types of ReportNodes:
     * RuleViolation - contains a RuleViolation, Class - represents a class and
     * contains the name of the class, Package - represents a package and
     * contains the name(s) of the package.
     */
    public void visit(AbstractReportNode node) {

        /*
         * The first node of result tree.
         * */
        if (node.getParent() == null) {
            this.packageBuf.insert(0,
                    "<html>" +
                    " <head>" +
                    "   <title>PMD</title>" +
                    " </head>" +
                    " <body>" + PMD.EOL + "" +
                    "<h2>Package View</h2>" +
                    "<table border=\"1\" align=\"center\" cellspacing=\"0\" cellpadding=\"3\">" +
                    " <tr>" + PMD.EOL + "" +
                    "<th>Package</th>" +
                    "<th>Class</th>" +
                    "<th>#</th>" +
                    " </tr>" + PMD.EOL);

            this.lenght = this.packageBuf.length();
        }


        super.visit(node);


        if (node instanceof ViolationNode) {
            ViolationNode vnode = (ViolationNode) node;
            vnode.getParent().addNumberOfViolation(1);
            RuleViolation vio = vnode.getRuleViolation();
            classBuf.append("<tr>" +
                    " <td>" + vio.getMethodName() + "</td>" +
                    " <td>" + this.displayRuleViolation(vio) + "</td>" +
                    "</tr>");
        }
        if (node instanceof ClassNode) {
            ClassNode cnode = (ClassNode) node;
            String str = (String) cnode.getClassName();

            classBuf.insert(0,
                    "<html>" +
                    " <head>" +
                    "   <title>PMD - " + str + "</title>" +
                    " </head>" +
                    " <body>" + PMD.EOL + "" +
                    "<h2>Class View</h2>" +
                    "<h3 align=\"center\">Class: " + str + "</h3>" +
                    "<table border=\"\" align=\"center\" cellspacing=\"0\" cellpadding=\"3\">" +
                    " <tr>" + PMD.EOL + "" +
                    "<th>Method</th>" +
                    "<th>Violation</th>" +
                    " </tr>" + PMD.EOL);

            classBuf.append("</table>" +
                    " </body>" +
                    "</html>");


            this.write(str + ".html", classBuf);
            classBuf = new StringBuffer();


            this.packageBuf.insert(this.lenght,
                    "<tr>" +
                    " <td>-</td>" +
                    " <td><a href=\"" + str + ".html\">" + str + "</a></td>" +
                    " <td>" + node.getNumberOfViolations() + "</td>" +
                    "</tr>" + PMD.EOL);
            node.getParent().addNumberOfViolation(node.getNumberOfViolations());
        }
        if (node instanceof PackageNode) {
            PackageNode pnode = (PackageNode) node;
            String str = "";

            // rootNode
            if (node.getParent() == null) {
                str = "Gesamt";
            }
            // all the other nodes
            else {
                str = (String) pnode.getPackageName();
                node.getParent().addNumberOfViolation(node.getNumberOfViolations());
            }

            this.packageBuf.insert(this.lenght,
                    "<tr>" +
                    " <td><b>" + str + "</b></td>" +
                    " <td>-</td>" +
                    " <td>" + node.getNumberOfViolations() + "</td>" +
                    "</tr>" + PMD.EOL);
        }
/*
+    * The first node of result tree.
+    * */
        if (node.getParent() == null) {
            this.packageBuf.append("</table>" +
                    " </body>" +
                    "</html>");

            this.write("index.html", this.packageBuf);
        }
    }
}
