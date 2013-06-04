package edu.asu.spring.quadriga.domain.factories;
import edu.asu.spring.quadriga.domain.IDictionary;

/**
 * Creates a instance of IDictionary for autowired
 * 
 */

public interface IDictionaryFactory {
	
	public abstract IDictionary  createDictionaryObject();
	
}
