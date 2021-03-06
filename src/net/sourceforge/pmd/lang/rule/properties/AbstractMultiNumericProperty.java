package net.sourceforge.pmd.lang.rule.properties;

/**
 * 
 * @author Brian Remedios
 */
public abstract class AbstractMultiNumericProperty<T> extends AbstractNumericProperty<T> {

    /**
     * Constructor for AbstractMultiNumericProperty.
     * @param theName String
     * @param theDescription String
     * @param lower Number
     * @param upper Number
     * @param theDefault T
     * @param theUIOrder float
     */
    protected AbstractMultiNumericProperty(String theName, String theDescription, Number lower, Number upper, T theDefault, float theUIOrder) {
        super(theName, theDescription, lower, upper, theDefault, theUIOrder);
    }

    /**
     * @return boolean
     * @see net.sourceforge.pmd.PropertyDescriptor#isMultiValue()
     */
    @Override
    public boolean isMultiValue() {
        return true;
    }
    
    /**
     * @return String
     */
    protected String defaultAsString() {
        return asDelimitedString(defaultValue());
    }
}
