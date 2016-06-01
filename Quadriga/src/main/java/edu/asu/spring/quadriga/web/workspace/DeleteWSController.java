package edu.asu.spring.quadriga.web.workspace;

import java.security.Principal;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IModifyWSManager;
import edu.asu.spring.quadriga.web.login.RoleNames;
import edu.asu.spring.quadriga.web.workspace.backing.ModifyWorkspaceForm;

@Controller
public class DeleteWSController {
    
    @Autowired
    private IModifyWSManager modifyWSManger;
    
    @Autowired
    private MessageSource messageSource;


    /**
     * This calls workspaceManager to delete the workspace submitted.
     * 
     * @param projectid
     * @param req
     * @param model
     * @param principal
     * @return String - URL of the form
     * @throws QuadrigaStorageException
     * @author Karthikeyan Mohan
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({
            @ElementAccessPolicy(type = CheckedElementType.WORKSPACE, paramIndex = 0, userRole = {
                    RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN}) })
    @RequestMapping(value = "auth/workbench/deleteSingleWorkspace/{workspaceid}", method = RequestMethod.GET)
    public String deleteSingleWorkspaceRequest(@RequestParam("projectId") String myprojectid,
            @Validated @ModelAttribute("workspaceform") ModifyWorkspaceForm workspaceForm, BindingResult result,
            @PathVariable("workspaceid") String workspaceid, Principal principal, RedirectAttributes redirectAttrs,
            Locale locale) throws QuadrigaStorageException, QuadrigaAccessException {
        modifyWSManger.deleteWorkspace(workspaceid);
        
        redirectAttrs.addFlashAttribute("show_success_alert", true);
        redirectAttrs.addFlashAttribute("success_alert_msg", messageSource.getMessage("workspace.delete.success", new String[] {}, locale));

        return "redirect:/auth/workbench/projects/" + myprojectid;
    }

}
