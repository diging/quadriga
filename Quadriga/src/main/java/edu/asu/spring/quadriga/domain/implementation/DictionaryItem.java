package edu.asu.spring.quadriga.domain.implementation;

import java.util.List;

import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IDictionaryItem;

/**
 * This class is a dictionaryItem bean class for dictionary items
 * 
 * @author  : Lohith Dwaraka
 *
 */

public class DictionaryItem implements IDictionaryItem 
{
	private String term;
	private String dictionaryItemId;
	private String pos;
	private String vocabulary;
	private String description;
	private List<IDictionary> dictionaries;
	
	/**
	 * getter for variable items 
	 * 
	 * @return 	Return items
	 */
	@Override
	public String getTerm() {
		return term;
	}
	/**
	 * setter for variable items 
	 * @param items
	 * 
	 */
	@Override
	public void setTerm(String term) {
		this.term=term;
	}

	/**
	 * getter for variable id 
	 * 
	 * @return id
	 */
	
	@Override
	public String getDictionaryItemId() {
		return dictionaryItemId;
	}

	/**
	 * setter for variable id 
	 * @param id
	 * 
	 */
	@Override
	public void setDictionaryItemId(String id) {
		this.dictionaryItemId=id;
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
	
	@Override
	public List<IDictionary> getDictionaries() {
		return dictionaries;
	}
	
	@Override
	public void setDictionaries(List<IDictionary> dictionaries) {
		this.dictionaries = dictionaries;
	}

}
