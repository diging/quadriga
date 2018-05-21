package edu.asu.spring.quadriga.web.admin.projects;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.spring.quadriga.exceptions.AdminRequiredException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IAdminProjectService;

@Controller
public class AddAdminController {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IAdminProjectService projectService;
    
    @RequestMapping(value = "auth/admin/projects/admin/add", method = RequestMethod.POST)
    public String addUserAsProjectAdmin(@RequestParam("projectIds") List<String> projectIds, RedirectAttributes redirectAttrs, Principal principal) throws QuadrigaStorageException{
        List<String> errors = new ArrayList<>();
        if(projectIds != null){
            projectIds.forEach(p -> {
                try {
                    projectService.addAdmin(p, principal.getName(), principal.getName());
                } catch (QuadrigaStorageException e) {
                    logger.error("Could not add admin.", e);
                    errors.add(principal.getName() + " could not be added as admin due to a storage issue.");
                } catch (AdminRequiredException e) {
                    logger.error("Could not add admin.", e);
                    errors.add("You need to be admin to complete this action.");
                }
            });
        }
        
        if (!errors.isEmpty()) {
            redirectAttrs.addFlashAttribute("show_error_alert", true);
            redirectAttrs.addFlashAttribute("error_alert_msg", String.join("<br>", errors));
        } else {
            redirectAttrs.addFlashAttribute("show_success_alert", true);
            redirectAttrs.addFlashAttribute("success_alert_msg", "Admin(s) successfully added.");
        }
        return "redirect:/auth/admin/projects";
    }
}
