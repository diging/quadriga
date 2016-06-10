package edu.asu.spring.quadriga.service.passthroughproject;

import javax.xml.bind.JAXBException;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.impl.passthroughproject.PassThroughProject;
import edu.asu.spring.quadriga.domain.impl.passthroughproject.XMLInfo;
import edu.asu.spring.quadriga.domain.passthroughproject.IPassThroughProject;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.NoSuchRoleException;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IPassThroughProjectManager {

    /**
     * This method is used to create a new workspace for an external project in
     * case the external id is not present in the database. Otherwise, the
     * existing internal workspace id will be returned.
     * 
     * @param passThroughProjectInfo
     *            The {@link XMLInfo} object.
     * @param projectId
     *            The project id of the project.
     * @param user
     *            The user info object.
     * @return The workspace id for an external project.
     * @throws JAXBException
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    String retrieveOrCreateWorkspace(XMLInfo passThroughProjectInfo, String projectId,
            IUser user) throws JAXBException, QuadrigaStorageException, QuadrigaAccessException;

    /**
     * This method is used for adding the {@link PassThroughProject} object to
     * the database.
     * 
     * @param user
     *            The user info object.
     * @param project
     *            The {@link PassThroughProject} object.
     * @return The project id of the newly added project.
     * @throws QuadrigaStorageException
     */
    String addPassThroughProject(IUser user, IProject project) throws QuadrigaStorageException;

    /**
     * This method returns the internal project id for a project using the given
     * parameters.
     * 
     * @param externalProjectid
     *            The external project id of the project.
     * @param userid
     *            The user id of the user.
     * @return The internal project id.
     * @throws QuadrigaStorageException
     * @throws NoSuchRoleException
     */
    String getInternalProjectId(String externalProjectid, String userid)
            throws QuadrigaStorageException, NoSuchRoleException;

    public abstract IPassThroughProject getPassThroughProject(XMLInfo passThroughProjectInfo);

    public abstract IProject retrieveOrCreateProject(XMLInfo projectInfo, IUser user) throws QuadrigaStorageException,
            NoSuchRoleException;

    public abstract IProject getPassthroughProject(String externalProjectId, String client) throws QuadrigaStorageException;

}
