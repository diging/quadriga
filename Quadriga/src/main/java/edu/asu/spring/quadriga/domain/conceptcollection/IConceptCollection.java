package edu.asu.spring.quadriga.domain.conceptcollection;

import java.util.Date;
import java.util.List;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.IWorkspace;

/**
 * @description   : interface to implement ConceptCollection.
 * 
 * @author        : Kiran Kumar Batna
 *
 */
public interface IConceptCollection 
{
	public abstract  void setConceptCollectionId(String conceptCollectionId);

	public abstract String getConceptCollectionId();
	
	public abstract void setConceptCollectionName(String conceptCollectionName);

	public abstract String getConceptCollectionName();
	
	public abstract void setDescription(String description);

	public abstract String getDescription();
	
	public abstract void setOwner(IUser owner);

	public abstract IUser getOwner();
	
	public abstract List<IConceptCollectionCollaborator> getConceptCollectionCollaborators();
	
	public abstract void setConceptCollectionCollaborators(List<IConceptCollectionCollaborator> 
	conceptCollectionCollaborators);
	
	public abstract String getCreatedBy();
	
	public abstract void setCreatedBy(String createdBy);
	
	public abstract Date getCreatedDate();
	
	public abstract void setCreatedDate(Date createdDate);
	
	public abstract String getUpdatedBy();
	
	public abstract void setUpdatedBy(String updatedBy);
	
	public abstract Date getUpdatedDate();
	
	public abstract void setUpdatedDate(Date updatedDate);

    void setWorkspaces(List<IWorkspace> workspaces);

    List<IWorkspace> getWorkspaces();

    void setProjects(List<IProject> projects);

    List<IProject> getProjects();

    void setConcepts(List<IConcept> concepts);

    List<IConcept> getConcepts();

}
