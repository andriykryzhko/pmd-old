package test.net.sourceforge.pmd.properties;

import java.util.Map;

import net.sourceforge.pmd.PropertyDescriptor;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;
import net.sourceforge.pmd.lang.rule.properties.BooleanProperty;
import net.sourceforge.pmd.lang.rule.properties.CharacterProperty;
import net.sourceforge.pmd.lang.rule.properties.EnumeratedProperty;
import net.sourceforge.pmd.lang.rule.properties.FloatProperty;
import net.sourceforge.pmd.lang.rule.properties.IntegerProperty;
import net.sourceforge.pmd.lang.rule.properties.StringProperty;
import net.sourceforge.pmd.lang.rule.properties.TypeProperty;

/**
 * 
 * @author Brian Remedios
 */
class NonRuleWithAllPropertyTypes extends AbstractJavaRule {

	// descriptors are public to enable us to write external tests
	public static final PropertyDescriptor singleStr	= new StringProperty("singleStr", "Property with a single string value", "hello world" , 3.0f);
	public static final PropertyDescriptor multiStr		= new StringProperty("multiStr", "Property with multiple string values", new String[] {"hello", "world"}, 5.0f, '|');
	
	public static final PropertyDescriptor singleInt	= new IntegerProperty("singleInt", "Property with a single integer value", 8 , 3.0f);
	public static final PropertyDescriptor multiInt		= new IntegerProperty("multiInt", "Property with multiple integer values", new int[] {1,2,3,4}, 5.0f);
	
	public static final PropertyDescriptor singleBool	= new BooleanProperty("singleBool", "Property with a single boolean value", true, 6.0f);
	public static final PropertyDescriptor multiBool	= new BooleanProperty("multiBool", "Property with multiple boolean values", new boolean[] { true, false}, 5.0f);
	
	public static final PropertyDescriptor singleChar	= new CharacterProperty("singleChar", "Property with a single character value", 'a', 5.0f);
	public static final PropertyDescriptor multiChar	= new CharacterProperty("multiChar", "Property with multiple character values", new char[] {'a', 'e', 'i', 'o', 'u'}, 6.0f, '|');
	
	public static final PropertyDescriptor singleFloat	= new FloatProperty("singleFloat", "Property with a single float value", 9f, 10f, .9f, 5.0f);
	public static final PropertyDescriptor multiFloat	= new FloatProperty("multiFloat", "Property with multiple float values", 0f, 5f, new float[] {1,2,3}, 6.0f);
	
	public static final PropertyDescriptor singleType	= new TypeProperty("singleType", "Property with a single type value", String.class, new String[] { "java.lang" }, 5.0f);
	public static final PropertyDescriptor multiType	= new TypeProperty("multiType", "Property with multiple type values", new Class[] {Integer.class, Object.class}, new String[] { "java.lang" }, 6.0f);

	public static final PropertyDescriptor enumType			= new EnumeratedProperty<Class>("enumType", "Property with a enumerated choices", new String[] {"String", "Object"}, new Class[] {String.class, Object.class}, 1, 5.0f);
	public static final PropertyDescriptor multiEnumType	= new EnumeratedProperty<Class>("enumType", "Property with a enumerated choices", new String[] {"String", "Object"}, new Class[] {String.class, Object.class}, new int[] {0,1}, 5.0f);
	
	
	private static final Map<String, PropertyDescriptor> propertyDescriptorsByName = asFixedMap(new PropertyDescriptor[] {
		singleStr, multiStr, singleInt, multiInt, singleBool, multiBool,
		singleChar, multiChar, singleFloat, multiFloat, singleType, multiType,
		enumType, multiEnumType
		});	  
	
	
	public NonRuleWithAllPropertyTypes() {
		super();
	}

    protected Map<String, PropertyDescriptor> propertiesByName() {
    	return propertyDescriptorsByName;
    }
}
