package edu.asu.spring.quadriga.web.workspace;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class AddTextController {

    @Autowired
    private ITextFileFactory textFileFactory;
    
    @Autowired
    private ITextFileService txtFileService;
    
    @RequestMapping(value="/auth/workbench/workspace/{workspaceid}/addtext", method=RequestMethod.GET)
    public ModelAndView addTextFileForm(@PathVariable("workspaceid") String workspaceid) throws QuadrigaStorageException, QuadrigaAccessException
    {
                   
            ModelAndView model = new ModelAndView("auth/workbench/workspace/addtext");
            model.getModelMap().put("textfile", textFileFactory.createTextFileObject());
            model.getModelMap().put("workspaceId", workspaceid );
            return model;
    }
    
    
    @RequestMapping(value="/auth/workbench/workspace/{workspaceid}/addtext", method=RequestMethod.POST)
	public ModelAndView saveTextFileForm(HttpServletResponse resp,
			@ModelAttribute("textfile") TextFile txtFile, @PathVariable("workspaceid") String workspaceid)
			throws QuadrigaStorageException, QuadrigaAccessException,
			IOException
    {
                   
            ModelAndView model = new ModelAndView("auth/workbench/workspace/addtext");
            model.getModelMap().put("textfile", textFileFactory.createTextFileObject());
            txtFileService.saveTextFile(txtFile);
            model.getModelMap().put("workspaceId", workspaceid );
            return model;
    }
    

}
