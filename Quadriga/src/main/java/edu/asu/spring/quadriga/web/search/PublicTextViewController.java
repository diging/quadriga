package edu.asu.spring.quadriga.web.search;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.asu.spring.quadriga.domain.enums.ETextAccessibility;
import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.service.textfile.ITextFileManager;
import edu.asu.spring.quadriga.web.util.TextHelper;

@Controller
public class PublicTextViewController {
    
    @Autowired
    private ITextFileManager tfManager;
    
    @Autowired
    private TextHelper textHelper;

    private static final Logger logger = LoggerFactory.getLogger(PublicTextViewController.class);

    @RequestMapping(value = "public/text/view")
    public ResponseEntity<String> viewTextfile(@RequestParam("txtid") String txtId, HttpServletResponse response,
            HttpServletRequest request) throws QuadrigaAccessException {   
        try {
            ITextFile textFile = tfManager.getTextFile(txtId);
            if (textFile.getAccessibility() == ETextAccessibility.PUBLIC) {
                tfManager.loadFile(textFile);
                return textHelper.getResponse(textFile.getFileContent(), response); 
            }
            
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);       
        } catch (FileStorageException e) {
            logger.error(e.getMessage());
            String respMessage = "Error while retrieving the file content";
            return new ResponseEntity<String>(respMessage, HttpStatus.NOT_FOUND);
        }

    }

}
