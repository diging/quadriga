package edu.asu.spring.quadriga.domain.implementation;

import edu.asu.spring.quadriga.domain.IDictionaryItems;

/**
 * This class is a dictionaryItem bean class for dictionary items
 * 
 * @author  : Lohith Dwaraka
 *
 */

public class DictionaryItems implements IDictionaryItems {

	
	private String items;
	private String dictionaryid;
	private String id;
	private String pos;
	private String vocabulary;
	private String description;
	
	/**
	 * getter for variable items 
	 * 
	 * @return 	Return items
	 */
	@Override
	public String getItems() {
		return items;
	}
	/**
	 * setter for variable items 
	 * @param items
	 * 
	 */
	@Override
	public void setItems(String items) {
		this.items=items;
		
	}

	/**
	 * getter for variable dictionaryId 
	 * 
	 * @return 	Return dictionaryId
	 */
	@Override
	public String getDictionaryId() {
		return dictionaryid;
	}

	/**
	 * setter for variable dictionaryId 
	 * @param dictionaryId1
	 * 
	 */
	@Override
	public void setDictionaryId(String dictionaryId1) {
		this.dictionaryid = dictionaryId1;
	}
	
	/**
	 * getter for variable id 
	 * 
	 * @return id
	 */
	
	@Override
	public String getId() {
		return id;
	}

	/**
	 * setter for variable id 
	 * @param id
	 * 
	 */
	@Override
	public void setId(String id) {
		this.id=id;
		
	}
	
	/**
	 * getter for variable pos 
	 * 
	 * @return pos
	 */

	@Override
	public String getPos() {
		return pos;
	}

	/**
	 * setter for variable pos 
	 * @param pos
	 * 
	 */
	
	@Override
	public void setPos(String pos) {
		this.pos = pos;
	}
	
	/**
	 * getter for variable vocabulary 
	 * 
	 * @return vocabulary
	 */
	
	@Override
	public String getVocabulary() {
		return vocabulary;
	}
	/**
	 * setter for variable vocabulary 
	 * @param vocabulary
	 * 
	 */
	@Override
	public void setVocabulary(String vocabulary) {
		this.vocabulary=vocabulary;
		
	}
	
	/**
	 * getter for variable description 
	 * 
	 * @return description
	 */

	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * setter for variable description 
	 * @param description
	 * 
	 */
	
	@Override
	public void setDescription(String description) {
		this.description = description;
	}

}
