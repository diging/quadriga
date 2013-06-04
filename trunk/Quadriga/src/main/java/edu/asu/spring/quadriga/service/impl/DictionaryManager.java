package edu.asu.spring.quadriga.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.IDBConnectionDictionaryManager;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.implementation.Dictionary;
import edu.asu.spring.quadriga.service.IDictionaryManager;

/**
 *   @Description : This class acts as a dummy Dictionary manager which adds list of Dictionary words
 *   				and their descriptions on the dictionary page.
 *   
 *   @implements  : IDictionaryManager Interface
 *   
 *   @Called By   : DictionaryController.java
 *   
 *   @author      : Lohith Dwaraka
 * 
 * 
 */
@Service
public class DictionaryManager implements IDictionaryManager {
	
	@Autowired
	@Qualifier("DBConnectionDictionaryManagerBean")
	private IDBConnectionDictionaryManager dbConnect;
	
	public List<IDictionary> getDictionariesList(String userId){
		
		List<IDictionary> dictionaryList = new ArrayList<IDictionary>();  
		
		dictionaryList = dbConnect.getDictionaryOfUser(userId);
		
		return dictionaryList;
	}
	
	public String updateDictionariesItems(Dictionary existingDictionaryList){
		return "";
	}
	
	public int deleteDictionariesItems(String dictionaryId){
		return 1;
	}
	
	public int addNewDictionariesItems(Dictionary newDictionary){
		
		return 1;
		
	}
	
	public IDictionary getDictionariesItems(String id){
		return null;
	}
}
