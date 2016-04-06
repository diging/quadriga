package edu.asu.spring.quadriga.service.impl.passthroughproject;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factory.passthroughproject.IPassThroughProjectFactory;
import edu.asu.spring.quadriga.domain.passthroughproject.IPassThroughProject;
import edu.asu.spring.quadriga.exceptions.NoSuchRoleException;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.passthroughproject.constants.Constants;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.passthroughproject.IPassThroughProjectDocumentReader;
import edu.asu.spring.quadriga.service.passthroughproject.IPassThroughProjectManager;

@Service
public class PassThroughProjectDocumentReader implements IPassThroughProjectDocumentReader {

    private static final Logger logger = LoggerFactory.getLogger(PassThroughProjectDocumentReader.class);

    @Autowired
    private IPassThroughProjectManager passThroughProjectManager;

    @Autowired
    private IUserManager userManager;

    @Autowired
    private IPassThroughProjectFactory passthrprojfactory;

    @Override
    public Document getXMLParser(String xml) throws ParserConfigurationException, SAXException, IOException {

        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        DocumentBuilder b = f.newDocumentBuilder();
        Document doc = b.parse(new InputSource(new StringReader(xml)));
        doc.getDocumentElement().normalize();
        return doc;
    }

    @Override
    public String getProjectID(Document document, String userid) throws QuadrigaStorageException, NoSuchRoleException {

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

        IPassThroughProject project = passthrprojfactory.createPassThroughProjectObject();
        project.setExternalProjectid(externalProjectId);
        project.setExternalUserName(externalUserName);
        project.setExternalUserId(externalUserId);
        project.setProjectName(name);
        project.setDescription(description);
        project.setClient(sender);

        String projectId = processProject(userid, project);
        return projectId;
    }

    @Override
    public String getWorsapceID(Document document, String projectId, String userid)
            throws JAXBException, QuadrigaStorageException, QuadrigaAccessException {

        String workspaceName = getTagValue(document, "workspace");
        if (workspaceName == null) {
            workspaceName = Constants.DEFAULT_WORKSPACE_NAME;
        }

        String externalWorkspaceId = getTagId(document, "workspace");
        if (externalWorkspaceId == null) {
            externalWorkspaceId = Constants.DEFAULT_WORKSPACE_ID;
        }

        String internalWorkspaceId = processWorkspace(externalWorkspaceId, workspaceName, projectId, userid);

        return internalWorkspaceId;
    }

    @Override
    public String getAnnotateData(String xml) {

        int startIndex = xml.indexOf("<element_events");
        int endIndex = xml.indexOf("</element_events>");

        String annotatedText = StringUtils.substring(xml, startIndex, endIndex + 17);

        return annotatedText;
    }

    private String processWorkspace(String externalWorkspaceId, String externalWorkspaceName, String projectId,
            String userid) throws JAXBException, QuadrigaStorageException, QuadrigaAccessException {
        IUser user = userManager.getUser(userid);
        String internalWorkspaceId = passThroughProjectManager.createWorkspaceForExternalProject(externalWorkspaceId,
                externalWorkspaceName, projectId, user);
        return internalWorkspaceId;
    }

    private String getTagId(Document document, String tagName) {

        try {
            Node node = document.getElementsByTagName(tagName).item(0);
            if (node == null) {
                return null;
            }
            NamedNodeMap nodeAttributeMap = node.getAttributes();
            Node idNode = nodeAttributeMap.getNamedItem("id");
            return idNode.getNodeValue();
        } catch (Exception ex) {
            logger.info("Problem while processing id for tag->" + tagName + " in xml. So returning null");
            return null;
        }
    }

    private String getTagValue(Document document, String tagName) {
        try {
            Node tagNode = document.getElementsByTagName(tagName).item(0);
            return tagNode != null ? tagNode.getFirstChild().getNodeValue() : null;
        } catch (Exception ex) {
            logger.info("Exception occurred while processsing tag ->" + tagName + ". So returning null");
            return null;
        }
    }

    private String processProject(String userid, IPassThroughProject project) throws QuadrigaStorageException, NoSuchRoleException {

        String internalProjetid = passThroughProjectManager.getInternalProjectId(project.getExternalProjectid(),
                userid);

        if (StringUtils.isEmpty(internalProjetid)) {
            return passThroughProjectManager.addPassThroughProject(userid, project);
        }
        return internalProjetid;
    }
}
