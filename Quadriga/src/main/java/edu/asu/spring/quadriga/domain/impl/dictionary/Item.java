package edu.asu.spring.quadriga.domain.impl.dictionary;

import java.util.Date;
import java.util.List;

import edu.asu.spring.quadriga.domain.dictionary.IDictionaryItems;
import edu.asu.spring.quadriga.domain.dictionary.IItem;

/**
 * This class is a dictionaryItem bean class for dictionary items
 * 
 * @author  : Lohith Dwaraka
 *
 */

public class Item implements IItem 
{
	private String term;
	private String dictionaryItemId;
	private String pos;
	private String vocabulary;
	private String description;
	private List<IDictionaryItems> itemDictionaries;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	
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
	public List<IDictionaryItems> getItemDictionaries() {
		return itemDictionaries;
	}
	@Override
	public void setItemDictionaries(List<IDictionaryItems> itemDictionaries) {
        this.itemDictionaries = itemDictionaries;		
	}
	@Override
	public String getCreatedBy() {
		return createdBy;
	}
	@Override
	public void setCreatedBy(String createdBy) {
       this.createdBy = createdBy;		
	}
	@Override
	public Date getCreatedDate() {
		return createdDate;
	}
	@Override
	public void setCreatedDate(Date createdDate) {
       this.createdDate = createdDate;		
	}
	@Override
	public String getUpdatedBy() {
		return updatedBy;
	}
	@Override
	public void setUpdatedBy(String updatedBy) {
       this.updatedBy = updatedBy;		
	}
	@Override
	public Date getUpdatedDate() {
		return updatedDate;
	}
	@Override
	public void setUpdatedDate(Date updatedDate) {
      this.updatedDate = updatedDate;		
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime
				* result
				+ ((dictionaryItemId == null) ? 0 : dictionaryItemId.hashCode());
		result = prime
				* result
				+ ((itemDictionaries == null) ? 0 : itemDictionaries.hashCode());
		result = prime * result + ((pos == null) ? 0 : pos.hashCode());
		result = prime * result + ((term == null) ? 0 : term.hashCode());
		result = prime * result
				+ ((updatedBy == null) ? 0 : updatedBy.hashCode());
		result = prime * result
				+ ((updatedDate == null) ? 0 : updatedDate.hashCode());
		result = prime * result
				+ ((vocabulary == null) ? 0 : vocabulary.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (dictionaryItemId == null) {
			if (other.dictionaryItemId != null)
				return false;
		} else if (!dictionaryItemId.equals(other.dictionaryItemId))
			return false;
		if (itemDictionaries == null) {
			if (other.itemDictionaries != null)
				return false;
		} else if (!itemDictionaries.equals(other.itemDictionaries))
			return false;
		if (pos == null) {
			if (other.pos != null)
				return false;
		} else if (!pos.equals(other.pos))
			return false;
		if (term == null) {
			if (other.term != null)
				return false;
		} else if (!term.equals(other.term))
			return false;
		if (updatedBy == null) {
			if (other.updatedBy != null)
				return false;
		} else if (!updatedBy.equals(other.updatedBy))
			return false;
		if (updatedDate == null) {
			if (other.updatedDate != null)
				return false;
		} else if (!updatedDate.equals(other.updatedDate))
			return false;
		if (vocabulary == null) {
			if (other.vocabulary != null)
				return false;
		} else if (!vocabulary.equals(other.vocabulary))
			return false;
		return true;
	}
}
