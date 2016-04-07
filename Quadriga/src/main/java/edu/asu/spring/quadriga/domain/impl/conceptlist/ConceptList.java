package edu.asu.spring.quadriga.domain.impl.conceptlist;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class representing concepts list
 * @author Lohith Dwaraka
 *
 */
@XmlRootElement(namespace=XMLConstants.QUADRIGA_NAMESPACE)
public class ConceptList {

	private String conceptListURL;
	private String path;
	public String getPath() {
		return path;
	}
	@XmlAttribute(name="path")
	public void setPath(String path) {
		this.path = path;
	}

	private List<Concept> concepts;
	public String getConceptListURL() {
		return conceptListURL;
	}

	@XmlAttribute(name="conceptPower-url")
	public void setConceptListURL(String conceptListURL) {
		this.conceptListURL = conceptListURL;
	}

	public List<Concept> getConcepts() {
		return concepts;
	}

	@XmlElement(name="concept", namespace=XMLConstants.QUADRIGA_NAMESPACE)
	public void setConcepts(List<Concept> concepts) {
		this.concepts = concepts;
	}
	

}
