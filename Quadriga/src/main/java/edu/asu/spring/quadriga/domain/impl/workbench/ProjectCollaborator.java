package edu.asu.spring.quadriga.domain.impl.workbench;

import java.util.Date;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectCollaborator;

@Service
public class ProjectCollaborator implements IProjectCollaborator 
{
	private IProject project;
	private ICollaborator collaborator;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;

	@Override
	public IProject getProject() {
		return project;
	}

	@Override
	public void setProject(IProject project) {
        this.project = project;		
	}

	@Override
	public ICollaborator getCollaborator() {
		return collaborator;
	}

	@Override
	public void setCollaborator(ICollaborator collaborator) {
        this.collaborator = collaborator;		
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
	public void setUpdateDate(Date updatedDate) {
        this.updatedDate = updatedDate;
	}

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result
//				+ ((collaborator == null) ? 0 : collaborator.hashCode());
//		result = prime * result
//				+ ((createdBy == null) ? 0 : createdBy.hashCode());
//		result = prime * result
//				+ ((createdDate == null) ? 0 : createdDate.hashCode());
//		//result = prime * result + ((project == null) ? 0 : project.hashCode());
//		result = prime * result
//				+ ((updatedBy == null) ? 0 : updatedBy.hashCode());
//		result = prime * result
//				+ ((updatedDate == null) ? 0 : updatedDate.hashCode());
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		ProjectCollaborator other = (ProjectCollaborator) obj;
//		if (collaborator == null) {
//			if (other.collaborator != null)
//				return false;
//		} else if (!collaborator.equals(other.collaborator))
//			return false;
//		if (createdBy == null) {
//			if (other.createdBy != null)
//				return false;
//		} else if (!createdBy.equals(other.createdBy))
//			return false;
//		if (createdDate == null) {
//			if (other.createdDate != null)
//				return false;
//		} else if (!createdDate.equals(other.createdDate))
//			return false;
////		if (project == null) {
////			if (other.project != null)
////				return false;
////		} else if (!project.equals(other.project))
////			return false;
//		if (updatedBy == null) {
//			if (other.updatedBy != null)
//				return false;
//		} else if (!updatedBy.equals(other.updatedBy))
//			return false;
//		if (updatedDate == null) {
//			if (other.updatedDate != null)
//				return false;
//		} else if (!updatedDate.equals(other.updatedDate))
//			return false;
//		return true;
//	}
}
