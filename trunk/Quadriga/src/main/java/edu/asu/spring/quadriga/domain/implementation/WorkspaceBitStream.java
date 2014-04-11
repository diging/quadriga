package edu.asu.spring.quadriga.domain.implementation;

import java.util.Date;
import java.util.List;

import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceBitStream;

public class WorkspaceBitStream implements IWorkspaceBitStream 
{
	private String workspaceId;
	private List<IBitStream> bitStreams;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;

	@Override
	public String getWorkspaceId() {
		return workspaceId;
	}

	@Override
	public void setWorkspaceId(String workspaceId) {
       this.workspaceId = workspaceId;
	}

	@Override
	public void setBitStreams(List<IBitStream> bitStreams) {
         this.bitStreams = bitStreams;
	}

	@Override
	public List<IBitStream> getBitStreams() {
		return bitStreams;
	}

	@Override
	public String getCreatedBy() {
		return createdBy;
	}

	@Override
	public void setCreatedBy(String createdBy) {
         this.createdBy = createdBy;
	}

	@Override
	public Date getCreatedDate() {
		return createdDate;
	}

	@Override
	public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
	}

	@Override
	public String getUpdatedBy() {
		return updatedBy;
	}

	@Override
	public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
	}

	@Override
	public Date getUpdatedDate() {
		return updatedDate;
	}

	@Override
	public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bitStreams == null) ? 0 : bitStreams.hashCode());
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result
				+ ((updatedBy == null) ? 0 : updatedBy.hashCode());
		result = prime * result
				+ ((updatedDate == null) ? 0 : updatedDate.hashCode());
		result = prime * result
				+ ((workspaceId == null) ? 0 : workspaceId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkspaceBitStream other = (WorkspaceBitStream) obj;
		if (bitStreams == null) {
			if (other.bitStreams != null)
				return false;
		} else if (!bitStreams.equals(other.bitStreams))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (updatedBy == null) {
			if (other.updatedBy != null)
				return false;
		} else if (!updatedBy.equals(other.updatedBy))
			return false;
		if (updatedDate == null) {
			if (other.updatedDate != null)
				return false;
		} else if (!updatedDate.equals(other.updatedDate))
			return false;
		if (workspaceId == null) {
			if (other.workspaceId != null)
				return false;
		} else if (!workspaceId.equals(other.workspaceId))
			return false;
		return true;
	}
}
