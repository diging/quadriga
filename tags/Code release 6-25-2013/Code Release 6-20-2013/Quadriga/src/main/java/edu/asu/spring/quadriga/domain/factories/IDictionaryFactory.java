package edu.asu.spring.quadriga.domain.factories;
import edu.asu.spring.quadriga.domain.IDictionary;


/**
 * Factory interface for Dicitonary factories.
 * 
 */

public interface IDictionaryFactory {
	
	public abstract IDictionary  createDictionaryObject();
	
}
