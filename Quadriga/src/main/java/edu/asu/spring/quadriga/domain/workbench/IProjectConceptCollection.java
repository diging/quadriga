package edu.asu.spring.quadriga.domain.workbench;

import java.util.Date;

import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;

public interface IProjectConceptCollection 
{
	public abstract IProject getProject();
	
	public abstract void setProject(IProject project);
	
	public abstract IConceptCollection getConceptCollection();
	
	public abstract void setConceptCollection(IConceptCollection conceptCollection);
	
	public abstract String getCreatedBy();
	
	public abstract void setCreatedBy(String createdBy);
	
	public abstract Date getCreatedDate();
	
	public abstract void setCreatedDate(Date createdDate);
	
	public abstract String getUpdatedBy();
	
	public abstract void setUpdatedBy(String updatedBy);

	public abstract Date getUpdatedDate();
	
	public abstract void setUpdatedDate(Date updatedDate);
}
