package edu.asu.spring.quadriga.web.manageusers;

import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.validator.UserRolesFormValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;
import edu.asu.spring.quadriga.web.users.backingbean.ModifyQuadrigaUser;
import edu.asu.spring.quadriga.web.users.backingbean.ModifyQuadrigaUserForm;
import edu.asu.spring.quadriga.web.users.backingbean.ModifyQuadrigaUserFormManager;

@Controller
public class ModifyUserRolesController {
    @Autowired
    private UserRolesFormValidator validator;

    @Autowired
    private IQuadrigaRoleManager rolemanager;

    @Autowired
    private IUserManager userManager;

    @Autowired
    private ModifyQuadrigaUserFormManager quadrigaUserMananger;

    @InitBinder
    protected void initBinder(WebDataBinder binder) throws Exception {
        binder.setValidator(validator);
        binder.registerCustomEditor(IQuadrigaRole.class,
                new QuadrigaRoleEditor());
    }

    /**
     * This method retrieve all the active users and their quadriga roles to be
     * displayed for updating.
     * 
     * @return ModelAndView - View to display users and quadriga roles.
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     * @author kiran batna
     */
    @RequestMapping(value = "auth/users/updateroles", method = RequestMethod.GET)
    public ModelAndView updateQuadrigaRolesRequest()
            throws QuadrigaStorageException, QuadrigaAccessException {
        ModelAndView model;
        ModifyQuadrigaUserForm userForm;
        model = new ModelAndView("auth/users/updateroles");

        // Get all Active Users
        List<IUser> activeUserList = userManager.getAllActiveUsers();

        // remove the admin user from the active user list
        Iterator<IUser> userIt = activeUserList.listIterator();
        while (userIt.hasNext()) {
            IUser user = userIt.next();
            if (user.getUserName().equals("admin")) {
                userIt.remove();
            }
        }

        userForm = quadrigaUserMananger
                .modifyUserQuadrigaRolesManager(activeUserList);

        // Get all Quadriga roles
        List<IQuadrigaRole> quadrigaRoles = rolemanager
                .getQuadrigaRoles(IQuadrigaRoleManager.MAIN_ROLES);

        // remove the invalid quadriga role object
        IQuadrigaRole role = rolemanager.getQuadrigaRoleByDbId(
                IQuadrigaRoleManager.MAIN_ROLES,
                RoleNames.DB_ROLE_QUADRIGA_NOACCOUNT);
        if (quadrigaRoles.contains(role))
            quadrigaRoles.remove(role);

        model.getModelMap().put("userrolesform", userForm);
        model.getModelMap().put("quadrigaroles", quadrigaRoles);
        model.getModelMap().put("success", 0);
        return model;
    }

    /**
     * This method updates the quadriga roles associated with the user
     * 
     * @param userForm
     * @param result
     * @param principal
     * @return ModelAndView - On success View showing the success message. On
     *         error View showing the data for updating.
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     * @author kiran batna
     */
    @RequestMapping(value = "auth/users/updateroles", method = RequestMethod.POST)
    public String updateCollaboratorRequest(
            @Validated @ModelAttribute("userrolesform") ModifyQuadrigaUserForm userForm,
            BindingResult result, Principal principal, ModelMap model,
            RedirectAttributes redirectAttributes)
            throws QuadrigaStorageException, QuadrigaAccessException {

        List<IQuadrigaRole> quadrigaRoles = new ArrayList<IQuadrigaRole>();

        /*
         * If there are errors return to update page and show errors
         */
        if (result.hasErrors()) {
            // Get all Active Users
            List<IUser> activeUserList = userManager.getAllActiveUsers();

            userForm = quadrigaUserMananger
                    .modifyUserQuadrigaRolesManager(activeUserList);

            // Get all Quadriga roles
            quadrigaRoles = rolemanager
                    .getQuadrigaRoles(IQuadrigaRoleManager.MAIN_ROLES);

            // remove the invalid quadriga role object
            IQuadrigaRole role = rolemanager.getQuadrigaRoleByDbId(
                    IQuadrigaRoleManager.MAIN_ROLES,
                    RoleNames.DB_ROLE_QUADRIGA_NOACCOUNT);
            if (quadrigaRoles.contains(role))
                quadrigaRoles.remove(role);

            model.put("userrolesform", userForm);
            model.put("quadrigaroles", quadrigaRoles);
            redirectAttributes.addFlashAttribute("show_error_alert", true);
            // FIXME this needs to be changed and done in the UI
//            StringBuffer errors = new StringBuffer("<ul>");
//            for (ObjectError error : result.getAllErrors()) {
//                errors.append("<li>");
//                errors.append(error.toString());
//                errors.append("</li>");
//            }
//            errors.append("</ul>");
            redirectAttributes.addFlashAttribute("error_alert_msg", "Please select at least one role for each user");
            return "redirect:/auth/users/updateroles";
        }

        List<ModifyQuadrigaUser> users = userForm.getUsers();

        for (ModifyQuadrigaUser user : users) {
            StringBuilder userRoles = new StringBuilder();
            String userName = user.getUserName();
            quadrigaRoles = user.getQuadrigaRoles();

            for (IQuadrigaRole role : quadrigaRoles) {
                userRoles.append(",");
                userRoles.append(role.getDBid());
            }

            userManager.updateUserQuadrigaRoles(userName, userRoles.toString()
                    .substring(1), principal.getName());
        }

        redirectAttributes.addFlashAttribute("show_success_alert", true);
        redirectAttributes.addFlashAttribute("success_alert_msg",
                "User roles were successfully updated.");

        return "redirect:/auth/users/manage";
    }

    class QuadrigaRoleEditor extends PropertyEditorSupport {
        public void setAsText(String text) {
            IQuadrigaRole role = rolemanager.getQuadrigaRoleByDbId(
                    IQuadrigaRoleManager.MAIN_ROLES, text);
            setValue(role);
        }
    }
}
