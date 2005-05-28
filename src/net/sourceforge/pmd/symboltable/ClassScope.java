/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd.symboltable;

import net.sourceforge.pmd.util.Applier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassScope extends AbstractScope {

    protected Map methodNames = new HashMap();
    protected Map variableNames = new HashMap();

    // FIXME - this breaks given sufficiently nested code
    private static int anonymousInnerClassCounter = 1;
    private String className;

    public ClassScope(String className) {
        this.className = className;
        anonymousInnerClassCounter = 1;
    }

    /**
     * This is only for anonymous inner classes
     * <p/>
     * FIXME - should have name like Foo$1, not Anonymous$1
     * to get this working right, the parent scope needs
     * to be passed in when instantiating a ClassScope
     */
    public ClassScope() {
        //this.className = getParent().getEnclosingClassScope().getClassName() + "$" + String.valueOf(anonymousInnerClassCounter);
        this.className = "Anonymous$" + String.valueOf(anonymousInnerClassCounter);
        anonymousInnerClassCounter++;
    }

    public void addDeclaration(VariableNameDeclaration variableDecl) {
        if (variableNames.containsKey(variableDecl)) {
            throw new RuntimeException("Variable " + variableDecl + " is already in the symbol table");
        }
        variableNames.put(variableDecl, new ArrayList());
    }

    public NameDeclaration addVariableNameOccurrence(NameOccurrence occurrence) {
        NameDeclaration decl = findVariableHere(occurrence);
        if (decl != null && !occurrence.isThisOrSuper()) {
            List nameOccurrences = (List) variableNames.get(decl);
            nameOccurrences.add(occurrence);
        }
        return decl;
    }

    public Map getVariableDeclarations() {
        VariableUsageFinderFunction f = new VariableUsageFinderFunction(variableNames);
        Applier.apply(f, variableNames.keySet().iterator());
        return f.getUsed();
    }

    public Map getMethodDeclarations() {
        return methodNames;
    }

    public ClassScope getEnclosingClassScope() {
        return this;
    }

    public String getClassName() {
        return this.className;
    }

    public void addDeclaration(MethodNameDeclaration decl) {
        methodNames.put(decl, new ArrayList());
    }

    protected NameDeclaration findVariableHere(NameOccurrence occurrence) {
        if (occurrence.isThisOrSuper() || occurrence.getImage().equals(className)) {
            if (variableNames.isEmpty()) {
                // this could happen if you do this:
                // public class Foo {
                //  private String x = super.toString();
                // }
                return null;
            }
            // return any name declaration, since all we really want is to get the scope
            // for example, if there's a
            // public class Foo {
            //  private static final int X = 2;
            //  private int y = Foo.X;
            // }
            // we'll look up Foo just to get a handle to the class scope
            // and then we'll look up X.
            return (NameDeclaration) variableNames.keySet().iterator().next();
        }

        List images = new ArrayList();
        images.add(occurrence.getImage());
        if (occurrence.getImage().startsWith(className)) {
            images.add(clipClassName(occurrence.getImage()));
        }
        ImageFinderFunction finder = new ImageFinderFunction(images);
        Applier.apply(finder, variableNames.keySet().iterator());
        return finder.getDecl();
    }

    public String toString() {
        String res = "ClassScope:" + className + ":";
        res += "\nmethods: " + super.glomNames(methodNames.keySet().iterator());
        res += "\nvariables: " + super.glomNames(variableNames.keySet().iterator());
        return res;
    }

    private String clipClassName(String in) {
        return in.substring(in.indexOf('.') + 1);
    }
}
