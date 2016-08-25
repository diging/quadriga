package edu.asu.spring.quadriga.domain.conceptcollection;

import java.util.Date;

public interface IConceptCollectionConcepts 
{
	public abstract IConceptCollection getConceptCollection();
	
	public abstract void setConceptCollection(IConceptCollection conceptCollection);
	
	public abstract IConcept getConcept();
	
	public abstract void setConcept(IConcept concept);
	
	public abstract String getCreatedBy();
	
	public abstract void setCreatedBy(String createdBy);
	
	public abstract Date getCreatedDate();
	
	public abstract void setCreatedDate(Date createdDate);
	
	public abstract String getUpdatedBy();
	
	public abstract void setUpdatedBy(String updatedBy);
	
	public abstract Date getUpdatedDate();
	
	public abstract void setUpdatedDate(Date updatedDate);

}
