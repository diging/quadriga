package edu.asu.spring.quadriga.web.dictionary;

import java.security.Principal;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.dictionary.IDictionaryCollaborator;
import edu.asu.spring.quadriga.domain.dictionary.IDictionaryItems;
import edu.asu.spring.quadriga.domain.factory.dictionary.IDictionaryFactory;
import edu.asu.spring.quadriga.domain.impl.WordpowerReply.DictionaryEntry;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * This class will handle list dictionaries items controller for the dictionary
 * 
 * @author : Lohith Dwaraka
 * 
 */
@Controller
public class DictionaryController {
    @Autowired
    private IDictionaryManager dictonaryManager;

    @Autowired
    private IUserManager usermanager;

    @Autowired
    private IQuadrigaRoleManager collaboratorRoleManager;

    @Autowired
    private IDictionaryFactory dictionaryFactory;

    @Autowired
    private IDictionaryManager dictionaryManager;

    @Autowired
    private MessageSource messageSource;

    private static final Logger logger = LoggerFactory.getLogger(DictionaryController.class);

    /**
     * Admin can use this page to check the list of dictionary items in a
     * dictionary and to search and add items from the word power
     * 
     * @return Return to the list dictionary items page of the Quadriga
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     * @throws JSONException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.DICTIONARY, paramIndex = 1, userRole = {
            RoleNames.ROLE_DICTIONARY_COLLABORATOR_ADMIN, RoleNames.ROLE_DICTIONARY_COLLABORATOR_READ,
            RoleNames.ROLE_DICTIONARY_COLLABORATOR_READ_WRITE }) })
    @RequestMapping(value = "auth/dictionaries/{dictionaryid}", method = RequestMethod.GET)
    public String getDictionaryPage(@PathVariable("dictionaryid") String dictionaryid, ModelMap model,
            Principal principal) throws QuadrigaStorageException, QuadrigaAccessException, JSONException {

        // fetch the dictionary details
        IDictionary dictionary = dictionaryManager.getDictionaryDetails(dictionaryid);

        String userName = principal.getName();

        // TODO: getDictionariesItems() should return IDictionaryItems
        List<IDictionaryItems> dictionaryItemList = dictonaryManager.getDictionaryItems(dictionaryid);

        model.addAttribute("dictionaryItemList", dictionaryItemList);
        model.addAttribute("dictionary", dictionary);

        List<IDictionaryCollaborator> existingCollaborators = dictonaryManager.showCollaboratingUsers(dictionaryid);
        model.addAttribute("collaboratingUsers", existingCollaborators);

        IDictionary dictionaryObj = dictionaryFactory.createDictionaryObject();
        dictionaryObj.setDictionaryId(dictionaryid);
        dictionaryObj = dictionaryManager.getDictionaryDetails(dictionaryid);

        model.addAttribute("owner", dictionaryObj.getOwner().getUserName().equals(userName));
        setPermissions(model, userName, existingCollaborators);

        String jsonTreeData = dictonaryManager.getProjectsTree(userName, dictionaryid);
        model.addAttribute("core", jsonTreeData);

        return "auth/dictionary/dictionary";
    }

    private void setPermissions(ModelMap model, String userName, List<IDictionaryCollaborator> existingCollaborators) {
        for (IDictionaryCollaborator collab : existingCollaborators) {
            // if current user is a collaborator, lets get their role
            if (collab.getCollaborator().getUserObj().getUserName().equals(userName)) {
                List<IQuadrigaRole> roles = collab.getCollaborator().getCollaboratorRoles();
                for (IQuadrigaRole role : roles) {
                    if (role.getId().equals(RoleNames.ROLE_DICTIONARY_COLLABORATOR_ADMIN)) {
                        model.addAttribute("isAdmin", true);
                        model.addAttribute("hasWrite", true);
                        model.addAttribute("hasRead", true);
                    }
                    if (role.getId().equals(RoleNames.ROLE_DICTIONARY_COLLABORATOR_READ_WRITE)) {
                        model.addAttribute("hasWrite", true);
                        model.addAttribute("hasRead", true);
                    }
                    if (role.getId().equals(RoleNames.ROLE_DICTIONARY_COLLABORATOR_READ)) {
                        model.addAttribute("hasRead", true);
                    }
                }
            }
        }
        // set owner permissions
        if (model.get("owner").equals(true)) {
            model.addAttribute("isAdmin", true);
            model.addAttribute("hasWrite", true);
            model.addAttribute("hasRead", true);
        }
    }

    /**
     * Admin can use this to delete a dictionary item to dictionary
     * 
     * @return Return to list dictionary item page
     * @throws QuadrigaStorageException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.DICTIONARY, paramIndex = 2, userRole = {
            RoleNames.ROLE_DICTIONARY_COLLABORATOR_ADMIN, RoleNames.ROLE_DICTIONARY_COLLABORATOR_READ_WRITE }) })
    @RequestMapping(value = "auth/dictionaries/deleteDictionaryItems/{dictionaryid}", method = RequestMethod.POST)
    public String deleteDictionaryItem(HttpServletRequest req, @PathVariable("dictionaryid") String dictionaryId,
            ModelMap model, Principal principal, Locale locale) throws QuadrigaStorageException,
            QuadrigaAccessException {

        IUser user = usermanager.getUser(principal.getName());
        String[] values = req.getParameterValues("selected");

        if (values == null) {
            List<IDictionaryItems> dictionaryItemList = dictonaryManager.getDictionaryItems(dictionaryId);
            String dictionaryName = dictonaryManager.getDictionaryName(dictionaryId);

            model.addAttribute("show_error_alert", true);
            model.addAttribute("error_alert_msg",
                    messageSource.getMessage("dictionary.items.remove.no_selection", new Object[] {}, locale));

            model.addAttribute("dictionaryItemList", dictionaryItemList);
            model.addAttribute("dictName", dictionaryName);
            model.addAttribute("dictID", dictionaryId);
            IDictionary dictionary = dictionaryManager.getDictionaryDetails(dictionaryId);
            model.addAttribute("dictionary", dictionary);
            JSONObject core = new JSONObject();
            model.addAttribute("core", core.toString());

            return "auth/dictionary/dictionary";
        }

        // Remove entries
        for (int i = 0; i < values.length; i++) {
            dictonaryManager.deleteDictionariesItems(dictionaryId, values[i], user.getUserName());
        }

        model.addAttribute("show_success_alert", true);
        model.addAttribute("success_alert_msg",
                messageSource.getMessage("dictionary.items.remove.success", new Object[] {}, locale));

        List<IDictionaryItems> dictionaryItemList = dictonaryManager.getDictionaryItems(dictionaryId);
        String dictionaryName = dictonaryManager.getDictionaryName(dictionaryId);
        model.addAttribute("dictionaryItemList", dictionaryItemList);
        model.addAttribute("dictName", dictionaryName);
        model.addAttribute("dictID", dictionaryId);
        IDictionary dictionary = dictionaryManager.getDictionaryDetails(dictionaryId);
        model.addAttribute("dictionary", dictionary);
        JSONObject core = new JSONObject();
        model.addAttribute("core", core.toString());
        return "auth/dictionary/dictionary";
    }

    /**
     * Admin can use this to update a dictionary item's item to dictionary
     * 
     * @return Return to list dictionary item page
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.DICTIONARY, paramIndex = 2, userRole = {
            RoleNames.ROLE_DICTIONARY_COLLABORATOR_ADMIN, RoleNames.ROLE_DICTIONARY_COLLABORATOR_READ_WRITE }) })
    @RequestMapping(value = "auth/dictionaries/updateDictionaryItems/{dictionaryid}", method = RequestMethod.POST)
    public String updateDictionaryItem(HttpServletRequest req, @PathVariable("dictionaryid") String dictionaryId,
            ModelMap model, Principal principal) throws QuadrigaStorageException, QuadrigaAccessException {

        IUser user = usermanager.getUser(principal.getName());
        String[] values = req.getParameterValues("selected");
        String msg = "";
        String errormsg = "";
        int flag = 0;
        if (values == null) {
            model.addAttribute("updatesuccess", 0);
            // model.addAttribute("updateerrormsg", "Items were not selected");
            List<IDictionaryItems> dictionaryItemList = dictonaryManager.getDictionaryItems(dictionaryId);
            String dictionaryName = dictonaryManager.getDictionaryName(dictionaryId);
            model.addAttribute("dictionaryItemList", dictionaryItemList);
            model.addAttribute("dictName", dictionaryName);
            model.addAttribute("dictID", dictionaryId);
            JSONObject core = new JSONObject();
            model.addAttribute("core", core.toString());
            IDictionary dictionary = dictionaryManager.getDictionaryDetails(dictionaryId);
            model.addAttribute("dictionary", dictionary);
            return "auth/dictionary/dictionary";
        } else {
            for (int i = 0; i < values.length; i++) {

                List<DictionaryEntry> dictionaryEntryList = dictonaryManager.getUpdateFromWordPower(dictionaryId,
                        values[i]);
                Iterator<DictionaryEntry> I = dictionaryEntryList.iterator();
                if (I.hasNext()) {
                    DictionaryEntry dictionaryEntry = I.next();
                    dictonaryManager.updateDictionariesItems(dictionaryId, values[i], dictionaryEntry.getLemma(),
                            dictionaryEntry.getPos());
                } else {
                    msg = "Error getting data from Word Power";
                    flag = 1;
                    errormsg = msg;
                }
                if (!msg.equals("")) {
                    flag = 1;
                    errormsg = msg;
                }
            }
        }

        if (flag == 0) {
            // these things don't need to be logged.
            logger.debug("Successfully updated");
            model.addAttribute("updatesuccess", 1);
            model.addAttribute("updatesuccessmsg", "Items updated successfully");
        } else if (flag == 1) {
            logger.info("Please check errormsg : " + errormsg);
            if (errormsg.equals("Item doesnot exists in this dictionary")) {
                model.addAttribute("updatesuccess", 0);
                model.addAttribute("updateerrormsg", "Items doesn't exist for dictionary id :" + dictionaryId);
            } else {
                model.addAttribute("updatesuccess", 0);
                model.addAttribute("updateerrormsg", errormsg);
            }
        }
        logger.debug("Item Returned ");
        List<IDictionaryItems> dictionaryItemList = dictonaryManager.getDictionaryItems(dictionaryId);
        String dictionaryName = dictonaryManager.getDictionaryName(dictionaryId);
        model.addAttribute("dictionaryItemList", dictionaryItemList);
        model.addAttribute("dictName", dictionaryName);
        IDictionary dictionary = dictionaryManager.getDictionaryDetails(dictionaryId);
        model.addAttribute("dictionary", dictionary);
        model.addAttribute("dictID", dictionaryId);
        JSONObject core = new JSONObject();
        model.addAttribute("core", core.toString());
        return "auth/dictionary/dictionary";
    }

}
