package edu.asu.spring.quadriga.domain.implementation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;




@XmlRootElement
public class DictionaryEntry {
	
	private String id;
	private String lemma;
	private String pos;
	private String description;
 	private String vocabulary;
 	private String term;
	
 	public String getId() {
		return id;
	}
	
	public String getLemma() {
		return lemma;
	}
	public String getPos() {
		return pos;
	}
	public String getDescription() {
		return description;
	}
	public String getVocabulary() {
		return vocabulary;
	}
	
	public String getTerm() {
		return term;
	}
	
	@XmlElement
	public void setId(String id) {
		System.out.println("called me getId()");
		this.id = id;
	}
	
	@XmlElement
	public void setLemma(String lemma) {
		this.lemma= lemma;
	}
	
	@XmlElement
	public void setPos(String pos) {
		this.pos= pos;
	}
	@XmlElement
	public void setDescription(String description) {
		this.description= description;
	}
	@XmlElement
	public void setVocabulary(String vocabulary) {
		this.vocabulary= vocabulary;
	}
	
	public void setTerm(String term) {
		this.term= term;
	}
}
