package net.sourceforge.pmd.swingui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.EventObject;
import java.util.List;
import java.util.Set;

import javax.swing.border.EtchedBorder;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.UIManager;

import net.sourceforge.pmd.AbstractRule;
import net.sourceforge.pmd.PMDException;
import net.sourceforge.pmd.Rule;
import net.sourceforge.pmd.RuleProperties;
import net.sourceforge.pmd.RuleSet;
import net.sourceforge.pmd.swingui.event.ListenerList;
import net.sourceforge.pmd.swingui.event.PMDDirectoryRequestEvent;
import net.sourceforge.pmd.swingui.event.PMDDirectoryRequestEventListener;
import net.sourceforge.pmd.swingui.event.PMDDirectoryReturnedEvent;
import net.sourceforge.pmd.swingui.event.PMDDirectoryReturnedEventListener;
import net.sourceforge.pmd.swingui.event.RulesEditingEvent;
import net.sourceforge.pmd.swingui.event.RulesEditingEventListener;


/**
 *
 * @author Donald A. Leckie
 * @since August 29, 2002
 * @version $Revision$, $Date$
 */
class RulesTree extends JTree implements IConstants
{

    private RulesEditor m_rulesEditor;
    private PMDDirectoryReturnedEventHandler m_pmdDirectoryReturnedEventHandler;
    private RulesTreeMouseListener m_mouseListener;
    private RulesEditingEventHandler m_rulesEditingEventHandler;

    // Constants
    private final String UNTITLED = "Untitled";

    /**
     ***************************************************************************
     *
     * @param rulesEditor
     */
    protected RulesTree(RulesEditor rulesEditor)
    {
        super(new RulesTreeModel(RulesTreeNode.createRootNode()));

        m_rulesEditor = rulesEditor;

        setRootVisible(true);
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        setCellRenderer(new TreeNodeRenderer());
        setCellEditor(new TreeCellEditor());
        setBackground(UIManager.getColor("pmdTreeBackground"));
        m_mouseListener = new RulesTreeMouseListener();
        addMouseListener(m_mouseListener);
        m_pmdDirectoryReturnedEventHandler = new PMDDirectoryReturnedEventHandler();
        m_rulesEditingEventHandler = new RulesEditingEventHandler();
    }

    /**
     *******************************************************************************
     *
     */
    protected void dispose()
    {
        if (m_mouseListener != null)
        {
            removeMouseListener(m_mouseListener);
        }

        if (m_pmdDirectoryReturnedEventHandler != null)
        {
            m_pmdDirectoryReturnedEventHandler.dispose();
        }

        if (m_rulesEditingEventHandler != null)
        {
            m_rulesEditingEventHandler.dispose();
        }

        m_rulesEditor = null;
        m_mouseListener = null;
        m_pmdDirectoryReturnedEventHandler = null;
        m_rulesEditingEventHandler = null;
    }

    /**
     *******************************************************************************
     *
     */
    protected void buildTree()
        throws PMDException
    {
        RuleSet[] ruleSets;
        RulesTreeNode rootNode;

        ruleSets = loadRuleSets();
        rootNode = (RulesTreeNode) getModel().getRoot();

        for (int n1 = 0; n1 < ruleSets.length; n1++)
        {
            RulesTreeNode ruleSetNode = new RulesTreeNode(ruleSets[n1]);

            rootNode.add(ruleSetNode);
            loadRuleTreeNodes(ruleSetNode);
        }

        expandNode(rootNode);
        TreePath treePath = new TreePath(rootNode.getPath());
        this.setSelectionPath(treePath);
    }

    /**
     *******************************************************************************
     *
     * @return
     */
    private RuleSet[] loadRuleSets() throws PMDException
    {
        PMDDirectoryRequestEvent.notifyRequestAllRuleSets(this);

        // The event is processed.  The requested rule set is assembled by another class
        // that calls notifyReturnedAllRuleSets.  The list of rule sets is stored in the
        // inner class PMDDirectoryReturnedEventHandler.  Then processing will then continue here.
        //

        List ruleSetList = m_pmdDirectoryReturnedEventHandler.getRuleSetList();

        //
        // Sort the rule sets by name in ascending order.
        //
        RuleSet[] ruleSets = new RuleSet[ruleSetList.size()];

        ruleSetList.toArray(ruleSets);
        Arrays.sort(ruleSets, new RuleSetNameComparator());

        return ruleSets;
    }

    /**
     *******************************************************************************
     *
     * @param ruleSetNode
     */
    private void loadRuleTreeNodes(RulesTreeNode ruleSetNode)
    {
        RuleSet ruleSet;
        Set setOfRules;
        Rule[] rules;

        ruleSet = ruleSetNode.getRuleSet();
        setOfRules = ruleSet.getRules();
        rules = new Rule[setOfRules.size()];

        setOfRules.toArray(rules);
        Arrays.sort(rules, new RuleNameComparator());

        for (int n = 0; n < rules.length; n++)
        {
            RulesTreeNode ruleNode = new RulesTreeNode(ruleSetNode, rules[n]);

            ruleSetNode.add(ruleNode);
            loadProperties(ruleNode);

            rules[n] = null;
        }
    }

    /**
     *******************************************************************************
     *
     * @param ruleNode
     */
    private void loadProperties(RulesTreeNode ruleNode)
    {
        Rule rule;
        RuleProperties properties;
        String[] propertyNames;
        Enumeration keys;
        int index;

        rule = ruleNode.getRule();
        properties = rule.getProperties();
        propertyNames = new String[properties.size()];
        keys = properties.keys();
        index = 0;

        while (keys.hasMoreElements())
        {
            propertyNames[index] = (String) keys.nextElement();
            index++;
        }

        Arrays.sort(propertyNames, new PropertyNameComparator());

        for (int n = 0; n < propertyNames.length; n++)
        {
            String propertyName;
            String propertyValue;
            String propertyValueType;
            RulesTreeNode propertyNode;

            propertyName = propertyNames[n];
            propertyValue = properties.getValue(propertyName);
            propertyValueType = properties.getValueType(propertyName);
            propertyNode = new RulesTreeNode(ruleNode, propertyName, propertyValue, propertyValueType);

            ruleNode.add(propertyNode);

            propertyNames[n] = null;
        }
    }

    /**
     ***************************************************************************
     *
     * @param node
     */
    protected void expandNode(RulesTreeNode node)
    {
        TreePath treePath = new TreePath(node.getPath());

        expandPath(treePath);
    }

    /**
     ***************************************************************************
     *
     * @return
     */
    protected RulesTreeNode getSelectedNode()
    {
        TreePath treePath = getSelectionPath();

        return (treePath == null) ? null : (RulesTreeNode) treePath.getLastPathComponent();
    }

    /**
     ***************************************************************************
     *
     * @param node
     *
     * @return
     */
    protected boolean isExpanded(RulesTreeNode node)
    {
        TreePath treePath = new TreePath(node.getPath());

        return isExpanded(treePath);
    }

    /**
     *******************************************************************************
     *******************************************************************************
     *******************************************************************************
     */
    private class RulesTreeMouseListener extends MouseAdapter
    {

        private JMenuItem m_addRuleSetMenuItem;
        private JMenuItem m_removeRuleSetMenuItem;
        private JMenuItem m_addRuleMenuItem;
        private JMenuItem m_removeRuleMenuItem;
        private JMenuItem m_addPropertyMenuItem;
        private JMenuItem m_removePropertyMenuItem;
        private JMenuItem m_includeMenuItem;

        /**
         ***********************************************************************
         *
         * @param event
         */
        public void mouseReleased(MouseEvent event)
        {
            if (event.isPopupTrigger())
            {
                RulesTree rulesTree;
                Point location;
                TreePath treePath;
                RulesTreeNode treeNode;
                JPopupMenu popupMenu;

                rulesTree = RulesTree.this;
                location = event.getPoint();
                treePath = rulesTree.getPathForLocation(location.x, location.y);
                rulesTree.setSelectionPath(treePath);
                treeNode = (RulesTreeNode) treePath.getLastPathComponent();
                popupMenu = null;

                if (treeNode.isRoot())
                {
                    popupMenu = createRootPopupMenu();
                }
                else if (treeNode.isRuleSet())
                {
                    popupMenu = createRuleSetPopupMenu();
                }
                else if (treeNode.isRule())
                {
                    popupMenu = createRulePopupMenu();
                }
                else if (treeNode.isProperty())
                {
                    popupMenu = createPropertyPopupMenu();
                }

                if (popupMenu != null)
                {
                    popupMenu.show(rulesTree, location.x, location.y);
                }
            }
        }

        /**
         ***********************************************************************
         *
         * @return
         */
        private JPopupMenu createRootPopupMenu()
        {
            JPopupMenu popupMenu = createPopupMenu(false);

            m_addRuleSetMenuItem.setEnabled(true);
            m_removeRuleSetMenuItem.setEnabled(false);
            m_addRuleMenuItem.setEnabled(false);
            m_removeRuleMenuItem.setEnabled(false);
            m_addPropertyMenuItem.setEnabled(false);
            m_removePropertyMenuItem.setEnabled(false);

            return popupMenu;
        }

        /**
         ***********************************************************************
         *
         * @return
         */
        private JPopupMenu createRuleSetPopupMenu()
        {
            JPopupMenu popupMenu = createPopupMenu(true);

            m_addRuleSetMenuItem.setEnabled(false);
            m_removeRuleSetMenuItem.setEnabled(true);
            m_addRuleMenuItem.setEnabled(true);
            m_removeRuleMenuItem.setEnabled(false);
            m_addPropertyMenuItem.setEnabled(false);
            m_removePropertyMenuItem.setEnabled(false);

            return popupMenu;
        }

        /**
         ***********************************************************************
         *
         * @return
         */
        private JPopupMenu createRulePopupMenu()
        {
            JPopupMenu popupMenu = createPopupMenu(true);

            m_addRuleSetMenuItem.setEnabled(false);
            m_removeRuleSetMenuItem.setEnabled(false);
            m_addRuleMenuItem.setEnabled(false);
            m_removeRuleMenuItem.setEnabled(true);
            m_addPropertyMenuItem.setEnabled(true);
            m_removePropertyMenuItem.setEnabled(false);

            return popupMenu;
        }

        /**
         ***********************************************************************
         *
         * @return
         */
        private JPopupMenu createPropertyPopupMenu()
        {
            JPopupMenu popupMenu = createPopupMenu(false);

            m_addRuleSetMenuItem.setEnabled(false);
            m_removeRuleSetMenuItem.setEnabled(false);
            m_addRuleMenuItem.setEnabled(false);
            m_removeRuleMenuItem.setEnabled(false);
            m_addPropertyMenuItem.setEnabled(false);
            m_removePropertyMenuItem.setEnabled(true);

            return popupMenu;
        }

        /**
         ***********************************************************************
         *
         * @return
         */
        private JPopupMenu createPopupMenu(boolean addInclude)
        {
            JPopupMenu popupMenu = new JPopupMenu();

            m_addRuleSetMenuItem = new JMenuItem("Add Rule Set");
            m_addRuleSetMenuItem.addActionListener(new AddRuleSetActionListener());
            popupMenu.add(m_addRuleSetMenuItem);

            m_removeRuleSetMenuItem = new JMenuItem("Remove Rule Set");
            m_removeRuleSetMenuItem.addActionListener(new RemoveRuleSetActionListener());
            popupMenu.add(m_removeRuleSetMenuItem);

            popupMenu.add(new JSeparator());

            m_addRuleMenuItem = new JMenuItem("Add Rule");
            m_addRuleMenuItem.addActionListener(new AddRuleActionListener());
            popupMenu.add(m_addRuleMenuItem);

            m_removeRuleMenuItem = new JMenuItem("Remove Rule");
            m_removeRuleMenuItem.addActionListener(new RemoveRuleActionListener());
            popupMenu.add(m_removeRuleMenuItem);

            popupMenu.add(new JSeparator());

            m_addPropertyMenuItem = new JMenuItem("Add Rule Property");
            m_addPropertyMenuItem.addActionListener(new AddRulePropertyActionListener());
            popupMenu.add(m_addPropertyMenuItem);

            m_removePropertyMenuItem = new JMenuItem("Remove Rule Property");
            m_removePropertyMenuItem.addActionListener(new RemoveRulePropertyActionListener());
            popupMenu.add(m_removePropertyMenuItem);

            if (addInclude)
            {
                popupMenu.add(new JSeparator());

                m_includeMenuItem = new JCheckBoxMenuItem("Include");
                m_includeMenuItem.addActionListener(new IncludeActionListener());
                m_includeMenuItem.setSelected(RulesTree.this.getSelectedNode().include());
                popupMenu.add(m_includeMenuItem);
            }

            return popupMenu;
        }
    }

    /**
     *******************************************************************************
     *******************************************************************************
     *******************************************************************************
     */
    private class IncludeActionListener implements ActionListener
    {

        public void actionPerformed(ActionEvent event)
        {
            JCheckBoxMenuItem includeMenuItem = (JCheckBoxMenuItem) event.getSource();
            RulesTree.this.getSelectedNode().setInclude(includeMenuItem.isSelected());
            RulesTree.this.repaint();
        }
    }

    /**
     *******************************************************************************
     *******************************************************************************
     *******************************************************************************
     */
    private class AddRuleSetActionListener implements ActionListener
    {

        public void actionPerformed(ActionEvent event)
        {
            RuleSet ruleSet = new RuleSet();
            String ruleSetName = UNTITLED;
            int counter = 0;
            RulesTree rulesTree = RulesTree.this;
            RulesTreeNode rootNode = rulesTree.getSelectedNode();

            while (rootNode.getChildNode(ruleSetName) != null)
            {
                counter++;
                ruleSetName = UNTITLED + "-" + counter;
            }

            ruleSet.setName(ruleSetName);

            RulesTreeNode ruleSetNode = new RulesTreeNode(ruleSet);
            DefaultTreeModel treeModel = (DefaultTreeModel) RulesTree.this.getModel();

            rootNode.add(ruleSetNode);
            treeModel.nodeStructureChanged(rootNode);

            if (rulesTree.isExpanded(ruleSetNode) == false)
            {
                rulesTree.expandNode(ruleSetNode);
            }

            rootNode.sortChildren();
        }
    }

    /**
     *******************************************************************************
     *******************************************************************************
     *******************************************************************************
     */
    private class RemoveRuleSetActionListener implements ActionListener
    {

        public void actionPerformed(ActionEvent event)
        {
            RulesTreeNode ruleSetNode = RulesTree.this.getSelectedNode();

            if (ruleSetNode != null)
            {
                String ruleSetName = ruleSetNode.getName();
                String template = "Do you really want to remove the rule set \"{0}\"?\nThe remove cannot be undone.";
                String[] args = {ruleSetName};
                String message = MessageFormat.format(template, args);

                if (MessageDialog.answerIsYes(m_rulesEditor, message))
                {
                    DefaultTreeModel treeModel = (DefaultTreeModel) RulesTree.this.getModel();
                    treeModel.removeNodeFromParent(ruleSetNode);
                }
            }
        }
    }

    /**
     *******************************************************************************
     *******************************************************************************
     *******************************************************************************
     */
    private class AddRuleActionListener implements ActionListener
    {

        /**
         ***************************************************************************
         *
         * @param event
         */
        public void actionPerformed(ActionEvent event)
        {
            Rule rule = null;

            try
            {
                rule = getNewRuleFromUser();
            }
            catch (PMDException exception)
            {
                String message = exception.getMessage();
                Exception originalException = exception.getOriginalException();
                MessageDialog.show(m_rulesEditor, message, originalException);

                return;
            }

            if (rule != null)
            {
                String className = rule.getClass().getName();
                int index = className.lastIndexOf('.') + 1;
                String baseRuleName = className.substring(index);
                String ruleName = baseRuleName;
                int counter = 0;
                RulesTree rulesTree = RulesTree.this;
                RulesTreeNode ruleSetNode = rulesTree.getSelectedNode();

                while (ruleSetNode.getChildNode(ruleName) != null)
                {
                    counter++;
                    ruleName = baseRuleName + "-" + counter;
                }

                rule.setName(ruleName);

                RulesTreeNode ruleNode = new RulesTreeNode(ruleSetNode, rule);
                DefaultTreeModel treeModel = (DefaultTreeModel) rulesTree.getModel();

                ruleSetNode.add(ruleNode);
                treeModel.nodeStructureChanged(ruleSetNode);

                if (rulesTree.isExpanded(ruleSetNode) == false)
                {
                    rulesTree.expandNode(ruleSetNode);
                }

                ruleSetNode.sortChildren();
                TreePath treePath = new TreePath(ruleNode.getPath());
                rulesTree.setSelectionPath(treePath);
            }
        }

        /**
         ***************************************************************************
         *
         * @return
         */
        private Rule getNewRuleFromUser()
        throws PMDException
        {
            RulesClassSelectDialog dialog = new RulesClassSelectDialog(m_rulesEditor);
            dialog.show();

            if (dialog.selectWasPressed())
            {
                File selectedFile = dialog.getSelectedClassFile();
                RuleClassLoader classLoader = new RuleClassLoader();
                Class clazz = classLoader.loadClass(selectedFile);

                try
                {
                    Object object = clazz.newInstance();

                    if (object instanceof AbstractRule)
                    {
                        return (Rule) object;
                    }

                    String abstractRuleClassName = AbstractRule.class.getName();
                    String template = "The selected class \"{0}\" must subclass the abstract rule class \"{1}\".";
                    String[] args = {clazz.getName(), abstractRuleClassName};
                    String message = MessageFormat.format(template, args);
                    MessageDialog.show(m_rulesEditor, message);
                }
                catch (InstantiationException exception)
                {
                    String template = "Could not instantiate class \"{0}\".";
                    String[] args = {clazz.getName()};
                    String message = MessageFormat.format(template, args);
                    MessageDialog.show(m_rulesEditor, message, exception);
                }
                catch (IllegalAccessException exception)
                {
                    String template = "Encountered illegal access while instantiating class \"{0}\".";
                    String[] args = {clazz.getName()};
                    String message = MessageFormat.format(template, args);
                    MessageDialog.show(m_rulesEditor, message, exception);
                }
            }

            return null;
        }
    }

    /**
     *******************************************************************************
     *******************************************************************************
     *******************************************************************************
     */
    private class RulesFileFilter implements FileFilter
    {
        /**
         ***************************************************************************
         *
         * @param file
         *
         * @return
         */
        public boolean accept(File file)
        {
            if (file.isDirectory())
            {
                return true;
            }

            return file.getName().endsWith(".class");
        }

        /**
         **************************************************************************
         *
         * @return
         */
        public String getDescription()
        {
            return "Rule Class Files";
        }
    }

    /**
     *******************************************************************************
     *******************************************************************************
     *******************************************************************************
     */
    private class RuleClassLoader extends ClassLoader
    {

        /**
         **************************************************************************
         *
         */
        private RuleClassLoader()
        {
            super();
        }

        /**
         **************************************************************************
         *
         */
        private Class loadClass(File file)
        {
            FileInputStream inputStream = null;
            Class clazz = null;

            try
            {
                inputStream = new FileInputStream(file);
                clazz = null;

                if (inputStream != null)
                {
                    final int size = 10000;
                    int byteCount = 0;
                    byte[] buffer = new byte[size];
                    ByteArrayOutputStream byteArrayOutputStream;

                    byteArrayOutputStream = new ByteArrayOutputStream(size);

                    try
                    {
                        while ((byteCount = inputStream.read(buffer)) > 0)
                        {
                            byteArrayOutputStream.write(buffer, 0, byteCount);
                        }
                    }
                    catch (IOException exception)
                    {
                        return null;
                    }

                    buffer = byteArrayOutputStream.toByteArray();
                    clazz = super.defineClass(null, buffer, 0, buffer.length);

                    if (clazz != null)
                    {
                        resolveClass(clazz);
                    }
                }
            }
            catch (FileNotFoundException exception)
            {
                clazz = null;
            }
            finally
            {
                if (inputStream != null)
                {
                    try
                    {
                        inputStream.close();
                    }
                    catch (IOException exception)
                    {
                        inputStream = null;
                    }
                }
            }

            return clazz;
        }
    }

    /**
     *******************************************************************************
     *******************************************************************************
     *******************************************************************************
     */
    private class RemoveRuleActionListener implements ActionListener
    {

        public void actionPerformed(ActionEvent event)
        {
            RulesTreeNode ruleNode = RulesTree.this.getSelectedNode();
            String ruleName = ruleNode.getName();
            String template = "Do you really want to remove the rule \"{0}\"?\nThe remove cannot be undone.";
            String[] args = {ruleName};
            String message = MessageFormat.format(template, args);

            if (MessageDialog.answerIsYes(m_rulesEditor, message))
            {
                DefaultTreeModel treeModel = (DefaultTreeModel) RulesTree.this.getModel();
                treeModel.removeNodeFromParent(ruleNode);
            }
        }
    }

    /**
     *******************************************************************************
     *******************************************************************************
     *******************************************************************************
     */
    private class AddRulePropertyActionListener implements ActionListener
    {

        public void actionPerformed(ActionEvent event)
        {
            String propertyName = UNTITLED;
            int counter = 0;
            RulesTree rulesTree = RulesTree.this;
            RulesTreeNode ruleNode = rulesTree.getSelectedNode();

            while (ruleNode.getChildNode(propertyName) != null)
            {
                counter++;
                propertyName = UNTITLED + "-" + counter;
            }

            RulesTreeNode propertyNode = new RulesTreeNode(ruleNode, propertyName, "", STRING);
            DefaultTreeModel treeModel = (DefaultTreeModel) rulesTree.getModel();

            ruleNode.add(propertyNode);
            treeModel.nodeStructureChanged(ruleNode);

            if (rulesTree.isExpanded(ruleNode) == false)
            {
                rulesTree.expandNode(ruleNode);
            }

            ruleNode.sortChildren();
        }
    }

    /**
     *******************************************************************************
     *******************************************************************************
     *******************************************************************************
     */
    private class RemoveRulePropertyActionListener implements ActionListener
    {

        public void actionPerformed(ActionEvent event)
        {
            RulesTreeNode propertyNode = RulesTree.this.getSelectedNode();
            String propertyName = propertyNode.getName();
            String template = "Do you really want to remove the property \"{0}\"?\nThe remove cannot be undone.";
            String[] args = {propertyName};
            String message = MessageFormat.format(template, args);

            if (MessageDialog.answerIsYes(m_rulesEditor, message))
            {
                DefaultTreeModel treeModel = (DefaultTreeModel) RulesTree.this.getModel();
                treeModel.removeNodeFromParent(propertyNode);
            }
        }
    }

    /**
     *******************************************************************************
     *******************************************************************************
     *******************************************************************************
     */
    private class TreeCellEditor extends DefaultTreeCellEditor
    {

        /**
         ***************************************************************************
         *
         */
        private TreeCellEditor()
        {
            super(RulesTree.this, (DefaultTreeCellRenderer) RulesTree.this.getCellRenderer());
        }

        /**
         ***************************************************************************
         *
         * @return
         */
        public boolean isCellEditable(EventObject event)
        {
            return false;
        }
    }

    /**
     ********************************************************************************
     ********************************************************************************
     ********************************************************************************
     */
    private class TreeNodeRenderer extends DefaultTreeCellRenderer
    {

        private Icon m_defaultClosedIcon;
        private Icon m_defaultLeafIcon;
        private Icon m_defaultOpenIcon;
        private Icon m_documentIcon;
        private Font m_plainFont;
        private Font m_italicFont;

        /**
         ***************************************************************************
         *
         */
        protected TreeNodeRenderer()
        {
            super();

            Font font;

            m_defaultClosedIcon = getDefaultClosedIcon();
            m_defaultLeafIcon = getDefaultLeafIcon();
            m_defaultOpenIcon = getDefaultOpenIcon();
            m_documentIcon = UIManager.getIcon("document");
            font = RulesTree.this.getFont();
            m_plainFont = new Font(font.getName(), Font.PLAIN, font.getSize());
            m_italicFont = new Font(font.getName(), Font.ITALIC, font.getSize());
            setBackgroundNonSelectionColor(UIManager.getColor("pmdTreeBackground"));
            setBackgroundSelectionColor(Color.yellow);
        }

        /**
         **************************************************************************
         *
         * @param tree
         * @param object
         * @param isSelected
         * @param isExpanded
         * @param isLeaf
         * @param row
         * @param hasFocus
         *
         * @return
         */
        public Component getTreeCellRendererComponent(JTree tree,
                                                      Object object,
                                                      boolean isSelected,
                                                      boolean isExpanded,
                                                      boolean isLeaf,
                                                      int row,
                                                      boolean hasFocus)
        {
            RulesTreeNode treeNode = (RulesTreeNode) object;

            if (treeNode.isProperty())
            {
                setClosedIcon(m_defaultClosedIcon);
                setLeafIcon(m_documentIcon);
                setOpenIcon(m_defaultOpenIcon);
            }
            else
            {
                setClosedIcon(m_defaultClosedIcon);
                setLeafIcon(m_defaultClosedIcon);
                setOpenIcon(m_defaultOpenIcon);
            }

            if (treeNode.hasUndefinedRuleClass())
            {
                setTextNonSelectionColor(Color.red);
                setTextSelectionColor(Color.red);
                setFont(m_plainFont);
            }
            else if (treeNode.include() && treeNode.includeAncestor())
            {
                setTextNonSelectionColor(Color.blue);
                setTextSelectionColor(Color.blue);
                setFont(m_plainFont);
            }
            else
            {
                setTextNonSelectionColor(Color.black);
                setTextSelectionColor(Color.black);
                setFont(m_italicFont);

            }

            updateUI();

            return super.getTreeCellRendererComponent(tree,
                                                      object,
                                                      isSelected,
                                                      isExpanded,
                                                      isLeaf,
                                                      row,
                                                      hasFocus);
        }

        /**
         **************************************************************************
         *
         * @param graphics
         */
        public void paint(Graphics graphics)
        {
            int x = getX();
            int y = getY();
            int width = getWidth();
            int height = getHeight();
            graphics.clearRect(x, y, width, height);
            super.paint(graphics);
        }
    }


    /**
     *******************************************************************************
     *******************************************************************************
     *******************************************************************************
     */
    private class RuleSetNameComparator implements Comparator
    {

        /**
         ************************************************************************
         *
         * @param objectA
         * @param objectB
         *
         * @return
         */
        public int compare(Object objectA, Object objectB)
        {
            String ruleSetNameA = ((RuleSet) objectA).getName();
            String ruleSetNameB = ((RuleSet) objectB).getName();

            return ruleSetNameA.compareToIgnoreCase(ruleSetNameB);
        }
    }

    /**
     *******************************************************************************
     *******************************************************************************
     *******************************************************************************
     */
    private class RuleNameComparator implements Comparator
    {

        /**
         ************************************************************************
         *
         * @param objectA
         * @param objectB
         *
         * @return
         */
        public int compare(Object objectA, Object objectB)
        {
            String ruleNameA = ((Rule) objectA).getName();
            String ruleNameB = ((Rule) objectB).getName();

            return ruleNameA.compareToIgnoreCase(ruleNameB);
        }
    }

    /**
     *******************************************************************************
     *******************************************************************************
     *******************************************************************************
     */
    private class PropertyNameComparator implements Comparator
    {

        /**
         ************************************************************************
         *
         * @param objectA
         * @param objectB
         *
         * @return
         */
        public int compare(Object objectA, Object objectB)
        {
            String propertyNameA = (String) objectA;
            String propertyNameB = (String) objectB;

            return propertyNameA.compareToIgnoreCase(propertyNameB);
        }
    }

    /**
     *******************************************************************************
     *******************************************************************************
     *******************************************************************************
     */
    private class PMDDirectoryReturnedEventHandler implements PMDDirectoryReturnedEventListener
    {

        private List m_ruleSetList;
        private String m_ruleSetPath;

        /**
         ***************************************************************************
         *
         */
        private PMDDirectoryReturnedEventHandler()
        {
            ListenerList.addListener((PMDDirectoryReturnedEventListener) this);
        }

        /**
         ***************************************************************************
         *
         */
        private void dispose()
        {
            ListenerList.removeListener((PMDDirectoryReturnedEventListener) this);

            if (m_ruleSetList != null)
            {
                m_ruleSetList.clear();
                m_ruleSetList = null;
            }

            m_ruleSetPath = null;
        }

        /**
         ***************************************************************************
         */
        private String getRuleSetPath()
        {
            return m_ruleSetPath;
        }

        /**
         ***************************************************************************
         */
        private List getRuleSetList()
        {
            return m_ruleSetList;
        }

        /**
         ***************************************************************************
         *
         * @param event
         */
        public void returnedRuleSetPath(PMDDirectoryReturnedEvent event)
        {
            m_ruleSetPath = event.getRuleSetPath();
        }

        /**
         ***************************************************************************
         *
         * @param event
         */
        public void returnedAllRuleSets(PMDDirectoryReturnedEvent event)
        {
            m_ruleSetList = event.getRuleSetList();
        }

        /**
         ***************************************************************************
         *
         * @param event
         */
        public void returnedDefaultRuleSets(PMDDirectoryReturnedEvent event)
        {
            m_ruleSetList = event.getRuleSetList();
        }

        /**
         ***************************************************************************
         *
         * @param event
         */
        public void returnedIncludedRules(PMDDirectoryReturnedEvent event)
        {
            m_ruleSetList = event.getRuleSetList();
        }
    }

    /**
     *******************************************************************************
     *******************************************************************************
     *******************************************************************************
     */
    private class RulesEditingEventHandler implements RulesEditingEventListener
    {

        /**
         ***************************************************************************
         *
         */
        private RulesEditingEventHandler()
        {
            ListenerList.addListener((RulesEditingEventListener) this);
        }

        /**
         ***************************************************************************
         *
         */
        private void dispose()
        {
            ListenerList.removeListener((RulesEditingEventListener) this);
        }

        /**
         ***************************************************************************
         *
         * @param event
         */
        public void saveData(RulesEditingEvent event)
        {
            SwingUtilities.invokeLater(new UpdateUI());
        }

        /**
         ***************************************************************************
         *
         * @param event
         */
        public void loadData(RulesEditingEvent event)
        {
        }
    }

    /**
     *******************************************************************************
     *******************************************************************************
     *******************************************************************************
     */
    private class UpdateUI implements Runnable
    {

        /**
         ***************************************************************************
         *
         */
        public void run()
        {
//            RulesTree.this.updateUI();
        }
    }
}
