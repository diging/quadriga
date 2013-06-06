package edu.asu.spring.quadriga.domain.implementation;

import edu.asu.spring.quadriga.domain.IDictionaryItems;

public class DictionaryItems implements IDictionaryItems {

	private String dictionaryid;
	private String items;
	
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

}
