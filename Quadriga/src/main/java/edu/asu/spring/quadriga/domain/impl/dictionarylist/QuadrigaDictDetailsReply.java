package edu.asu.spring.quadriga.domain.impl.dictionarylist;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class that would contains the response data for individual
 * vocabularies.
 * @author Ashwin Prabhu Verleker
 *
 */
@XmlRootElement(name="QuadrigaReply", namespace="http://www.digitalhps.org/Quadriga")
public class QuadrigaDictDetailsReply {

	private DictionaryItemList dictionaryItemsList;

	public DictionaryItemList getDictionaryItemsList() {
		return dictionaryItemsList;
	}

	@XmlElement(namespace="http://www.digitalhps.org/Quadriga")
	public void setDictionaryItemsList(DictionaryItemList dictionaryItemsList) {
		this.dictionaryItemsList = dictionaryItemsList;
	}
}
