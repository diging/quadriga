package edu.asu.spring.quadriga.rest;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import edu.asu.spring.quadriga.exceptions.DocumentParserException;
import edu.asu.spring.quadriga.exceptions.QStoreStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.RestException;
import edu.asu.spring.quadriga.service.IRestMessage;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.passthroughproject.IPassThroughProjectManager;

/**
 * 
 * Controller for pass through projects rest apis exposed to other clients
 *
 */
@Controller
public class PassThroughProjectRestController {

    @Autowired
    private IPassThroughProjectManager passThroughProjectManager;

    @Autowired
    private IUserManager userManager;

    @Autowired
    private IRestMessage errorMessageRest;

    @RequestMapping(value = "rest/passthroughproject", method = RequestMethod.POST)
    public ResponseEntity<String> getPassThroughProject(HttpServletRequest request,
            @RequestHeader("Accept") String accept, HttpServletResponse response, @RequestBody String xml,
            Principal principal) throws RestException {

        xml = xml.trim();
        if (xml.isEmpty()) {
            String errorMsg = errorMessageRest.getErrorMsg("Please provide XML in body of the post request.");
            return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);

        }
        String userid = principal.getName();
        String networkId = null;
        try {
            networkId = passThroughProjectManager.callQStore(xml, userManager.getUser(userid));
        } catch (QStoreStorageException | DocumentParserException | QuadrigaStorageException e) {
            String errorMsg = errorMessageRest.getErrorMsg(e.getMessage());
            return new ResponseEntity<String>(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_XML);
        httpHeaders.set("networkid", networkId);

        return new ResponseEntity<String>("Success", httpHeaders, HttpStatus.OK);
    }

}
