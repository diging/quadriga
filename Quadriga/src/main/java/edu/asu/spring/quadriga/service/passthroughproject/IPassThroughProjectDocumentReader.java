package edu.asu.spring.quadriga.service.passthroughproject;

import java.io.IOException;
import java.security.Principal;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IPassThroughProjectDocumentReader {

	Document getXMLParser(String xml) throws ParserConfigurationException, SAXException, IOException;

	String getProjectID(Document document, Principal principal) throws QuadrigaStorageException;

	String getWorsapceID(Document document, String projectId, Principal principal)
			throws JAXBException, QuadrigaStorageException, QuadrigaAccessException;

	String getAnnotateData(String xml);

}
