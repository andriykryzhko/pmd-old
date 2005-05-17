/* Generated By:JJTree: Do not edit this line. SimpleNode.java */
package net.sourceforge.pmd.ast;

import net.sourceforge.pmd.IPositionProvider;
import net.sourceforge.pmd.jaxen.DocumentNavigator;
import net.sourceforge.pmd.dfa.IDataFlowNode;
import net.sourceforge.pmd.symboltable.Scope;

import java.util.ArrayList;
import java.util.List;

import org.jaxen.BaseXPath;
import org.jaxen.XPath;
import org.jaxen.JaxenException;

public class SimpleNode implements Node , IPositionProvider {
    protected Node parent;
    protected Node[] children;
    protected int id;
    protected JavaParser parser;
    private String image;
    private int beginLine = -1;
    private int endLine;
    private int beginColumn = -1;
    private int endColumn;
    private Scope scope;
    private boolean discardable;

    private IDataFlowNode dataFlowNode;

    public IDataFlowNode getDataFlowNode() {
        if (this.dataFlowNode == null) {
            if (this.parent != null) {
                return ((SimpleNode) parent).getDataFlowNode();
            }
            return null; //TODO wise?
        }
        return dataFlowNode;
    }

    public void discardIfNecessary() {
        if (discardable) {
            SimpleNode parent = (SimpleNode)this.jjtGetParent();
            SimpleNode kid = (SimpleNode) this.jjtGetChild(0);
            kid.jjtSetParent(parent);
            parent.jjtReplaceChild(this, kid);
        }
    }

    public void setDataFlowNode(IDataFlowNode dataFlowNode) {
        this.dataFlowNode = dataFlowNode;
    }

    public void setDiscardable() {
        this.discardable = true;
    }

    public void setUnDiscardable() {
        this.discardable = false;
    }

    public SimpleNode(int i) {
        id = i;
    }

    public SimpleNode(JavaParser p, int i) {
        this(i);
        parser = p;
    }

    public void jjtOpen() {
        if (beginLine == -1 && parser.token.next != null) {
            beginLine = parser.token.next.beginLine;
            beginColumn = parser.token.next.beginColumn;
        }
    }

    public void jjtClose() {
        if (beginLine == -1 && (children == null || children.length == 0)) {
            beginColumn = parser.token.beginColumn;
        }
        if (beginLine == -1) {
            beginLine = parser.token.beginLine;
        }
        endLine = parser.token.endLine;
        endColumn = parser.token.endColumn;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public Scope getScope() {
        if (scope == null) {
            return ((SimpleNode) parent).getScope();
        }
        return scope;
    }

    public int getBeginLine() {
        return beginLine;
    }

    public void testingOnly__setBeginLine(int i) {
        this.beginLine = i;
    }

    public void testingOnly__setBeginColumn(int i) {
        this.beginColumn = i;
    }

    public int getBeginColumn() {
        if (beginColumn != -1) {
            return beginColumn;
        } else {
            if ((children != null) && (children.length > 0)) {
                return ((SimpleNode) children[0]).getBeginColumn();
            } else {
                throw new RuntimeException("Unable to determine begining line of Node.");
            }
        }
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getEndLine() {
        return endLine;
    }

    public int getEndColumn() {
        return endColumn;
    }

    /**
     * Traverses up the tree to find the first parent instance of type parentType
     *
     * @param parentType class which you want to find.
     * @return Node of type parentType.  Returns null if none found.
     */
    public Node getFirstParentOfType(Class parentType) {
        Node parentNode = jjtGetParent();
        while (parentNode != null && parentNode.getClass() != parentType) {
            parentNode = parentNode.jjtGetParent();
        }
        return parentNode;
    }

    /**
     * Traverses up the tree to find all of the parent instances of type parentType
     *
     * @param parentType classes which you want to find.
     * @return List of parentType instances found.
     */
    public List getParentsOfType(Class parentType) {
        List parents = new ArrayList();
        Node parentNode = jjtGetParent();
        while (parentNode != null) {
            if (parentNode.getClass() == parentType) {
                parents.add(parentNode);
            }
            parentNode = parentNode.jjtGetParent();
        }
        return parents;
    }

    public List findChildrenOfType(Class targetType) {
        List list = new ArrayList();
        findChildrenOfType(targetType, list);
        return list;
    }

    public void findChildrenOfType(Class targetType, List results) {
        findChildrenOfType(this, targetType, results, true);
    }

    public void findChildrenOfType(Class targetType, List results, boolean descendIntoNestedClasses) {
        this.findChildrenOfType(this, targetType, results, descendIntoNestedClasses);
    }

    private void findChildrenOfType(Node node, Class targetType, List results, boolean descendIntoNestedClasses) {
        if (node.getClass().equals(targetType)) {
            results.add(node);
        }

        if (!descendIntoNestedClasses) {
            if (node instanceof ASTClassOrInterfaceDeclaration && ((ASTClassOrInterfaceDeclaration)node).isNested()) {
                return;
            }

            if (node instanceof ASTClassOrInterfaceBodyDeclaration && ((ASTClassOrInterfaceBodyDeclaration)node).isAnonymousInnerClass()) {
                return;
            }
        }

        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            Node child = node.jjtGetChild(i);
            if (child.jjtGetNumChildren() > 0) {
                findChildrenOfType(child, targetType, results, descendIntoNestedClasses);
            } else {
                if (child.getClass().equals(targetType)) {
                    results.add(child);
                }
            }
        }
    }

    public void jjtSetParent(Node n) {
        parent = n;
    }

    public Node jjtGetParent() {
        return parent;
    }

    public void jjtReplaceChild(Node old, Node newNode) {
        for (int i = 0; i < children.length; i++) {
            if (children[i] == old) {
                children[i] = newNode;
                return;
            }
        }
        throw new RuntimeException("PMD INTERNAL ERROR: SimpleNode.jjtReplaceChild called to replace a node, but couldn't find the old node");
    }

    public void jjtAddChild(Node n, int i) {
        if (children == null) {
            children = new Node[i + 1];
        } else if (i >= children.length) {
            Node c[] = new Node[i + 1];
            System.arraycopy(children, 0, c, 0, children.length);
            children = c;
        }
        children[i] = n;
    }

    public Node jjtGetChild(int i) {
        return children[i];
    }

    public int jjtGetNumChildren() {
        return (children == null) ? 0 : children.length;
    }

    /**
     * Accept the visitor. *
     */
    public Object jjtAccept(JavaParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }

    /**
     * Accept the visitor. *
     */
    public Object childrenAccept(JavaParserVisitor visitor, Object data) {
        if (children != null) {
            for (int i = 0; i < children.length; ++i) {
                children[i].jjtAccept(visitor, data);
            }
        }
        return data;
    }

    /* You can override these two methods in subclasses of SimpleNode to
       customize the way the node appears when the tree is dumped.  If
       your output uses more than one line you should override
       toString(String), otherwise overriding toString() is probably all
       you need to do. */

    public String toString() {
        return JavaParserTreeConstants.jjtNodeName[id];
    }

    public String toString(String prefix) {
        return prefix + toString();
    }

    /**
     * 
     * @return a String with an XML representation of this node and its children
     */
    public String asXml() {
        String cn = getClass().getName();
        if (cn.indexOf('.') != -1) {
            cn = cn.substring(cn.lastIndexOf('.')+1);
        }

        final StringBuffer sb = new StringBuffer();
        
        sb.append('<');
        sb.append(cn);
        sb.append(" id=\"");
        sb.append(id);
        sb.append("\">");
        if (children!=null) {            
            for (int i=0;i<children.length;i++) {
                sb.append(((SimpleNode) children[i]).asXml());
            }
        }
        sb.append("</");
        sb.append(cn);
        sb.append('>');
        return sb.toString();
    }
    
    /* Override this method if you want to customize how the node dumps
       out its children. */
    public void dump(String prefix) {
        System.out.println(toString(prefix));
        dumpChildren(prefix);
    }

    protected void dumpChildren(String prefix) {
        if (children != null) {
            for (int i = 0; i < children.length; ++i) {
                SimpleNode n = (SimpleNode) children[i];
                if (n != null) {
                    n.dump(prefix + " ");
                }
            }
        }
    }


    /**
     *Traverses down the tree to find the first child instance of type childType
     *
     * @param childType class which you want to find.
     * @return Node of type childType.  Returns <code>null</code> if none found.
     */
    public Node getFirstChildOfType(Class childType) {
        return getFirstChildOfType(childType, this);
    }

    private Node getFirstChildOfType(Class childType, Node node) {
            for (int i=0;i<node.jjtGetNumChildren();i++) {
                Node n = node.jjtGetChild(i);
                if (n!=null) {
                if (n.getClass().equals(childType))
                    return n;
                Node n2 = getFirstChildOfType(childType, n);
                if (n2!=null)
                    return n2;
                }
            }
        return null;
    }

    /**
     * Finds if this node contains a child of the given type.
     * This is an utility method that uses {@link #findChildrenOfType(Class)}
     * 
     * @param type the node type to search
     * @return <code>true</code> if there is at lease on child of the given type and <code>false</code> in any other case
     */
    public final boolean containsChildOfType(Class type) {
        return !findChildrenOfType(type).isEmpty();
    }

    public List findChildNodesWithXPath(String xpathString) throws JaxenException {
        return new BaseXPath(xpathString, new DocumentNavigator()).selectNodes(this);
    }
}

