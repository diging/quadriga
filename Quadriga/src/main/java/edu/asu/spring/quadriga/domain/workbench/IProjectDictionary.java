package edu.asu.spring.quadriga.domain.workbench;

import java.util.Date;

import edu.asu.spring.quadriga.domain.dictionary.IDictionary;

public interface IProjectDictionary 
{
	public abstract IProject getProject();
	
	public abstract void setProject(IProject project);
	
	public abstract IDictionary getDictionary();
	
	public abstract void setDictionary(IDictionary dictionary);

	public abstract String getCreatedBy();
	
	public abstract void setCreatedBy(String createdBy);
	
	public abstract Date getCreatedDate();
	
	public abstract void setCreatedDate(Date createdDate);
	
	public abstract String getUpdatedBy();
	
	public abstract void setUpdatedBy(String updatedBy);
	
	public abstract Date getUpdatedDate();
	
	public abstract void setUpdatedDate(Date updatedDate);

}
