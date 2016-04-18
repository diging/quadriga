package edu.asu.spring.quadriga.rest;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.workspace.ITextFile;

@Controller
public class TextUploadRestController {

    @RequestMapping(value = "rest/workspace/{workspaceid}/uploadtext", method = RequestMethod.POST)
    public ResponseEntity<String> uploadText(@PathVariable("workspaceid") String wsId,
            HttpServletResponse response, HttpServletRequest request, @RequestBody String xml){
        
        ITextFile txtFile; //TODO build a xml parser that returns a TextFile Object
        return null;
    }
}
