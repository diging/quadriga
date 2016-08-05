package edu.asu.spring.quadriga.web.workspace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.textfile.ITextFileManager;

@Controller
public class UpdateTextAccessibilityController {

    @Autowired
    private ITextFileManager tfManager;

    @RequestMapping(value = "/auth/workbench/workspace/{workspaceId}/{textId}/updateAccessibility", method = RequestMethod.GET)
    public String updateTextFileAccessibility(Model model, @PathVariable("textId") String textId,
            @PathVariable("workspaceId") String workspaceId) throws QuadrigaStorageException, FileStorageException {

        ITextFile textFile = tfManager.getTextFile(textId);
        ITextFile updatedTextFile = tfManager.updateTextFileAccessibility(textFile);
        tfManager.saveTextFileToDB(updatedTextFile);
        return "redirect:/auth/workbench/workspace/" + workspaceId;
    }
}
