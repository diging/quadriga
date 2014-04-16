package edu.asu.spring.quadriga.domain.dictionary;

import java.sql.Date;

import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;

public interface IWorkspaceDictionary {
	
	public abstract void setWorkspace(IWorkSpace Workspace);
    
    public abstract IWorkSpace getWorkspace();
    
    public abstract void setDictionary(IDictionary dictionary);
    
    public abstract IDictionary getDictionary();
    
    public abstract void setCreatedBy(String createdBy);
    
    public abstract String getCreatedBy();
    
    public abstract void setCreatedDate(Date createdDate);
    
    public abstract Date getCreatedDate();
    
    public abstract void setUpdatedBy(String updatedBy);
    
    public abstract String getupdatedBy();
    
    public abstract void setUpdatedDate(Date createdDate);
    
    public abstract Date getUpdatedDate();
     

}
