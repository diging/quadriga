package edu.asu.spring.quadriga.web.settings;

import java.security.Principal;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckAccess;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.aspects.annotations.InjectProject;
import edu.asu.spring.quadriga.aspects.annotations.InjectProjectById;
import edu.asu.spring.quadriga.aspects.annotations.ProjectIdentifier;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.settings.impl.AboutText;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.publicwebsite.IAboutTextManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.utilities.INetworkUtils;
import edu.asu.spring.quadriga.validator.HTMLContentValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;
import edu.asu.spring.quadriga.web.network.INetworkStatus;

/**
 * This controller manages public website's about page of a project. Information
 * of title and description is editable.
 *
 * @author Rajat Aggarwal
 *
 */

@Controller
public class WebsiteAboutEditController {

    @Autowired
    private IAboutTextManager aboutTextManager;

    @Autowired
    private IRetrieveProjectManager projectManager;

    @Autowired
    private INetworkManager nwManager;

    @Autowired
    private HTMLContentValidator validator;

    @Autowired
    private INetworkUtils nwUtils;

    private final Logger logger = LoggerFactory.getLogger(WebsiteAboutEditController.class);

    /**
     * Attach the custom validator to the Spring context
     */
    @InitBinder("aboutTextBean")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
            RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/projects/{ProjectId}/settings/editabout", method = RequestMethod.GET)
    @InjectProjectById
    public String editAbout(@ProjectIdentifier @PathVariable("ProjectId") String projectId, Model model,
            Principal principal, @InjectProject IProject project) throws QuadrigaStorageException {
        model.addAttribute("project", project);
        if (aboutTextManager.getAboutTextByProjectId(projectId) == null) {
            model.addAttribute("aboutTextBean", new AboutText());
        } else {
            model.addAttribute("aboutTextBean", aboutTextManager.getAboutTextByProjectId(projectId));
        }
        model.addAttribute("networks", nwManager.getNetworksInProject(projectId, INetworkStatus.APPROVED));
        return "auth/editabout";
    }

    /**
     * . Any change made in the about project page is updated into the database
     * here and a "You successfully edited the about text" message is displayed.
     * 
     * @author Rajat Aggarwal
     *
     */

    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
            RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/projects/{ProjectId}/settings/saveabout", method = RequestMethod.POST)
    @InjectProjectById
    public ModelAndView saveAbout(@ProjectIdentifier @PathVariable("ProjectId") String projectId,
            @Validated @ModelAttribute("aboutTextBean") AboutText formBean, BindingResult result, ModelAndView model,
            Principal principal, @InjectProject IProject project) throws QuadrigaStorageException {
        model.setViewName("auth/editabout");
        List<INetwork> networks = nwManager.getNetworksInProject(projectId, INetworkStatus.APPROVED);
        model.addObject("project", project);
        model.addObject("networks", networks);
        if (result.hasErrors()) {
            model.addObject("aboutTextBean", formBean);
        } else {
            aboutTextManager.saveAbout(projectId, formBean);
            model.addObject("show_success_alert", true);
            model.addObject("success_alert_msg", "You successfully edited the about text");
        }
        return model;
    }

    /**
     * This method is used to return a JSON string for visualizing a network
     * based on the Network id selected from the UI.
     * 
     * @param projectId
     *            Unix Name given for the Project
     * @param networkId
     *            Network Id for the network selected from the UI.
     * @param principal
     *            principal object which is required to fetch information about
     *            logged in user.
     * 
     * @return Returns a JSON string as a response entity based on the network
     *         selected from the UI.
     * @throws QuadrigaStorageException
     * @throws JAXBException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 4, userRole = {
            RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/projects/{projectId}/settings/editabout/visualize/{networkId}", method = RequestMethod.GET, produces = "text/plain")
    @InjectProjectById
    public ResponseEntity<String> visualizeNetworks(@ProjectIdentifier @PathVariable("projectId") String projectid,
            @PathVariable("networkId") String networkId, Principal principal,
            @CheckAccess @InjectProject IProject project) throws QuadrigaStorageException {
        boolean isValid = false;
        try {
            isValid = nwManager.isNetworkInProject(projectid, networkId);
        } catch (QuadrigaStorageException e) {
            logger.error("Encountered an error while retrieving the json string", e);
            return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (isValid) {
            return nwUtils.sendNetworkAsResponseEntity(networkId);
        } else {
            return new ResponseEntity<String>("", HttpStatus.UNAUTHORIZED);
        }
    }
}