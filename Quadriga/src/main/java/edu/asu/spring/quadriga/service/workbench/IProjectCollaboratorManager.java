package edu.asu.spring.quadriga.service.workbench;

import java.util.List;

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectCollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorManager;

public interface IProjectCollaboratorManager extends ICollaboratorManager {
    /**
     * This method returns the collaborators associated with the project.
     * 
     * @param projectId
     * @return List<IProjectCollaborator>
     * @throws QuadrigaStorageException
     */
    public abstract List<IProjectCollaborator> getProjectCollaborators(String projectId)
            throws QuadrigaStorageException;

    /**
     * This method returns the names of creator of project and project admins.
     * 
     * @param project
     * @return List<IProjectCollaborator>
     */
    public abstract List<String> getProjectOwnerAndAdminNames(IProject project);
}