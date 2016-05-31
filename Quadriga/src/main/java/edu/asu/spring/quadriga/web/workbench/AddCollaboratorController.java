package edu.asu.spring.quadriga.web.workbench;

import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.factory.workbench.IProjectCollaboratorFactory;
import edu.asu.spring.quadriga.domain.impl.Collaborator;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectCollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workbench.IProjectCollaboratorManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.validator.CollaboratorValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class AddCollaboratorController {

    @Autowired
    private IRetrieveProjectManager retrieveprojectManager;

    @Autowired
    private IProjectCollaboratorManager projectCollaboratorManager;

    @Autowired
    private IUserManager usermanager;

    @Autowired
    private IUserFactory userFactory;

    @Autowired
    private ICollaboratorFactory collaboratorFactory;

    @Autowired
    private IQuadrigaRoleManager collaboratorRoleManager;

    @Autowired
    private IQuadrigaRoleManager quadrigaRoleManager;

    @Autowired
    private CollaboratorValidator collaboratorValidator;

    @Autowired
    private IProjectCollaboratorFactory projectCollaboratorFactory;

    private static final Logger logger = LoggerFactory.getLogger(AddCollaboratorController.class);

    @InitBinder()
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder, WebDataBinder validateBinder)
            throws Exception {

        validateBinder.setValidator(collaboratorValidator);

        binder.registerCustomEditor(IUser.class, "userObj", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {

                IUser user;
                try {
                    user = usermanager.getUser(text);
                    setValue(user);
                } catch (QuadrigaStorageException e) {
                    logger.error("collaborator validator UserObj ", e);
                }

            }
        });
        binder.registerCustomEditor(List.class, "collaboratorRoles", new PropertyEditorSupport() {

            @Override
            public void setAsText(String text) {

                String[] roleIds = text.split(",");
                List<IQuadrigaRole> roles = new ArrayList<IQuadrigaRole>();
                for (String roleId : roleIds) {
                    IQuadrigaRole role = collaboratorRoleManager.getQuadrigaRoleById(
                            IQuadrigaRoleManager.PROJECT_ROLES, roleId.trim());
                    roles.add(role);
                }
                setValue(roles);
            }
        });
    }

    @ModelAttribute
    public ICollaborator getCollaborator() {
        // IProjectCollaborator projectCollaborator =
        // projectCollaboratorFactory.createProjectCollaboratorObject();
        ICollaborator collaborator = collaboratorFactory.createCollaborator();
        // projectCollaborator.setCollaborator(collaborator);
        // //.setUserObj(userFactory.createUserObject());
        collaborator.setUserObj(userFactory.createUserObject());
        return collaborator;
    }

    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
            RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/{projectid}/addcollaborators", method = RequestMethod.GET)
    public ModelAndView displayAddCollaborator(@PathVariable("projectid") String projectid, Principal principal)
            throws QuadrigaStorageException, QuadrigaAccessException {

        ModelAndView model;

        // retrieve the project details
        IProject project = retrieveprojectManager.getProjectDetails(projectid);

        ICollaborator collaborator = collaboratorFactory.createCollaborator();
        // collaborator.setUserObj(userFactory.createUserObject());

        // IProjectCollaborator projectCollaborator =
        // projectCollaboratorFactory.createProjectCollaboratorObject();

        model = new ModelAndView("auth/workbench/addcollaborators");
        model.getModel().put("collaborator", collaborator);
        model.getModel().put("projectname", project.getProjectName());
        model.getModelMap().put("projectdesc", project.getDescription());
        model.getModelMap().put("myprojectid", projectid);

        // retrieve the collaborators who are not associated with project
        // TODO: getProjectNonCollaborators() method has not been changed for
        // mapper
        List<IUser> nonCollaboratingUsers = projectCollaboratorManager.getUsersNotCollaborating(projectid);

        // remove the restricted user
        Iterator<IUser> userIterator = nonCollaboratingUsers.iterator();
        while (userIterator.hasNext()) {
            // fetch the quadriga roles and eliminate the restricted user
            IUser user = userIterator.next();
            List<IQuadrigaRole> userQuadrigaRole = user.getQuadrigaRoles();
            for (IQuadrigaRole role : userQuadrigaRole) {
                if ((role.getId().equals(RoleNames.ROLE_QUADRIGA_RESTRICTED))
                        || (user.getUserName().equals(principal.getName()))) {
                    userIterator.remove();
                    break;
                }
            }
        }
        model.getModelMap().put("notCollaboratingUsers", nonCollaboratingUsers);

        List<IProjectCollaborator> projectCollaborators = retrieveprojectManager.getCollaboratingUsers(projectid);
        model.getModelMap().put("projectCollaborators", projectCollaborators);

        // mapping collaborator Roles to jsp and restricting ADMIN role for
        // newly added collaborator
        List<IQuadrigaRole> collaboratorRoles = collaboratorRoleManager
                .getQuadrigaRoles(IQuadrigaRoleManager.PROJECT_ROLES);

        Iterator<IQuadrigaRole> collabRoleIterator = collaboratorRoles.iterator();
        while (collabRoleIterator.hasNext()) {
            IQuadrigaRole collabRole = collabRoleIterator.next();
            if (collabRole.getId().equals(RoleNames.ROLE_COLLABORATOR_OWNER)) {
                collabRoleIterator.remove();
            }
        }

        model.getModelMap().put("possibleCollaboratorRoles", collaboratorRoles);

        return model;
    }

    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
            RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/{projectid}/addcollaborators", method = RequestMethod.POST)
    public ModelAndView addCollaborator(@PathVariable("projectid") String projectid,
            @Validated @ModelAttribute("collaborator") Collaborator collaborator, BindingResult result,
            Principal principal) throws QuadrigaStorageException, QuadrigaAccessException {

        ModelAndView model = new ModelAndView("auth/workbench/addcollaborators");

        // retrieve the project details
        IProject project = retrieveprojectManager.getProjectDetails(projectid);
        model.getModelMap().put("projectname", project.getProjectName());
        model.getModelMap().put("projectdesc", project.getDescription());
        model.getModelMap().put("myprojectid", projectid);

        if (result.hasErrors()) {
            model.getModelMap().put("collaborator", collaborator);
            model.getModelMap().addAttribute("show_error_alert", true);
            model.getModelMap().addAttribute("error_alert_msg", "Could not add collaborator.");
        } else {
            projectCollaboratorManager.addCollaborator(collaborator, projectid, principal.getName());
            model.getModelMap().put("collaborator", collaboratorFactory.createCollaborator());
            model.getModelMap().addAttribute("show_success_alert", true);
            model.getModelMap().addAttribute("success_alert_msg", "Collaborator successfully added.");
        }

        List<IUser> nonCollaboratingUsers = projectCollaboratorManager.getUsersNotCollaborating(projectid);
        // remove the restricted user
        Iterator<IUser> userIterator = nonCollaboratingUsers.iterator();
        while (userIterator.hasNext()) {
            // fetch the quadriga roles and eliminate the restricted user
            IUser user = userIterator.next();
            List<IQuadrigaRole> userQuadrigaRole = user.getQuadrigaRoles();
            for (IQuadrigaRole role : userQuadrigaRole) {
                if (role.getId().equals(RoleNames.ROLE_QUADRIGA_RESTRICTED)) {
                    userIterator.remove();
                    break;
                }
            }
        }
        model.getModelMap().put("notCollaboratingUsers", nonCollaboratingUsers);

        List<IQuadrigaRole> collaboratorRoles = new ArrayList<IQuadrigaRole>();
        collaboratorRoles = collaboratorRoleManager.getQuadrigaRoles(IQuadrigaRoleManager.PROJECT_ROLES);
        Iterator<IQuadrigaRole> collabRoleIterator = collaboratorRoles.iterator();
        while (collabRoleIterator.hasNext()) {
            IQuadrigaRole collabRole = collabRoleIterator.next();
            if (collabRole.getId().equals(RoleNames.ROLE_COLLABORATOR_OWNER)) {
                collabRoleIterator.remove();
            }
        }
        model.getModelMap().put("possibleCollaboratorRoles", collaboratorRoles);

        // List<ICollaborator> collaborators =
        // retrieveProjCollabManager.getProjectCollaborators(projectid);
        List<IProjectCollaborator> projectCollaborators = retrieveprojectManager.getCollaboratingUsers(projectid);
        model.getModelMap().put("projectCollaborators", projectCollaborators);

        return model;
    }
}
