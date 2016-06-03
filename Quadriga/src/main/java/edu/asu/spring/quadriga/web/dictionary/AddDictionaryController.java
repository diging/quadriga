package edu.asu.spring.quadriga.web.dictionary;

import java.security.Principal;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factory.dictionary.IDictionaryFactory;
import edu.asu.spring.quadriga.domain.impl.dictionary.Dictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;
import edu.asu.spring.quadriga.validator.NewDictionaryValidator;

@Controller
public class AddDictionaryController {

    @Autowired
    private IDictionaryManager dictionaryManager;

    @Autowired
    private IDictionaryFactory dictionaryFactory;

    @Autowired
    private IUserManager usermanager;
    
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private NewDictionaryValidator dictionaryValidator;

    @InitBinder
    protected void initBinder(WebDataBinder validateBinder) {
         validateBinder.setValidator(dictionaryValidator);
    }

    /**
     * Handles the bean mapping from form tag
     * 
     * 
     * @return Used to handle the data from the form:form tag and map it to
     *         Dicitonary object
     */
    @RequestMapping(value = "auth/dictionaries/add", method = RequestMethod.GET)
    public String addDictionaryForm(Model m) {
        m.addAttribute("dictionary", dictionaryFactory.createDictionaryObject());
        return "auth/dictionaries/addDictionary";
    }

    /**
     * Admin can use this page to new dictionary to his list
     * 
     * @return Return to the add dictionary status page
     * @throws QuadrigaStorageException
     */
    @RequestMapping(value = "auth/dictionaries/add", method = RequestMethod.POST)
    public String addDictionaryHandle(@Validated @ModelAttribute("dictionary") Dictionary dictionary, BindingResult results, ModelMap model,
            Principal principal, RedirectAttributes redirectAttrs, Locale locale)
            throws QuadrigaStorageException {
        IUser user = usermanager.getUser(principal.getName());
        dictionary.setOwner(user);

        
        if (results.hasErrors()) {
            model.addAttribute("dictionary", dictionary);
            model.addAttribute("show_error_alert",  true);
            model.addAttribute("error_alert_msg", messageSource.getMessage("dictionary.create.failure", new Object[] {}, locale));
            return "auth/dictionaries/addDictionary";
        }

        dictionaryManager.addNewDictionary(dictionary);
        redirectAttrs.addFlashAttribute("show_success_alert", true);
        redirectAttrs.addFlashAttribute("success_alert_msg",
                messageSource.getMessage("dictionary.create.success", new Object[] {}, locale));
        
        return "redirect:/auth/dictionaries";
    }
}
