package edu.asu.spring.quadriga.domain.factory.dictionary;

import edu.asu.spring.quadriga.domain.dictionary.IDictionary;

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

}
