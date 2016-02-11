package edu.asu.spring.quadriga.web.workspace;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.factory.workbench.ITextFileFactory;
import edu.asu.spring.quadriga.domain.impl.workspace.TextFile;
import edu.asu.spring.quadriga.domain.impl.workspace.WorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.textfile.ITextFileService;
import edu.asu.spring.quadriga.validator.AddTextValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class AddTextController {

    @Autowired
    private ITextFileFactory textFileFactory;

    @Autowired
    private ITextFileService txtFileService;

    @Autowired
    private AddTextValidator txtValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {

        binder.setValidator(txtValidator);
    }

    @RequestMapping(value = "/auth/workbench/workspace/{workspaceid}/addtext", method = RequestMethod.GET)
    public ModelAndView addTextFileForm(@PathVariable("workspaceid") String workspaceid)
            throws QuadrigaStorageException, QuadrigaAccessException {

        ModelAndView model = new ModelAndView("auth/workbench/workspace/addtext");
        model.getModelMap().put("textfile", textFileFactory.createTextFileObject());
        model.getModelMap().put("workspaceId", workspaceid);
        model.getModelMap().put("success", "0");
        return model;
    }

    @RequestMapping(value = "/auth/workbench/workspace/{workspaceid}/addtext", method = RequestMethod.POST)
    public ModelAndView saveTextFileForm(HttpServletResponse resp,
            @Validated @ModelAttribute("textfile") TextFile txtFile, BindingResult result,
            @PathVariable("workspaceid") String workspaceid)
                    throws QuadrigaStorageException, QuadrigaAccessException, IOException {

        ModelAndView model = new ModelAndView("auth/workbench/workspace/addtext");
        if (result.hasErrors()) {
            model.getModelMap().put("textfile", txtFile);
            model.getModelMap().put("workspaceId", workspaceid);
            model.getModelMap().put("success", "0");
        } else {
            model.getModelMap().put("textfile", textFileFactory.createTextFileObject());
            model.getModelMap().put("workspaceId", workspaceid);
            txtFile.setWorkspaceId(workspaceid);
            txtFileService.saveTextFile(txtFile);
            model.getModelMap().put("success", "1");
        }
        return model;
    }

}
