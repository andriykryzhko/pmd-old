package net.sourceforge.pmd.swingui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.LookAndFeel;
import javax.swing.UIDefaults;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

/**
 *
 * @author Donald A. Leckie
 * @since August 27, 2002
 * @version $Revision$, $Date$
 */
public class PMDLookAndFeel extends WindowsLookAndFeel
{

    /**
     ****************************************************************************
     *
     * @return
     */
    public String getDescription()
    {
        return "Source Forge PMD look and feel";
    }

    /**
     ****************************************************************************
     *
     * @return
     */
    public String getID()
    {
        return "SourceForgePMD";
    }

    /**
     ****************************************************************************
     *
     * @return
     */
    public String getName()
    {
        return "SourceForgePMD";
    }

    /**
     ****************************************************************************
     *
     * @return
     */
    public boolean isNativeLookAndFeel()
    {
        return false;
    }

    /**
     ****************************************************************************
     *
     * @return
     */
    public boolean isSupportedLookAndFeel()
    {
        return true;
    }

    /**
     ****************************************************************************
     *
     * @param table
     */
    protected void initClassDefaults(UIDefaults table)
    {
        super.initClassDefaults(table);

        String pkgName = "net.sourceforge.pmd.swingui";
    }



    /**
     ****************************************************************************
     *
     * @param table
     */
    protected void initSystemColorDefaults(UIDefaults table)
    {
          super.initSystemColorDefaults(table);

          Color darkBlue = Color.blue.darker();

          String[] defaultSystemColors =
          {
                                   "pmdBlue", String.valueOf(darkBlue.getRGB()),
                                   "pmdGray", "#C5C5C5",
                                    "pmdRed", String.valueOf(Color.red.getRGB()),
                                  "pmdGreen", "#336666",
                                  "pmdCream", "#FFFCED",
                         "pmdTreeBackground", "#F5F5F5",
                        "pmdTableBackground", "#F5F5F5",
                                "mediumGray", "#686868",
                            "mediumDarkGray", "#434343",
                                  "paleGray", "#AAAAAA",
                  "pmdTableHeaderBackground", "#686868",
                  "pmdTableHeaderForeground", "#FFFFFF",
                 "pmdEditingPanelBackground", String.valueOf(Color.lightGray.getRGB()),
                    "disabledTextBackground", "#AAAAAA",
          };

          loadSystemColors(table, defaultSystemColors, isNativeLookAndFeel());
    }



    /**
     ****************************************************************************
     *
     * @param table
     */
    protected void initComponentDefaults(UIDefaults table)
    {
        super.initComponentDefaults(table);

        Class wlafClass = WindowsLookAndFeel.class;
        Class plafClass = PMDLookAndFeel.class;
        Object[] defaults =
        {
            "document",         LookAndFeel.makeIcon(plafClass, "icons/document.gif"),
            "save",             LookAndFeel.makeIcon(plafClass, "icons/save.gif"),
            "saveAs",           LookAndFeel.makeIcon(plafClass, "icons/saveAs.gif"),
            "print",            LookAndFeel.makeIcon(plafClass, "icons/print.gif"),
            "copy",             LookAndFeel.makeIcon(plafClass, "icons/copy.gif"),
            "edit",             LookAndFeel.makeIcon(plafClass, "icons/edit.gif"),
            "view",             LookAndFeel.makeIcon(plafClass, "icons/view.gif"),
            "help",             LookAndFeel.makeIcon(plafClass, "icons/help.gif"),
            "pmdLogo",          LookAndFeel.makeIcon(plafClass, "icons/pmdLogo.gif"),
            "labelFont",        new Font("Dialog", Font.BOLD, 12),
            "label14Font",      new Font("Dialog", Font.BOLD, 14),
            "label16Font",      new Font("Dialog", Font.BOLD, 16),
            "dataFont",         new Font("Dialog", Font.PLAIN, 12),
            "codeFont",         new Font("Monospaced", Font.PLAIN, 12),
            "tabFont",          new Font("SansSerif", Font.BOLD, 12),
            "titleFont",        new Font("SansSerif", Font.BOLD, 14),
            "buttonFont",       new Font("SansSerif", Font.BOLD, 12),
            "messageFont",      new Font("Dialog", Font.PLAIN, 12),
            "serif12Font",      new Font("Serif", Font.PLAIN, 12),
            "serif14Font",      new Font("Serif", Font.PLAIN, 14),

            // These are all the icons defined in the WindowsLookAndFeel.  We redefine them
            // here because of the way they are defined in that class: in terms of the return
            // value of getClass().  I.e., getClass() just returns the handle to the invoking
            // class, which now is PMDLookAndFeel.  That means that the icons are searched
            // for in the PMD look and feel package, which is not where they really are.
            // Since we've just called the superclass method, the icons have been installed
            // incorrectly in the table.  Reinstall them using the correct class.

            "Tree.openIcon",               LookAndFeel.makeIcon(wlafClass, "icons/TreeOpen.gif"),
            "Tree.closedIcon",             LookAndFeel.makeIcon(wlafClass, "icons/TreeClosed.gif"),
            "Tree.leafIcon",               LookAndFeel.makeIcon(wlafClass, "icons/TreeLeaf.gif"),

            "FileChooser.newFolderIcon",   LookAndFeel.makeIcon(wlafClass, "icons/NewFolder.gif"),
            "FileChooser.upFolderIcon",    LookAndFeel.makeIcon(wlafClass, "icons/UpFolder.gif"),
            "FileChooser.homeFolderIcon",  LookAndFeel.makeIcon(wlafClass, "icons/HomeFolder.gif"),
            "FileChooser.detailsViewIcon", LookAndFeel.makeIcon(wlafClass, "icons/DetailsView.gif"),
            "FileChooser.listViewIcon",    LookAndFeel.makeIcon(wlafClass, "icons/ListView.gif"),

            "FileView.directoryIcon",      LookAndFeel.makeIcon(wlafClass, "icons/Directory.gif"),
            "FileView.fileIcon",           LookAndFeel.makeIcon(wlafClass, "icons/File.gif"),
            "FileView.computerIcon",       LookAndFeel.makeIcon(wlafClass, "icons/Computer.gif"),
            "FileView.hardDriveIcon",      LookAndFeel.makeIcon(wlafClass, "icons/HardDrive.gif"),
            "FileView.floppyDriveIcon",    LookAndFeel.makeIcon(wlafClass, "icons/FloppyDrive.gif"),
        };

        table.putDefaults(defaults);
    }
}