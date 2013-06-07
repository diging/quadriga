package edu.asu.spring.quadriga.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.IDBConnectionDictionaryManager;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IDictionaryItems;
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
	

	public String addNewDictionary(Dictionary dictionary){

		String msg = dbConnect.addDictionary(dictionary);
		
		return msg;
	}
	
	public String updateDictionariesItems(Dictionary existingDictionaryList){
		return "";
	}
	
	public int deleteDictionariesItems(String dictionaryId){
		return 1;
	}
	
	public String addNewDictionariesItems(String dictionaryId,String item,String owner){
		String msg=null;
		try {
			msg = dbConnect.addDictionaryItems(dictionaryId,item,owner);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return msg;
		
	}
	


	public List<IDictionaryItems> getDictionariesItems(String dictionaryid) {
	    
		List<IDictionaryItems> dictionaryItemList = null;
		try {
			dictionaryItemList = dbConnect.getDictionaryItemsDetails(dictionaryid);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
			
		return dictionaryItemList;
	}
	
	public String getDictionaryName(String dictionaryid) {
	    
		String dictionaryName="";
		try {
			dictionaryName = dbConnect.getDictionaryName(dictionaryid);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
			
		return dictionaryName;
	}



}
