package net.sourceforge.pmd;

import net.sourceforge.pmd.ast.JavaParser;

import java.io.InputStream;
import java.io.Reader;

public interface JLSVersion {
    public JavaParser createParser(InputStream in);
    public JavaParser createParser(Reader in);
}
