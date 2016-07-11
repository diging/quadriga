package edu.asu.spring.quadriga.web.workspace;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.service.textfile.ITextFileManager;
import edu.asu.spring.quadriga.web.login.RoleNames;
import edu.asu.spring.quadriga.web.util.TextHelper;

@Controller
public class ViewTextController {

    @Autowired
    private ITextFileManager tfManager;
    
    @Autowired
    private TextHelper textHelper;

    private static final Logger logger = LoggerFactory.getLogger(ViewTextController.class);

    @ResponseBody
    @RequestMapping(value = "/auth/workbench/workspace/{projectid}/{workspaceid}/viewtext", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    @ElementAccessPolicy(type = CheckedElementType.WORKSPACE, paramIndex = 0, userRole = {
            RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN, RoleNames.ROLE_QUADRIGA_ADMIN,
            RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN, RoleNames.ROLE_WORKSPACE_COLLABORATOR_EDITOR })
    public ResponseEntity<String> viewTextfile(@RequestParam("txtid") String txtId, HttpServletResponse response,
            HttpServletRequest request) throws QuadrigaAccessException {   
        try {
            String text = tfManager.retrieveTextFileContent(txtId);
            return textHelper.getResponse(text, response);        
        } catch (FileStorageException e) {
            logger.error(e.getMessage());
            String respMessage = "Error while retrieving the file content";
            return new ResponseEntity<String>(respMessage, HttpStatus.NOT_FOUND);
        }

    }

}
