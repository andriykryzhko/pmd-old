package net.sourceforge.pmd.swingui.event;

import java.util.EventObject;
import java.util.Iterator;
import java.util.List;

import net.sourceforge.pmd.RuleSet;

/**
 *
 * @author Donald A. Leckie
 * @since December 13, 2002
 * @version $Revision$, $Date$
 */
public class PMDDirectoryRequestEvent extends EventObject
{

    private List m_ruleSetList;
    private RuleSet m_ruleSet;
    private int m_lowestPriorityForAnalysis;

    /**
     *****************************************************************************
     *
     * @param source
     */
    private PMDDirectoryRequestEvent(Object source)
    {
        super(source);
    }

    /**
     *****************************************************************************
     *
     * @param source
     * @param ruleSetList
     */
    private PMDDirectoryRequestEvent(Object source, List ruleSetList)
    {
        super(source);

        m_ruleSetList = ruleSetList;
    }

    /**
     *****************************************************************************
     *
     * @param source
     * @param ruleSet
     */
    private PMDDirectoryRequestEvent(Object source, RuleSet ruleSet)
    {
        super(source);

        m_ruleSet = ruleSet;
    }

    /**
     *****************************************************************************
     *
     * @param source
     * @param lowestPriorityForAnalysis
     */
    private PMDDirectoryRequestEvent(Object source, int lowestPriorityForAnalysis)
    {
        super(source);

        m_lowestPriorityForAnalysis = lowestPriorityForAnalysis;
    }

    /**
     *****************************************************************************
     *
     * @return
     */
    public List getRuleSetList()
    {
        return m_ruleSetList;
    }

    /**
     *****************************************************************************
     *
     * @return
     */
    public RuleSet getRuleSet()
    {
        return m_ruleSet;
    }

    /**
     *****************************************************************************
     *
     * @return
     */
    public int getLowestPriorityForAnalysis()
    {
        return m_lowestPriorityForAnalysis;
    }

    /**
     *****************************************************************************
     *
     * @param source
     */
    public static final void notifyRequestRuleSetPath(Object source)
    {
        PMDDirectoryRequestEvent event = new PMDDirectoryRequestEvent(source);
        List listenerList = ListenerList.getListeners(PMDDirectoryRequestEventListener.class);
        Iterator listeners = listenerList.iterator();

        while (listeners.hasNext())
        {
            PMDDirectoryRequestEventListener listener;

            listener = (PMDDirectoryRequestEventListener) listeners.next();
            listener.requestRuleSetPath(event);
        }
    }

    /**
     *****************************************************************************
     *
     * @param source
     */
    public static final void notifyRequestAllRuleSets(Object source)
    {
        PMDDirectoryRequestEvent event = new PMDDirectoryRequestEvent(source);
        List listenerList = ListenerList.getListeners(PMDDirectoryRequestEventListener.class);
        Iterator listeners = listenerList.iterator();

        while (listeners.hasNext())
        {
            PMDDirectoryRequestEventListener listener;

            listener = (PMDDirectoryRequestEventListener) listeners.next();
            listener.requestAllRuleSets(event);
        }
    }

    /**
     *****************************************************************************
     *
     * @param source
     */
    public static final void notifyRequestDefaultRuleSets(Object source)
    {
        PMDDirectoryRequestEvent event = new PMDDirectoryRequestEvent(source);
        List listenerList = ListenerList.getListeners(PMDDirectoryRequestEventListener.class);
        Iterator listeners = listenerList.iterator();

        while (listeners.hasNext())
        {
            PMDDirectoryRequestEventListener listener;

            listener = (PMDDirectoryRequestEventListener) listeners.next();
            listener.requestDefaultRuleSets(event);
        }
    }

    /**
     *****************************************************************************
     *
     * @param source
     */
    public static final void notifyRequestIncludedRules(Object source, int lowestPriorityForAnalysis)
    {
        PMDDirectoryRequestEvent event = new PMDDirectoryRequestEvent(source, lowestPriorityForAnalysis);
        List listenerList = ListenerList.getListeners(PMDDirectoryRequestEventListener.class);
        Iterator listeners = listenerList.iterator();

        while (listeners.hasNext())
        {
            PMDDirectoryRequestEventListener listener;

            listener = (PMDDirectoryRequestEventListener) listeners.next();
            listener.requestIncludedRules(event);
        }
    }
}
