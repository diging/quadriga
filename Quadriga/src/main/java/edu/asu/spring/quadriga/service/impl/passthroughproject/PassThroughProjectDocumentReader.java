package edu.asu.spring.quadriga.service.impl.passthroughproject;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import edu.asu.spring.quadriga.domain.factory.passthroughproject.IPassThroughProjectFactory;
import edu.asu.spring.quadriga.domain.impl.passthroughproject.PassThroughProjectInfo;
import edu.asu.spring.quadriga.domain.passthroughproject.IPassThroughProject;
import edu.asu.spring.quadriga.exceptions.DocumentParserException;
import edu.asu.spring.quadriga.passthroughproject.constants.Constants;
import edu.asu.spring.quadriga.rest.PassThroughProjectRestController;
import edu.asu.spring.quadriga.service.passthroughproject.IPassThroughProjectDocumentReader;

/**
 * 
 * This class contains utility methods to process the xml file submitted to
 * {@link PassThroughProjectRestController}
 */
@Service
public class PassThroughProjectDocumentReader implements IPassThroughProjectDocumentReader {

    @Autowired
    private IPassThroughProjectFactory passthrprojfactory;

    private Document getXMLDocument(String xml) throws ParserConfigurationException, SAXException, IOException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xml)));
        doc.getDocumentElement().normalize();
        return doc;
    }

    @Override
    public IPassThroughProject getPassThroughProject(PassThroughProjectInfo passThroughProjectInfo) {

        IPassThroughProject project = passthrprojfactory.createPassThroughProjectObject();
        project.setExternalProjectid(passThroughProjectInfo.getExternalProjectId());
        project.setExternalUserName(passThroughProjectInfo.getExternalUserName());
        project.setExternalUserId(passThroughProjectInfo.getExternalUserId());
        project.setProjectName(passThroughProjectInfo.getName());
        project.setDescription(passThroughProjectInfo.getDescription());
        project.setClient(passThroughProjectInfo.getSender());

        return project;
    }

    @Override
    public String getNetwork(String xml) {

        int startIndex = xml.indexOf("<element_events");
        int endIndex = xml.indexOf("</element_events>");

        return StringUtils.substring(xml, startIndex, endIndex + 17);
    }

    private String getTagId(Document document, String tagName) {

        Node node = document.getElementsByTagName(tagName).item(0);
        if (node == null) {
            return null;
        }
        NamedNodeMap nodeAttributeMap = node.getAttributes();
        Node idNode = nodeAttributeMap.getNamedItem("id");
        return idNode.getNodeValue();
    }

    private String getTagValue(Document document, String tagName) {
        Node tagNode = document.getElementsByTagName(tagName).item(0);
        return tagNode != null ? tagNode.getFirstChild().getNodeValue() : null;
    }

    @Override
    public PassThroughProjectInfo getPassThroughProjectInfo(String xml) throws DocumentParserException {
        Document document = null;
        try {
            document = getXMLDocument(xml);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new DocumentParserException(e);
        }

        String externalProjectId = getTagId(document, "project");
        if (externalProjectId == null) {
            externalProjectId = Constants.DEFAULT_PROJECT_ID;
        }

        String externalUserName = getTagValue(document, "user_name");
        if (externalUserName == null) {
            externalUserName = Constants.DEFAULT_VOGONWEB_USER_NAME;
        }

        String externalUserId = getTagValue(document, "user_id");
        if (externalUserId == null) {
            externalUserId = Constants.DEFAULT_VOGONWEB_USER_ID;
        }

        String name = getTagValue(document, "name");
        if (name == null) {
            name = Constants.DEFAULT_PROJECT_NAME;
        }

        String description = getTagValue(document, "description");
        if (description == null) {
            description = Constants.DEFAULT_PROJECT_DESCRIPTION;
        }

        String sender = getTagValue(document, "sender");
        if (sender == null) {
            sender = Constants.VOGONWEB_SENDER;
        }

        String workspaceName = getTagValue(document, "workspace");
        if (workspaceName == null) {
            workspaceName = Constants.DEFAULT_WORKSPACE_NAME;
        }

        String externalWorkspaceId = getTagId(document, "workspace");
        if (externalWorkspaceId == null) {
            externalWorkspaceId = Constants.DEFAULT_WORKSPACE_ID;
        }
        String networkName = getTagValue(document, "network_name");
        if (networkName == null) {
            networkName = Constants.VOGONWEB_NETWORK_NAME;
        }

        PassThroughProjectInfo info = new PassThroughProjectInfo();
        info.setExternalProjectId(externalProjectId);
        info.setExternalUserId(externalUserId);
        info.setExternalUserName(externalUserName);
        info.setName(name);
        info.setDescription(description);
        info.setSender(sender);
        info.setWorkspaceName(workspaceName);
        info.setExternalWorkspaceId(externalWorkspaceId);
        info.setNetworkName(networkName);

        return info;
    }
}
