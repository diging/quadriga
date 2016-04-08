package edu.asu.spring.quadriga.service.passthroughproject;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.passthroughproject.IPassThroughProject;
import edu.asu.spring.quadriga.exceptions.DocumentParserException;
import edu.asu.spring.quadriga.exceptions.NoSuchRoleException;
import edu.asu.spring.quadriga.exceptions.QStoreStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IPassThroughProjectManager {

    String createWorkspaceForExternalProject(String externalWorkspaceId, String externalWorkspaceName, String projectId,
            IUser user) throws JAXBException, QuadrigaStorageException, QuadrigaAccessException;

    String addPassThroughProject(String userid, IPassThroughProject project) throws QuadrigaStorageException;

    String getInternalProjectId(String externalProjectid, String userid)
            throws QuadrigaStorageException, NoSuchRoleException;

    String callQStore(String xml, IUser user)
            throws QStoreStorageException, QuadrigaStorageException, QuadrigaAccessException, JAXBException,
            ParserConfigurationException, SAXException, IOException, NoSuchRoleException, DocumentParserException;

}
