package edu.asu.spring.quadriga.service.passthroughproject;

import java.io.IOException;
import java.security.Principal;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IPassThroughProjectManager {

    String createWorkspaceForExternalProject(String response,IUser user) throws JAXBException;

    String addPassThroughProject(Principal principal,
            String projectName, String description, String externalProjectid,
            String externalUserId, String externalUserName, String client) throws QuadrigaStorageException ;

    void getPassThroughProjectDTO();

    String callQStore(String xml,IUser user) throws ParserConfigurationException, SAXException, IOException, JAXBException;
}
