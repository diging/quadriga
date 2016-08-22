package edu.asu.spring.quadriga.web.workbench;

import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
import edu.asu.spring.quadriga.aspects.annotations.InjectProject;
import edu.asu.spring.quadriga.aspects.annotations.InjectProjectById;
import edu.asu.spring.quadriga.aspects.annotations.ProjectIdentifier;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.factories.IModifyCollaboratorFormFactory;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.workbench.IProjectCollaboratorManager;
import edu.asu.spring.quadriga.validator.CollaboratorFormValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaborator;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorForm;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorFormManager;

@Controller
public class ModifyProjectCollaboratorController {
    @Autowired
    private IProjectCollaboratorManager projectCollaboratorManager;

    @Autowired
    private IModifyCollaboratorFormFactory collaboratorFactory;

    @Autowired
    private IQuadrigaRoleManager roleManager;

    @Autowired
    private CollaboratorFormValidator validator;

    @Autowired
    private ModifyCollaboratorFormManager collaboratorManager;

    @Autowired
    private MessageSource messageSource;

    @InitBinder("collaboratorform")
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder, WebDataBinder validateBinder)
            throws Exception {

        validateBinder.setValidator(validator);

        binder.registerCustomEditor(List.class, "collaborators.collaboratorRoles", new PropertyEditorSupport() {

            @Override
            public void setAsText(String text) {
                String[] roleIds = text.split(",");
                List<IQuadrigaRole> roles = new ArrayList<IQuadrigaRole>();
                for (String roleId : roleIds) {
                    IQuadrigaRole role = roleManager.getQuadrigaRoleById(IQuadrigaRoleManager.PROJECT_ROLES,
                            roleId.trim());
                    roles.add(role);
                }
                setValue(roles);
            }
        });

    }

    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
            RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/{projectid}/updatecollaborators", method = RequestMethod.GET)
    @InjectProjectById
    public ModelAndView updateCollaboratorRequestForm(@ProjectIdentifier @PathVariable("projectid") String projectid,
            @InjectProject IProject project) throws QuadrigaStorageException, QuadrigaAccessException {
        ModelAndView model;
        ModifyCollaboratorForm collaboratorForm;
        List<ModifyCollaborator> collaboratorList = new ArrayList<ModifyCollaborator>();

        // create model view
        model = new ModelAndView("auth/workbench/updatecollaborators");

        // retrieve the list of Collaborators and their roles.
        collaboratorList = collaboratorManager.getProjectCollaborators(projectid);

        // fetch the roles that can be associated to the workspace collaborator
        List<IQuadrigaRole> quadrigaProjectRoles = roleManager.getQuadrigaRoles(IQuadrigaRoleManager.PROJECT_ROLES);
        quadrigaProjectRoles.remove(RoleNames.ROLE_COLLABORATOR_OWNER);
        System.out.println(quadrigaProjectRoles);

        // create a model for collaborators
        collaboratorForm = collaboratorFactory.createCollaboratorFormObject();

        collaboratorForm.setCollaborators(collaboratorList);

        // add the project collaborator roles to the model
        model.getModelMap().put("projectcollabroles", quadrigaProjectRoles);
        model.getModelMap().put("collaboratorform", collaboratorForm);
        model.getModelMap().put("myprojectid", projectid);
        model.getModel().put("myprojectname", project.getProjectName());
        model.getModelMap().put("projectdesc", project.getDescription());
        model.getModelMap().put("success", 0);

        return model;
    }

    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 3, userRole = {
            RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/workbench/{projectid}/updatecollaborators", method = RequestMethod.POST)

    @InjectProjectById
    public ModelAndView updateCollaboratorRequest(
            @Validated @ModelAttribute("collaboratorform") ModifyCollaboratorForm collaboratorForm,
            BindingResult result, @ProjectIdentifier @PathVariable("projectid") String projectid,
            @InjectProject IProject project, Principal principal, Locale locale)
            throws QuadrigaStorageException, QuadrigaAccessException {

        // create model view
        ModelAndView model = new ModelAndView("auth/workbench/updatecollaborators");
        model.getModelMap().addAttribute("project", project);

        model.getModelMap().put("collaboratorform", collaboratorForm);
        model.getModel().put("projectname", project.getProjectName());
        model.getModelMap().put("projectdesc", project.getDescription());
        model.getModelMap().put("myprojectid", projectid);

        // retrieve the collaborator roles and assign it to a map
        // fetch the roles that can be associated to the workspace
        // collaborator
        List<IQuadrigaRole> collaboratorRoles = roleManager.getQuadrigaRoles(IQuadrigaRoleManager.PROJECT_ROLES);
        model.getModelMap().put("projcollabroles", collaboratorRoles);

        if (result.hasErrors()) {
            // retrieve the project details

            model.getModelMap().addAttribute("show_error_alert", true);
            model.getModelMap().addAttribute("error_alert_msg",
                    messageSource.getMessage("collaborators.add.failure", new Object[] {}, locale));
            // add the model map

        } else {
            // fetch the user and his collaborator roles
            for (ModifyCollaborator collab : collaboratorForm.getCollaborators()) {
                StringBuilder collabRoles = new StringBuilder();
                String collabUser = collab.getUserName();
                List<IQuadrigaRole> values = collab.getCollaboratorRoles();

                // fetch the role names for the roles and form a string
                for (IQuadrigaRole role : values) {
                    collabRoles.append(",");
                    collabRoles.append(role.getDBid());
                }

                projectCollaboratorManager.updateCollaborators(projectid, collabUser,
                        collabRoles.toString().substring(1), principal.getName());
                model.getModelMap().put("myprojectid", projectid);

                model.getModelMap().addAttribute("show_success_alert", true);
                model.getModelMap().addAttribute("success_alert_msg",
                        messageSource.getMessage("collaborators.success.update", new Object[] {}, locale));
            }
        }

        List<ModifyCollaborator> projCollaborators = collaboratorManager.getProjectCollaborators(projectid);
        collaboratorForm.setCollaborators(projCollaborators);

        return model;
    }

}
