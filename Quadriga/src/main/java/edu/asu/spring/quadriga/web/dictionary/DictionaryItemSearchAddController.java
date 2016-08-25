package edu.asu.spring.quadriga.web.dictionary;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.dictionary.IDictionaryItems;
import edu.asu.spring.quadriga.domain.impl.WordpowerReply.DictionaryEntry;
import edu.asu.spring.quadriga.domain.impl.dictionary.Item;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * This class will handle add and search dictionaries items controller for the
 * dictionary from word power
 * 
 * @author : Lohith Dwaraka
 * 
 */
@Controller
public class DictionaryItemSearchAddController {
    @Autowired
    private IDictionaryManager dictionaryManager;

    @Autowired
    private IUserManager usermanager;
    
    @Autowired
    private MessageSource messageSource;

    private static final Logger logger = LoggerFactory.getLogger(DictionaryItemSearchAddController.class);

    /**
     * Handles the add dictionary item page
     * 
     * @return Return to the adddictionaryitems JSP
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.DICTIONARY, paramIndex = 1, userRole = {
            RoleNames.ROLE_DICTIONARY_COLLABORATOR_ADMIN, RoleNames.ROLE_DICTIONARY_COLLABORATOR_READ_WRITE }) })
    @RequestMapping(value = "auth/dictionaries/addDictionaryItems/{dictionaryid}", method = RequestMethod.GET)
    public String addDictionaryItemPage(@PathVariable("dictionaryid") String dictionaryid, ModelMap model) throws QuadrigaAccessException {

        model.addAttribute("dictionaryid", dictionaryid);
        return "auth/dictionaries/addDictionaryItems";
    }

    /**
     * Handles the form tag for add dictionary item to dictionary
     * 
     * @return Return to list dictionary item page
     * @throws QuadrigaUIAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.DICTIONARY, paramIndex = 2, userRole = {
            RoleNames.ROLE_DICTIONARY_COLLABORATOR_ADMIN, RoleNames.ROLE_DICTIONARY_COLLABORATOR_READ_WRITE }) })
    @RequestMapping(value = "auth/dictionaries/addDictionaryItems/{dictionaryid}", method = RequestMethod.POST)
    public String addDictionaryItem(HttpServletRequest req, @PathVariable("dictionaryid") String dictionaryId,
            @ModelAttribute("SpringWeb") Item dictionaryItems, ModelMap model, Principal principal, Locale locale)
            throws QuadrigaStorageException, QuadrigaAccessException {
        
        String[] values = req.getParameterValues("selected");
        String pos = req.getParameter("pos");
        String word = req.getParameter("word");
        
        if (values == null || values.length == 0) {
            String dictionaryName = dictionaryManager.getDictionaryName(dictionaryId);
            
            List<DictionaryEntry> dictionaryEntryList = dictionaryManager.searchWordPower(word, pos);
            
            model.addAttribute("word", word);
            model.addAttribute("pos", pos);
            model.addAttribute("dictionaryEntryList", dictionaryEntryList);
            model.addAttribute("dictName", dictionaryName);
            // fetch the dictionary details
            IDictionary dictionary = dictionaryManager.getDictionaryDetails(dictionaryId);
            model.addAttribute("dictionary", dictionary);
            model.addAttribute("dictID", dictionaryId);
            
            model.addAttribute("show_error_alert", true);
            model.addAttribute("error_alert_msg", messageSource.getMessage("dictionary.items.add.no_selection", new Object[] {}, locale));

            return "auth/dictionaries/addDictionaryItems";
        }
        
        dictionaryManager.addDictionaryItems(dictionaryItems, values, dictionaryId);
        model.addAttribute("show_success_alert", true);
        model.addAttribute("success_alert_msg", messageSource.getMessage("dictionary.items.add.success", new Object[] {}, locale));
        
        List<IDictionaryItems> dictionaryItemList = dictionaryManager.getDictionaryItems(dictionaryId);
        String dictionaryName = dictionaryManager.getDictionaryName(dictionaryId);
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
     * Admin can use this to search from term and pos from word power
     * 
     * @return Return to list dictionary item page
     * @throws QuadrigaStorageException
     * @throws QuadrigaUIAccessException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.DICTIONARY, paramIndex = 1, userRole = {
            RoleNames.ROLE_DICTIONARY_COLLABORATOR_ADMIN, RoleNames.ROLE_DICTIONARY_COLLABORATOR_READ_WRITE }) })
    @RequestMapping(value = "auth/dictionaries/dictionary/wordSearch/{dictionaryid}", method = RequestMethod.POST)
    public String searchDictionaryItemRestHandle(@PathVariable("dictionaryid") String dictionaryid,
            @RequestParam("itemName") String item, @RequestParam("posdropdown") String pos, Principal principal,
            ModelMap model, Locale locale) throws QuadrigaStorageException, QuadrigaAccessException {
        
        List<DictionaryEntry> dictionaryEntryList = null;
        if (!item.equals("")) {
            dictionaryEntryList = dictionaryManager.searchWordPower(item, pos);
        }
        
        if (dictionaryEntryList == null || dictionaryEntryList.isEmpty()) {
            model.addAttribute("show_info_alert", true);
            model.addAttribute("info_alert_msg", messageSource.getMessage("dictionary.items.add.no_results", new String[] {item, pos}, locale));
        }
        
        model.addAttribute("dictionaryEntryList", dictionaryEntryList);
        String dictionaryName = dictionaryManager.getDictionaryName(dictionaryid);
        model.addAttribute("dictName", dictionaryName);
        model.addAttribute("dictionaryid", dictionaryid);
        model.addAttribute("word", item);
        model.addAttribute("pos", pos);
       
        return "auth/dictionaries/addDictionaryItems";
    }

}
