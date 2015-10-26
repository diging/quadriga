package edu.asu.spring.quadriga.rest;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.passthroughproject.IPassThroughProjectManager;

public class PassThroughProjectRestController {
	
    @Autowired
    private IPassThroughProjectManager passThroughProjectManager;
    
    @Autowired
    private IUserManager userManager;
	
	@ResponseBody
	@RequestMapping(value = "rest/passthroughproject", method = RequestMethod.POST)
	public String getPassThroughProject(HttpServletRequest request,
			HttpServletResponse response, @RequestBody String xml,
			Principal principal) throws QuadrigaException, ParserConfigurationException, SAXException, IOException, JAXBException, TransformerException, QuadrigaStorageException {
		
		return null;
	}

	
}
