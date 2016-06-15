package edu.asu.spring.quadriga.domain.conceptcollection;

import java.util.Date;
import java.util.List;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.workbench.IProjectConceptCollection;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceConceptCollection;

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
	
	public abstract List<IConceptCollectionConcepts> getConceptCollectionConcepts();
	
	public abstract void setConceptCollectionConcepts(List<IConceptCollectionConcepts> conceptCollectionConcepts);
	
    public abstract List<IProjectConceptCollection> getConceptCollectionProjects();
    
    public abstract void setConceptCollectionProjects(List<IProjectConceptCollection> conceptCollectionProjects);
    
    public abstract List<IWorkspaceConceptCollection> getConceptCollectionWorkspaces();
    
    public abstract void setConceptCollectionWorkspaces(List<IWorkspaceConceptCollection> conceptCollectionWorkspaces);
	
	public abstract String getCreatedBy();
	
	public abstract void setCreatedBy(String createdBy);
	
	public abstract Date getCreatedDate();
	
	public abstract void setCreatedDate(Date createdDate);
	
	public abstract String getUpdatedBy();
	
	public abstract void setUpdatedBy(String updatedBy);
	
	public abstract Date getUpdatedDate();
	
	public abstract void setUpdatedDate(Date updatedDate);

}
