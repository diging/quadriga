package edu.asu.spring.quadriga.db;

import java.util.List;

import edu.asu.spring.quadriga.domain.IDictionary;

/**
 * Interface for the DBConnectionDictionaryManager Class.
 * 
 * @author Lohith Dwaraka
 *
 */

public interface IDBConnectionDictionaryManager {
	
	public abstract List<IDictionary> getDictionaryOfUser(String userName);
}
