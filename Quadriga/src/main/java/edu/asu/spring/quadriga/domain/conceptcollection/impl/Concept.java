package edu.asu.spring.quadriga.domain.conceptcollection.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.conceptcollection.IConcept;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;

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
    private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	
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
    public void setConceptCollections(List<IConceptCollection> conceptCollections) {
        this.conceptCollections = conceptCollections;
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
                + ((conceptId == null) ? 0 : conceptId.hashCode());
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
        Concept other = (Concept) obj;
        if (conceptId == null) {
            if (other.conceptId != null)
                return false;
        } else if (!conceptId.equals(other.conceptId))
            return false;
        return true;
    }

	
	

}
