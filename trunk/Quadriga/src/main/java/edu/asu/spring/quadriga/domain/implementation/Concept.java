package edu.asu.spring.quadriga.domain.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IConcept;
import edu.asu.spring.quadriga.domain.IConceptCollection;

/**
 * uses to create a concept and also fill in a object upon downloading from the concept power
 * @author satyaswaroop boddu
 */
@Service
public class Concept implements IConcept 
{

	private String conceptId;
	private String pos;
	private String description;
	private String lemma;
	private List<IConceptCollection> conceptCollections;
	
	@Override
	public String getConceptId() {
		return conceptId;
	}
	
	@Override
	public void setConceptId(String conceptId) {
		this.conceptId=conceptId;
	}
	
	@Override
	public String getPos() {
		return pos;
	}
	
	@Override
	public void setPos(String pos) {
		this.pos=pos;
	}
	
	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description=description;
	}
	
	@Override
	public String getLemma() {
		return lemma;
	}

	@Override
	public void setLemma(String lemma) {
		this.lemma = lemma;
	}

	@Override
	public List<IConceptCollection> getConceptCollections() {
		return conceptCollections;
	}

	@Override
	public void setConceptCollections(
			List<IConceptCollection> conceptCollections) 
	{
		this.conceptCollections = conceptCollections;
	}
}
