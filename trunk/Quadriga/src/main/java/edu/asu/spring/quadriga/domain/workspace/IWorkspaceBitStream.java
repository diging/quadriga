package edu.asu.spring.quadriga.domain.workspace;

import java.util.Date;
import java.util.List;

import edu.asu.spring.quadriga.domain.IBitStream;

public interface IWorkspaceBitStream 
{
	public abstract String getWorkspaceId();
	
	public abstract void setWorkspaceId(String workspaceId);
	
	public abstract void setBitStreams(List<IBitStream> bitStreams);

	public abstract List<IBitStream> getBitStreams();
	
	public abstract String getCreatedBy();
	
	public abstract void setCreatedBy(String createdBy);
	
	public abstract Date getCreatedDate();
	
	public abstract void setCreatedDate(Date createdDate);
	
	public abstract String getUpdatedBy();
	
	public abstract void setUpdatedBy(String updatedBy);
	
	public abstract Date getUpdatedDate();
	
	public abstract void setUpdatedDate(Date updatedDate);

}
