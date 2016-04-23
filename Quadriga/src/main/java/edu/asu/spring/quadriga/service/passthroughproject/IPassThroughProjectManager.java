package edu.asu.spring.quadriga.service.passthroughproject;

import javax.xml.bind.JAXBException;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.impl.passthroughproject.PassThroughProjectInfo;
import edu.asu.spring.quadriga.domain.passthroughproject.IPassThroughProject;
import edu.asu.spring.quadriga.exceptions.NoSuchRoleException;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IPassThroughProjectManager {

    String savePassThroughProject(IUser user, IPassThroughProject project)
            throws QuadrigaStorageException, NoSuchRoleException;

    String createWorkspaceForExternalProject(PassThroughProjectInfo passThroughProjectInfo, String projectId,
            IUser user) throws JAXBException, QuadrigaStorageException, QuadrigaAccessException;

}
