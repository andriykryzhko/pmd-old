/*
 * User: tom
 * Date: Oct 1, 2002
 * Time: 1:33:38 PM
 */
package net.sourceforge.pmd.symboltable;

import net.sourceforge.pmd.ast.SimpleNode;

import java.util.List;
import java.util.ArrayList;

public interface ContextManager {
    public Scope getCurrentScope();
    public void openScope(Scope scope);
    public void leaveScope();
}
