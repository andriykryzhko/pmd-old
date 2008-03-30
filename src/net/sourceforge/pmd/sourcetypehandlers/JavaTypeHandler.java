package net.sourceforge.pmd.sourcetypehandlers;

import java.io.Writer;

import net.sourceforge.pmd.ast.ASTCompilationUnit;
import net.sourceforge.pmd.ast.DumpFacade;
import net.sourceforge.pmd.ast.SimpleJavaNode;
import net.sourceforge.pmd.dfa.DataFlowFacade;
import net.sourceforge.pmd.symboltable.SymbolFacade;
import net.sourceforge.pmd.typeresolution.TypeResolutionFacade;

/**
 * Implementation of VisitorsFactory for the Java AST. It uses anonymous classes
 * as adapters of the visitors to the VisitorStarter interface.
 *
 * @author pieter_van_raemdonck - Application Engineers NV/SA - www.ae.be
 */
public abstract class JavaTypeHandler implements SourceTypeHandler {

    public VisitorStarter getDataFlowFacade() {
        return new VisitorStarter() {
            public void start(Object rootNode) {
                new DataFlowFacade().initializeWith((ASTCompilationUnit) rootNode);
            }
        };
    }

    public VisitorStarter getSymbolFacade() {
        return new VisitorStarter() {
            public void start(Object rootNode) {
                new SymbolFacade().initializeWith((ASTCompilationUnit) rootNode);
            }
        };
    }
    
    public VisitorStarter getTypeResolutionFacade(final ClassLoader classLoader) {
        return new VisitorStarter() {
            public void start(Object rootNode) {
                new TypeResolutionFacade().initializeWith(classLoader, (ASTCompilationUnit) rootNode);
            }
        };
    }

    public VisitorStarter getDumpFacade(final Writer writer, final String prefix, final boolean recurse) {
        return new VisitorStarter() {
            public void start(Object rootNode) {
                new DumpFacade().initializeWith(writer, prefix, recurse, (SimpleJavaNode)rootNode);
            }
        };
    }
}
