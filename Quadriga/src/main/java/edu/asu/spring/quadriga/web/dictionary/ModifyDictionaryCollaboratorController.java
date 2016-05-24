package edu.asu.spring.quadriga.web.dictionary;

import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.factories.IModifyCollaboratorFormFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryCollaboratorManager;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;
import edu.asu.spring.quadriga.validator.CollaboratorFormValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaborator;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorForm;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorFormManager;

@Controller
public class ModifyDictionaryCollaboratorController {
    @Autowired
    private ModifyCollaboratorFormManager collaboratorFormManager;

    @Autowired
    private IModifyCollaboratorFormFactory collaboratorFactory;

    @Autowired
    private IQuadrigaRoleManager collaboratorRoleManager;

    @Autowired
    private IDictionaryCollaboratorManager dictionaryCollabManager;

    @Autowired
    private IDictionaryManager dictManager;

    @Autowired
    private CollaboratorFormValidator validator;

    @Autowired
    private MessageSource messageSource;

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder, WebDataBinder validateBinder)
            throws Exception {

        validateBinder.setValidator(validator);

        binder.registerCustomEditor(List.class, "collaborators.collaboratorRoles", new PropertyEditorSupport() {

            @Override
            public void setAsText(String text) {
                String[] roleIds = text.split(",");
                List<IQuadrigaRole> roles = new ArrayList<IQuadrigaRole>();
                for (String roleId : roleIds) {
                    IQuadrigaRole role = collaboratorRoleManager.getQuadrigaRoleById(IQuadrigaRoleManager.DICT_ROLES,
                            roleId.trim());
                    roles.add(role);
                }
                setValue(roles);
            }
        });

    }

    /**
     * This method retrieves the collaborators associated with a dictionary for
     * updating the selected collaborator roles
     * 
     * @param dictionaryid
     * @return ModelAndView
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.DICTIONARY, paramIndex = 1, userRole = { RoleNames.ROLE_DICTIONARY_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/dictionaries/{dictionaryid}/collaborators/update", method = RequestMethod.GET)
    public ModelAndView updateCollaboratorForm(@PathVariable("dictionaryid") String dictionaryid)
            throws QuadrigaStorageException, QuadrigaAccessException {
        ModelAndView model = new ModelAndView("auth/dictionaries/updatecollaborators");

        // fetch the dictionary details
        IDictionary dictionary = dictManager.getDictionaryDetails(dictionaryid);

        // create a model for collaborators
        ModifyCollaboratorForm collaboratorForm = collaboratorFactory.createCollaboratorFormObject();

        List<ModifyCollaborator> modifyCollaborator = collaboratorFormManager.getDictionaryCollaborators(dictionaryid);

        collaboratorForm.setCollaborators(modifyCollaborator);

        // fetch the concept collection collaborator roles
        List<IQuadrigaRole> collaboratorRoles = collaboratorRoleManager
                .getQuadrigaRoles(IQuadrigaRoleManager.DICT_ROLES);

        // add the collaborator roles to the model
        model.getModelMap().put("dictcollabroles", collaboratorRoles);
        model.getModelMap().put("collaboratorform", collaboratorForm);
        model.getModelMap().put("dictionaryid", dictionaryid);
        model.getModelMap().put("dictionaryname", dictionary.getDictionaryName());
        model.getModelMap().put("dictionarydesc", dictionary.getDescription());
        return model;
    }

    /**
     * This method updates the roles of selected collaborator associated with
     * the given dictionary
     * 
     * @param collaboratorForm
     * @param result
     * @param dictionaryid
     * @param principal
     * @return
     * @throws QuadrigaStorageException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.DICTIONARY, paramIndex = 3, userRole = { RoleNames.ROLE_DICTIONARY_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/dictionaries/{dictionaryid}/collaborators/update", method = RequestMethod.POST)
    public String updateCollaboratorForm(
            @Validated @ModelAttribute("collaboratorform") ModifyCollaboratorForm collaboratorForm,
            BindingResult result, @PathVariable("dictionaryid") String dictionaryid, Principal principal, Model model,
            Locale locale, RedirectAttributes redirectAttrs) throws QuadrigaStorageException, QuadrigaAccessException {
        String userName = principal.getName();

        if (result.hasErrors()) {
            // fetch the dictionary details
            IDictionary dictionary = dictManager.getDictionaryDetails(dictionaryid);

            List<ModifyCollaborator> dictCollaborators = collaboratorFormManager
                    .getDictionaryCollaborators(dictionaryid);
            collaboratorForm.setCollaborators(dictCollaborators);
            // add the model map
            model.addAttribute("collaboratorform", collaboratorForm);
            model.addAttribute("dictionaryid", dictionaryid);
            model.addAttribute("dictionaryname", dictionary.getDictionaryName());
            model.addAttribute("dictionarydesc", dictionary.getDescription());

            // retrieve the collaborator roles and assign it to a map
            // fetch the roles that can be associated to the workspace
            // collaborator
            List<IQuadrigaRole> collaboratorRoles = collaboratorRoleManager
                    .getQuadrigaRoles(IQuadrigaRoleManager.DICT_ROLES);
            model.addAttribute("dictcollabroles", collaboratorRoles);

            model.addAttribute("show_error_alert", true);
            model.addAttribute("error_alert_msg",
                    messageSource.getMessage("dictionary.collaborators.update.failure", new Object[] {}, locale));

            return "auth/dictionaries/updatecollaborators";
        }
        List<ModifyCollaborator> dictCollaborators = collaboratorForm.getCollaborators();

        // fetch the user and his collaborator roles
        for (ModifyCollaborator collab : dictCollaborators) {
            StringBuilder collabRoles = new StringBuilder();
            String collabUser = collab.getUserName();
            List<IQuadrigaRole> values = collab.getCollaboratorRoles();

            // fetch the role names for the roles and form a string
            for (IQuadrigaRole role : values) {
                collabRoles.append(",");
                collabRoles.append(role.getDBid());
            }

            dictionaryCollabManager.updateCollaborators(dictionaryid, collabUser, collabRoles.toString().substring(1),
                    userName);
        }
        
        redirectAttrs.addFlashAttribute("show_success_alert", true);
        redirectAttrs.addFlashAttribute("success_alert_msg", messageSource.getMessage("dictionary.collaborators.update.success", new Object[] {}, locale));
        

        return "redirect:/auth/dictionaries/" + dictionaryid;

    }
}
