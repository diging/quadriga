package edu.asu.spring.quadriga.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.security.Principal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.passthroughproject.IPassThroughProjectManager;

@Controller
public class PassThroughProjectRestController {

    private IPassThroughProjectManager passThroughProjectManager;

    @Autowired
    private IUserManager userManager;

    @ResponseBody
    @RequestMapping(value = "rest/passthroughproject", method = RequestMethod.POST)
    public String getPassThroughProject(HttpServletRequest request,
            HttpServletResponse response, @RequestBody String xml,
            Principal principal) throws QuadrigaException,
            ParserConfigurationException, SAXException, IOException,
            JAXBException, TransformerException, QuadrigaStorageException {

        Document document = getXMLParser(xml);
        String projetId = getProjectId(document);
        String userName = getTagValue(document,"user_name");
        String userId = getTagValue(document,"user_id");
        String name = getTagValue(document,"name");
        String description = getTagValue(document,"description");
        String sender = getTagValue(document,"sender");
        
        String annotatedText = getAnnotateData(xml);
        
        
        System.out.println(projetId+" "+userName+" "+userId+" "+name+" "+description+" "+sender+" ");

        passThroughProjectManager.callQStore(annotatedText, userManager.getUser(principal.getName()));

        return null;
    }

    private String getAnnotateData(String xml) {
        
        int startIndex = xml.indexOf("<element_events");
        int endIndex = xml.indexOf("</element_events>");
        
        StringBuffer annotatedText = new StringBuffer(StringUtils.substring(xml, startIndex,endIndex));
        annotatedText.append("</element_events>");
        
        return annotatedText.toString();
    }

    private String getTagValue(Document document,String tagName) {
        Node tagNode = document.getElementsByTagName(tagName).item(0);
        return tagNode.getFirstChild().getNodeValue();
       
    }

    private Document getXMLParser(String xml)
            throws ParserConfigurationException, SAXException, IOException {

        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        DocumentBuilder b = f.newDocumentBuilder();
        Document doc = b.parse(new InputSource(new StringReader(xml)));
        doc.getDocumentElement().normalize();
        return doc;
    }

    private String getProjectId(Document document) {

        Node project = document.getElementsByTagName("project").item(0);
        NamedNodeMap projetctAttributeMap = project.getAttributes();
        Node idNode = projetctAttributeMap.getNamedItem("id");
        return idNode.getNodeValue();
    }


}
