package edu.asu.spring.quadriga.web.dictionary;

import java.security.Principal;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.dictionary.IDictionaryItems;
import edu.asu.spring.quadriga.domain.dictionary.impl.Item;
import edu.asu.spring.quadriga.domain.impl.WordpowerReply.DictionaryEntry;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * This class will handle list dictionaries items controller for the dictionary
 * (collaborators)
 * 
 * @author : Lohith Dwaraka
 * 
 */
@Controller
public class DictionaryItemCollabController {

    @Autowired
    private IDictionaryManager dictonaryManager;

    private static final Logger logger = LoggerFactory
            .getLogger(DictionaryController.class);

    @Autowired
    private IUserManager usermanager;

    @Autowired
    private IQuadrigaRoleManager collaboratorRoleManager;

    /**
     * Retrieve the dictionary items associated with the logged in user
     * 
     * @param dictionaryid
     * @param model
     * @param principal
     * @return
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @RequestMapping(value = "auth/dictionaries/collab/{dictionaryid}", method = RequestMethod.GET)
    public String getDictionaryCollabPage(
            @PathVariable("dictionaryid") String dictionaryid, ModelMap model,
            Principal principal) throws QuadrigaStorageException,
            QuadrigaAccessException {
        IUser user = usermanager.getUser(principal.getName());

        List<IDictionaryItems> dictionaryItemList = dictonaryManager
                .getDictionaryItemsDetailsCollab(dictionaryid);
        
        String dictionaryName = dictonaryManager.getDictionaryName(dictionaryid);
        List<String> roles = dictonaryManager.getDictionaryCollaboratorRoles(
                user.getUserName(), dictionaryid);
        for (String tempRole : roles) {
            String roleType = collaboratorRoleManager.getQuadrigaRoleByDbId(
                    IQuadrigaRoleManager.DICT_ROLES, tempRole).getId();
            if (roleType
                    .equals(RoleNames.ROLE_DICTIONARY_COLLABORATOR_READ_WRITE)) {
                model.addAttribute("roleAccess", 1);
            }
        }
        model.addAttribute("dictionaryItemList", dictionaryItemList);
        model.addAttribute("dictName", dictionaryName);
        model.addAttribute("dictionaryid", dictionaryid);

        return "auth/dictionary/dictionarycollab";
    }

    /**
     * Admin can use this to delete a dictionary item to dictionary
     * 
     * @return Return to list dictionary item page
     * @throws QuadrigaStorageException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.DICTIONARY, paramIndex = 2, userRole = {
            RoleNames.ROLE_DICTIONARY_COLLABORATOR_ADMIN,
            RoleNames.ROLE_DICTIONARY_COLLABORATOR_READ_WRITE }) })
    @RequestMapping(value = "auth/dictionaries/deleteDictionaryItemsCollab/{dictionaryid}", method = RequestMethod.POST)
    public String deleteDictionaryItem(HttpServletRequest req,
            @PathVariable("dictionaryid") String dictionaryId, ModelMap model,
            Principal principal) throws QuadrigaStorageException, QuadrigaAccessException {

        IUser user = usermanager.getUser(principal.getName());
        String[] values = req.getParameterValues("selected");
        String msg = "";
        String errormsg = "";
        int flag = 0;

        if (values == null) {
            model.addAttribute("collab", 1);
            model.addAttribute("delsuccess", 0);
            // model.addAttribute("delerrormsg", "Items were not selected");

            List<IDictionaryItems> dictionaryItemList = dictonaryManager
                    .getDictionaryItemsDetailsCollab(dictionaryId);
            String dictionaryName = dictonaryManager
                    .getDictionaryName(dictionaryId);
            model.addAttribute("dictionaryItemList", dictionaryItemList);
            model.addAttribute("dictName", dictionaryName);
            List<String> roles = dictonaryManager
                    .getDictionaryCollaboratorRoles(user.getUserName(),
                            dictionaryId);
            for (String tempRole : roles) {
                String roleType = collaboratorRoleManager
                        .getQuadrigaRoleByDbId(IQuadrigaRoleManager.DICT_ROLES,
                                tempRole).getId();
                if (roleType
                        .equals(RoleNames.ROLE_DICTIONARY_COLLABORATOR_READ_WRITE)) {
                    model.addAttribute("roleAccess", 1);
                }
            }

            model.addAttribute("dictID", dictionaryId);
            return "auth/dictionary/dictionarycollab";
        } else {
            for (int i = 0; i < values.length; i++) {
                logger.info("Deleting item for dictionary id: " + dictionaryId
                        + " and term id : " + i + " : " + values[i]);
                dictonaryManager.deleteDictionaryItemsCollab(dictionaryId,
                        values[i]);
                if (!msg.equals("")) {
                    flag = 1;
                    errormsg = msg;
                }
            }
        }
        if (flag == 0) {
            model.addAttribute("delsuccess", 1);
            // model.addAttribute("delsuccessmsg",
            // "Items  deleted successfully");
        } else if (flag == 1) {
            logger.info(" Errormsg " + errormsg);
            if (errormsg.equals("Item doesnot exists in this dictionary")) {
                model.addAttribute("delsuccess", 0);
                model.addAttribute("delerrormsg",
                        "Items doesn't exist for dictionary id :"
                                + dictionaryId);
            } else {
                model.addAttribute("delsuccess", 0);
                model.addAttribute("delerrormsg", errormsg);
            }
        }

        List<IDictionaryItems> dictionaryItemList = dictonaryManager
                .getDictionaryItemsDetailsCollab(dictionaryId);
        String dictionaryName = dictonaryManager
                .getDictionaryName(dictionaryId);
        List<String> role = dictonaryManager.getDictionaryCollaboratorRoles(
                user.getUserName(), dictionaryId);
        for (String tempRole : role) {
            String roleType = collaboratorRoleManager.getQuadrigaRoleByDbId(
                    IQuadrigaRoleManager.DICT_ROLES, tempRole).getId();
            if (roleType
                    .equals(RoleNames.ROLE_DICTIONARY_COLLABORATOR_READ_WRITE)) {
                model.addAttribute("roleAccess", 1);
            }
        }
        model.addAttribute("dictionaryItemList", dictionaryItemList);
        model.addAttribute("dictName", dictionaryName);
        model.addAttribute("dictID", dictionaryId);

        return "auth/dictionary/dictionarycollab";
    }

    /**
     * Admin can use this to update a dictionary item's item to dictionary
     * 
     * @return Return to list dictionary item page
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.DICTIONARY, paramIndex = 2, userRole = {
            RoleNames.ROLE_DICTIONARY_COLLABORATOR_ADMIN,
            RoleNames.ROLE_DICTIONARY_COLLABORATOR_READ_WRITE }) })
    @RequestMapping(value = "auth/dictionaries/updateDictionaryItemsCollab/{dictionaryid}", method = RequestMethod.POST)
    public String updateDictionaryItem(HttpServletRequest req,
            @PathVariable("dictionaryid") String dictionaryId, ModelMap model,
            Principal principal) throws QuadrigaStorageException, QuadrigaAccessException {

        IUser user = usermanager.getUser(principal.getName());
        String[] values = req.getParameterValues("selected");
        String msg = "";
        String errormsg = "";
        int flag = 0;
        if (values == null) {
            model.addAttribute("updatesuccess", 0);

            List<IDictionaryItems> dictionaryItemList = dictonaryManager
                    .getDictionaryItemsDetailsCollab(dictionaryId);
            String dictionaryName = dictonaryManager
                    .getDictionaryName(dictionaryId);
            List<String> roles = dictonaryManager
                    .getDictionaryCollaboratorRoles(user.getUserName(),
                            dictionaryId);
            for (String tempRole : roles) {
                String roleType = collaboratorRoleManager
                        .getQuadrigaRoleByDbId(IQuadrigaRoleManager.DICT_ROLES,
                                tempRole).getId();
                if (roleType
                        .equals(RoleNames.ROLE_DICTIONARY_COLLABORATOR_READ_WRITE)) {
                    model.addAttribute("roleAccess", 1);
                }
            }

            model.addAttribute("collab", 1);
            model.addAttribute("dictionaryItemList", dictionaryItemList);
            model.addAttribute("dictName", dictionaryName);
            model.addAttribute("dictID", dictionaryId);
            return "auth/dictionary/dictionarycollab";
        } else {
            for (int i = 0; i < values.length; i++) {

                List<DictionaryEntry> dictionaryEntryList = dictonaryManager
                        .getUpdateFromWordPower(dictionaryId, values[i]);
                Iterator<DictionaryEntry> I = dictionaryEntryList.iterator();
                if (I.hasNext()) {
                    DictionaryEntry dictionaryEntry = I.next();
                    dictonaryManager.updateDictionariesItems(dictionaryId,
                            values[i], dictionaryEntry.getLemma(),
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
                model.addAttribute("updateerrormsg",
                        "Items doesn't exist for dictionary id :"
                                + dictionaryId);
            } else {
                model.addAttribute("updatesuccess", 0);
                model.addAttribute("updateerrormsg", errormsg);
            }
        }
        logger.debug("Item Returned ");

        List<IDictionaryItems> dictionaryItemList = dictonaryManager
                .getDictionaryItemsDetailsCollab(dictionaryId);
        String dictionaryName = dictonaryManager
                .getDictionaryName(dictionaryId);
        model.addAttribute("dictionaryItemList", dictionaryItemList);
        List<String> role = dictonaryManager.getDictionaryCollaboratorRoles(
                user.getUserName(), dictionaryId);
        for (String tempRole : role) {
            String roleType = collaboratorRoleManager.getQuadrigaRoleByDbId(
                    IQuadrigaRoleManager.DICT_ROLES, tempRole).getId();
            if (roleType
                    .equals(RoleNames.ROLE_DICTIONARY_COLLABORATOR_READ_WRITE)) {
                model.addAttribute("roleAccess", 1);
            }
        }

        model.addAttribute("dictName", dictionaryName);
        model.addAttribute("dictID", dictionaryId);

        return "auth/dictionary/dictionarycollab";
    }

    /**
     * This methods adds the selected items to the dictionary.
     * 
     * @param req
     * @param dictionaryId
     * @param dictionaryItems
     * @param model
     * @param principal
     * @return
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.DICTIONARY, paramIndex = 2, userRole = {
            RoleNames.ROLE_DICTIONARY_COLLABORATOR_ADMIN,
            RoleNames.ROLE_DICTIONARY_COLLABORATOR_READ_WRITE }) })
    @RequestMapping(value = "auth/dictionaries/addDictionaryItemsCollab/{dictionaryid}", method = RequestMethod.POST)
    public String addDictionaryItem(HttpServletRequest req,
            @PathVariable("dictionaryid") String dictionaryId,
            @ModelAttribute("SpringWeb") Item dictionaryItems, ModelMap model,
            Principal principal) throws QuadrigaStorageException,
            QuadrigaAccessException {
        IUser user = usermanager.getUser(principal.getName());
        String msg = "";
        String[] values = req.getParameterValues("selected");
        if (values != null) {
            dictonaryManager.addDictionaryItems(dictionaryItems, values,
                    dictionaryId);
        } else {
            model.addAttribute("additemsuccess", 2);

            List<IDictionaryItems> dictionaryItemList = dictonaryManager
                    .getDictionaryItemsDetailsCollab(dictionaryId);
            String dictionaryName = dictonaryManager
                    .getDictionaryName(dictionaryId);
            List<String> roles = dictonaryManager
                    .getDictionaryCollaboratorRoles(user.getUserName(),
                            dictionaryId);
            for (String tempRole : roles) {
                String roleType = collaboratorRoleManager
                        .getQuadrigaRoleByDbId(IQuadrigaRoleManager.DICT_ROLES,
                                tempRole).getId();
                if (roleType
                        .equals(RoleNames.ROLE_DICTIONARY_COLLABORATOR_READ_WRITE)) {
                    model.addAttribute("roleAccess", 1);
                }
            }

            model.addAttribute("collab", 1);
            model.addAttribute("dictionaryItemList", dictionaryItemList);
            model.addAttribute("dictName", dictionaryName);
            model.addAttribute("dictID", dictionaryId);

            return "auth/dictionary/dictionarycollab";
        }

        if (msg.equals("")) {
            model.addAttribute("additemsuccess", 1);
        } else {
            if (msg.equals("ItemExists")) {
                model.addAttribute("additemsuccess", 0);
                model.addAttribute("errormsg",
                        "Items already exist for dictionary id :"
                                + dictionaryId);
            } else {
                model.addAttribute("additemssuccess", 0);
                model.addAttribute("errormsg", msg);
            }
        }

        List<IDictionaryItems> dictionaryItemList = dictonaryManager
                .getDictionaryItemsDetailsCollab(dictionaryId);
        String dictionaryName = dictonaryManager
                .getDictionaryName(dictionaryId);
        List<String> role = dictonaryManager.getDictionaryCollaboratorRoles(
                user.getUserName(), dictionaryId);
        for (String tempRole : role) {
            String roleType = collaboratorRoleManager.getQuadrigaRoleByDbId(
                    IQuadrigaRoleManager.DICT_ROLES, tempRole).getId();
            if (roleType
                    .equals(RoleNames.ROLE_DICTIONARY_COLLABORATOR_READ_WRITE)) {
                model.addAttribute("roleAccess", 1);
            }
        }
        model.addAttribute("dictionaryItemList", dictionaryItemList);
        model.addAttribute("dictName", dictionaryName);
        model.addAttribute("dictID", dictionaryId);

        return "auth/dictionary/dictionarycollab";
    }

    /**
     * Handles the add dictionary item page
     * 
     * @return Return to the adddictionaryitems JSP
     */
    @RequestMapping(value = "auth/dictionaries/addDictionaryItemsCollab/{dictionaryid}", method = RequestMethod.GET)
    public String addDictionaryPage(
            @PathVariable("dictionaryid") String dictionaryid, ModelMap model) {
        model.addAttribute("collab", 1);
        model.addAttribute("dictionaryid", dictionaryid);
        return "auth/dictionaries/addDictionaryItemsCollab";
    }

    /**
     * Admin can use this to search from term and pos from word power
     * 
     * @return Return to list dictionary item page
     * @throws QuadrigaStorageException
     * @throws QuadrigaUIAccessException
     */

    @RequestMapping(value = "auth/dictionaries/dictionarycollab/wordSearch/{dictionaryid}", method = RequestMethod.POST)
    public String searchDictionaryItemRestHandle(
            @PathVariable("dictionaryid") String dictionaryid,
            @RequestParam("itemName") String item,
            @RequestParam("posdropdown") String pos, ModelMap model,
            Principal principal) throws QuadrigaStorageException,
            QuadrigaAccessException {
        try {
            List<DictionaryEntry> dictionaryEntryList = null;
            if (!item.equals("")) {
                logger.debug("Query for Item :" + item + " and pos :" + pos);
                dictionaryEntryList = dictonaryManager.searchWordPower(item,
                        pos);
            }
            model.addAttribute("status", 1);
            model.addAttribute("dictionaryEntryList", dictionaryEntryList);

            List<IDictionaryItems> dictionaryItemList = dictonaryManager
                    .getDictionaryItems(dictionaryid);
            String dictionaryName = dictonaryManager
                    .getDictionaryName(dictionaryid);
            model.addAttribute("dictionaryItemList", dictionaryItemList);
            model.addAttribute("dictName", dictionaryName);
            model.addAttribute("dictionaryid", dictionaryid);
            if (dictionaryEntryList == null) {
                model.addAttribute("errorstatus", 1);
            }
            model.addAttribute("collab", 1);

        } catch (QuadrigaStorageException e) {
            throw new QuadrigaStorageException(
                    "Oops the DB is an hard hangover, please try later");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "auth/dictionaries/addDictionaryItemsCollab";
    }
}
