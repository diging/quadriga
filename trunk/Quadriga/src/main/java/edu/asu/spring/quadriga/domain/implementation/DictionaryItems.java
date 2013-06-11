package edu.asu.spring.quadriga.domain.implementation;

import edu.asu.spring.quadriga.domain.IDictionaryItems;

public class DictionaryItems implements IDictionaryItems {

	
	private String items;
	private String dictionaryid;
	private String id;
	private String pos;
	private String vocabulary;
	private String description;
	
	
	@Override
	public String getItems() {
		return items;
	}

	@Override
	public void setItems(String items) {
		this.items=items;
		
	}

	@Override
	public String getDictionaryId() {
		return dictionaryid;
	}

	@Override
	public void setDictionaryId(String dictionaryId1) {
		this.dictionaryid = dictionaryId1;
	}
	
	
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id=id;
		
	}

	@Override
	public String getPos() {
		return pos;
	}

	@Override
	public void setPos(String pos) {
		this.pos = pos;
	}
	
	@Override
	public String getVocabulary() {
		return vocabulary;
	}

	@Override
	public void setVocabulary(String vocabulary) {
		this.vocabulary=vocabulary;
		
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

}
