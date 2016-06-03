package edu.asu.spring.quadriga.service.workbench;

import java.security.Principal;
import java.util.List;

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IModifyProjectManager {

    public abstract void deleteProjectRequest(List<String> projectIdList, Principal principal)
            throws QuadrigaStorageException;

    public abstract void assignEditorRole(String projectId, String owner) throws QuadrigaStorageException;

    public abstract void removeEditorRole(String projectId, String owner) throws QuadrigaStorageException;

    void updateProject(String projID, String projName, String projDesc, String projectAccess, String userName)
            throws QuadrigaStorageException;

    void updateProjectURL(String projID, String unixName, String userName) throws QuadrigaStorageException;

    void addNewProject(IProject project, String userName) throws QuadrigaStorageException;

}
