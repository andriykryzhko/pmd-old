package net.sourceforge.pmd.util.viewer.model;

import net.sourceforge.pmd.jaxen.Attribute;


/**
 * A toolkit for vaious attribute translations
 *
 * @author Boris Gruschko ( boris at gruschko.org )
 * @version $Id$
 */
public class AttributeToolkit {
    /**
     * formats a value for it's usage in XPath expressions
     *
     * @param attribute atribute which value should be formatted
     * @return formmated value
     */
    public static String formatValueForXPath(Attribute attribute) {
        return "'" + attribute.getValue() + "'";
    }

    /**
     * constructs a predicate from the given attribute
     *
     * @param attribute attribute to be formatted as predicate
     * @return predicate
     */
    public static String constructPredicate(Attribute attribute) {
        return "[@" + attribute.getName() + "=" +
                formatValueForXPath(attribute) + "]";
    }
}


/*
 * $Log$
 * Revision 1.4  2005/08/21 19:25:32  tomcopeland
 * Adding Boris' viewer back into the repository; as long as someone is using it, removing it is not good
 *
 * Revision 1.1.1.1  2005/08/15 19:51:42  tomcopeland
 * Import of Boris Grushko's viewer code
 *
 * Revision 1.2  2004/09/27 19:42:52  tomcopeland
 * A ridiculously large checkin, but it's all just code reformatting.  Nothing to see here...
 *
 * Revision 1.1  2003/09/23 20:32:42  tomcopeland
 * Added Boris Gruschko's new AST/XPath viewer
 *
 * Revision 1.1  2003/09/24 01:33:03  bgr
 * moved to a new package
 *
 * Revision 1.1  2003/09/23 07:52:16  bgr
 * menus added
 *
 */
