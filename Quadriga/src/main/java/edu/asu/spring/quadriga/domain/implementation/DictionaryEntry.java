package edu.asu.spring.quadriga.domain.implementation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



/**
 * This class would act as a bean to map to the word power rest API
 * 
 * @author  : Lohith Dwaraka
 *
 */
@XmlRootElement
public class DictionaryEntry {
	
	private String id;
	private String lemma;
	private String pos;
	private String description;
 	private String vocabulary;
 	private String term;
	
 	/**
	 * getter for variable id 
	 * 
	 * @return id
	 */
 	public String getId() {
		return id;
	}
	
 	/**
	 * getter for variable lemma 
	 * 
	 * @return lemma
	 */
	public String getLemma() {
		return lemma;
	}
	
	/**
	 * getter for variable pos 
	 * 
	 * @return pos
	 */
	public String getPos() {
		return pos;
	}
	
	/**
	 * getter for variable description 
	 * 
	 * @return description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * getter for variable vocabulary 
	 * 
	 * @return vocabulary
	 */
	public String getVocabulary() {
		return vocabulary;
	}
	
	/**
	 * getter for variable term 
	 * 
	 * @return term
	 */
	
	public String getTerm() {
		return term;
	}
	
	
	/**
	 * setter for variable id from the rest 
	 * @param id
	 * @return 	void
	 */
	
	@XmlElement
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * setter for variable lemma from the rest 
	 * @param lemma
	 * @return 	void
	 */
	
	@XmlElement
	public void setLemma(String lemma) {
		this.lemma= lemma;
	}
	
	
	/**
	 * setter for variable pos from the rest 
	 * @param pos
	 * @return 	void
	 */
	
	@XmlElement
	public void setPos(String pos) {
		this.pos= pos;
	}
	
	/**
	 * setter for variable description from the rest 
	 * @param description
	 * @return 	void
	 */
	
	@XmlElement
	public void setDescription(String description) {
		this.description= description;
	}
	
	/**
	 * setter for variable vocabulary from the rest 
	 * @param vocabulary
	 * @return 	void
	 */
	
	@XmlElement
	public void setVocabulary(String vocabulary) {
		this.vocabulary= vocabulary;
	}
	
	/**
	 * setter for variable term from the rest 
	 * @param term
	 * @return  void
	 */
	
	public void setTerm(String term) {
		this.term= term;
	}
}
