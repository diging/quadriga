package edu.asu.spring.quadriga.web.workspace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.factory.workbench.ITextFileFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class AddTextController {

    @Autowired
    private ITextFileFactory textFileFactory;
    
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT,paramIndex = 1, userRole = {RoleNames.ROLE_COLLABORATOR_ADMIN,RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN,RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR} )})
    public ModelAndView addWorkSpaceRequestForm(@PathVariable("projectid") String projectid) throws QuadrigaStorageException, QuadrigaAccessException
    {
            ModelAndView model;        
            model = new ModelAndView("auth/workbench/workspace/addtext");
            model.getModelMap().put("textfile", textFileFactory.createTextFileObject());
            model.getModelMap().put("projectid", projectid);
            model.getModelMap().put("success", 0);
            return model;
    }
    

}
