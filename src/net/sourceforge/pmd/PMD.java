/*
 * User: tom
 * Date: Jun 17, 2002
 * Time: 3:23:17 PM
 */
package net.sourceforge.pmd;

import net.sourceforge.pmd.ast.JavaParser;
import net.sourceforge.pmd.ast.ASTCompilationUnit;
import net.sourceforge.pmd.ast.JavaParserVisitor;
import net.sourceforge.pmd.ast.ParseException;
import net.sourceforge.pmd.renderers.Renderer;
import net.sourceforge.pmd.renderers.XMLRenderer;
import net.sourceforge.pmd.renderers.HTMLRenderer;
import net.sourceforge.pmd.swingui.PMDViewer;
import net.sourceforge.pmd.cpd.FileFinder;
import net.sourceforge.pmd.cpd.JavaFileOrDirectoryFilter;

import javax.swing.*;
import java.io.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

public class PMD {

    /**
     * @param reader - an InputStream to the Java code to analyse
     * @param ruleSet - the set of rules to process against the file
     * @param ctx - the context in which PMD is operating.  This contains the Renderer and whatnot
     */
    public void processFile(Reader reader, RuleSet ruleSet, RuleContext ctx) {
        try {
            JavaParser parser = new JavaParser(reader);
            ASTCompilationUnit c = parser.CompilationUnit();
            //c.dump("");
            List acus = new ArrayList();
            acus.add(c);
            ruleSet.apply(acus, ctx);
            reader.close();
        } catch (ParseException pe) {
            System.out.println("Error while parsing " + ctx.getSourceCodeFilename() + " at line " + pe.currentToken.beginLine + "; continuing...");
        } catch (Throwable t) {
            System.out.println("Error while parsing " +  ctx.getSourceCodeFilename() + "; "+ t.getMessage() + "; continuing...");
            t.printStackTrace();
        }
    }

    /**
     * @param fileContents - an InputStream to the Java code to analyse
     * @param ruleSet - the set of rules to process against the file
     * @param ctx - the context in which PMD is operating.  This contains the Report and whatnot
     */
    public void processFile(InputStream fileContents, RuleSet ruleSet, RuleContext ctx) {
        processFile(new InputStreamReader(fileContents), ruleSet, ctx);
    }

    public static void main(String[] args) {
        if (args[0].equals("-g")) {
            try {
                PMDViewer.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        if (args.length != 3) {
            throw new RuntimeException("Please pass in a java source code filename, a report format, and a rule set file name");
        }

        String inputFileName = args[0];
        String reportFormat = args[1];
        String ruleSetFilename = args[2];

        File inputFile = new File(inputFileName);
        if (!inputFile.exists()) {
            throw new RuntimeException("File " + inputFileName + " doesn't exist");
        }

        List files = null;
        if (!inputFile.isDirectory()) {
            files = new ArrayList();
            files.add(inputFile);
        } else {
            FileFinder finder = new FileFinder();
            files = finder.findFilesFrom(inputFile.getAbsolutePath(), new JavaFileOrDirectoryFilter(), true);
        }

        PMD pmd = new PMD();

        RuleContext ctx = new RuleContext();
        RuleSetFactory ruleSetFactory = new RuleSetFactory();
        RuleSet rules = ruleSetFactory.createRuleSet(pmd.getClass().getClassLoader().getResourceAsStream(ruleSetFilename));
        ctx.setReport(new Report());

        try {
            for (Iterator i = files.iterator(); i.hasNext();) {
                File file = (File)i.next();
                ctx.setSourceCodeFilename(file.getAbsolutePath());
                pmd.processFile(new FileInputStream(file), rules, ctx);
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }

        Renderer rend = null;
        if (reportFormat.equals("xml")) {
            rend = new XMLRenderer();
        } else {
            rend = new HTMLRenderer();
        }
        System.out.println(rend.render(ctx.getReport()));
    }
}
