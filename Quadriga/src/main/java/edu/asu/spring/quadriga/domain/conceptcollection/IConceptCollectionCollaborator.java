package edu.asu.spring.quadriga.domain.conceptcollection;

import java.util.Date;

import edu.asu.spring.quadriga.domain.ICollaborator;

public interface IConceptCollectionCollaborator 
{
	public abstract IConceptCollection getConceptCollection();
	
	public abstract void setConceptCollection(IConceptCollection conceptCollection);
	
	public abstract ICollaborator getCollaborator();
	
	public abstract void setCollaborator(ICollaborator collaborator);
	
	public abstract String getCreatedBy();
	
	public abstract void setCreatedBy(String createdBy);
	
	public abstract Date getCreatedDate();
	
	public abstract void setCreatedDate(Date createdDate);
	
	public abstract String getUpdatedBy();
	
	public abstract void setUpdatedBy(String updatedBy);
	
	public abstract Date getUpdatedDate();
	
	public abstract void setUpdatedDate(Date updatedDate);
}
