package edu.asu.spring.quadriga.service;

import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.dto.CollaboratorDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface ICollaboratorManager {

    public abstract void updateCollaborators(String dtoId, String collabUser,
            String collaboratorRole, String username)
            throws QuadrigaStorageException;

    public abstract void deleteCollaborators(String collaboratorListAsString,
            String dtoId) throws QuadrigaStorageException;

    /**
     * Adds a new collaborator to an object. For each role for the user, there will be an extra
     * {@link CollaboratorDTO}.
     * @param collaboratorName Username of collaborator
     * @param collabRoleList Comma-separated list of roles for the new collaborator.
     * @param dtoId Id of the object the collaborator should be added to.
     * @param userAddingCollaborator Username of user who adds the collaborator.
     * @throws QuadrigaStorageException
     */
    public abstract void addCollaborator(String collaboratorName,
            String collabRoleList, String dtoId, String userAddingCollaborator)
            throws QuadrigaStorageException;

    public abstract void addCollaborator(ICollaborator collaborator, String dtoId,
            String loggedInUser) throws QuadrigaStorageException;

    public abstract void transferOwnership(String dtoId, String oldOwner,
            String newOwner, String collabRole) throws QuadrigaException;

	public abstract List<IUser> getUsersNotCollaborating(String dtoId);

}