package edu.asu.spring.quadriga.domain.proxy;

import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.implementation.Collaborator;
import edu.asu.spring.quadriga.domain.implementation.Project;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

/**
 * This class acts a proxy to {@link Project} which can be used while getting {@link List} of {@link IProject}. {@link ProjectProxy} would not contain {@link List} of {@link IWorkSpace} and {@link List} of {@link Collaborator}
 *  
 * @author Lohith Dwaraka
 *
 */
public class ProjectProxy implements IProject 
{
	private String name;
	private String description;
	private String unixName;
	private String internalid;
	private EProjectAccessibility projectAccess;
	private IRetrieveProjectManager projectManager;
	private Project project;
	
	@Override
	public String getProjectName() {
		return this.name;
	}

	@Override
	public void setProjectName(String name) {
		this.name = name;

	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;

	}

	@Override
	public String getUnixName() {
		return this.unixName;
	}

	@Override
	public void setUnixName(String unixName) {
		this.unixName  = unixName;

	}

	@Override
	public IUser getOwner() {
		return null;
	}

	@Override
	public void setOwner(IUser owner) {

	}

	@Override
	public List<ICollaborator> getCollaborators() {
		return null;
	}

	@Override
	public void setCollaborators(List<ICollaborator> collaborators) {

	}

	@Override
	public EProjectAccessibility getProjectAccess() {
		return this.projectAccess;
	}

	@Override
	public void setProjectAccess(EProjectAccessibility projectAccess) {
		this.projectAccess = projectAccess;

	}


	@Override
	public void setProjectId(String internalid) {
		this.internalid = internalid;

	}

	@Override
	public String getProjectId() {
		return this.internalid;
	}

	@Override
	public List<IWorkSpace> getWorkspaces() {
		return null;
	}

	@Override
	public void setWorkspaces(List<IWorkSpace> workspaces) {
		
	}

	@Override
	public List<IConceptCollection> getConceptCollections() {
		return null;
	}

	@Override
	public void setConceptCollections(
			List<IConceptCollection> conceptCollections) {
	}

	@Override
	public List<IDictionary> getDictionaries() {
		return null;
	}

	@Override
	public void setDictionaries(List<IDictionary> dictionaries) {
	}


	public IRetrieveProjectManager getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(IRetrieveProjectManager projectManager) {
		this.projectManager = projectManager;
	}

}
