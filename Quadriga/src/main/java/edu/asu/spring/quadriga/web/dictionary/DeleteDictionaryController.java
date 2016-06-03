package edu.asu.spring.quadriga.web.dictionary;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;

@Controller
public class DeleteDictionaryController {

    @Autowired
    private IDictionaryManager dictionaryManager;

    @Autowired
    private IUserManager usermanager;

    @Autowired
    private MessageSource messageSource;

    /**
     * This method retrieves the dictionaries associated with the logged in user
     * for deletion
     * 
     * @param model
     * @return
     * @throws QuadrigaStorageException
     */
    @RequestMapping(value = "auth/dictionaries/delete", method = RequestMethod.GET)
    public String deleteDictionaryGet(Model model) throws QuadrigaStorageException {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = user.getUsername();
        List<IDictionary> dictionaryList = dictionaryManager.getDictionariesList(user.getUsername());

        model.addAttribute("dictinarylist", dictionaryList);
        model.addAttribute("userId", userId);

        return "auth/dictionaries/deleteDictionary";
    }

    /**
     * This method deletes the selected dictionaries.
     * 
     * @param req
     * @param model
     * @param principal
     * @return
     * @throws QuadrigaStorageException
     */
    @RequestMapping(value = "auth/dictionaries/delete", method = RequestMethod.POST)
    public String deleteDictionary(HttpServletRequest req, ModelMap model, Principal principal, Locale locale, RedirectAttributes redirectAttrs)
            throws QuadrigaStorageException {
        IUser user = usermanager.getUser(principal.getName());

        List<String> dictionariesToDelete = null;
        String[] selected = req.getParameterValues("selected");
        if (selected != null) {
            dictionariesToDelete = Arrays.asList(selected);
        }

        if (dictionariesToDelete == null || dictionariesToDelete.isEmpty()) {
            List<IDictionary> dictionaryList = dictionaryManager.getDictionariesList(user.getUserName());

            model.addAttribute("dictinarylist", dictionaryList);
            model.addAttribute("userId", user.getUserName());

            model.addAttribute("show_error_alert", true);
            model.addAttribute("error_alert_msg",
                    messageSource.getMessage("dictionary.delete.no_selection", new Object[] {}, locale));

            return "auth/dictionaries/deleteDictionary";
        }

        StringBuffer errorMsg = new StringBuffer();
        StringBuffer successMsg = new StringBuffer();
        for (String dictId : dictionariesToDelete) {
            dictId = dictId.trim();
            String owner = dictionaryManager.getDictionaryOwner(dictId);
            String dictName = dictionaryManager.getDictionaryName(dictId);

            if (!owner.equals(principal.getName())) {
                redirectAttrs.addFlashAttribute("show_error_alert", true);
                errorMsg.append(messageSource.getMessage("dictionary.delete.not_owner", new String[] { dictName },
                        locale));
                errorMsg.append("\n");
                continue;
            }

            try {
                dictionaryManager.deleteDictionary(dictId);
                redirectAttrs.addFlashAttribute("show_success_alert", true);
                successMsg.append(messageSource.getMessage("dictionary.delete.success", new String[] { dictName },
                        locale));
                successMsg.append("\n");
            } catch (QuadrigaStorageException ex) {
                redirectAttrs.addFlashAttribute("show_error_alert", true);
                errorMsg.append(messageSource.getMessage("dictionary.delete.error", new String[] { dictName }, locale));
                errorMsg.append("\n");
            }

        }
        redirectAttrs.addFlashAttribute("success_alert_msg", successMsg.toString());
        redirectAttrs.addFlashAttribute("error_alert_msg", errorMsg.toString());

        return "redirect:/auth/dictionaries";
    }
}
