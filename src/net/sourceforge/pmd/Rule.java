package net.sourceforge.pmd;

import java.util.*;

public interface Rule {
    public String getName();
    public String getDescription();
    public void apply(List astCompilationUnits, RuleContext ctx);
}
