package edu.asu.spring.quadriga.web.workbench;

import java.security.Principal;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.spring.quadriga.accesschecks.IProjectSecurityChecker;
import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factory.workbench.IModifyProjectFormFactory;
import edu.asu.spring.quadriga.domain.factory.workbench.IProjectFactory;
import edu.asu.spring.quadriga.domain.impl.workbench.Project;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workbench.IModifyProjectManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.validator.ProjectValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class ModifyProjectController {
    @Autowired
    private IModifyProjectManager projectManager;

    @Autowired
    private IRetrieveProjectManager retrieveProjectManager;

    @Autowired
    private IProjectFactory projectFactory;

    @Autowired
    private IProjectSecurityChecker projectSecurity;

    @Autowired
    private IUserManager userManager;

    @Autowired
    private ProjectValidator validator;

    @Autowired
    private IListWSManager wsManager;

    @Autowired
    private ICollaboratorFactory collaboratorFactory;

    @Autowired
    private IModifyProjectFormFactory projectFormFactory;

    @Resource(name = "projectconstants")
    private Properties messages;


    /**
     * Attach the custom validator to the Spring context
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }


    /**
     * This method is called during editing a project.
     * 
     * @param projectid
     *            - project internal id.
     * @param model
     * @return String - URL for project editing page.
     * @throws QuadrigaStorageException
     * @author Kiran Kumar Batna
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT,paramIndex = 1, userRole = {RoleNames.ROLE_COLLABORATOR_ADMIN,RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN} )})
    @RequestMapping(value = "auth/workbench/modifyproject/{projectid}", method = RequestMethod.GET)
    public ModelAndView updateProjectRequestForm(@PathVariable("projectid") String projectid, Principal principal)
            throws QuadrigaStorageException, QuadrigaAccessException {
        ModelAndView model = new ModelAndView("auth/workbench/modifyproject");
        IProject project = retrieveProjectManager.getProjectDetails(projectid);
        model.getModelMap().put("project", project);
        model.getModelMap().put("unixnameurl", messages.getProperty("project_unix_name.url"));
        return model;
    }

    /**
     * This method is called during editing a project.
     * 
     * @param projectid
     *            - project internal id.
     * @param project
     *            - Spring Project object.
     * @param model
     * @param principal
     * @return String - URL for project editing page.
     * @throws QuadrigaStorageException
     * @author Kiran Kumar Batna
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 3, userRole = {
            RoleNames.ROLE_COLLABORATOR_ADMIN,RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/modifyproject/{projectid}", method = RequestMethod.POST)
    public ModelAndView updateProjectRequest(@Validated @ModelAttribute("project") Project project,
            BindingResult result, @PathVariable("projectid") String projectid, Principal principal,
            RedirectAttributes redirectAttributes) throws QuadrigaStorageException, QuadrigaAccessException {
        ModelAndView model;
        String userName = principal.getName();
        if (result.hasErrors()) {
            model = new ModelAndView("auth/workbench/modifyproject");
            model.getModelMap().put("project", project);
            return model;
        }
        projectManager.updateProject(project.getProjectId(), project.getProjectName(), project.getDescription(),
                project.getProjectAccess().name(), userName);
        redirectAttributes.addFlashAttribute("show_success_alert", true);
        redirectAttributes.addFlashAttribute("success_alert_msg", "Project has been updated successfully.");
        model = new ModelAndView("redirect:/auth/workbench/projects/" + projectid);
        return model;
    }

    /**
     * This controller function would assign editor roles to project owner
     * 
     * @param projectId
     * @param model
     * @param principal
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @RequestMapping(value = "auth/workbench/assignownereditor/{projectid}", method = RequestMethod.GET)
    public String assignOwnerEditorRole(@PathVariable("projectid") String projectId, ModelMap model,
            Principal principal) throws QuadrigaStorageException, QuadrigaAccessException {
        IUser user = userManager.getUser(principal.getName());
        String userName = user.getUserName();
        projectManager.assignEditorRole(projectId, userName);
        IProject project = retrieveProjectManager.getProjectDetails(projectId);

        // retrieve all the workspaces associated with the project
        List<IWorkSpace> workspaceList = wsManager.listActiveWorkspace(projectId, userName);
        if (projectSecurity.isProjectOwner(userName, projectId)) {
            model.addAttribute("owner", 1);
        } else {
            model.addAttribute("owner", 0);
        }
        if (projectSecurity.isEditor(userName, projectId)) {
            model.addAttribute("editoraccess", 1);
        } else {
            model.addAttribute("editoraccess", 0);
        }
        model.addAttribute("project", project);
        model.addAttribute("workspaceList", workspaceList);
        return "auth/workbench/project";
    }

    /**
     * This controller function would assign editor roles to project owner
     * 
     * @param projectId
     * @param model
     * @param principal
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @RequestMapping(value = "auth/workbench/deleteownereditor/{projectid}", method = RequestMethod.GET)
    public String deleteOwnerEditorRole(@PathVariable("projectid") String projectId, ModelMap model,
            Principal principal) throws QuadrigaStorageException, QuadrigaAccessException {
        String userName = principal.getName();
        projectManager.removeEditorRole(projectId, userName);
        IProject project = retrieveProjectManager.getProjectDetails(projectId);

        // retrieve all the workspaces associated with the project
        List<IWorkSpace> workspaceList = wsManager.listActiveWorkspace(projectId, userName);
        if (projectSecurity.isProjectOwner(userName, projectId)) {
            model.addAttribute("owner", 1);
        } else {
            model.addAttribute("owner", 0);
        }
        if (projectSecurity.isEditor(userName, projectId)) {
            model.addAttribute("editoraccess", 1);
        } else {
            model.addAttribute("editoraccess", 0);
        }
        model.addAttribute("project", project);
        model.addAttribute("workspaceList", workspaceList);
        return "auth/workbench/project";
    }
    
}
