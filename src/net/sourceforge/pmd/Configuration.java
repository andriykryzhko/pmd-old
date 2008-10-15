package net.sourceforge.pmd;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import net.sourceforge.pmd.lang.Language;
import net.sourceforge.pmd.lang.LanguageVersion;
import net.sourceforge.pmd.lang.LanguageVersionDiscoverer;
import net.sourceforge.pmd.renderers.Renderer;
import net.sourceforge.pmd.renderers.RendererFactory;
import net.sourceforge.pmd.util.ClasspathClassLoader;

/**
 * This class contains the details for the runtime configuration of PMD.
 * There are several aspects to the configuration of PMD.
 * <p>
 * The aspects related to generic PMD behavior:
 * <ul>
 * 	<li>Suppress marker is used in source files to suppress a RuleViolation,
 *	    defaults to {@link PMD#SUPPRESS_MARKER}.
 *          {@link #getSuppressMarker()}</li>
 * 	<li>The number of threads to create when invoking on multiple files,
 *	    defaults one thread per available processor.
 *          {@link #getThreads()}</li>
 * 	<li>A ClassLoader to use when loading classes during Rule processing
 * 	    (e.g. during type resolution), defaults to ClassLoader of the
 * 	    Configuration class.
 *          {@link #getClassLoader()}</li>
 *	<li>A means to configure a ClassLoader using a prepended classpath
 *	    String, instead of directly setting it programmatically.
 *          {@link #prependClasspath(String)}</li>
 *	<li>A LanguageVersionDiscoverer instance, which defaults to using the
 *	    default LanguageVersion of each Language.  Means are provided to
 *	    change the LanguageVersion for each Language.
 *          {@link #getLanguageVersionDiscoverer()}</li>
 * </ul>
 * <p>
 * The aspects related to Rules and Source files are:
 * <ul>
 * 	<li>A comma separated list of RuleSets URIs.
 *          {@link #getRuleSets()}</li>
 * 	<li>A minimum priority threshold when loading Rules from RuleSets,
 * 	    defaults to {@link RulePriority#LOW}.
 *          {@link #getMinimumPriority()}</li>
 * 	<li>The character encoding of source files, defaults to the system default
 * 	    as returned by <code>System.getProperty("file.encoding")</code>.
 *          {@link #getSourceEncoding()}</li>
 *	<li>A comma separated list of input paths to process for source files.
 *	    This may include files, directories, archives (e.g. ZIP files), etc.
 *          {@link #getInputPaths()}</li>
 * </ul>
 * <p>
 * <ul>
 * 	<li>The renderer format to use for Reports.
 *          {@link #getReportFormat()}</li>
 * 	<li>The file to which the Report should render.
 *          {@link #getReportFile()}</li>
 *	<li>An indicator of whether to use File short names in Reports, defaults
 *	    to <code>false</code>.
 *          {@link #isReportShortNames()}</li>
 * 	<li>The initialization properties to use when creating a Renderer instance.
 *          {@link #getReportProperties()}</li>
 *	<li>An indicator of whether to show suppressed Rule violations in Reports.
 *          {@link #isShowSuppressedViolations()}</li>
 * </ul>
 * <p>
 * The aspects related to special PMD behavior are:
 * <ul>
 * 	<li>An indicator of whether PMD should log debug information.
 *          {@link #isDebug()}</li>
 * 	<li>An indicator of whether PMD should perform stress testing behaviors,
 *          such as randomizing the order of file processing.
 *          {@link #isStressTest()}</li>
 * 	<li>An indicator of whether PMD should log benchmarking information.
 *          {@link #isBenchmark()}</li>
 * </ul>
 */
public class Configuration {

    // General behavior options
    private String suppressMarker = PMD.SUPPRESS_MARKER;
    private int threads = Runtime.getRuntime().availableProcessors();
    private ClassLoader classLoader = getClass().getClassLoader();
    private LanguageVersionDiscoverer languageVersionDiscoverer = new LanguageVersionDiscoverer();

    // Rule and source file options
    private String ruleSets;
    private RulePriority minimumPriority = RulePriority.LOW;
    private String sourceEncoding = System.getProperty("file.encoding");
    private String inputPaths;

    // Reporting options
    private String reportFormat;
    private String reportFile;
    private boolean reportShortNames = false;
    private Properties reportProperties = new Properties();
    private boolean showSuppressedViolations = false;

    // Special behavior options
    private boolean debug;
    private boolean stressTest;
    private boolean benchmark;

    /**
     * Get the suppress marker.  This the source level marker used to indicate
     * a RuleViolation should be suppressed.
     * @return The suppress marker.
     */
    public String getSuppressMarker() {
	return suppressMarker;
    }

    /**
     * Set the suppress marker.
     * @param suppressMarker The suppress marker to use.
     */
    public void setSuppressMarker(String suppressMarker) {
	this.suppressMarker = suppressMarker;
    }

    /**
     * Get the number of threads to use when processing Rules.
     * @return The number of threads.
     */
    public int getThreads() {
	return threads;
    }

    /**
     * Set the number of threads to use when processing Rules.
     * @param threads The number of threads.
     */
    public void setThreads(int threads) {
	this.threads = threads;
    }

    /**
     * Get the ClassLoader being used by PMD when processing Rules.
     * @return The ClassLoader being used
     */
    public ClassLoader getClassLoader() {
	return classLoader;
    }

    /**
     * Set the ClassLoader being used by PMD when processing Rules.
     * Setting a value of <code>null</code> will cause the default
     * ClassLoader to be used.
     * @param classLoader The ClassLoader to use
     */
    public void setClassLoader(ClassLoader classLoader) {
	if (classLoader == null) {
	    classLoader = getClass().getClassLoader();
	}
	this.classLoader = classLoader;
    }

    /**
     * Prepend the specified classpath like string to the current ClassLoader
     * of the configuration.  If no ClassLoader is currently configured, the
     * ClassLoader used to load the {@link Configuration} class will be used as
     * the parent ClassLoader of the created ClassLoader.
     * <p>
     * If the classpath String looks like a URL to a file (i.e. starts with
     * <code>file://</code>) the file will be read with each line representing
     * an entry on the classpath.  
     * 
     * @param classpath The prepended classpath.
     * @see Configuration#setClassLoader(ClassLoader)
     * @see ClasspathClassLoader
     */
    public void prependClasspath(String classpath) throws IOException {
	if (classLoader == null) {
	    classLoader = Configuration.class.getClassLoader();
	}
	if (classpath != null) {
	    classLoader = new ClasspathClassLoader(classpath, classLoader);
	}
    }

    /**
     * Get the LanguageVersionDiscoverer, used to determine the LanguageVersion
     * of a source file.
     * @return The LanguageVersionDiscoverer.
     */
    public LanguageVersionDiscoverer getLanguageVersionDiscoverer() {
	return languageVersionDiscoverer;
    }

    /**
     * Set the given LanguageVersion as the current default for it's Language.
     *
     * @param languageVersion the LanguageVersion
     */
    public void setDefaultLanguageVersion(LanguageVersion languageVersion) {
	setDefaultLanguageVersions(Arrays.asList(languageVersion));
    }

    /**
     * Set the given LanguageVersions as the current default for their Languages.
     *
     * @param languageVersions The LanguageVersions.
     */
    public void setDefaultLanguageVersions(List<LanguageVersion> languageVersions) {
	for (LanguageVersion languageVersion : languageVersions) {
	    languageVersionDiscoverer.setDefaultLanguageVersion(languageVersion);
	}
    }

    /**
     * Get the LanguageVersion of the source file with given name. This depends on the fileName
     * extension, and the java version.
     * <p/>
     * For compatibility with older code that does not always pass in a correct filename,
     * unrecognized files are assumed to be java files.
     *
     * @param fileName Name of the file, can be absolute, or simple.
     * @return the LanguageVersion
     */
    // FUTURE Delete this? I can't think of a good reason to keep it around.  Failure to determine the LanguageVersion for a file should be a hard error, or simply cause the file to be skipped?
    public LanguageVersion getLanguageVersionOfFile(String fileName) {
	LanguageVersion languageVersion = languageVersionDiscoverer.getDefaultLanguageVersionForFile(fileName);
	if (languageVersion == null) {
	    // For compatibility with older code that does not always pass in
	    // a correct filename.
	    languageVersion = languageVersionDiscoverer.getDefaultLanguageVersion(Language.JAVA);
	}
	return languageVersion;
    }

    /**
     * Get the comma separated list of RuleSets URIs.
     * @return The RuleSet URIs.
     */
    public String getRuleSets() {
	return ruleSets;
    }

    /**
     * Set the command separated list of RuleSet URIs.
     * @param ruleSets
     */
    public void setRuleSets(String ruleSets) {
	this.ruleSets = ruleSets;
    }

    /**
     * Get the minimum priority threshold when loading Rules from RuleSets.
     * @return The minimum priority threshold.
     */
    public RulePriority getMinimumPriority() {
	return minimumPriority;
    }

    /**
     * Set the minimum priority threshold when loading Rules from RuleSets.
     * @param minimumPriority The minimum priority.
     */
    public void setMinimumPriority(RulePriority minimumPriority) {
	this.minimumPriority = minimumPriority;
    }

    /**
     * Get the character encoding of source files.
     * @return The character encoding.
     */
    public String getSourceEncoding() {
	return sourceEncoding;
    }

    /**
     * Set the character encoding of source files.
     * @param sourceEncoding The character encoding.
     */
    public void setSourceEncoding(String sourceEncoding) {
	this.sourceEncoding = sourceEncoding;
    }

    /**
     * Get the comma separated list of input paths to process for source files.
     * @return A comma separated list.
     */
    public String getInputPaths() {
	return inputPaths;
    }

    /**
     * Set the comma separated list of input paths to process for source files.
     * @param inputPaths The comma separated list.
     */
    public void setInputPaths(String inputPaths) {
	this.inputPaths = inputPaths;
    }

    /**
     * Get whether to use File short names in Reports.
     * @return <code>true</code> when using short names in reports.
     */
    public boolean isReportShortNames() {
	return reportShortNames;
    }

    /**
     * Set whether to use File short names in Reports.
     * @param reportShortNames <code>true</code> when using short names in reports.
     */
    public void setReportShortNames(boolean reportShortNames) {
	this.reportShortNames = reportShortNames;
    }

    /**
     * Create a Renderer instance based upon the configured reporting options.
     * @return A Renderer instance.
     */
    public Renderer createRenderer() {
	Renderer renderer = RendererFactory.createRenderer(reportFormat, this.reportProperties);
	renderer.setShowSuppressedViolations(this.showSuppressedViolations);
	return renderer;
    }

    /**
     * Get the report format.
     * @return The report format.
     */
    public String getReportFormat() {
	return reportFormat;
    }

    /**
     * Set the report format.  This should be a name of a Renderer.
     * @param reportFormat The report format.
     * 
     * @see Renderer
     */
    public void setReportFormat(String reportFormat) {
	this.reportFormat = reportFormat;
    }

    /**
     * Get the file to which the report should render.
     * @return The file to which to render.
     */
    public String getReportFile() {
	return reportFile;
    }

    /**
     * Set the file to which the report should render.
     * @param reportFile
     */
    public void setReportFile(String reportFile) {
	this.reportFile = reportFile;
    }

    /**
     * Get whether the report should show suppressed violations.
     * @return <code>true</code> if showing suppressed violations,
     * <code>false</code> otherwise.
     */
    public boolean isShowSuppressedViolations() {
	return showSuppressedViolations;
    }

    /**
     * Set whether the report should show suppressed violations.
     * @param showSuppressedViolations <code>true</code> if showing suppressed
     * violations, <code>false</code> otherwise.
     */
    public void setShowSuppressedViolations(boolean showSuppressedViolations) {
	this.showSuppressedViolations = showSuppressedViolations;
    }

    /**
     * Get the Report properties.  These are used to create the Renderer.
     * @return The report properties.
     */
    public Properties getReportProperties() {
	return reportProperties;
    }

    /**
     * Set the Report properties.  These are used to create the Renderer.
     * @param reportProperties The Report properties to set.
     */
    public void setReportProperties(Properties reportProperties) {
	this.reportProperties = reportProperties;
    }

    /**
     * Return the debug indicator.  If this value is <code>true</code>
     * then PMD will log debug information.
     * @return <code>true</code> if debug logging is enbaled, <code>false</code> otherwise.
     */
    public boolean isDebug() {
	return debug;
    }

    /**
     * Set the debug indicator.
     * @param debug The debug indicator to set.
     * @see #isDebug()
     */
    public void setDebug(boolean debug) {
	this.debug = debug;
    }

    /**
     * Return the stress test indicator.  If this value is <code>true</code>
     * then PMD will randomize the order of file processing to attempt to
     * shake out bugs.
     * @return <code>true</code> if stress test is enbaled, <code>false</code> otherwise.
     */
    public boolean isStressTest() {
	return stressTest;
    }

    /**
     * Set the stress test indicator.
     * @param stressTest The stree test indicator to set.
     * @see #isStressTest()
     */
    public void setStressTest(boolean stressTest) {
	this.stressTest = stressTest;
    }

    /**
     * Return the benchmark indicator.  If this value is <code>true</code>
     * then PMD will log benchmark information.
     * @return <code>true</code> if benchmark logging is enbaled, <code>false</code> otherwise.
     */
    public boolean isBenchmark() {
	return benchmark;
    }

    /**
     * Set the benchmark indicator.
     * @param benchmark The benchmark indicator to set.
     * @see #isBenchmark()
     */
    public void setBenchmark(boolean benchmark) {
	this.benchmark = benchmark;
    }
}