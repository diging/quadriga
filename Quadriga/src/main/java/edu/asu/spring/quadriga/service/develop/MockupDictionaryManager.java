package edu.asu.spring.quadriga.service.develop;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IDictionaryItems;
import edu.asu.spring.quadriga.domain.implementation.Dictionary;
import edu.asu.spring.quadriga.domain.implementation.DictionaryEntry;
import edu.asu.spring.quadriga.domain.implementation.DictionaryEntryBackupXJC;
import edu.asu.spring.quadriga.service.IDictionaryManager;;

/**
 *   @Description : This class acts as a dummy Dictionary manager which adds list of Dictionary words
 *   				and their descriptions on the dictionary page.
 *   
 *   @implements  : IDictionaryManager Interface
 *   
 *   @Called By   : DictionaryController.java
 *   
 *   @author      : Lohith Dwarka
 * 
 * 
 */

//@Service
public class MockupDictionaryManager implements IDictionaryManager {

	public List<IDictionary> getDictionariesList(String userId){
		
		IDictionary diction1= new Dictionary();
		IDictionary diction2= new Dictionary();
		IDictionary diction3= new Dictionary();
		
		diction1.setName("Horse");
		diction1.setId("jdoe");
		diction1.setDescription("solid-hoofed herbivorous quadruped domesticated"+
								"since prehistoric times a padded gymnastic apparatus"+
								"on legs troops trained to fight on horseback");
		
		diction2.setName("Dog");
		diction2.setId("jdoe");
		diction2.setDescription("a member of the genus Canis (probably descended from"+ 
								"the common wolf) that has been domesticated by man since"+ 
								"prehistoric times");
		
		diction3.setName("Cat");
		diction3.setId("jdoe");
		diction3.setDescription("feline mammal usually having thick soft fur and no ability"+ 
								"to roar: domestic cats; wildcats an informal term for a youth or man");
		
		List <IDictionary> dictionaryList = new ArrayList<IDictionary>();
		dictionaryList.add(diction1);
		dictionaryList.add(diction2);
		dictionaryList.add(diction3);
		return dictionaryList;
	}
	
	public String updateDictionariesItems(Dictionary existingDictionaryList){
		return "";
	}
	
	public int deleteDictionariesItems(String dictionaryId){
		return 1;
	}
	
	public String addNewDictionary(Dictionary newDictionary){
		
		return "";
		
	}

	
	public List<IDictionaryItems> getDictionariesItems(String id){
		return null;
	}

	@Override
	public String getDictionaryName(String dictionaryid) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public DictionaryEntry callRestUri(String url, String item, String pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addNewDictionariesItems(String dictionaryId, String item,
			String id, String pos,			String owner) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteDictionariesItems(String dictionaryId, String item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateDictionariesItems(String dictionaryId, String item,
			String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
