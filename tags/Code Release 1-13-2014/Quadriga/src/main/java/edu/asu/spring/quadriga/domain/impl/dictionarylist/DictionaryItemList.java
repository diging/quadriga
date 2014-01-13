package edu.asu.spring.quadriga.domain.impl.dictionarylist;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class to contain the dictionary item list from Quadriga
 * @author Ashwin Prabhu Verleker
 *
 */
@XmlRootElement(name="dictionaryItemsList", namespace="http://www.digitalhps.org/Quadriga")
public class DictionaryItemList {
	private String dictionaryUrl;
	private String path;
	public String getPath() {
		return path;
	}
	@XmlAttribute(name="path")
	public void setPath(String path) {
		this.path = path;
	}

	private List<DictionaryItem> dictionaryItems;

	public String getDictionaryUrl() {
		return dictionaryUrl;
	}
	
	@XmlAttribute(name="wordPower-url")
	public void setDictionaryUrl(String dictionaryUrl) {
		this.dictionaryUrl = dictionaryUrl;
	}
	
	@XmlElement(name="dictionaryItem", namespace="http://www.digitalhps.org/Quadriga")
	public List<DictionaryItem> getDictionaryItems() {
		return dictionaryItems;
	}

	public void setDictionaryItems(List<DictionaryItem> dictionaryItems) {
		this.dictionaryItems = dictionaryItems;
	}

	
}
