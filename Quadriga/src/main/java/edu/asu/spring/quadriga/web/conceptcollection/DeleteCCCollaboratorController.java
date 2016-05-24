package edu.asu.spring.quadriga.web.conceptcollection;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.factories.IModifyCollaboratorFormFactory;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.IConceptCollectionFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.conceptcollection.ICCCollaboratorManager;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.validator.CollaboratorFormDeleteValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaborator;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorForm;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorFormManager;

@Controller
public class DeleteCCCollaboratorController {

    @Autowired
    private IModifyCollaboratorFormFactory collaboratorFormFactory;

    @Autowired
    private ModifyCollaboratorFormManager collaboratorFormManager;

    @Autowired
    private ICCCollaboratorManager collaboratorManager;

    @Autowired
    private CollaboratorFormDeleteValidator validator;

    @Autowired
    private IConceptCollectionFactory collectionFactory;

    @Autowired
    private IConceptCollectionManager conceptControllerManager;

    @Autowired
    private MessageSource messageSource;

    @InitBinder
    protected void initBinder(WebDataBinder validateBinder) {
        validateBinder.setValidator(validator);
    }

    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.CONCEPTCOLLECTION, paramIndex = 1, userRole = { RoleNames.ROLE_CC_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/conceptcollections/{collectionid}/deletecollaborators", method = RequestMethod.GET)
    public ModelAndView deleteCollaborators(@PathVariable("collectionid") String collectionid, Principal principal)
            throws QuadrigaAccessException, QuadrigaStorageException {
        ModelAndView model;
        ModifyCollaboratorForm collaboratorForm;

        model = new ModelAndView("auth/conceptcollection/deletecollaborators");

        // fetch the concept collection details
        IConceptCollection conceptCollection = conceptControllerManager.getConceptCollection(collectionid);

        collaboratorForm = collaboratorFormFactory.createCollaboratorFormObject();

        List<ModifyCollaborator> modifyCollaborators = collaboratorFormManager
                .getConceptCollectionCollaborators(collectionid);

        // if current user is collaborator, remove from list
        // collaborators shouldn't be able to remove themselves
        // should be done through a different workflow
        removeCurrentUser(principal, modifyCollaborators);

        collaboratorForm.setCollaborators(modifyCollaborators);

        model.getModelMap().put("collectionid", collectionid);
        model.getModelMap().put("collectionname", conceptCollection.getConceptCollectionName());
        model.getModelMap().put("collectiondesc", conceptCollection.getDescription());
        model.getModelMap().put("collaboratorForm", collaboratorForm);
        model.getModelMap().put("success", 0);
        return model;
    }

    private void removeCurrentUser(Principal principal, List<ModifyCollaborator> modifyCollaborators) {
        ModifyCollaborator currentUser = null;
        for (ModifyCollaborator collaborator : modifyCollaborators) {
            if (collaborator.getUserName().equals(principal.getName())) {
                currentUser = collaborator;
                break;
            }
        }
        if (currentUser != null) {
            modifyCollaborators.remove(currentUser);
        }
    }

    /**
     * @description deletes the collaborator from current conceptcollection
     * @param collectionid
     *            id of the conceptcollection
     * @param model
     * @param req
     *            contains string array returned by jsp
     * @return String having path for showcollaborators jsp page
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.CONCEPTCOLLECTION, paramIndex = 1, userRole = { RoleNames.ROLE_CC_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/conceptcollections/{collectionid}/deleteCollaborator", method = RequestMethod.POST)
    public String deleteCollaborators(@PathVariable("collectionid") String collectionid,
            @Validated @ModelAttribute("collaboratorForm") ModifyCollaboratorForm collaboratorForm,
            BindingResult result, Principal principal, Locale locale, RedirectAttributes redirectAttrs, Model model)
            throws QuadrigaStorageException, QuadrigaAccessException {
        List<ModifyCollaborator> collaborators;

        model.addAttribute("collectionid", collectionid);

        if (result.hasErrors()) {
            // fetch the concept collection details
            // fetch the concept collection details
            IConceptCollection conceptCollection = conceptControllerManager.getConceptCollection(collectionid);

            model.addAttribute("collectionname", conceptCollection.getConceptCollectionName());
            model.addAttribute("collectiondesc", conceptCollection.getDescription());

            collaborators = collaboratorFormManager.getConceptCollectionCollaborators(collectionid);
            collaboratorForm.setCollaborators(collaborators);
            model.addAttribute("success", 0);
            model.addAttribute("error", 1);
            model.addAttribute("collaboratorForm", collaboratorForm);
            model.addAttribute("show_error_alert", true);
            model.addAttribute("error_alert_msg",
                    messageSource.getMessage("collaborator_user_selection.required", new Object[] {}, locale));
            return "auth/conceptcollection/deletecollaborators";
        }
        collaborators = collaboratorForm.getCollaborators();
        StringBuilder collabUser = new StringBuilder();

        for (ModifyCollaborator collaborator : collaborators) {
            String user = collaborator.getUserName();
            if (user != null) {
                collabUser.append(",");
                collabUser.append(user);
            }
        }
        collaboratorManager.deleteCollaborators(collabUser.toString().substring(1), collectionid);

        redirectAttrs.addFlashAttribute("show_success_alert", true);
        redirectAttrs.addFlashAttribute("success_alert_msg",
                messageSource.getMessage("collaborator_deleted.success", new Object[] {}, locale));

        return "redirect:/auth/conceptcollections/" + collectionid;
    }

}
