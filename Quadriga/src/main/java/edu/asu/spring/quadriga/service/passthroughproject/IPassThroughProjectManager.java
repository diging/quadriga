package edu.asu.spring.quadriga.service.passthroughproject;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public interface IPassThroughProjectManager {

    String createWorkspaceForExternalProject(String response) throws JAXBException;

    void addPassThroughProject();

    void getPassThroughProjectDTO();

    String callQStore(String xml) throws ParserConfigurationException, SAXException, IOException, JAXBException;
}
