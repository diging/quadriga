package edu.asu.spring.quadriga.web.manageusers;

import java.security.Principal;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;

@Controller
public class DeactivateUserController {
    @Autowired 
    private IUserManager usermanager;

    @Autowired
    private MessageSource messageSource;

    /**
     * Method to handle the deactivation of a Quadriga user by an admin
     * 
     * @param sUserName The userid of the user whose account is to be deactivated
     * 
     * @return  Return to the user management page of the quadriga
     * @throws QuadrigaStorageException 
     */
    @RequestMapping(value="auth/users/deactivate/{userName}", method = RequestMethod.GET)
    public String deactivateUser(@PathVariable("userName") String sUserName, ModelMap model, Principal principal, RedirectAttributes redirectAttrs, Locale locale) throws QuadrigaStorageException {

        int success = usermanager.deactivateUser(sUserName, principal.getName());
        if (success == 1) {
            redirectAttrs.addFlashAttribute("show_success_alert", true);
            redirectAttrs.addFlashAttribute("success_alert_msg", messageSource.getMessage("users.manage.deactivate.success", new String[] {sUserName}, locale));
        } else {
            redirectAttrs.addFlashAttribute("show_error_alert", true);
            redirectAttrs.addFlashAttribute("error_alert_msg", messageSource.getMessage("users.manage.deactivate.failure", new String[] {sUserName}, locale));
        }
        return "redirect:/auth/users/manage";
    }
}
