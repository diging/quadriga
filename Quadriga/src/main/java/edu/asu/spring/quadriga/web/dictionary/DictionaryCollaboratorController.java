package edu.asu.spring.quadriga.web.dictionary;

import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.dictionary.IDictionaryCollaborator;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.impl.Collaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryCollaboratorManager;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;
import edu.asu.spring.quadriga.validator.CollaboratorValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * this class is used for deleting the collaborators and displaying them on the
 * collaborators details page from dictionaries.
 * 
 * @author rohit pendbhaje
 *
 */
@Controller
public class DictionaryCollaboratorController {

    @Autowired
    private IDictionaryManager dictionaryManager;

    @Autowired
    private IDictionaryCollaboratorManager dictCollaboratorManager;

    @Autowired
    private IUserManager userManager;

    @Autowired
    private ICollaboratorFactory collaboratorFactory;
   
    @Autowired
    private IUserFactory userFactory;

    @Autowired
    private IQuadrigaRoleManager collaboratorRoleManager;

    @Autowired
    private CollaboratorValidator collaboratorValidator;
    
    @Autowired
    private MessageSource messageSource;

    private static final Logger logger = LoggerFactory
            .getLogger(DictionaryCollaboratorController.class);

    @InitBinder
    protected void initBinder(HttpServletRequest request,
            ServletRequestDataBinder binder, WebDataBinder validateBinder)
            throws Exception {

        validateBinder.setValidator(collaboratorValidator);

        binder.registerCustomEditor(IUser.class, "userObj",
                new PropertyEditorSupport() {
                    @Override
                    public void setAsText(String text) {

                        IUser user;
                        try {
                            user = userManager.getUser(text);
                            setValue(user);
                        } catch (QuadrigaStorageException e) {
                            logger.error("Issue connecting to DB", e);
                        }

                    }
                });

        binder.registerCustomEditor(List.class, "collaboratorRoles",
                new PropertyEditorSupport() {

                    @Override
                    public void setAsText(String text) {

                        String[] roleIds = text.split(",");
                        List<IQuadrigaRole> roles = new ArrayList<IQuadrigaRole>();
                        for (String roleId : roleIds) {
                            IQuadrigaRole role = collaboratorRoleManager
                                    .getQuadrigaRoleById(
                                            IQuadrigaRoleManager.DICT_ROLES,
                                            roleId.trim());
                            roles.add(role);
                        }
                        setValue(roles);
                    }
                });
    }

    /**
     * this method is used to display collaborators on the add collaboratos page
     * 
     * 
     * @param dictionaryId
     * @param principal
     *            current session user
     * @return ModelAndView return model and view from controller
     * @throws QuadrigaStorageException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.DICTIONARY, paramIndex = 1, userRole = { RoleNames.ROLE_DICTIONARY_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/dictionaries/{dictionaryid}/collaborators/add", method = RequestMethod.GET)
    public ModelAndView displayCollaborators(
            @PathVariable("dictionaryid") String dictionaryId,
            Principal principal) throws QuadrigaStorageException,
            QuadrigaAccessException {

        ModelAndView modelAndView = new ModelAndView(
                "auth/dictionaries/showAddCollaborators");

        // fetch the dictionary details
        IDictionary dictionary = dictionaryManager
                .getDictionaryDetails(dictionaryId);

        modelAndView.getModelMap().put("dictionaryid", dictionaryId);
        modelAndView.getModelMap().put("dictionaryname",
                dictionary.getDictionaryName());
        modelAndView.getModelMap().put("dictionarydesc",
                dictionary.getDescription());

        // IDictionaryCollaborator dictCollaborator =
        // dictCollaboratorFactory.createDictionaryCollaboratorObject();
        ICollaborator collaborator = collaboratorFactory.createCollaborator();
        if (collaborator != null) {
            collaborator.setUserObj(userFactory.createUserObject());
        }
        modelAndView.getModelMap().put("collaborator", collaborator);

        List<IUser> nonCollaboratingUsers = dictCollaboratorManager
                .getUsersNotCollaborating(dictionaryId);
        // remove the restricted user
        if (nonCollaboratingUsers != null) {
            Iterator<IUser> userIterator = nonCollaboratingUsers.iterator();
            while (userIterator.hasNext()) {
                // fetch the quadriga roles and eliminate the restricted user
                IUser user = userIterator.next();
                List<IQuadrigaRole> userQuadrigaRole = user.getQuadrigaRoles();
                for (IQuadrigaRole role : userQuadrigaRole) {
                    if ((role.getId()
                            .equals(RoleNames.ROLE_QUADRIGA_RESTRICTED))
                            || (user.getUserName().equals(principal.getName()))) {
                        userIterator.remove();
                        break;
                    }
                }
            }
        }
        modelAndView.getModelMap().put("nonCollaboratingUsers",
                nonCollaboratingUsers);

        List<IQuadrigaRole> collaboratorRoles = new ArrayList<IQuadrigaRole>();
        collaboratorRoles = collaboratorRoleManager
                .getQuadrigaRoles(IQuadrigaRoleManager.DICT_ROLES);

        modelAndView.getModelMap().put("possibleCollaboratorRoles",
                collaboratorRoles);

        List<IDictionaryCollaborator> collaborators = dictionaryManager
                .showCollaboratingUsers(dictionaryId);
        modelAndView.getModelMap().put("collaboratingUsers", collaborators);

        return modelAndView;
    }

    /**
     * this method is used to adds the collaborators from the dictionary
     * 
     * @param dictionaryId
     * @param collaborator
     *            object returned from the view
     * @param result
     *            result of the validated object (in this case collaborator)
     * @param principal
     *            current session user
     * @return ModelAndView return model and view from controller
     * @throws QuadrigaStorageException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.DICTIONARY, paramIndex = 1, userRole = { RoleNames.ROLE_DICTIONARY_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/dictionaries/{dictionaryid}/collaborators/add", method = RequestMethod.POST)
    public ModelAndView addCollaborators(
            @PathVariable("dictionaryid") String dictionaryId,
            @Validated @ModelAttribute("collaborator") Collaborator collaborator,
            BindingResult result, Principal principal, Locale locale)
            throws QuadrigaStorageException, QuadrigaAccessException {
        ModelAndView model = new ModelAndView(
                "auth/dictionaries/showAddCollaborators");

        // fetch dictionary details
        IDictionary dictionary = dictionaryManager
                .getDictionaryDetails(dictionaryId);
        model.getModelMap().put("dictionaryname",
                dictionary.getDictionaryName());
        model.getModelMap().put("dictionarydesc", dictionary.getDescription());

        if (result.hasErrors()) {
            model.getModelMap().put("collaborator", collaborator);
            model.getModelMap().addAttribute("show_error_alert", true);
            model.getModelMap().addAttribute("error_alert_msg", messageSource.getMessage("dictionary.collaborators.add.failure", new Object[] {}, locale));
        } else {
            dictCollaboratorManager.addCollaborator(collaborator, dictionaryId,
                    principal.getName());
            model.getModelMap().put("collaborator",
                    collaboratorFactory.createCollaborator());
            model.getModelMap().addAttribute("show_success_alert", true);
            model.getModelMap().addAttribute("success_alert_msg", messageSource.getMessage("dictionary.collaborators.add.success", new Object[] {}, locale));
        }

        // mapping collaborators absent in the dictionary
        List<IUser> nonCollaboratingUsers = dictCollaboratorManager
                .getUsersNotCollaborating(dictionaryId);
        model.getModelMap().put("nonCollaboratingUsers", nonCollaboratingUsers);

        // mapping existing collaborators present in the dictionary
        List<IDictionaryCollaborator> collaborators = dictionaryManager
                .showCollaboratingUsers(dictionaryId);
        model.getModelMap().put("collaboratingUsers", collaborators);

        // mapping collaborator roles
        List<IQuadrigaRole> collaboratorRoles = new ArrayList<IQuadrigaRole>();
        collaboratorRoles = collaboratorRoleManager
                .getQuadrigaRoles(IQuadrigaRoleManager.DICT_ROLES);

        Iterator<IQuadrigaRole> iterator = collaboratorRoles.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId()
                    .equals(RoleNames.ROLE_COLLABORATOR_OWNER)) {
                iterator.remove();
            }
        }
        model.getModelMap().put("possibleCollaboratorRoles", collaboratorRoles);

        return model;

    }

}
