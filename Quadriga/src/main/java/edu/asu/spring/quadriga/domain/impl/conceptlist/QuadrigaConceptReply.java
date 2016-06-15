package edu.asu.spring.quadriga.domain.impl.conceptlist;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class that would contains the response data for individual
 * vocabularies.
 * @author Lohith Dwaraka
 *
 */
@XmlRootElement(name="QuadrigaReply", namespace=XMLConstants.QUADRIGA_NAMESPACE)
public class QuadrigaConceptReply {

	private ConceptList conceptList;

	public ConceptList getConceptList() {
		return conceptList;
	}

	@XmlElement(name="conceptList", namespace=XMLConstants.QUADRIGA_NAMESPACE)
	public void setConceptList(ConceptList conceptList) {
		this.conceptList = conceptList;
	}
	
	
}
