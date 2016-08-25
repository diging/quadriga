package edu.asu.spring.quadriga.web.manageusers;

import java.security.Principal;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.spring.quadriga.exceptions.QuadrigaNotificationException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.validator.AccountApprovalValidator;
import edu.asu.spring.quadriga.web.manageusers.beans.ApproveAccount;

/**
 * Controller for backing the manage users page in regards to showing existing accounts 
 * and approving/rejecting new ones.
 * 
 * @author jdamerow
 *
 */
@Controller
public class AccountApprovalController {
    
    @Autowired 
    private IUserManager usermanager;

    @Autowired
    private MessageSource messageSource;

    @InitBinder
    protected void initBinder(WebDataBinder binder) throws Exception {
        binder.setValidator(new AccountApprovalValidator());
    }

    @RequestMapping(value = "auth/users/access/handleRequest", method = RequestMethod.POST)
    public String handleApprovalRequest(Model model, @Validated @ModelAttribute("approveAccount") ApproveAccount approveAccount, BindingResult result, Locale locale, RedirectAttributes attr, Principal principal) throws QuadrigaStorageException, QuadrigaNotificationException {
        
        if (result.hasErrors()) {
            StringBuffer errors = new StringBuffer();
            for (ObjectError error : result.getAllErrors()) {
                errors.append(messageSource.getMessage(error, locale) + "<br>");
            }
            
            attr.addFlashAttribute("show_error_alert", true);
            attr.addFlashAttribute("error_alert_msg", errors.toString());
            return "redirect:/auth/users/manage";
        }
        
        if(approveAccount.getAction().equalsIgnoreCase("approve")) {
            //User Request has been approved by the admin
            StringBuilder sRoles = new StringBuilder();
            String[] roles = approveAccount.getRoles();
            for(int i = 0; i < roles.length; i++) {
                if(i==0){
                    sRoles.append(roles[i]);
                } else {
                    sRoles.append(",");
                    sRoles.append(roles[i]);
                }
            }
            usermanager.approveUserRequest(approveAccount.getUsername(), sRoles.toString(), principal.getName());
        }
        else {
            //User Request denied by the admin
            usermanager.denyUserRequest(approveAccount.getUsername(), principal.getName());
        }
        
        return "redirect:/auth/users/manage";
    }
    
}
