package edu.asu.spring.quadriga.domain.workbench;

import java.util.Date;
import java.util.List;

import edu.asu.spring.quadriga.domain.IDictionary;

public interface IProjectDictionary 
{
	public abstract String getProjectId();
	
	public abstract void setProjectId(String projectId);
	
	public abstract List<IDictionary> getDictionaries();
	
	public abstract void setDictionaries(List<IDictionary> dictionaries);
	
	public abstract String getCreatedBy();
	
	public abstract void setCreatedBy(String createdBy);
	
	public abstract Date getCreatedDate();
	
	public abstract void setCreatedDate(Date createdDate);
	
	public abstract String getUpdatedBy();
	
	public abstract void setUpdatedBy(String updatedBy);
	
	public abstract Date getUpdatedDate();
	
	public abstract void setUpdatedDate(Date updatedDate);

}
