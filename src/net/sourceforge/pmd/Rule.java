package net.sourceforge.pmd;

import java.util.*;

public interface Rule {
    public String getName();
    public String getMessage();
    public String getDescription();
    public String getExample();
    public void setName(String name);
    public void setMessage(String message);
    public void setDescription(String description);
    public void setExample(String example);
    public void apply(List astCompilationUnits, RuleContext ctx);
    public boolean hasProperty(String name);
    public void addProperty(String name, String value);
    public int getIntProperty(String name);
    public boolean getBooleanProperty(String name);
    public String getStringProperty(String name);
    public double getDoubleProperty(String name);
    public Properties getProperties();
    public boolean isInclude();
    public void setInclude(boolean include);
}
