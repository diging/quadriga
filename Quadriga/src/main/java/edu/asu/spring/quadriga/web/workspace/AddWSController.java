package edu.asu.spring.quadriga.web.workspace;

import java.security.Principal;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.factory.workspace.IWorkspaceFactory;
import edu.asu.spring.quadriga.domain.workspace.IWorkspace;
import edu.asu.spring.quadriga.domain.workspace.impl.Workspace;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IModifyWSManager;
import edu.asu.spring.quadriga.validator.WorkspaceValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class AddWSController {
    
    @Autowired
    private IWorkspaceFactory workspaceFactory;
    
    @Autowired
    private IModifyWSManager modifyWSManger;

    @Autowired
    private WorkspaceValidator validator;
    
    @Autowired
    private MessageSource messageSource;


    /**
     * Attach the custom validator to the Spring context
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {

        binder.setValidator(validator);
    }

    /**
     * This is called on the addworkspace form load.
     * 
     * @param model
     * @return String - containing the path to addworkspace jsp page.
     * @throws QuadrigaStorageException
     * @author Kiran Kumar Batna
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
            RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN,
            RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR }) })
    @RequestMapping(value = "auth/workbench/{projectid}/workspace/add", method = RequestMethod.GET)
    public ModelAndView addWorkSpaceRequestForm(@PathVariable("projectid") String projectid)
            throws QuadrigaStorageException, QuadrigaAccessException {
        ModelAndView model;

        model = new ModelAndView("auth/workbench/workspace/addworkspace");
        model.getModelMap().put("workspace", workspaceFactory.createWorkspaceObject());
        model.getModelMap().put("wsprojectid", projectid);
        model.getModelMap().put("success", 0);
        return model;
    }

    /**
     * This calls workspace manager to add workspace details into the database.
     * 
     * @param workspace
     * @param model
     * @param principal
     * @return String - On success loads success page and on failure loads the
     *         same form with error messages.
     * @throws QuadrigaStorageException
     * @author Kiran Kumar Batna
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 3, userRole = {
            RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN,
            RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR }) })
    @RequestMapping(value = "auth/workbench/{projectid}/workspace/add", method = RequestMethod.POST)
    public String addWorkSpaceRequest(@Validated @ModelAttribute("workspace") Workspace workspace,
            BindingResult result, @PathVariable("projectid") String projectid, Model model, Principal principal,
            RedirectAttributes redirectAttrs, Locale locale) throws QuadrigaStorageException, QuadrigaAccessException {

        if (result.hasErrors()) {
            model.addAttribute("workspace", workspace);
            model.addAttribute("wsprojectid", projectid);
            model.addAttribute("show_error_alert", true);
            model.addAttribute("error_alert_msg", messageSource.getMessage("workspace.add.not.created", new Object[]{}, locale));
            
            return "auth/workbench/workspace/addworkspace";
        }

        modifyWSManger.addWorkspaceToProject((IWorkspace)workspace, projectid, principal.getName());
        redirectAttrs.addFlashAttribute("show_success_alert", true);
        redirectAttrs.addFlashAttribute("success_alert_msg", "Workspace was successfully added.");

        return "redirect:/auth/workbench/projects/" + projectid;
    }
}
