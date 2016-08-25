package edu.asu.spring.quadriga.rest;

import java.io.StringWriter;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import edu.asu.spring.quadriga.domain.IProfile;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.RestException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.IUserProfileManager;

/**
 * Controller for {@link IUser} related rest APIs exposed to other clients
 * 
 * @author LohithDwaraka
 * 
 */

@Controller
public class UserRestController {

    @Autowired
    private IUserProfileManager profileManager;

    @Autowired
    private IRestVelocityFactory restVelocityFactory;

    @Autowired
    private IUserManager userManager;

    /**
     * Rest interface to fetch the user details http://<<URL>:
     * <PORT>>/quadriga/rest/userdetails/
     * http://localhost:8080/quadriga/rest/userdetails/
     * 
     * @author Lohith Dwaraka
     * @param userId
     * @param model
     * @return
     * @throws RestException
     */
    @RequestMapping(value = "rest/userdetails", method = RequestMethod.GET, produces = "application/xml")
    public ResponseEntity<String> getUserDetails(ModelMap model, Principal principal, HttpServletRequest req)
            throws RestException {

        try {
            IUser userDetails = userManager.getUser(principal.getName());
            VelocityEngine engine = restVelocityFactory.getVelocityEngine();
            List<IProfile> authFiles = profileManager.getUserProfiles(userDetails.getUserName());
            engine.init();
            Template template = engine.getTemplate("velocitytemplates/userDetails.vm");
            VelocityContext context = new VelocityContext();
            context.put("url", ServletUriComponentsBuilder.fromContextPath(req).toUriString());
            context.put("userdetails", userDetails);
            context.put("list", authFiles);
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            return new ResponseEntity<String>(writer.toString(), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new RestException(404, e);
        } catch (ParseErrorException e) {
            throw new RestException(500, e);
        } catch (MethodInvocationException e) {
            throw new RestException(500, e);
        } catch (QuadrigaStorageException e) {
            throw new RestException(500, e);
        } catch (Exception e) {
            throw new RestException(500, e);
        }

    }
}
