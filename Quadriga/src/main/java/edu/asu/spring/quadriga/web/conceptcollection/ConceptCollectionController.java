package edu.asu.spring.quadriga.web.conceptcollection;

import java.security.Principal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

import edu.asu.spring.quadriga.aspects.IAuthorization;
import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.conceptpower.model.ConceptpowerReply;
import edu.asu.spring.quadriga.conceptpower.model.ConceptpowerReply.ConceptEntry;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.conceptcollection.IConcept;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollectionCollaborator;
import edu.asu.spring.quadriga.domain.conceptcollection.impl.ConceptCollection;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.IConceptFactory;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.impl.ConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.impl.CollectionsValidator;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.conceptcollection.IConceptCollectionDeepMapper;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * This class is the controller for the concept collection requests.
 * 
 * @author SatyaSwaroop Boddu
 * 
 */

@Controller
public class ConceptCollectionController {

    @Autowired
    private IConceptCollectionManager conceptControllerManager;

    @Autowired
    private CollectionsValidator validator;

    @Autowired
    private IConceptFactory conceptFactory;

    @Autowired
    private IConceptCollectionDeepMapper conceptCollectionDeepMapper;

    @Autowired
    private IUserManager usermanager;

    @Autowired
    @Qualifier("conceptCollectionAuthorization")
    private IAuthorization authorization;

    /**
     * Attach the custom validator to the Spring context
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    /**
     * This is used to fetch the user related concept collections from database.
     * 
     * @param model
     * @return Returns the list of concept collections of user to the view
     * @throws QuadrigaStorageException
     */
    @RequestMapping(value = "auth/conceptcollections", method = RequestMethod.GET)
    public String conceptCollectionHandler(ModelMap model, Principal principal) throws QuadrigaStorageException {
        model.addAttribute("conceptlist", conceptControllerManager.getCollectionsOwnedbyUser(principal.getName()));
        model.addAttribute("collaborationlist", conceptControllerManager.getUserCollaborations(principal.getName()));
        return "auth/conceptcollections";
    }

    private void fillModel(String collectionId, ModelMap model, String username) throws QuadrigaStorageException,
            QuadrigaAccessException, JSONException {
        IConceptCollection collection = conceptControllerManager.getConceptCollection(collectionId);
        String adminRoles[] = { RoleNames.ROLE_CC_COLLABORATOR_ADMIN, RoleNames.ROLE_COLLABORATOR_OWNER };
        String readRole[] = { RoleNames.ROLE_CC_COLLABORATOR_READ };
        String readWriteRole[] = { RoleNames.ROLE_CC_COLLABORATOR_READ_WRITE };

        boolean isAdmin = authorization.chkAuthorization(username, collectionId, adminRoles);
        boolean hasRead = authorization.chkAuthorization(username, collectionId, readRole);
        boolean hasReadWrite = authorization.chkAuthorization(username, collectionId, readWriteRole);

        if (isAdmin) {
            model.addAttribute("isAdmin", true);
        } else {
            model.addAttribute("isAdmin", false);
        }

        if (hasRead) {
            model.addAttribute("hasRead", true);
        } else {
            model.addAttribute("hasRead", false);
        }

        if (hasReadWrite) {
            model.addAttribute("hasReadWrite", true);
        } else {
            model.addAttribute("hasReadWrite", false);
        }
        model.addAttribute("concept", collection);
        conceptControllerManager.getCollaborators(collection);
        model.addAttribute("collectionid", collectionId);

        model.addAttribute("owner", collection.getOwner().getUserName().equals(username));

        // TODO: showCollaboratingUsers() should be changed with mapper
        List<IConceptCollectionCollaborator> collaboratingUsers = conceptControllerManager
                .showCollaboratingUsers(collectionId);
        model.addAttribute("collaboratingUsers", collaboratingUsers);
    }

    /**
     * This method is used to search the conceptpower for items and will also
     * give options to add it.
     * 
     * @param collection_id
     * @param req
     *            HttpRequest must have pos and search name as parameters to hit
     *            the conceptpower rest service
     * @param model
     * @return
     * @throws QuadrigaStorageException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.CONCEPTCOLLECTION, paramIndex = 1, userRole = {
            RoleNames.ROLE_CC_COLLABORATOR_ADMIN, RoleNames.ROLE_CC_COLLABORATOR_READ_WRITE }) })
    @RequestMapping(value = "auth/conceptcollections/{collection_id}/searchitems", method = RequestMethod.GET)
    public String conceptSearchHandler(@PathVariable("collection_id") String collection_id, HttpServletRequest req,
            ModelMap model) throws QuadrigaStorageException, QuadrigaAccessException, QuadrigaException {
        try {
            ConceptpowerReply conReply = conceptControllerManager.search(req.getParameter("name"),
                    req.getParameter("pos"));

            if (conReply != null) {
                List<ConceptEntry> lists = conReply.getConceptEntry();
                lists.sort(new Comparator<ConceptEntry>() {

                    @Override
                    public int compare(ConceptEntry o1, ConceptEntry o2) {
                        return o1.getLemma().toLowerCase().compareTo(o2.getLemma().toLowerCase());
                    }
                });
                model.addAttribute("result", conReply.getConceptEntry());
            }
        } catch (HttpMessageNotReadableException hex) {
            throw new QuadrigaException(hex);
        }
        model.addAttribute("collectionid", collection_id);
        return "auth/searchitems";
    }

    /**
     * This method is used to save items into a conceptcollection by searching
     * and choosing from the conceptpower
     * 
     * @param collectionId
     * @param req
     *            HttpRequest must have all the selected concept items ids.
     * @param model
     * @param principal
     * @return
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     * @throws JSONException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.CONCEPTCOLLECTION, paramIndex = 1, userRole = {
            RoleNames.ROLE_CC_COLLABORATOR_ADMIN, RoleNames.ROLE_CC_COLLABORATOR_READ_WRITE }) })
    @RequestMapping(value = "auth/conceptcollections/{collection_id}/addItems", method = RequestMethod.POST)
    public String saveItemsHandler(@PathVariable("collection_id") String collectionId, HttpServletRequest req,
            ModelMap model, Principal principal, RedirectAttributes redirectAttrs) throws QuadrigaStorageException, QuadrigaAccessException,
            JSONException {

        String[] selectedIds = req.getParameterValues("selected");

        Map<String, ConceptEntry> allEntries = buildConceptEntries(req.getParameterValues("id"),
                req.getParameterValues("lemma"), req.getParameterValues("pos"), req.getParameterValues("description"),
                req.getParameterValues("type"), req.getParameterValues("conceptList"));

        IConceptCollection conceptCollection = conceptCollectionDeepMapper.getConceptCollectionDetails(collectionId);
        int addedConcepts = 0;

        if (conceptCollection != null) {
            Set<String> existingConceptIds = new HashSet<String>();
            for (IConcept concept : conceptCollection.getConcepts()) {
                existingConceptIds.add(concept.getConceptId());
            }

            if (selectedIds != null) {
                for (String id : selectedIds) {
                    ConceptEntry entry = allEntries.get(id);

                    if (!existingConceptIds.contains(id)) {
                        conceptControllerManager.addItems(entry.getLemma(), id, entry.getPos(), entry.getDescription(),
                                collectionId, principal.getName());
                        addedConcepts++;
                    }
                }
            }
        }

        
        redirectAttrs.addFlashAttribute("show_success_alert", true);
        redirectAttrs.addFlashAttribute("success_alert_msg", addedConcepts + " Concepts successfully added.");
        return "redirect:/auth/conceptcollections/" + collectionId;
    }

    private Map<String, ConceptEntry> buildConceptEntries(String[] ids, String[] lemmas, String[] pos,
            String[] description, String[] types, String[] lists) {
        Map<String, ConceptEntry> entries = new HashMap<String, ConceptpowerReply.ConceptEntry>();

        for (int i = 0; i < ids.length; i++) {
            ConceptEntry entry = new ConceptEntry();
            entry.setId(ids[i]);
            entry.setConceptList(lists[i]);
            entry.setDescription(description[i]);
            entry.setLemma(lemmas[i]);
            entry.setPos(pos[i]);
            entry.setType(types[i]);
            entries.put(entry.getId(), entry);
        }

        return entries;
    }

    /**
     * This method handles the addcollections.jsp form which is used to add a
     * new conceptcollection to quadriga
     * 
     * @return
     */
    @RequestMapping(value = "auth/conceptcollections/addCollectionsForm", method = RequestMethod.GET)
    public ModelAndView addCollectionsForm() {
        return new ModelAndView("auth/conceptcollections/addCollectionsForm", "command",
                new ConceptCollectionFactory().createConceptCollectionObject());
    }

    /**
     * This method is used to save the form data submitted for adding a
     * conceptcollection
     * 
     * @param collection
     * @param result
     * @param model
     * @param principal
     * @return Returns the list of concept collections of user to the view
     * @throws QuadrigaStorageException
     * 
     * */
    @RequestMapping(value = "auth/conceptcollections/addCollectionsForm", method = RequestMethod.POST)
    public ModelAndView addConceptCollection(@Validated @ModelAttribute("collection") ConceptCollection collection,
            BindingResult result, Model model, Principal principal) throws QuadrigaStorageException {
        if (result.hasErrors()) {
            model.addAttribute("Error", "Error: " + collection.getConceptCollectionName() + "already exists.");
            return new ModelAndView("auth/conceptcollections/addCollectionsForm", "command",
                    new ConceptCollectionFactory().createConceptCollectionObject());
        }

        IUser user = usermanager.getUser(principal.getName());
        collection.setOwner(user);

        conceptControllerManager.addConceptCollection(collection);

        return new ModelAndView("redirect:/auth/conceptcollections");

    }

    /**
     * Returns the list of concept collections of user to the view
     * 
     * @param req
     *            HttpReqest must have selected items ids as parameters
     * @param model
     * @param principal
     * @return
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     * @throws JSONException
     * 
     * */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.CONCEPTCOLLECTION, paramIndex = 1, userRole = {
            RoleNames.ROLE_CC_COLLABORATOR_ADMIN, RoleNames.ROLE_CC_COLLABORATOR_READ_WRITE }) })
    @RequestMapping(value = "auth/conceptcollections/{collection_id}/deleteitems", method = RequestMethod.POST)
    public String deleteItems(@PathVariable("collection_id") String collectionId, HttpServletRequest req,
            ModelMap model, Principal principal, RedirectAttributes redirectAttrs) throws QuadrigaStorageException, QuadrigaAccessException,
            JSONException {

        String[] selectedIds = req.getParameterValues("selected");
        IConceptCollection conceptCollection = conceptCollectionDeepMapper.getConceptCollectionDetails(collectionId);

        int deletedConceptNr = 0;
        if (selectedIds != null && conceptCollection != null) {
            for (String id : selectedIds) {

                conceptControllerManager.deleteItem(id, collectionId, principal.getName());
                IConcept concept = conceptFactory.createConceptObject();
                concept.setConceptId(id);
                if (conceptCollection.getConcepts().contains(concept)) {
                    conceptCollection.getConcepts().remove(concept);
                }
            }
            deletedConceptNr = selectedIds.length;
        }

        conceptControllerManager.fillConceptCollection(conceptCollection);

        fillModel(collectionId, model, principal.getName());
        if (deletedConceptNr > 0) {
            redirectAttrs.addFlashAttribute("show_success_alert", true);
            redirectAttrs.addFlashAttribute("success_alert_msg", deletedConceptNr + " Concepts successfully deleted.");
        } else {
            redirectAttrs.addFlashAttribute("show_info_alert", true);
            redirectAttrs.addFlashAttribute("info_alert_msg", "You didn't select any concepts to delete.");
        }
        return "redirect:/auth/conceptcollections/" + collectionId;
    }

}
