package edu.asu.spring.quadriga.web.dictionary;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.factories.impl.ModifyCollaboratorFormFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryCollaboratorManager;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;
import edu.asu.spring.quadriga.validator.CollaboratorFormDeleteValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaborator;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorForm;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorFormManager;

@Controller
public class DictionaryDeleteCollabController {

    @Autowired
    private ModifyCollaboratorFormManager collaboratorFormManager;

    @Autowired
    private IDictionaryManager dictionaryManager;

    @Autowired
    private IDictionaryCollaboratorManager dictCollaboratorManager;

    @Autowired
    private CollaboratorFormDeleteValidator validator;

    @Autowired
    private ICollaboratorFactory collaboratorFactory;

    @Autowired
    private IUserFactory userFactory;

    @Autowired
    private ModifyCollaboratorFormFactory collaboratorFormFactory;

    @Autowired
    private IQuadrigaRoleManager collaboratorRoleManager;

    @Autowired
    private MessageSource messageSource;

    @InitBinder
    protected void initBinder(WebDataBinder validateBinder) throws Exception {
        validateBinder.setValidator(validator);
    }

    /**
     * This method deletes the selected collaborators associated with the given
     * workspace
     * 
     * @param dictionaryId
     * @param collaboratorForm
     * @param result
     * @param model
     * @param principal
     * @return ModelAndView
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.DICTIONARY, paramIndex = 1, userRole = { RoleNames.ROLE_DICTIONARY_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/dictionaries/{dictionaryid}/collaborators/delete", method = RequestMethod.POST)
    public String deleteCollaborators(@PathVariable("dictionaryid") String dictionaryId,
            @Validated @ModelAttribute("collaboratorForm") ModifyCollaboratorForm collaboratorForm,
            BindingResult result, ModelMap model, Principal principal, Locale locale, RedirectAttributes redirectAttrs) throws QuadrigaStorageException,
            QuadrigaAccessException {

        if (result.hasErrors()) {
            addCollaboratorsToForm(dictionaryId, collaboratorForm, principal);

            IDictionary dictionary = dictionaryManager.getDictionaryDetails(dictionaryId);
            model.addAttribute("collaboratorForm", collaboratorForm);
            model.addAttribute("dictionaryid", dictionaryId);
            model.addAttribute("dictionaryname", dictionary.getDictionaryName());
            model.addAttribute("dictionarydesc", dictionary.getDescription());

            // show error message
            model.addAttribute("show_error_alert", true);
            model.addAttribute("error_alert_msg",
                    messageSource.getMessage("dictionary.collaborators.remove.failure", new Object[] {}, locale));
           
            return "auth/dictionaries/showDeleteCollaborators";
        }

        List<ModifyCollaborator> collaborators = collaboratorForm.getCollaborators();
        for (ModifyCollaborator collaborator : collaborators) {
            String userName = collaborator.getUserName();
            if (userName != null) {
                dictCollaboratorManager.deleteCollaborators(userName, dictionaryId);
            }
        }
        redirectAttrs.addFlashAttribute("show_success_alert", true);
        redirectAttrs.addFlashAttribute("success_alert_msg",
                messageSource.getMessage("dictionary.collaborators.remove.success", new Object[] {}, locale));

        return "redirect:/auth/dictionaries/" + dictionaryId;
    }

    /**
     * This method retrieves the collaborators associated with given workspace
     * 
     * @param dictionaryId
     * @param principal
     * @return ModelAndView
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.DICTIONARY, paramIndex = 1, userRole = { RoleNames.ROLE_DICTIONARY_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/dictionaries/{dictionaryid}/collaborators/delete", method = RequestMethod.GET)
    public ModelAndView displayCollaborators(@PathVariable("dictionaryid") String dictionaryId, Principal principal)
            throws QuadrigaStorageException, QuadrigaAccessException {

        ModelAndView modelAndView;
        ModifyCollaboratorForm collaboratorForm;

        // fetch the dictionary details
        IDictionary dictionary = dictionaryManager.getDictionaryDetails(dictionaryId);

        modelAndView = new ModelAndView("auth/dictionaries/showDeleteCollaborators");

        collaboratorForm = collaboratorFormFactory.createCollaboratorFormObject();
        addCollaboratorsToForm(dictionaryId, collaboratorForm, principal);

        modelAndView.getModelMap().put("collaboratorForm", collaboratorForm);
        modelAndView.getModelMap().put("dictionaryid", dictionaryId);
        modelAndView.getModelMap().put("dictionaryname", dictionary.getDictionaryName());
        modelAndView.getModelMap().put("dictionarydesc", dictionary.getDescription());

        return modelAndView;
    }

    private void addCollaboratorsToForm(String dictionaryId, ModifyCollaboratorForm collaboratorForm,
            Principal principal) throws QuadrigaStorageException {
        List<ModifyCollaborator> modifyCollaborators = collaboratorFormManager.getDictionaryCollaborators(dictionaryId);

        // if current user is collaborator, remove from list
        // collaborators shouldn't be able to remove themselves
        // should be done through a different workflow
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
        
        collaboratorForm.setCollaborators(modifyCollaborators);
    }
    
}
