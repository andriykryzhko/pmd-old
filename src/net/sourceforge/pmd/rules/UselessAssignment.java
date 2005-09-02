package net.sourceforge.pmd.rules;

import net.sourceforge.pmd.AbstractRule;
import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.ast.SimpleNode;
import net.sourceforge.pmd.dfa.IDataFlowNode;
import net.sourceforge.pmd.dfa.pathfinder.DAAPathFinder;
import net.sourceforge.pmd.dfa.pathfinder.Executable;
import net.sourceforge.pmd.dfa.variableaccess.VariableAccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UselessAssignment extends AbstractRule implements Executable  {

    private RuleContext rc;
    private SimpleNode tmp;

    public Object visit(ASTMethodDeclaration node, Object data) {
        this.rc = (RuleContext) data;
        this.tmp = node;

/*
        IDataFlowNode n1 = node.getDataFlowNode();
        List f = n1.getFlow();
        for (Iterator i = f.iterator(); i.hasNext();) {
            DataFlowNode dfan = (DataFlowNode)i.next();
            System.out.println(dfan);
            List va = dfan.getVariableAccess();
            for (Iterator j = va.iterator(); j.hasNext();) {
                VariableAccess o = (VariableAccess)j.next();
                System.out.println(o);
            }
        }
*/

        DAAPathFinder a = new DAAPathFinder((IDataFlowNode) node.getDataFlowNode().getFlow().get(0), this);
        a.run();

        return data;
    }

    public void execute(List path) {
        Map hash = new HashMap();
        //System.out.println("path size is " + path.size());
        for (int i = 0; i < path.size(); i++) {
            //System.out.println("i = " + i);
            IDataFlowNode inode = (IDataFlowNode) path.get(i);
            if (inode.getVariableAccess() != null) {
                for (int j = 0; j < inode.getVariableAccess().size(); j++) {
                    //System.out.println("j = " + j);
                    VariableAccess va = (VariableAccess) inode.getVariableAccess().get(j);

                    Object o = hash.get(va.getVariableName());
                    if (o != null) {
                        List array = (List) o;
                        int last = ((Integer) array.get(0)).intValue();
                        // At some point investigate and possibly reintroduce this line2 thing
                        //int line2 = ((Integer) array.get(1)).intValue();

                        // DD - definition followed by another definition
                        if (va.accessTypeMatches(last) && va.isDefinition()) {
                            addViolation(rc, tmp, va.getVariableName());
                        }
/*                        // UR - ??
                      else if (last == VariableAccess.UNDEFINITION && va.isReference()) {
                            //this.rc.getReport().addRuleViolation(createRuleViolation(rc, inode.getSimpleNode(), va.getVariableName(), "UR"));
                        }
                        // DU - variable is defined and then goes out of scope
                        // i.e., unused parameter
                        else if (last == VariableAccess.DEFINITION && va.isUndefinition()) {
                            if (inode.getSimpleNode() != null) {
                                this.rc.getReport().addRuleViolation(createRuleViolation(rc, tmp, va.getVariableName(), "DU"));
                            }
                        }
*/
                    }
                    List array = new ArrayList();
                    array.add(new Integer(va.getAccessType()));
                    array.add(new Integer(inode.getLine()));
                    hash.put(va.getVariableName(), array);
                }
            }
        }
    }
}
