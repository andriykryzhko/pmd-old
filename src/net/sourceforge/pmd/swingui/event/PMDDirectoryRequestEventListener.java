package net.sourceforge.pmd.swingui.event;

import net.sourceforge.pmd.PMDException;

import java.util.EventListener;

/**
 *
 * @author Donald A. Leckie
 * @since December 13, 2002
 * @version $Revision$, $Date$
 */
public interface PMDDirectoryRequestEventListener extends EventListener
{

    /**
     *******************************************************************************
     *
     * @param event
     */
    public void requestRuleSetPath(PMDDirectoryRequestEvent event);

    /**
     *******************************************************************************
     *
     * @param event
     */
    public void requestAllRuleSets(PMDDirectoryRequestEvent event) throws PMDException;

    /**
     *******************************************************************************
     *
     * @param event
     */
    public void requestDefaultRuleSets(PMDDirectoryRequestEvent event);

    /**
     *******************************************************************************
     *
     * @param event
     */
    public void requestIncludedRules(PMDDirectoryRequestEvent event) throws PMDException;
}
