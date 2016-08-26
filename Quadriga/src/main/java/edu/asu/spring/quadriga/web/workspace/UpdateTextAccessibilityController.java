package edu.asu.spring.quadriga.web.workspace;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.spring.quadriga.domain.enums.ETextAccessibility;
import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.textfile.ITextFileManager;

@Controller
public class UpdateTextAccessibilityController {

    @Autowired
    private ITextFileManager tfManager;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/auth/workbench/workspace/{workspaceId}/{textId}/{accessStatus}", method = RequestMethod.POST)
    public String updateTextFileAccessibility(Model model, @PathVariable("textId") String textId,
            @PathVariable("workspaceId") String workspaceId, @PathVariable("accessStatus") String accessibility,
            RedirectAttributes redirectAttributes, Locale locale) throws FileStorageException {

        ITextFile textFile = null;
        try {
            textFile = tfManager.getTextFile(textId);
        } catch (QuadrigaStorageException ex) {
            redirectAttributes.addFlashAttribute("show_error_alert", true);
            redirectAttributes.addFlashAttribute("error_alert_msg",
                    messageSource.getMessage("workspace.text.retrieve.failure", new Object[] {}, locale));
        }

        if (accessibility == null || accessibility.isEmpty()) {
            redirectAttributes.addFlashAttribute("show_error_alert", true);
            redirectAttributes.addFlashAttribute("error_alert_msg",
                    messageSource.getMessage("workspace.text.accessibility.failure", new Object[] {}, locale));
            return "redirect:/auth/workbench/workspace/" + workspaceId;
        }

        textFile.setAccessibility(ETextAccessibility.valueOf(accessibility.toUpperCase()));
        tfManager.storeTextFile(textFile);
        return "redirect:/auth/workbench/workspace/" + workspaceId;
    }
}