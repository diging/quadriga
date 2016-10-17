package edu.asu.spring.quadriga.web.projectblog;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckAccess;
import edu.asu.spring.quadriga.aspects.annotations.CheckAccessById;
import edu.asu.spring.quadriga.aspects.annotations.CheckPublicAccess;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.aspects.annotations.InjectProject;
import edu.asu.spring.quadriga.aspects.annotations.InjectProjectByName;
import edu.asu.spring.quadriga.aspects.annotations.ProjectIdentifier;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.impl.projectblog.ProjectBlogEntry;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.network.IJsonCreator;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.INetworkTransformationManager;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;
import edu.asu.spring.quadriga.service.projectblog.IProjectBlogEntryManager;
import edu.asu.spring.quadriga.validator.AddProjectBlogEntryValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;
import edu.asu.spring.quadriga.web.network.INetworkStatus;
import edu.asu.spring.quadriga.web.workspace.ModifyWSController;

/**
 * This controller is responsible for providing UI to create project blog entry
 * and save the entry to database table <code>tbl_projectblogentry</code>.
 *
 * @author PawanMahalle
 */
@Controller
public class AddProjectBlogEntryController {

    @Autowired
    private IUserManager userManager;

    @Autowired
    private INetworkManager networkmanager;

    @Autowired
    private AddProjectBlogEntryValidator validator;

    @Autowired
    private IProjectBlogEntryManager projectBlogEntryManager;

    @Autowired
    private INetworkTransformationManager transformationManager;

    @Autowired
    private IJsonCreator jsonCreator;

    private static final Logger logger = LoggerFactory.getLogger(AddProjectBlogEntryController.class);

    /**
     * Attach the custom validator to the Spring context
     */
    @InitBinder("projectBlogEntry")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    /**
     * provides form to add project blog entry details to an authorized user.
     * Only project owners and admins are allowed to add blog entry to the
     * project.
     * 
     * @param projectUnixName
     *            The project unix name passed into url
     * @param project
     *            project instance obtained using @InjectProject annotation
     * @param projectId
     *            project id passed as request parameter from project blog page
     * @param model
     *            model object
     * @return page to add project blog entries.
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 3, userRole = {
            RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @InjectProjectByName
    @RequestMapping(value = "sites/{ProjectUnixName}/addprojectblogentry", method = RequestMethod.GET)
    public String addProjectBlogEntryForm(@ProjectIdentifier @PathVariable("ProjectUnixName") String projectUnixName,
            @InjectProject IProject project, @RequestParam("projectId") String projectId, Model model)
            throws QuadrigaStorageException, QuadrigaAccessException {
        List<INetwork> networks = networkmanager.getNetworksInProject(project.getProjectId(), INetworkStatus.APPROVED);
        // Add the critical data to model object
        model.addAttribute("projectBlogEntry", new ProjectBlogEntry());
        model.addAttribute("project", project);
        model.addAttribute("networks", networks);
        return "sites/addprojectblogentry";
    }

    /**
     * adds the project blog entry created by user to
     * <code>tbl_projectblogentry</code> and redirects the page to latest
     * project blog entries page.
     * 
     * @param projectBlogEntry
     *            {@linkplain ProjectBlogEntry} instance passed as model
     *            attribute
     * @param result
     *            result of form validation using
     *            {@linkplain AddProjectBlogEntryValidator}
     * @param projectUnixName
     *            The project unix name passed into url
     * @param project
     *            project instance obtained using @InjectProject annotation
     * @param projectId
     *            project id passed as request parameter from project blog page
     * @param principal
     *            principal object which is required to fetch information about
     *            logged in user.
     * @return model for project blog page if form entries are correct otherwise
     *         returns model for current page with error messages
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 5, userRole = {
            RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @InjectProjectByName
    @RequestMapping(value = "sites/{ProjectUnixName}/addprojectblogentry", method = RequestMethod.POST)
    public ModelAndView addProjectBlogEntryRequest(
            @Validated @ModelAttribute("projectBlogEntry") ProjectBlogEntry projectBlogEntry, BindingResult result,
            @ProjectIdentifier @PathVariable("ProjectUnixName") String projectUnixName, @InjectProject IProject project,
            @RequestParam("projectId") String projectId, Principal principal)
            throws QuadrigaStorageException, QuadrigaAccessException {

        ModelAndView model;

        if (result.hasErrors()) {
            model = new ModelAndView("sites/addprojectblogentry");
            model.getModelMap().put("project", project);

        } else {
            String username = principal.getName();
            IUser user = userManager.getUser(username);
            projectBlogEntry.setAuthor(user);

            projectBlogEntryManager.addNewProjectBlogEntry(projectBlogEntry);
            model = new ModelAndView("redirect:" + "/sites/" + project.getUnixName() + "/projectblog");

            model.getModelMap().put("project", project);
        }
        return model;
    }

    /**
     * This method is used to return a JSON string for visualizing a network
     * based on the Network id selected from the UI.
     * 
     * @param unixName
     *            Unix Name given for the Project
     * @param networkId
     *            Network Id for the network selected from the UI.
     * @param principal
     *            principal object which is required to fetch information about
     *            logged in user.
     * @param project
     *            project instance obtained using @InjectProject annotation
     *        
     * @return Returns a JSON string as a response entity based on the network
     *         selected from the UI.
     * @throws QuadrigaStorageException
     * @throws JAXBException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 4, userRole = {
            RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "sites/{projectUnixName}/visualizenetwork/{networkId}", method = RequestMethod.GET, produces = "text/plain")
    public ResponseEntity<String> visualizeNetworks(@ProjectIdentifier @PathVariable("projectUnixName") String unixName,
            @PathVariable("networkId") String networkId, Principal principal,
            @CheckAccess @InjectProject IProject project,@CheckAccessById @RequestParam("projectId") String projectId) {
        ITransformedNetwork transformedNetwork = null;
        try {
            transformedNetwork = transformationManager.getTransformedNetwork(networkId);
        } catch (QuadrigaStorageException qse) {

            logger.error("Error:" + qse);
            return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String json = null;
        if (transformedNetwork != null) {
            json = jsonCreator.getJson(transformedNetwork.getNodes(), transformedNetwork.getLinks());
        } else {
            return new ResponseEntity<String>(json, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>(json, HttpStatus.OK);
    }
}
