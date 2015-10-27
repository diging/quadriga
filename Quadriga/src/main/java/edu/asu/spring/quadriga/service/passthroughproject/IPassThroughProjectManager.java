package edu.asu.spring.quadriga.service.passthroughproject;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import edu.asu.spring.quadriga.domain.IUser;

public interface IPassThroughProjectManager {

    String createWorkspaceForExternalProject(String response,IUser user) throws JAXBException;

    void addPassThroughProject();

    void getPassThroughProjectDTO();

    String callQStore(String xml,IUser user) throws ParserConfigurationException, SAXException, IOException, JAXBException;
}
