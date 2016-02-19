package edu.asu.spring.quadriga.rest;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.passthroughproject.IPassThroughProjectDocumentReader;
import edu.asu.spring.quadriga.service.passthroughproject.IPassThroughProjectManager;

@Controller
public class PassThroughProjectRestController {

    @Autowired
    private IPassThroughProjectManager passThroughProjectManager;

    @Autowired
    private IUserManager userManager;
    
    @Autowired
    private IPassThroughProjectDocumentReader passThroughProjectDocumentReader;

    @RequestMapping(value = "rest/passthroughproject", method = RequestMethod.POST)
    public ResponseEntity<String> getPassThroughProject(HttpServletRequest request, @RequestHeader("Accept") String accept,
            HttpServletResponse response, @RequestBody String xml,
            Principal principal) throws QuadrigaException,
            ParserConfigurationException, SAXException, IOException,
            JAXBException, TransformerException, QuadrigaStorageException,
            QuadrigaAccessException {

        Document document = passThroughProjectDocumentReader.getXMLParser(xml);

        String projectId = passThroughProjectDocumentReader.getProjectID(document, principal);
        
        String workspaceId = passThroughProjectDocumentReader.getWorsapceID(document, projectId, principal);

        String networkId = passThroughProjectManager.callQStore(
                workspaceId, xml,
                userManager.getUser(principal.getName()));
        
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.valueOf(accept));
        httpHeaders.set("networkid", networkId);

        return new ResponseEntity<String>("Success", httpHeaders, HttpStatus.OK);
    }

}
