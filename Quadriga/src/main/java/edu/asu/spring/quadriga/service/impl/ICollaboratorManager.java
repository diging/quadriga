package edu.asu.spring.quadriga.service.impl;

import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.dto.CollaboratorDTO;
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

}