package edu.asu.spring.quadriga.domain;

import java.util.List;

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;

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

	public abstract void setCollaborators(List<ICollaborator> collaborators);

	public abstract List<ICollaborator> getCollaborators();

	public abstract List<IConcept> getConcepts();

	public abstract void setConcepts(List<IConcept> concepts);
	
	public abstract List<IProject> getProjects();
	
	public abstract void setProjects(List<IProject> projects);
	
	public abstract List<IWorkSpace> getWorkspaces();
	
	public void setWorkspaces(List<IWorkSpace> workspaces);


}
