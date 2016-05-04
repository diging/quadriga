package edu.asu.spring.quadriga.service.passthroughproject;

import javax.xml.bind.JAXBException;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.impl.passthroughproject.PassThroughProject;
import edu.asu.spring.quadriga.domain.impl.passthroughproject.PassThroughProjectInfo;
import edu.asu.spring.quadriga.domain.passthroughproject.IPassThroughProject;
import edu.asu.spring.quadriga.exceptions.NoSuchRoleException;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IPassThroughProjectManager {

    /**
     * This method is used to save the {@link PassThroughProject} object in the
     * database.
     * 
     * @param user
     *            The user info object.
     * @param project
     *            The pass through info object
     * @return The internal project id
     * @throws QuadrigaStorageException
     * @throws NoSuchRoleException
     */
    String savePassThroughProject(IUser user, IPassThroughProject project)
            throws QuadrigaStorageException, NoSuchRoleException;

    /**
     * This method will return the workspace id for an external project. If the
     * provided id does not exist in the database a new workspace entry will be
     * created and its id will be returned.
     * 
     * @param passThroughProjectInfo
     *            The {@link PassThroughProjectInfo} object.
     * @param projectId
     *            The project id of the project.
     * @param user
     *            The user info object.
     * @return The workspace id for an external project.
     * @throws JAXBException
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    String createWorkspaceForExternalProject(PassThroughProjectInfo passThroughProjectInfo, String projectId,
            IUser user) throws JAXBException, QuadrigaStorageException, QuadrigaAccessException;

    /**
     * This method will create a {@link PassThroughProject} instance from the
     * provided {@link PassThroughProjectInfo}.
     * 
     * @param passThroughProjectInfo
     *            The {@link PassThroughProjectInfo} object.
     * @return The {@link PassThroughProject} object.
     */
    IPassThroughProject getPassThroughProject(PassThroughProjectInfo passThroughProjectInfo);

}
