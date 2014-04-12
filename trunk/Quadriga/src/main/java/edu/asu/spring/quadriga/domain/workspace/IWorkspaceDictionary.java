package edu.asu.spring.quadriga.domain.workspace;

import java.util.Date;

import edu.asu.spring.quadriga.domain.dictionary.IDictionary;

public interface IWorkspaceDictionary 
{
	public abstract IWorkSpace getWorkspace();
	
	public abstract void setWorkspace(IWorkSpace workspace);
	
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
