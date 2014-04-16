package edu.asu.spring.quadriga.domain.dictionary;

import java.sql.Date;

import edu.asu.spring.quadriga.domain.workbench.IProject;

public interface IProjectDictionary {
	
     public abstract void setProject(IProject project);
     
     public abstract IProject getProject();
     
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
