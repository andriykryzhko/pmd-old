package net.sourceforge.pmd;

import net.sourceforge.pmd.ast.ASTCompilationUnit;
import net.sourceforge.pmd.ast.JavaParser;
import net.sourceforge.pmd.ast.ParseException;
import net.sourceforge.pmd.cpd.FileFinder;
import net.sourceforge.pmd.cpd.JavaLanguage;
import net.sourceforge.pmd.renderers.CSVRenderer;
import net.sourceforge.pmd.renderers.EmacsRenderer;
import net.sourceforge.pmd.renderers.HTMLRenderer;
import net.sourceforge.pmd.renderers.IDEAJRenderer;
import net.sourceforge.pmd.renderers.Renderer;
import net.sourceforge.pmd.renderers.TextRenderer;
import net.sourceforge.pmd.renderers.XMLRenderer;
import net.sourceforge.pmd.symboltable.SymbolFacade;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class PMD {

    public static final String EOL = System.getProperty("line.separator", "\n");

    /**
     * @param reader - a Reader to the Java code to analyse
     * @param ruleSet - the set of rules to process against the file
     * @param ctx - the context in which PMD is operating.  This contains the Renderer and whatnot
     */
    public void processFile(Reader reader, RuleSet ruleSet, RuleContext ctx) throws PMDException {
        try {
            JavaParser parser = new JavaParser(reader);
            ASTCompilationUnit c = parser.CompilationUnit();
            Thread.yield();
            SymbolFacade stb = new SymbolFacade();
            stb.initializeWith(c);
            List acus = new ArrayList();
            acus.add(c);
            ruleSet.apply(acus, ctx);
            reader.close();
        } catch (ParseException pe) {
            throw new PMDException("Error while parsing " + ctx.getSourceCodeFilename(), pe);
        } catch (Exception e) {
            throw new PMDException("Error while processing " + ctx.getSourceCodeFilename(), e);
        }
    }

    /**
     * @param fileContents - an InputStream to the Java code to analyse
     * @param ruleSet - the set of rules to process against the file
     * @param ctx - the context in which PMD is operating.  This contains the Report and whatnot
     */
    public void processFile(InputStream fileContents, RuleSet ruleSet, RuleContext ctx) throws PMDException {
        processFile(new InputStreamReader(fileContents), ruleSet, ctx);
    }

    public static void main(String[] args) {
        CommandLineOptions opts = new CommandLineOptions(args);

        List files;
        if (opts.containsCommaSeparatedFileList()) {
            files = collectFromCommaDelimitedString(opts.getInputFileName());
        } else {
            files = collectFilesFromOneName(opts.getInputFileName());
        }

        PMD pmd = new PMD();

        RuleContext ctx = new RuleContext();
        ctx.setReport(new Report());

        try {
            RuleSetFactory ruleSetFactory = new RuleSetFactory();
            RuleSet rules = ruleSetFactory.createRuleSet(opts.getRulesets());
            for (Iterator i = files.iterator(); i.hasNext();) {
                File file = (File) i.next();
                ctx.setSourceCodeFilename(glomName(opts.shortNamesEnabled(), opts.getInputFileName(), file));
                try {
                    pmd.processFile(new FileInputStream(file), rules, ctx);
                } catch (PMDException pmde) {
                    if (opts.debugEnabled()) {
                        pmde.getReason().printStackTrace();
                    }
                    ctx.getReport().addError(new Report.ProcessingError(pmde.getMessage(), glomName(opts.shortNamesEnabled(), opts.getInputFileName(), file)));
                }
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (RuleSetNotFoundException rsnfe) {
            rsnfe.printStackTrace();
        }

        Renderer renderer = null;
        if (opts.getReportFormat().equals("xml")) {
            renderer = new XMLRenderer();
        } else if (opts.getReportFormat().equals("ideaj")) {
            renderer = new IDEAJRenderer(args);
        } else if (opts.getReportFormat().equals("text")) {
            renderer = new TextRenderer();
        } else if (opts.getReportFormat().equals("emacs")) {
            renderer = new EmacsRenderer();
        } else if (opts.getReportFormat().equals("csv")) {
            renderer = new CSVRenderer();
        } else if (opts.getReportFormat().equals("html")) {
            renderer = new HTMLRenderer();
        } else if (!opts.getReportFormat().equals("")) {
            try {
                renderer = (Renderer)Class.forName(opts.getReportFormat()).newInstance();
            } catch (Exception e) {
                System.out.println("Unable to load format type of " + opts.getReportFormat());
                return;
            }
        }
        System.out.println(renderer.render(ctx.getReport()));
    }

    private static String glomName(boolean shortNames, String inputFileName, File file) {
        if (shortNames && inputFileName.indexOf(',') == -1 && (new File(inputFileName)).isDirectory()) {
            String name = file.getAbsolutePath().substring(inputFileName.length());
            if (name.startsWith(System.getProperty("file.separator"))) {
                name = name.substring(1);
            }
            return name;
        } else {
            return file.getAbsolutePath();
        }
    }

    private static List collectFilesFromOneName(String inputFileName) {
        return collect(inputFileName);
    }

    private static List collectFromCommaDelimitedString(String fileList) {
        List files = new ArrayList();
        for (StringTokenizer st = new StringTokenizer(fileList, ","); st.hasMoreTokens();) {
            files.addAll(collect(st.nextToken()));
        }
        return files;
    }

    private static List collect(String filename) {
        File inputFile = new File(filename);
        if (!inputFile.exists()) {
            throw new RuntimeException("File " + inputFile.getName() + " doesn't exist");
        }
        List files;
        if (!inputFile.isDirectory()) {
            files = new ArrayList();
            files.add(inputFile);
        } else {
            FileFinder finder = new FileFinder();
            files = finder.findFilesFrom(inputFile.getAbsolutePath(), new JavaLanguage.JavaFileOrDirectoryFilter(), true);
        }
        return files;
    }

}
