package edu.asu.spring.quadriga.rest;

import java.io.IOException;
import java.io.StringReader;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.passthroughproject.constants.Constants;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.passthroughproject.IPassThroughProjectManager;

@Controller
public class PassThroughProjectRestController {

    private static final Logger logger = LoggerFactory
            .getLogger(PassThroughProjectRestController.class);

    @Autowired
    private IPassThroughProjectManager passThroughProjectManager;

    @Autowired
    private IUserManager userManager;

    @ResponseBody
    @RequestMapping(value = "rest/passthroughproject", method = RequestMethod.POST)
    public String getPassThroughProject(HttpServletRequest request,
            HttpServletResponse response, @RequestBody String xml,
            Principal principal) throws QuadrigaException,
            ParserConfigurationException, SAXException, IOException,
            JAXBException, TransformerException, QuadrigaStorageException,
            QuadrigaAccessException {

        Document document = getXMLParser(xml);

        String projectId = processProjectInformation(document, principal);
        
        String workspaceId = processWorkspaceInformation(document, projectId, principal);

        String annotatedText = getAnnotateData(xml);

        String networkId = passThroughProjectManager.callQStore(
                workspaceId, annotatedText,
                userManager.getUser(principal.getName()));

        return null;
    }

    private String processWorkspaceInformation(Document document, String projectId, Principal principal) throws JAXBException, QuadrigaStorageException, QuadrigaAccessException {
        
        String workspaceName = getTagValue(document, "workspace");
        if(workspaceName == null){
            workspaceName = Constants.DEFAULT_WORKSPACE_NAME;
        }
        
        String externalWorkspaceId = getTagId(document, "workspace");
        if(externalWorkspaceId == null){
            externalWorkspaceId = Constants.DEFAULT_WORKSPACE_ID;
        }
        
        String internalWorkspaceId = processWorkspace(externalWorkspaceId,
                workspaceName, projectId, principal);
        
        return internalWorkspaceId;
    }

    private String processProjectInformation(Document document,
            Principal principal) throws QuadrigaStorageException {

        String externalProjectId = getTagId(document,"project");
        if(externalProjectId == null){
            externalProjectId = Constants.DEFAULT_PROJECT_ID;
        }
        
        String externalUserName = getTagValue(document, "user_name");
        if(externalUserName == null){
            externalUserName = Constants.DEFAULT_VOGONWEB_USER_NAME;
        }
        
        String externalUserId = getTagValue(document, "user_id");
        if(externalUserId == null){
            externalUserId = Constants.DEFAULT_VOGONWEB_USER_ID;
        }
        
        String name = getTagValue(document, "name");
        if(name == null){
            name = Constants.DEFAULT_PROJECT_NAME;
        }
        
        String description = getTagValue(document, "description");
        if(description == null){
            description = Constants.DEFAULT_PROJECT_DESCRIPTION;
        }
        
        String sender = getTagValue(document, "sender");
        if(sender == null){
            sender = Constants.VOGONWEB_SENDER;
        }

        String projectId = processProject(principal, externalProjectId, name,
                description, externalUserName, externalUserId, sender);
        return projectId;
    }

    private String processProject(Principal principal,
            String externalProjectid, String name, String description,
            String externalUserName, String externalUserId, String sender)
            throws QuadrigaStorageException {

        String internalProjetid = passThroughProjectManager
                .getInternalProjectId(externalProjectid, principal);

        if (StringUtils.isEmpty(internalProjetid)) {
            return passThroughProjectManager.addPassThroughProject(principal,
                    name, description, externalProjectid, externalUserId,
                    externalUserName, sender);
        }
        return internalProjetid;
    }

    private String processWorkspace(String externalWorkspaceId,
            String externalWorkspaceName, String projectId, Principal principal)
            throws JAXBException, QuadrigaStorageException,
            QuadrigaAccessException {
        // TODO Auto-generated method stub
        IUser user = userManager.getUser(principal.getName());
        String internalWorkspaceId = passThroughProjectManager
                .createWorkspaceForExternalProject(externalWorkspaceId,
                        externalWorkspaceName, projectId, user);
        return internalWorkspaceId;
    }

    private String getAnnotateData(String xml) {

        int startIndex = xml.indexOf("<element_events");
        int endIndex = xml.indexOf("</element_events>");

        String annotatedText = StringUtils.substring(xml, startIndex,
                endIndex + 17);

        return annotatedText;
    }

    private String getTagValue(Document document, String tagName) {
        try{
        Node tagNode = document.getElementsByTagName(tagName).item(0);
        return tagNode!=null ? tagNode.getFirstChild().getNodeValue() : null;
        }catch(Exception ex){
            logger.info("Exception occurred while processsing tag ->"+tagName+". So returning null");
            return null;
        }
    }

    private Document getXMLParser(String xml)
            throws ParserConfigurationException, SAXException, IOException {

        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        DocumentBuilder b = f.newDocumentBuilder();
        Document doc = b.parse(new InputSource(new StringReader(xml)));
        doc.getDocumentElement().normalize();
        return doc;
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
            logger.info("Problem while processing id for tag->"+ tagName +" in xml. So returning null");
            return null;
        }
    }

 
}
