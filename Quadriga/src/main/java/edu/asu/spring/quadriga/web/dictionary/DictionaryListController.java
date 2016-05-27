package edu.asu.spring.quadriga.web.dictionary;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;

/**
 * This class will handle list dictionaries controller for the dictionary
 * 
 * @author : Lohith Dwaraka
 * 
 */
@Controller
public class DictionaryListController {
   
    @Autowired
    private IDictionaryManager dictionaryManager;

    private static final Logger logger = LoggerFactory.getLogger(DictionaryListController.class);

    /**
     * Admin can use this page to check the list of dictionary he is accessible
     * to
     * 
     * @return Return to the dictionary home page of the Quadriga
     * @throws QuadrigaStorageException 
     * @throws QuadrigaAccessException 
     */
    @RequestMapping(value = "auth/dictionaries", method = RequestMethod.GET)
    public String listDictionary(ModelMap model, Principal principal) throws QuadrigaStorageException, QuadrigaAccessException {
        String userId = principal.getName();
        List<IDictionary> dictionaryList = null;
        List<IDictionary> dictionaryCollabList = null;
        dictionaryList = dictionaryManager.getDictionariesList(userId);

        dictionaryCollabList = dictionaryManager.getDictionaryCollabOfUser(userId);
        
        model.addAttribute("dictionarylist", dictionaryList);
        model.addAttribute("dictionaryCollabList", dictionaryCollabList);
        model.addAttribute("userId", userId);

        return "auth/dictionaries";
    }

    

    
}
