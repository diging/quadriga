package edu.asu.spring.quadriga.domain.factory.dictionary;

import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * Factory interface for Dictionary factories.
 * 
 */

public interface IDictionaryFactory {

	/**
	 * Create Dictionary factory object
	 * @return IDictionary
	 */
	public abstract IDictionary createDictionaryObject();
	
	public abstract IDictionary cloneDictionaryObject(IDictionary dictionary) throws QuadrigaStorageException;

}
