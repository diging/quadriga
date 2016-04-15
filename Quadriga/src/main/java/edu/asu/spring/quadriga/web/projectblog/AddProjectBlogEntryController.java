package edu.asu.spring.quadriga.web.projectblog;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
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
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.aspects.annotations.InjectProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.impl.projectblog.ProjectBlogEntry;
import edu.asu.spring.quadriga.domain.projectblog.IProjectBlogEntry;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.projectblog.IProjectBlogEntryManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.validator.AddProjectBlogEntryValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * This controller is responsible for providing UI to create project blog entry
 * and save the entry to database table <code>tbl_projectblogentry</code>.
 *
 * @author PawanMahalle
 */
@PropertySource(value = "classpath:/user.properties")
@Controller
public class AddProjectBlogEntryController {

    @Autowired
    private IUserManager userManager;

    @Autowired
    private AddProjectBlogEntryValidator validator;

    @Autowired
    private IProjectBlogEntryManager projectBlogEntryManager;

    @Autowired
    private IRetrieveProjectManager projectManager;

    /**
     * Attach the custom validator to the Spring context
     */
    @InitBinder("projectBlogEntry")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    /**
     * provides form to add project blog entry details to the authorized user.
     * Only project owners and admins are allowed to add entry to the blog.
     * 
     * @param projectUnixName
     *            The project Unix name
     * @param projectId
     *            The id assigned to project by framework
     * @param model
     *            Model instance
     * @return view to add new project blog entry
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     *             This exception is thrown when user other than project admin
     *             and owner tries to add project blog entry.
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 3, userRole = {
            RoleNames.ROLE_COLLABORATOR_ADMIN, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "sites/{ProjectUnixName}/addprojectblogentry", method = RequestMethod.GET)
    public String addProjectBlogEntryForm(@PathVariable("ProjectUnixName") String projectUnixName,
            @InjectProject(unixNameParameter = "ProjectUnixName") IProject project,
            @RequestParam("projectId") String projectId, Model model)
            throws QuadrigaStorageException, QuadrigaAccessException {

        if (project == null) {
            return "forbidden";
        }

        model.addAttribute("projectBlogEntry", new ProjectBlogEntry());
        model.addAttribute("project", project);

        return "sites/addprojectblogentry";
    }

    /**
     * adds the project blog entry created by user to
     * <code>tbl_projectblogentry</code> and redirects the page to latest
     * project blog entries page.
     * 
     * @param projectBlogEntry
     *            instance of {@linkplain IProjectBlogEntry} to be added to
     *            table
     * @param result
     * @param projectId
     * @param model
     * @param principal
     * @return
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 5, userRole = {
            RoleNames.ROLE_COLLABORATOR_ADMIN, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "sites/{ProjectUnixName}/addprojectblogentry", method = RequestMethod.POST)
    public ModelAndView addProjectBlogEntryRequest(
            @Validated @ModelAttribute("projectBlogEntry") ProjectBlogEntry projectBlogEntry, BindingResult result,
            @PathVariable("ProjectUnixName") String projectUnixName,
            @InjectProject(unixNameParameter = "ProjectUnixName") IProject project,
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
}
