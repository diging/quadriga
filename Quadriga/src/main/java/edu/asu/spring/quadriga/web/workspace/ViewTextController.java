package edu.asu.spring.quadriga.web.workspace;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.textfile.ITextFileManager;

@Controller
public class ViewTextController {

    @Autowired
    private ITextFileManager tfManager;

    @RequestMapping(value = "/auth/workbench/workspace/{projectid}/{workspaceid}/viewtext", method = RequestMethod.GET)
    public HttpServletResponse viewTextfile(@RequestParam("txtid") String txtId, HttpServletResponse response,
            HttpServletRequest request)
                    throws QuadrigaStorageException, QuadrigaAccessException, FileStorageException, IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(tfManager.retrieveTextFileContent(txtId));
        return response;
    }

}
