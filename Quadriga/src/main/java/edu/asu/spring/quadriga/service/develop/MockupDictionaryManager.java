package edu.asu.spring.quadriga.service.develop;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.implementation.Dictionary;
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

@Service
public class MockupDictionaryManager implements IDictionaryManager {

	public ArrayList<IDictionary> getDictionaries(String userId){
		
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
		
		ArrayList <IDictionary> dictionaryList = new ArrayList<IDictionary>();
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
	
	public int addNewDictionariesItems(Dictionary newDictionary){
		
		return 1;
		
	}
	
	public IDictionary getDictionariesItems(String id){
		return null;
	}
	
}
