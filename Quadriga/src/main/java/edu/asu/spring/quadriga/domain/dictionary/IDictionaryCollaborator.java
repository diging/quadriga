package edu.asu.spring.quadriga.domain.dictionary;

import java.util.Date;

import edu.asu.spring.quadriga.domain.ICollaborator;

public interface IDictionaryCollaborator 
{
	public abstract IDictionary getDictionary();
	
	public abstract void setDictionary(IDictionary dictionary);
	
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
