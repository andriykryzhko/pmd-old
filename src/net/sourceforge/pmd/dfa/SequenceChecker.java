/*
 * Created on 11.07.2004
 */
package net.sourceforge.pmd.dfa;

import java.util.ArrayList;
import java.util.List;

/**
 * @author raik
 *         <p/>
 *         Computes the first sequence in a list.
 *         <p/>
 *         e.g.
 *         IF_START			0
 *         WHILE_EXPR		1
 *         WHILE_END		2
 *         IF_END			3
 *         <p/>
 *         The first sequence is WHILE_EXPR und WHILE_END. It returns always the
 *         first inner nested scope.
 */
public class SequenceChecker {

    private Status root;
    private Status aktStatus;
    private List vector;

    private int firstIndex = -1;
    private int lastIndex = -1;

//	----------------------------------------------------------------------------

    /*
     * Element of logical structure of brace nodes.
     * */
    private static class Status {
        public static final int ROOT = -1;

        private ArrayList nextSteps = new ArrayList();
        private int type;
        private boolean lastStep;


        public Status(int type) {
            this(type, false);
        }

        public Status(int type, boolean lastStep) {
            this.type = type;
            this.lastStep = lastStep;
        }

        public void addStep(Status type) {
            nextSteps.add(type);
        }

        public Status step(int type) {
            for (int i = 0; i < this.nextSteps.size(); i++) {
                if (type == ((Status) nextSteps.get(i)).type) {
                    return (Status) nextSteps.get(i);
                }
            }
            return null;
        }

        public boolean isLastStep() {
            return this.lastStep;
        }

        public boolean hasMoreSteps() {
            return this.nextSteps.size() > 1;
        }
    }

    /*
     * Defines the logical structure.
     * */
    public SequenceChecker(List v) {
        root = new Status(Status.ROOT);
        this.aktStatus = root;
        this.vector = v;

        Status ifNode = new Status(NodeType.IF_EXPR);
        Status ifSt = new Status(NodeType.IF_LAST_STATEMENT);
        Status ifStWithoutElse = new Status(NodeType.IF_LAST_STATEMENT_WITHOUT_ELSE, true);
        Status elseSt = new Status(NodeType.ELSE_LAST_STATEMENT, true);
        Status whileNode = new Status(NodeType.WHILE_EXPR);
        Status whileSt = new Status(NodeType.WHILE_LAST_STATEMENT, true);
        Status switchNode = new Status(NodeType.SWITCH_START);
        Status caseSt = new Status(NodeType.CASE_LAST_STATEMENT);
        Status switchDefault = new Status(NodeType.SWITCH_LAST_DEFAULT_STATEMENT);
        Status switchEnd = new Status(NodeType.SWITCH_END, true);

        Status forInit = new Status(NodeType.FOR_INIT);
        Status forExpr = new Status(NodeType.FOR_EXPR);
        Status forUpdate = new Status(NodeType.FOR_UPDATE);
        Status forSt = new Status(NodeType.FOR_BEFORE_FIRST_STATEMENT);
        Status forEnd = new Status(NodeType.FOR_END, true);

        Status doSt = new Status(NodeType.DO_BEFORE_FIRST_STATEMENT);
        Status doExpr = new Status(NodeType.DO_EXPR, true);

        root.addStep(ifNode);
        root.addStep(whileNode);
        root.addStep(switchNode);
        root.addStep(forInit);
        root.addStep(forExpr);
        root.addStep(forUpdate);
        root.addStep(forSt);
        root.addStep(doSt);

        ifNode.addStep(ifSt);
        ifNode.addStep(ifStWithoutElse);
        ifSt.addStep(elseSt);
        ifStWithoutElse.addStep(root);
        elseSt.addStep(root);

        whileNode.addStep(whileSt);
        whileSt.addStep(root);

        switchNode.addStep(caseSt);
        switchNode.addStep(switchDefault);
        switchNode.addStep(switchEnd);
        caseSt.addStep(caseSt);
        caseSt.addStep(switchDefault);
        caseSt.addStep(switchEnd);
        switchDefault.addStep(switchEnd);
        switchDefault.addStep(caseSt);
        switchEnd.addStep(root);

        forInit.addStep(forExpr);
        forInit.addStep(forUpdate);
        forInit.addStep(forSt);
        forExpr.addStep(forUpdate);
        forExpr.addStep(forSt);
        forUpdate.addStep(forSt);
        forSt.addStep(forEnd);
        forEnd.addStep(root);

        doSt.addStep(doExpr);
        doExpr.addStep(root);

    }

    /**
     * Finds the first most inner sequence e.g IFStart & IFEnd. If no sequence
     * is found or the list is empty the method return false.
     */
    public boolean run() {
        this.aktStatus = root;
        this.firstIndex = 0;
        this.lastIndex = 0;
        boolean lookAhead = false;

        for (int i = 0; i < this.vector.size(); i++) {
            StackObject so = (StackObject) vector.get(i);
            aktStatus = this.aktStatus.step(so.getType());

            if (aktStatus == null) {
                if (lookAhead) {
                    this.lastIndex = i - 1;
                    return false;
                }
                this.aktStatus = root;
                this.firstIndex = i;
                i--;
                continue;
            } else {
                if (aktStatus.isLastStep() && !aktStatus.hasMoreSteps()) {
                    this.lastIndex = i;
                    return false;
                } else if (aktStatus.isLastStep() && aktStatus.hasMoreSteps()) {
                    lookAhead = true;
                    this.lastIndex = i;
                }
            }
        }
        if (this.firstIndex != this.lastIndex) {
            return false;
        }
        return true;
    }

    public int getFirstIndex() {
        return this.firstIndex;
    }

    public int getLastIndex() {
        return this.lastIndex;
    }

}
