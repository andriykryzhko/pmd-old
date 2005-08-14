package net.sourceforge.pmd.dfa;

import net.sourceforge.pmd.ast.ASTBreakStatement;
import net.sourceforge.pmd.ast.ASTContinueStatement;
import net.sourceforge.pmd.ast.ASTDoStatement;
import net.sourceforge.pmd.ast.ASTExpression;
import net.sourceforge.pmd.ast.ASTForInit;
import net.sourceforge.pmd.ast.ASTForStatement;
import net.sourceforge.pmd.ast.ASTForUpdate;
import net.sourceforge.pmd.ast.ASTIfStatement;
import net.sourceforge.pmd.ast.ASTReturnStatement;
import net.sourceforge.pmd.ast.ASTStatement;
import net.sourceforge.pmd.ast.ASTStatementExpression;
import net.sourceforge.pmd.ast.ASTSwitchLabel;
import net.sourceforge.pmd.ast.ASTSwitchStatement;
import net.sourceforge.pmd.ast.ASTVariableDeclarator;
import net.sourceforge.pmd.ast.ASTWhileStatement;
import net.sourceforge.pmd.ast.JavaParserVisitorAdapter;
import net.sourceforge.pmd.ast.SimpleNode;

/**
 * @author raik
 *         <p/>
 *         Sublayer of DataFlowFacade. Finds all data flow nodes and stores the
 *         type information (@see StackObject). At last it uses this information to
 *         link the nodes.
 */
public class DataFlowCreator extends JavaParserVisitorAdapter {

    private Structure dataFlow;

    public void compute(SimpleNode node) {
        this.dataFlow = new Structure();
        this.dataFlow.createStartNode(node.getBeginLine());
        this.dataFlow.createNewNode(node);

        node.jjtAccept(this, dataFlow);

        this.dataFlow.createEndNode(node.getEndLine());
        try {
            // links all data flow nodes
            Linker linker = new Linker(dataFlow);
            linker.computePaths();
        } catch (LinkerException e) {
            e.printStackTrace();
            // TODO error message
        } catch (SequenceException e) {
            e.printStackTrace();
            // TODO error message
        }
    }

    public Object visit(ASTStatementExpression node, Object data) {
        if (!(data instanceof Structure)) {
            return data;
        }
        dataFlow.createNewNode(node);
        return super.visit(node, data);
    }

    public Object visit(ASTVariableDeclarator node, Object data) {
        if (!(data instanceof Structure)) {
            return data;
        }
        dataFlow.createNewNode(node);
        return super.visit(node, data);
    }

    public Object visit(ASTExpression node, Object data) {
        if (!(data instanceof Structure)) {
            return data;
        }
        if (node.jjtGetParent() instanceof ASTIfStatement) {
            dataFlow.createNewNode(node); // START IF
            dataFlow.pushOnStack(NodeType.IF_EXPR, dataFlow.getLast());
        } else if (node.jjtGetParent() instanceof ASTWhileStatement) {
            dataFlow.createNewNode(node); // START WHILE
            dataFlow.pushOnStack(NodeType.WHILE_EXPR, dataFlow.getLast());
        } else if (node.jjtGetParent() instanceof ASTSwitchStatement) {
            dataFlow.createNewNode(node); // START SWITCH
            dataFlow.pushOnStack(NodeType.SWITCH_START, dataFlow.getLast());
        } else if (node.jjtGetParent() instanceof ASTForStatement) {
            dataFlow.createNewNode(node); // FOR EXPR
            dataFlow.pushOnStack(NodeType.FOR_EXPR, dataFlow.getLast());
        } else if (node.jjtGetParent() instanceof ASTDoStatement) {
            dataFlow.createNewNode(node); // DO EXPR
            dataFlow.pushOnStack(NodeType.DO_EXPR, dataFlow.getLast());
        }

        return super.visit(node, data);
    }

    public Object visit(ASTForInit node, Object data) {
        if (!(data instanceof Structure)) {
            return data;
        }
        super.visit(node, data);
        dataFlow.pushOnStack(NodeType.FOR_INIT, dataFlow.getLast());
        this.addForExpressionNode(node, dataFlow);
        return data;
    }

    public Object visit(ASTForUpdate node, Object data) {
        if (!(data instanceof Structure)) {
            return data;
        }
        this.addForExpressionNode(node, dataFlow);
        super.visit(node, data);
        dataFlow.pushOnStack(NodeType.FOR_UPDATE, dataFlow.getLast());
        return data;
    }

    public Object visit(ASTStatement node, Object data) {
        if (!(data instanceof Structure)) {
            return data;
        }
        if (node.jjtGetParent() instanceof ASTForStatement) {
            this.addForExpressionNode(node, dataFlow);
            dataFlow.pushOnStack(NodeType.FOR_BEFORE_FIRST_STATEMENT, dataFlow.getLast());
        } else if (node.jjtGetParent() instanceof ASTDoStatement) {
            dataFlow.pushOnStack(NodeType.DO_BEFORE_FIRST_STATEMENT, dataFlow.getLast());
            dataFlow.createNewNode((SimpleNode) node.jjtGetParent());
        }

        super.visit(node, data);

        if (node.jjtGetParent() instanceof ASTIfStatement) {
            ASTIfStatement st = (ASTIfStatement) node.jjtGetParent();
            if (!st.hasElse()) {
                dataFlow.pushOnStack(NodeType.IF_LAST_STATEMENT_WITHOUT_ELSE, dataFlow.getLast());
            } else if (st.hasElse() && !st.jjtGetChild(1).equals(node)) {
                dataFlow.pushOnStack(NodeType.ELSE_LAST_STATEMENT, dataFlow.getLast());
            } else {
                dataFlow.pushOnStack(NodeType.IF_LAST_STATEMENT, dataFlow.getLast());
            }
        } else if (node.jjtGetParent() instanceof ASTWhileStatement) {
            dataFlow.pushOnStack(NodeType.WHILE_LAST_STATEMENT, dataFlow.getLast());
        } else if (node.jjtGetParent() instanceof ASTForStatement) {
            dataFlow.pushOnStack(NodeType.FOR_END, dataFlow.getLast());
        }
        return data;
    }

    public Object visit(ASTSwitchStatement node, Object data) {
        if (!(data instanceof Structure)) {
            return data;
        }
        super.visit(node, data);
        dataFlow.pushOnStack(NodeType.SWITCH_END, dataFlow.getLast());
        return data;
    }

    public Object visit(ASTSwitchLabel node, Object data) {
        if (!(data instanceof Structure)) {
            return data;
        }
        //super.visit(node, data);
        if (node.jjtGetNumChildren() == 0) {
            dataFlow.pushOnStack(NodeType.SWITCH_LAST_DEFAULT_STATEMENT, dataFlow.getLast());
        } else {
            dataFlow.pushOnStack(NodeType.CASE_LAST_STATEMENT, dataFlow.getLast());
        }
        return data;
    }

    public Object visit(ASTBreakStatement node, Object data) {
        if (!(data instanceof Structure)) {
            return data;
        }
        dataFlow.createNewNode(node);
        dataFlow.pushOnStack(NodeType.BREAK_STATEMENT, dataFlow.getLast());
        return super.visit(node, data);
    }

    public Object visit(ASTContinueStatement node, Object data) {
        if (!(data instanceof Structure)) {
            return data;
        }
        dataFlow.createNewNode(node);
        dataFlow.pushOnStack(NodeType.CONTINUE_STATEMENT, dataFlow.getLast());
        return super.visit(node, data);
    }

    public Object visit(ASTReturnStatement node, Object data) {
        if (!(data instanceof Structure)) {
            return data;
        }
        dataFlow.createNewNode(node);
        dataFlow.pushOnStack(NodeType.RETURN_STATEMENT, dataFlow.getLast());
        return super.visit(node, data);
    }

    /*
    * The method handles the special "for" loop. It creates always an
    * expression node even if the loop looks like for(;;).
    * */
    private void addForExpressionNode(SimpleNode node, Structure dataFlow) {

        try {
            ASTForStatement parent = (ASTForStatement) node.jjtGetParent();
            boolean hasExpressionChild = false;
            boolean hasForInitNode = false;
            boolean hasForUpdateNode = false;

            for (int i = 0; i < parent.jjtGetNumChildren(); i++) {
                if (parent.jjtGetChild(i) instanceof ASTExpression)
                    hasExpressionChild = true;
                else if (parent.jjtGetChild(i) instanceof ASTForUpdate)
                    hasForUpdateNode = true;
                else if (parent.jjtGetChild(i) instanceof ASTForInit)
                    hasForInitNode = true;

            }
            if (!hasExpressionChild) {
                if (node instanceof ASTForInit) {
                    dataFlow.createNewNode(node); // FOR EXPRESSION
                    dataFlow.pushOnStack(NodeType.FOR_EXPR, dataFlow.getLast());
                } else if (node instanceof ASTForUpdate) {
                    if (!hasForInitNode) {
                        dataFlow.createNewNode(node); // FOR EXPRESSION
                        dataFlow.pushOnStack(NodeType.FOR_EXPR, dataFlow.getLast());
                    }
                } else if (node instanceof ASTStatement) {
                    if (!hasForInitNode && !hasForUpdateNode) {
                        dataFlow.createNewNode(node); // FOR EXPRESSION
                        dataFlow.pushOnStack(NodeType.FOR_EXPR, dataFlow.getLast());
                    }
                }
            }
        } catch (ClassCastException e) {
            System.err.println("Wrong use of method addForExpressionNode(..).");
            return;
        }
    }
}
