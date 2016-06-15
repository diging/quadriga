package edu.asu.spring.quadriga.web.manageusers;

import java.security.Principal;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.UserOwnsOrCollaboratesDeletionException;
import edu.asu.spring.quadriga.service.IUserManager;

@Controller
public class DeleteUserController {

    @Autowired 
    private IUserManager usermanager;

    @Autowired
    private MessageSource messageSource;

    /**
     * Controller method to delete a user.
     * @param sUserName
     * @param model
     * @param principal
     * @return
     * @throws QuadrigaStorageException
     */
    @RequestMapping(value="auth/users/delete/{userName}", method = RequestMethod.GET)
    public ModelAndView deleteUser(@PathVariable("userName") String sUserName,Principal principal, RedirectAttributes redirectAttrs, Locale locale) throws QuadrigaStorageException 
    {

        ModelAndView model;
        try {
            usermanager.deleteUser(sUserName,principal.getName());
            redirectAttrs.addFlashAttribute("show_success_alert", true);
            redirectAttrs.addFlashAttribute("success_alert_msg", messageSource.getMessage("users.manage.delete.success", new String[] {}, locale));
        } catch (UserOwnsOrCollaboratesDeletionException e) {
            redirectAttrs.addFlashAttribute("show_error_alert", true);
            redirectAttrs.addFlashAttribute("error_alert_msg", messageSource.getMessage("users.manage.delete.failure.projects_exists", new String[] {}, locale));
        }
        model = new ModelAndView("redirect:/auth/users/manage");
        
        return model;
    }
    
}
