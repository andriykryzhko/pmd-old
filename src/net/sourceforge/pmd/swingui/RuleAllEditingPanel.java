package net.sourceforge.pmd.swingui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.border.EmptyBorder;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Donald A. Leckie
 * @since September 8, 2002
 * @version $Revision$, $Date$
 */
public class RuleAllEditingPanel extends JPanel
{
    private RuleSetEditingPanel m_ruleSetPanel;
    private RuleEditingPanel m_rulePanel;
    private RulePropertyEditingPanel m_rulePropertyPanel;
    private boolean m_isEditing;

    /**
     *******************************************************************************
     *
     * @return
     */
    public RuleAllEditingPanel()
    {
        super(new BorderLayout());

        EmptyBorder emptyBorder = new EmptyBorder(15, 15, 15, 15);

        setBorder(emptyBorder);

        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        m_ruleSetPanel = new RuleSetEditingPanel();
        m_rulePanel = new RuleEditingPanel();
        m_rulePropertyPanel = new RulePropertyEditingPanel();

        JScrollPane ruleSetScrollPane = ComponentFactory.createScrollPane(m_ruleSetPanel);
        JScrollPane ruleScrollPane = ComponentFactory.createScrollPane(m_rulePanel);
        JScrollPane rulePropertyScrollPane = ComponentFactory.createScrollPane(m_rulePropertyPanel);

        mainPanel.add(ruleSetScrollPane, BorderLayout.NORTH);
        mainPanel.add(ruleScrollPane, BorderLayout.CENTER);
        mainPanel.add(rulePropertyScrollPane, BorderLayout.SOUTH);
    }

    /**
     *******************************************************************************
     *
     * @return
     */
    public RuleSetEditingPanel getRuleSetEditingPanel()
    {
        return m_ruleSetPanel;
    }

    /**
     *******************************************************************************
     *
     * @return
     */
    public RuleEditingPanel getRuleEditingPanel()
    {
        return m_rulePanel;
    }

    /**
     *******************************************************************************
     *
     * @return
     */
    public RulePropertyEditingPanel getRulePropertyEditingPanel()
    {
        return m_rulePropertyPanel;
    }

    /**
     *******************************************************************************
     *
     * @param data
     */
    protected void setIsEditing(boolean isEditing)
    {
        m_isEditing = isEditing;
        m_ruleSetPanel.setIsEditing(isEditing);
        m_rulePanel.setIsEditing(isEditing);
        m_rulePropertyPanel.setIsEditing(isEditing);
    }

    /**
     *******************************************************************************
     *
     * @param data
     */
    public void setData(IRulesEditingData data)
    {
        m_ruleSetPanel.setData(data);
        m_rulePanel.setData(data);
        m_rulePropertyPanel.setData(data);
    }

    /**
     *******************************************************************************
     *
     */
    public void saveData()
    {
        if (m_isEditing)
        {
            m_ruleSetPanel.saveData();
            m_rulePanel.saveData();
            m_rulePropertyPanel.saveData();
        }
    }
}