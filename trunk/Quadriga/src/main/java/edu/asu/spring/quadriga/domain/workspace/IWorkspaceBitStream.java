package edu.asu.spring.quadriga.domain.workspace;

import java.util.Date;

import edu.asu.spring.quadriga.domain.dspace.IBitStream;

public interface IWorkspaceBitStream 
{
	public abstract IWorkSpace getWorkspace();
	
	public abstract void setWorkspace(IWorkSpace workspace);
	
	public abstract IBitStream getBitStream();
	
	public abstract void setBitStream(IBitStream bitStream);
	
	public abstract String getCreatedBy();
	
	public abstract void setCreatedBy(String createdBy);
	
	public abstract Date getCreatedDate();
	
	public abstract void setCreatedDate(Date createdDate);
	
	public abstract String getUpdatedBy();
	
	public abstract void setUpdatedBy(String updatedBy);
	
	public abstract Date getUpdatedDate();
	
	public abstract void setUpdatedDate(Date updatedDate);

}
