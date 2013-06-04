package edu.asu.spring.quadriga.service;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.implementation.Dictionary;
/**
 * Interface that has methods to be implemented on the DictionaryManager class
 * 
 * @author Lohith Dwaraka
 *
 */
public interface IDictionaryManager {
	public abstract ArrayList<IDictionary> getDictionaries(String sUserId);
	
	public abstract String updateDictionariesItems(Dictionary existingDictionaryList);
	
	public abstract int deleteDictionariesItems(String dictionaryId);
	
	public abstract int addNewDictionariesItems(Dictionary newDictionary);
	
	public abstract IDictionary getDictionariesItems(String id);
}
