package edu.asu.spring.quadriga.service.workbench;

import java.util.List;

import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectCollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IRetrieveProjectManager {

    public abstract IProject getProjectDetails(String projectId)
            throws QuadrigaStorageException;
    public abstract IProject getProjectDetailsByUnixName(String unixName) throws QuadrigaStorageException;

    public abstract List<IProject> getProjectList(String sUserName)
            throws QuadrigaStorageException;

    public abstract List<IProjectCollaborator> getCollaboratingUsers(String projectId) throws QuadrigaStorageException;

    public abstract List<IProject> getProjectListAsWorkspaceCollaborator(String sUserName)
            throws QuadrigaStorageException;

    public abstract List<IProject> getProjectListAsWorkspaceOwner(String sUserName)
            throws QuadrigaStorageException;

    public abstract List<IProject> getCollaboratorProjectList(String sUserName)
            throws QuadrigaStorageException;

    public abstract List<IProject> getProjectListByCollaboratorRole(String sUserName,String role)
            throws QuadrigaStorageException;

    boolean getPublicProjectWebsiteAccessibility(String unixName) 
            throws QuadrigaStorageException;
    
    boolean canAccessProjectWebsite(String unixName, String user) 
            throws QuadrigaStorageException;
    
    public abstract List<IProject> getProjectListByAccessibility(EProjectAccessibility accessibility)
            throws QuadrigaStorageException;
    
	public abstract List<IProject> getRecentProjectList(String sUserName)
			throws QuadrigaStorageException;
	
	public abstract List<IProject> getProjectListBySearchTermAndAccessiblity(String searchTerm, String accessibility) 
			throws QuadrigaStorageException;
    
	public abstract List<IProject> getProjectList() throws QuadrigaStorageException;
}
