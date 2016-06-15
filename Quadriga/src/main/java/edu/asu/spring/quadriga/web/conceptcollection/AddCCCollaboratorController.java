package edu.asu.spring.quadriga.web.conceptcollection;

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
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollectionCollaborator;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.IConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.impl.Collaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.conceptcollection.ICCCollaboratorManager;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.validator.CollaboratorValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * @description this class will handle all the collaborators controls in
 *              conceptcollection
 * @author rohit pendbhaje
 *
 */
@Controller
public class AddCCCollaboratorController {

    @Autowired
    private ICollaboratorFactory collaboratorFactory;

    @Autowired
    private IUserFactory userFactory;

    @Autowired
    private IQuadrigaRoleManager collaboratorRoleManager;

    @Autowired
    private IConceptCollectionFactory collectionFactory;

    @Autowired
    private ICCCollaboratorManager collaboratorManager;

    @Autowired
    private IConceptCollectionManager conceptControllerManager;

    @Autowired
    private CollaboratorValidator collaboratorValidator;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private IUserManager usermanager;

    private static final Logger logger = LoggerFactory.getLogger(AddCCCollaboratorController.class);

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder, WebDataBinder validateBinder)
            throws Exception {

        validateBinder.setValidator(collaboratorValidator);

        binder.registerCustomEditor(IUser.class, "userObj", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {

                IUser user;
                try {
                    user = usermanager.getUser(text);
                    setValue(user);
                } catch (QuadrigaStorageException e) {
                    logger.error("", e);
                }
            }
        });

        binder.registerCustomEditor(List.class, "collaboratorRoles", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                String roleIds[] = text.split(",");
                List<IQuadrigaRole> roles = new ArrayList<IQuadrigaRole>();
                for (String roleId : roleIds) {
                    IQuadrigaRole role = collaboratorRoleManager.getQuadrigaRoleById(
                            IQuadrigaRoleManager.CONCEPT_COLLECTION_ROLES, roleId.trim());
                    roles.add(role);
                }
                setValue(roles);
            }
        });
    }

    /**
     * @description maps non-collaborating users, collaborating users and their
     *              roles for current conceptcollection and
     * @param collectionid
     *            id of the collection
     * @param model
     * @param principal
     * @return String having path for showcollaborators jsp page.
     * @throws QuadrigaAccessException
     * @throws QuadrigaStorageException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.CONCEPTCOLLECTION, paramIndex = 1, userRole = { RoleNames.ROLE_CC_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/conceptcollections/{collectionid}/addcollaborators", method = RequestMethod.GET)
    public ModelAndView addCollaborator(@PathVariable("collectionid") String collectionid, Principal principal)
            throws QuadrigaAccessException, QuadrigaStorageException {

        ModelAndView model = new ModelAndView("auth/conceptcollection/addcollaborators");

        // fetch the concept collection details
        IConceptCollection conceptCollection = conceptControllerManager.getConceptCollection(collectionid);

        // fetch the non collaborators and add it to the model
        List<IUser> nonCollaboratorList = collaboratorManager.getUsersNotCollaborating(collectionid);

        // remove the restricted user
        Iterator<IUser> userIterator = nonCollaboratorList.iterator();
        while (userIterator.hasNext()) {
            // fetch the quadriga roles and eliminate the restricted user
            IUser user = userIterator.next();
            List<IQuadrigaRole> userQuadrigaRole = user.getQuadrigaRoles();
            for (IQuadrigaRole role : userQuadrigaRole) {
                if ((role.getId().equals(RoleNames.ROLE_QUADRIGA_RESTRICTED))
                        || (user.getUserName().equals(principal.getName()))) {
                    userIterator.remove();
                    break;
                }
            }

        }
        model.getModelMap().put("nonCollaboratorList", nonCollaboratorList);

        model.getModelMap().put("collectionid", collectionid);
        model.getModelMap().put("collectionname", conceptCollection.getConceptCollectionName());
        model.getModelMap().put("collectiondesc", conceptCollection.getDescription());

        ICollaborator collaborator = collaboratorFactory.createCollaborator();
        collaborator.setUserObj(userFactory.createUserObject());
        model.getModelMap().put("ccCollaborator", collaborator);

        List<IQuadrigaRole> collaboratorRoleList = collaboratorRoleManager
                .getQuadrigaRoles(IQuadrigaRoleManager.CONCEPT_COLLECTION_ROLES);
        model.getModelMap().put("collaboratorRoles", collaboratorRoleList);

        // TODO: showCollaboratingUsers() should be changed with mapper
        List<IConceptCollectionCollaborator> ccCollaboratingUsers = conceptControllerManager
                .showCollaboratingUsers(collectionid);
        model.getModelMap().put("ccCollaboratingUsers", ccCollaboratingUsers);
        return model;
    }

    /**
     * @description this method will add collaborators for current
     *              conceptcollection
     * @param collectionid
     *            id of the collection
     * @param model
     * @param ccCollaborator
     *            object returned by jsp to controller
     * @param principal
     * @return String having path for showcollaborators jsp page.
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.CONCEPTCOLLECTION, paramIndex = 1, userRole = { RoleNames.ROLE_CC_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/conceptcollections/{collection_id}/addcollaborators", method = RequestMethod.POST)
    public ModelAndView addCollaborators(@PathVariable("collection_id") String collectionid, Principal principal,
            @Validated @ModelAttribute("ccCollaborator") Collaborator collaborator, BindingResult result, Locale locale)
            throws QuadrigaStorageException, QuadrigaAccessException {
        
        ModelAndView model = new ModelAndView("auth/conceptcollection/addcollaborators");

        String username = principal.getName();
        // fetch the concept collection details
        IConceptCollection conceptCollection = conceptControllerManager.getConceptCollection(collectionid);

        model.getModelMap().put("collectionname", conceptCollection.getConceptCollectionName());

        model.getModelMap().put("collectionid", collectionid);

        if (result.hasErrors()) {
            model.getModelMap().put("collaborator", collaborator);
            model.getModelMap().addAttribute("show_error_alert", true);
            model.getModelMap().addAttribute("error_alert_msg",
                    messageSource.getMessage("concept_collection.collaborators.add.failure", new Object[] {}, locale));
        } else {
            collaboratorManager.addCollaborator(collaborator, collectionid, username);
            model.getModelMap().addAttribute("show_success_alert", true);
            model.getModelMap().addAttribute("success_alert_msg", messageSource.getMessage("concept_collection.collaborators.add.success", new Object[] {}, locale));
            model.getModelMap().put("collaborator", collaboratorFactory.createCollaborator());
        }

        List<IUser> nonCollaboratorList = collaboratorManager.getUsersNotCollaborating(collectionid);
        // remove the restricted user
        Iterator<IUser> userIterator = nonCollaboratorList.iterator();
        while (userIterator.hasNext()) {
            // fetch the quadriga roles and eliminate the restricted user
            IUser user = userIterator.next();
            List<IQuadrigaRole> userQuadrigaRole = user.getQuadrigaRoles();
            for (IQuadrigaRole role : userQuadrigaRole) {
                if (role.getId().equals(RoleNames.ROLE_QUADRIGA_RESTRICTED)) {
                    userIterator.remove();
                    break;
                }
            }
        }
        model.getModelMap().put("nonCollaboratorList", nonCollaboratorList);

        List<IQuadrigaRole> collaboratorRoleList = collaboratorRoleManager
                .getQuadrigaRoles(IQuadrigaRoleManager.CONCEPT_COLLECTION_ROLES);

        model.getModelMap().put("collaboratorRoles", collaboratorRoleList);

        // TODO: showCollaboratingUsers() should be changed with mapper
        List<IConceptCollectionCollaborator> ccCollaborators = conceptControllerManager
                .showCollaboratingUsers(collectionid);
        model.getModelMap().put("ccCollaboratingUsers", ccCollaborators);

        return model;
    }
}
