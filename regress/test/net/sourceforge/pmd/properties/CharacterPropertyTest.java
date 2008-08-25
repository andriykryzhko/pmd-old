package test.net.sourceforge.pmd.properties;

import org.junit.Test;

import net.sourceforge.pmd.PropertyDescriptor;
import net.sourceforge.pmd.lang.rule.properties.CharacterProperty;

/**
 * Evaluates the functionality of the CharacterProperty descriptor by testing its ability to catch creation
 * errors (illegal args), flag invalid characters, and serialize/deserialize any default values.
 *  
 * @author Brian Remedios
 */
public class CharacterPropertyTest extends AbstractPropertyDescriptorTester {

	private static final char delimiter = '|';
	private static final char[] charSet = filter(allChars.toCharArray(), delimiter);
	
	public CharacterPropertyTest() {
		super();
	}

	/**
	 * Method createValue.
	 * @param count int
	 * @return Object
	 */
	protected Object createValue(int count) {
		
		if (count == 1) return new Character(randomChar(charSet));
		
		Character[] values = new Character[count];
		for (int i=0; i<values.length; i++) values[i] = (Character)createValue(1);
		return values;
	}

	/**
	 * Method createBadValue.
	 * @param count int
	 * @return Object
	 */
	protected Object createBadValue(int count) {
		
		if (count == 1) return null;
		
		Character[] values = new Character[count];
		for (int i=0; i<values.length; i++) values[i] = (Character)createBadValue(1);
		return values;
	}
	
	 @Test
	 public void testErrorForBad() { }	// not until char properties use illegal chars
		
	
	/**
	 * Method createProperty.
	 * @param multiValue boolean
	 * @return PropertyDescriptor
	 */
	protected PropertyDescriptor createProperty(boolean multiValue) {
		
		return multiValue ?
			new CharacterProperty("testCharacter", "Test character property", new char[] {'a', 'b', 'c'}, 1.0f, delimiter) :
			new CharacterProperty("testCharacter", "Test character property", 'a', 1.0f);
	}

	/**
	 * Creates a bad property that is missing either its name or description or includes a delimiter
	 * in the set of legal values.
	 * 
	 * @param multiValue boolean
	 * @return PropertyDescriptor
	 */
	protected PropertyDescriptor createBadProperty(boolean multiValue) {
		
		return multiValue ?
			new CharacterProperty("testCharacter", "Test character property", new char[] {'a', 'b', 'c'}, 1.0f, delimiter) :
			new CharacterProperty("", "Test character property", 'a', 1.0f);
	}
	
    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(CharacterPropertyTest.class);
    }
}
