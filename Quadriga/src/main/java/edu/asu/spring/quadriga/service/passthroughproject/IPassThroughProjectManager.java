package edu.asu.spring.quadriga.service.passthroughproject;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.passthroughproject.IPassThroughProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IPassThroughProjectManager {

    String createWorkspaceForExternalProject(String externalWorkspaceId, String externalWorkspaceName, String projectId,
            IUser user) throws JAXBException, QuadrigaStorageException, QuadrigaAccessException;

    String addPassThroughProject(String userid, IPassThroughProject project) throws QuadrigaStorageException;

    String getInternalProjectId(String externalProjectid, String userid) throws QuadrigaStorageException;

    String callQStore(String workspaceId, String xml, IUser user, String annotatedText)
            throws ParserConfigurationException, SAXException, IOException, JAXBException, QuadrigaStorageException,
            QuadrigaAccessException;

}
