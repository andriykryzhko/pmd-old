/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd.lang.xpath;

import net.sourceforge.pmd.lang.Language;
import net.sourceforge.pmd.lang.LanguageVersion;
import net.sourceforge.pmd.lang.LanguageVersionHandler;

/**
 * This class serves as the means to perform XPath related static initialization.
 * For example, initializing custom Jaxen Functions.
 * Initialization should be performed before any XPath related operations are
 * performed.
 */
public class Initializer {

    /**
     * Perform all initialization.
     */
    public static void initialize() {
        // noop as initialization is done in static block below
    }

    static {
        initializeGlobal();
        initializeLanguages();
    }

    private static void initializeGlobal() {
        MatchesFunction.registerSelfInSimpleContext();
    }

    private static void initializeLanguages() {
        for (Language language : Language.values()) {
            for (LanguageVersion languageVersion : language.getVersions()) {
                LanguageVersionHandler languageVersionHandler = languageVersion.getLanguageVersionHandler();
                if (languageVersionHandler != null) {
                    languageVersionHandler.getXPathHandler().initialize();
                }
            }
        }
    }
}
