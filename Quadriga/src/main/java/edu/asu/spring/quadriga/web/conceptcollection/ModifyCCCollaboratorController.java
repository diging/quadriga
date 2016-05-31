package edu.asu.spring.quadriga.web.conceptcollection;

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
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.factories.IModifyCollaboratorFormFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.conceptcollection.ICCCollaboratorManager;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.validator.CollaboratorFormValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaborator;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorForm;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorFormManager;

@Controller
public class ModifyCCCollaboratorController {
    @Autowired
    private IModifyCollaboratorFormFactory collaboratorFactory;

    @Autowired
    private IQuadrigaRoleManager collaboratorRoleManager;

    @Autowired
    private CollaboratorFormValidator validator;

    @Autowired
    private IConceptCollectionManager conceptControllerManager;

    @Autowired
    private ICCCollaboratorManager collaboratorManager;

    @Autowired
    private ModifyCollaboratorFormManager collaboratorFormManager;

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
                    IQuadrigaRole role = collaboratorRoleManager.getQuadrigaRoleById(
                            IQuadrigaRoleManager.CONCEPT_COLLECTION_ROLES, roleId.trim());
                    roles.add(role);
                }
                setValue(roles);
            }
        });

    }

    /**
     * This method retrieves the collaborators associated with given concept
     * collection for updation the collaborator roles.
     * 
     * @param collectionid
     * @param principal
     * @return ModelAndView
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.CONCEPTCOLLECTION, paramIndex = 1, userRole = { RoleNames.ROLE_CC_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/conceptcollections/{collectionid}/updatecollaborators", method = RequestMethod.GET)
    public ModelAndView updateCollaboratorForm(@PathVariable("collectionid") String collectionid, Principal principal)
            throws QuadrigaStorageException, QuadrigaAccessException {
        ModelAndView model = new ModelAndView("auth/conceptcollection/updatecollaborators");

        // fetch the concept collection details
        IConceptCollection conceptCollection = conceptControllerManager.getConceptCollection(collectionid);

        // create a model for collaborators
        ModifyCollaboratorForm collaboratorForm = collaboratorFactory.createCollaboratorFormObject();

        List<ModifyCollaborator> modifyCollaborator = collaboratorFormManager.getConceptCollectionCollaborators(collectionid);

        collaboratorForm.setCollaborators(modifyCollaborator);

        // fetch the concept collection collaborator roles
        List<IQuadrigaRole> collaboratorRoles = collaboratorRoleManager
                .getQuadrigaRoles(IQuadrigaRoleManager.CONCEPT_COLLECTION_ROLES);

        // add the collaborator roles to the model
        model.getModelMap().put("cccollabroles", collaboratorRoles);
        model.getModelMap().put("collaboratorform", collaboratorForm);
        model.getModelMap().put("collectionid", collectionid);
        model.getModelMap().put("collectionname", conceptCollection.getConceptCollectionName());
        model.getModelMap().put("collectiondesc", conceptCollection.getDescription());
        return model;
    }

    /**
     * This method updated the roles of selected collaborator associated with
     * the given concept collection
     * 
     * @param collaboratorForm
     * @param result
     * @param collectionid
     * @param principal
     * @return ModelAndView
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.CONCEPTCOLLECTION, paramIndex = 3, userRole = { RoleNames.ROLE_CC_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/conceptcollections/{collectionid}/updatecollaborators", method = RequestMethod.POST)
    public String updateCollaboratorForm(
            @Validated @ModelAttribute("collaboratorform") ModifyCollaboratorForm collaboratorForm,
            BindingResult result, @PathVariable("collectionid") String collectionid, Principal principal, Model model,
            RedirectAttributes redirectAttrs, Locale locale) throws QuadrigaStorageException, QuadrigaAccessException {
        // create model view

        List<ModifyCollaborator> ccCollaborators;
        if (result.hasErrors()) {
            // fetch the concept collection details
            IConceptCollection conceptCollection = conceptControllerManager.getConceptCollection(collectionid);

            // add a variable to display the entire page

            ccCollaborators = collaboratorFormManager.getConceptCollectionCollaborators(collectionid);
            collaboratorForm.setCollaborators(ccCollaborators);
            // add the model map
            model.addAttribute("collaboratorform", collaboratorForm);
            model.addAttribute("collectionid", collectionid);
            model.addAttribute("collectionname", conceptCollection.getConceptCollectionName());
            model.addAttribute("collectiondesc", conceptCollection.getDescription());

            // retrieve the collaborator roles and assign it to a map
            // fetch the roles that can be associated to the workspace
            // collaborator
            List<IQuadrigaRole> collaboratorRoles = collaboratorRoleManager
                    .getQuadrigaRoles(IQuadrigaRoleManager.CONCEPT_COLLECTION_ROLES);
            model.addAttribute("cccollabroles", collaboratorRoles);

            model.addAttribute("show_error_alert", true);
            model.addAttribute("error_alert_msg",
                    messageSource.getMessage("collaborator_update.roles_required", new Object[] {}, locale));

            return "auth/conceptcollection/updatecollaborators";
        }

        ccCollaborators = collaboratorForm.getCollaborators();

        // fetch the user and his collaborator roles
        for (ModifyCollaborator collab : ccCollaborators) {
            StringBuilder collabRoles = new StringBuilder();
            String collabUser = collab.getUserName();
            List<IQuadrigaRole> values = collab.getCollaboratorRoles();

            // fetch the role names for the roles and form a string
            for (IQuadrigaRole role : values) {
                collabRoles.append(",");
                collabRoles.append(role.getDBid());
            }

            // adding the logic to retrieve the user name of full name is
            // empty
            if (!collabUser.isEmpty()) {
                collaboratorManager.updateCollaborators(collectionid, collabUser, collabRoles.toString().substring(1),
                        principal.getName());
            }
        }

        redirectAttrs.addFlashAttribute("show_success_alert", true);
        redirectAttrs.addFlashAttribute("success_alert_msg",
                messageSource.getMessage("collaborator_update.success", new Object[] {}, locale));

        return "redirect:/auth/conceptcollections/" + collectionid;
    }

}
