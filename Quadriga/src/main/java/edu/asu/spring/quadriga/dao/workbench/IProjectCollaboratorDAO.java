package edu.asu.spring.quadriga.dao.workbench;

import java.util.List;

import edu.asu.spring.quadriga.dao.ICollaboratorDAO;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.dto.ProjectCollaboratorDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IProjectCollaboratorDAO extends ICollaboratorDAO<ProjectCollaboratorDTO> {

	/**
     * Retrieves all the user who are not associated with the project as collaborators.
     * @param projectid
     * @return List<IUser> - list of users who are not associated with specified project
     *                       as collaborators.
     * @throws QuadrigaStorageException
     */
    public abstract List<IUser> getProjectNonCollaborators(String projectid) throws QuadrigaStorageException;


}
