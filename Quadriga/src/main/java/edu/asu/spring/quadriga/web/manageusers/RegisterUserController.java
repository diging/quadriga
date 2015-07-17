package edu.asu.spring.quadriga.web.manageusers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.UsernameExistsException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.web.manageusers.beans.AccountRequest;

@Controller
public class RegisterUserController {

    @Autowired 
    private IUserManager usermanager;

    @RequestMapping(value = "register")
    public String initRegisterPage(ModelMap model) {
        model.addAttribute("request", new AccountRequest());
        return "register";
    }
    
    @RequestMapping(value = "register-user")
    public String registerUser(ModelMap model, @ModelAttribute AccountRequest request) throws QuadrigaStorageException {
        boolean success = false;
        try {
            success = usermanager.addNewUser(request);
        } catch (UsernameExistsException e) {
           model.addAttribute("errormsg_username_in_use", "Username already in use.");
           request.setPassword("");
           request.setRepeatedPassword("");
           model.addAttribute("request", request);
           return "register";
        } catch (QuadrigaStorageException e) {
            throw e;
        }
        
        if (!success) {
            model.addAttribute("errormsg_failure", "Sorry, user could not be added.");
            request.setPassword("");
            request.setRepeatedPassword("");
            model.addAttribute("request", request);
            return "register";
        }
        
        model.addAttribute("successmsg", "User account request created. An administrator will review your request.");
        return "redirect:/login";
    }
}
