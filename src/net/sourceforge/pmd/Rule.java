package net.sourceforge.pmd;

import java.util.List;

public interface Rule {
    public static final int LOWEST_PRIORITY = 5;
    public static final String[] PRIORITIES = {"High", "Medium High", "Medium", "Medium Low", "Low"};

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
    public void addProperty(String name, String property);
    public int getIntProperty(String name);
    public boolean getBooleanProperty(String name);
    public String getStringProperty(String name);
    public double getDoubleProperty(String name);
    public RuleProperties getProperties();
    public boolean include();
    public void setInclude(boolean include);
    public int getPriority();
    public String getPriorityName();
    public void setPriority(int priority);
}
