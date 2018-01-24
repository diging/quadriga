package edu.asu.spring.quadriga.web;

import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import edu.asu.spring.quadriga.aspects.annotations.NoAuthorizationCheck;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.web.config.social.SocialSignInStatus;

/**
 * The controller to manage the login step performed by every user in Quadriga
 * Database
 * 
 * @author Ram Kumar Kumaresan
 */
@Controller
public class LoginController {

    @Autowired
    private IUserManager userManager;

    @Autowired
    private IRetrieveProjectManager retrieveProjectManager;

    @Autowired
    private MessageSource messageSource;

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * A valid authenticated user is redirected to the home page.
     * 
     * @return Returned to the home page of Quadriga.
     * @throws QuadrigaStorageException
     */
    @NoAuthorizationCheck
    @RequestMapping(value = "auth/welcome", method = RequestMethod.GET)
    public String validUserHandle(ModelMap model, Principal principal, Authentication authentication) {

        // Get the LDAP-authenticated userid
        String sUserId = principal.getName();
        model.addAttribute("username", sUserId);
        return "redirect:home";

    }

    /**
     * User requests a login page
     * 
     * @return Redirected to the login page
     * @throws QuadrigaStorageException
     */
    @NoAuthorizationCheck
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model) throws QuadrigaStorageException {

        List<IProject> projectList = retrieveProjectManager.getProjectListByAccessibility(EProjectAccessibility.PUBLIC);
        Collections.sort(projectList, new Comparator<IProject>() {

            @Override
            public int compare(IProject o1, IProject o2) {
                return o1.getUpdatedDate().compareTo(o2.getUpdatedDate());
            }
        });
        int size = projectList.size() > 4 ? 4 : projectList.size();
        model.addAttribute("latestProjects", projectList.subList(0, size));
        return "login";

    }

    /**
     * Authentication failed. User credentials mismatch causes this request.
     * 
     * @return Redirected to login page
     */
    @NoAuthorizationCheck
    @RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
    public String loginError(ModelMap model) {
        model.addAttribute("error", "true");
        return "login";
    }
    /**
     * User attempts to register/login using the Social SignIn button
     * @param type Status of the Social SignIn attempt 
     * @return Redirected to login page
     */
    @NoAuthorizationCheck
    @RequestMapping(value = "/sociallogin", method = RequestMethod.GET)
    public String socialLogin(@RequestParam(value = "type", required = false) String type, ModelMap model,
            Locale locale) {
        String socialSignInErrorMessage = messageSource.getMessage("social.signin.failed", new String[] {}, locale);
        SocialSignInStatus socialSignInStatus = SocialSignInStatus.getSocialSignInStatus(type);
        String socialSignInCompletionStatus = "danger";
        if (type != null) {

            switch (socialSignInStatus) {
            case REGISTRATION_SUCCESS:
                socialSignInErrorMessage = messageSource.getMessage("social.registration.success", new String[] {}, locale);
                socialSignInCompletionStatus = "success";
                break;
            case REGISTRATION_FAILED:
                socialSignInErrorMessage = messageSource.getMessage("social.registration.failed", new String[] {}, locale);
                break;
            case ACCOUNT_APPROVAL_PENDING:
                socialSignInErrorMessage = messageSource.getMessage("social.account.approval.pending", new String[] {}, locale);
                break;
            }
        }

        model.addAttribute("social_signin_message", socialSignInErrorMessage);
        model.addAttribute("social_signin_completion_status", socialSignInCompletionStatus);
        return "login";
    }
}
