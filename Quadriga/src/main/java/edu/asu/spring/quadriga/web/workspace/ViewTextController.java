package edu.asu.spring.quadriga.web.workspace;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.web.login.RoleNames;

public class ViewTextController {
    
    
    
    
    
    @AccessPolicies({
        @ElementAccessPolicy(type = CheckedElementType.WORKSPACE, paramIndex = 1, userRole = {
                RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN }),
        @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 2, userRole = {
                RoleNames.ROLE_COLLABORATOR_ADMIN, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN,
                RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR }) })
@RequestMapping(value = "/auth/workbench/workspace/{projectid}/{workspaceid}/viewtext?txtid={textId}", method = RequestMethod.GET)
public void viewTextfile(@PathVariable("textId") String txtId,
        @PathVariable("projectid") String projid) throws QuadrigaStorageException, QuadrigaAccessException {

    System.out.println(txtId);
}


}
