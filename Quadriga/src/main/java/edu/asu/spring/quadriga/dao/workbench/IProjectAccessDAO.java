package edu.asu.spring.quadriga.dao.workbench;

import java.util.List;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IProjectAccessDAO {

    /**
     * This method checks if the Unix name for a project already exists in the
     * database
     * 
     * @param unixName
     * @param projectId
     * @return true - if the unix name is already associated to the any project.
     *         false - if the unix name is not associated to any project.
     * @throws QuadrigaStorageException
     */
    public String getProjectIdByUnixName(String unixName) throws QuadrigaStorageException;

    /**
     * This method checks if the given user is a collaborator for a project and
     * has a particular collaborator role.
     * 
     * @param userName
     * @param collaboratorRole
     * @param projectId
     * @return true - if the given user is a collaborator with the specified
     *         role for the given project. false - if the give user is not a
     *         collaborator with the specified role for the given project.
     * @throws QuadrigaStorageException
     */
    public abstract boolean isCollaborator(String userName, String collaboratorRole, String projectId)
            throws QuadrigaStorageException;

    /**
     * This method checks how many projects a user owns.
     * 
     * @param userName
     * @return Number of owned projects
     * @throws QuadrigaStorageException
     */
    public int getNrOfOwnedProjects(String userName) throws QuadrigaStorageException;

    /**
     * This method checks if the given user is owner for the given project
     * 
     * @param projectId
     * @return The project owner
     * @throws QuadrigaStorageException
     */
    public String getProjectOwner(String projectId) throws QuadrigaStorageException;

    /**
     * This method checks if the given user is a collaborator associated to any
     * project
     * 
     * @param userName
     * @param collaboratorRole
     * @return true - if the given user is associated with any project with
     *         specified collaborator role. false - if the given user is not
     *         associated with any project with specified collaborator role.
     * @throws QuadrigaStorageException
     */
    public int getNrOfProjectsCollaboratingOn(String userName, String collaboratorRole) throws QuadrigaStorageException;

    public List<String> getProjectCollaboratorRoles(String userName, String projectId);

}
