package edu.asu.spring.quadriga.service.develop;

import java.util.ArrayList;
import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.dictionary.IDictionaryItems;
import edu.asu.spring.quadriga.domain.dictionary.IItem;
import edu.asu.spring.quadriga.domain.impl.WordpowerReply;
import edu.asu.spring.quadriga.domain.impl.WordpowerReply.DictionaryEntry;
import edu.asu.spring.quadriga.domain.impl.dictionary.Dictionary;
import edu.asu.spring.quadriga.domain.impl.dictionary.Item;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;

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
public abstract class MockupDictionaryManager implements IDictionaryManager {

	public List<IDictionary> getDictionariesList(String userId){
		
		IDictionary diction1= new Dictionary();
		IDictionary diction2= new Dictionary();
		IDictionary diction3= new Dictionary();
		
		diction1.setDictionaryName("Horse");
		diction1.setDictionaryId("jdoe");
		diction1.setDescription("solid-hoofed herbivorous quadruped domesticated"+
								"since prehistoric times a padded gymnastic apparatus"+
								"on legs troops trained to fight on horseback");
		
		diction2.setDictionaryName("Dog");
		diction2.setDictionaryId("jdoe");
		diction2.setDescription("a member of the genus Canis (probably descended from"+ 
								"the common wolf) that has been domesticated by man since"+ 
								"prehistoric times");
		
		diction3.setDictionaryName("Cat");
		diction3.setDictionaryId("jdoe");
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
	
	public void addNewDictionary(IDictionary newDictionary){
		
		
	}

	
	public List<IItem> getDictionariesItems(String id){
		return null;
	}

	@Override
	public String getDictionaryName(String dictionaryid) {
		// TODO Auto-generated method stub
		return null;
	}





	@Override
	public void addNewDictionariesItems(String dictionaryId, String item,
			String id, String pos,			String owner) {
		// TODO Auto-generated method stub
	}


	@Override
	public void updateDictionariesItems(String dictionaryId, String itemid,
			String term, String pos) {
		// TODO Auto-generated method stub
	}



	@Override
	public List<DictionaryEntry> searchWordPower(String item, String pos) {
		// TODO Auto-generated method stub
		List <WordpowerReply.DictionaryEntry> diction=null;
		return diction;
	}

	@Override
	public List<DictionaryEntry> getUpdateFromWordPower(String dictionaryId,
			String itemid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item getDictionaryItemIndex(String termId,
			Item dictionaryItems) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteDictionariesItems(String dictionaryId, String itemid,
			String ownerName) throws QuadrigaStorageException {
		// TODO Auto-generated method stub
	}

	@Override
	public List<IDictionaryItems> getDictionariesItems(String dictionaryid,
			String ownerName) throws QuadrigaStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IUser> showNonCollaboratingUsers(String collectionid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addCollaborators(ICollaborator collaborator, String dictionaryid, String userName, String sessionUser) {
		// TODO Auto-generated method stub
	}

	
	@Override
	public void deleteDictionary(String user, String dictionaryId) {
		// TODO Auto-generated method stub
	}

	
}
