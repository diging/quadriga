package edu.asu.spring.quadriga.web.conceptcollection;

import java.security.Principal;
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
import edu.asu.spring.quadriga.domain.conceptcollection.impl.ConceptCollection;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.service.conceptcollection.IModifyConceptCollectionManager;
import edu.asu.spring.quadriga.validator.ConceptCollectionValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class ModifyConceptCollectionController {
    
    @Autowired
    private IConceptCollectionManager conceptControllerManager;

    @Autowired
    private IModifyConceptCollectionManager collectionManager;

    @Autowired
    private ConceptCollectionValidator validator;
    
    @Autowired
    private MessageSource messageSource;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    /**
     * This method retrieves the concept collection details for updation.
     * 
     * @param collectionid
     * @param principal
     * @return ModelAndView
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.CONCEPTCOLLECTION, paramIndex = 1, userRole = { RoleNames.ROLE_CC_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/conceptcollections/updatecollection/{collectionid}", method = RequestMethod.GET)
    public ModelAndView updateConceptCollectionDetials(
            @PathVariable("collectionid") String collectionid,
            Principal principal) throws QuadrigaStorageException,
            QuadrigaAccessException {
        ModelAndView model = new ModelAndView(
                "auth/conceptcollections/updatecollectiondetails");
        // retrieve the collection details
        IConceptCollection collection = conceptControllerManager
                .getConceptCollection(collectionid);
        model.getModelMap().put("collection", collection);

        return model;
    }

    /**
     * This method updates the concept collection details.
     * 
     * @param collection
     * @param result
     * @param collectionid
     * @param principal
     * @return
     * @throws QuadrigaStorageException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.CONCEPTCOLLECTION, paramIndex = 3, userRole = { RoleNames.ROLE_CC_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/conceptcollections/updatecollection/{collectionid}", method = RequestMethod.POST)
    public String updateConceptCollectionDetails(
            @Validated @ModelAttribute("collection") ConceptCollection collection,
            BindingResult result,
            @PathVariable("collectionid") String collectionid,
            Principal principal, Model model, RedirectAttributes redirectAttrs, Locale locale) throws QuadrigaStorageException,
            QuadrigaAccessException {
        if (result.hasErrors()) {
            model.addAttribute("collection", collection);
            model.addAttribute("show_error_alert", true);
            model.addAttribute("error_alert_msg",
                    messageSource.getMessage("concept_collection.modify.failure", new Object[] {}, locale));

            return "auth/conceptcollections/updatecollectiondetails";
        } else {
            collectionManager.updateCollectionDetails(collection, principal.getName());
            redirectAttrs.addFlashAttribute("show_success_alert", true);
            redirectAttrs.addFlashAttribute("success_alert_msg",
                    messageSource.getMessage("concept_collection.modify.success", new Object[] {}, locale));
        }
        
        return "redirect:/auth/conceptcollections/" + collectionid;
    }

}
