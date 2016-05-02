package edu.asu.spring.quadriga.rest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.RestException;
import edu.asu.spring.quadriga.exceptions.TextFileParseException;
import edu.asu.spring.quadriga.service.IRestMessage;
import edu.asu.spring.quadriga.service.textfile.ITextFileManager;
import edu.asu.spring.quadriga.utilities.ITextXMLParser;

@Controller
public class TextUploadRestController {

    @Autowired
    ITextXMLParser txtXMLParser;

    @Autowired
    ITextFileManager tfManager;

    @Autowired
    private IRestVelocityFactory restVelocityFactory;

    @Autowired
    private IRestMessage errorMessageRest;
    
    @Resource(name = "projectconstants")
    private Properties messages;    

    @RequestMapping(value = "rest/project/{projectid}/workspace/{workspaceid}/uploadtext", method = RequestMethod.POST, produces = "application/xml")
    public ResponseEntity<String> uploadText(@PathVariable("workspaceid") String wsId,
            @PathVariable("projectid") String projId, HttpServletResponse response, HttpServletRequest request,
            @RequestBody String xml) throws RestException {
        String textURI = messages.getProperty("textfiles_location.url");
        ITextFile txtFile = null;
        try {
            txtFile = txtXMLParser.parseTextXML(xml, wsId, projId);
        } catch (TextFileParseException tfe) {
            String errorMsg = errorMessageRest.getErrorMsg(tfe.getMessage());
            return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);
        }
        try {
            tfManager.saveTextFile(txtFile);
        } catch (QuadrigaStorageException e) {
            String errorMsg = errorMessageRest.getErrorMsg(e.getMessage());
            return new ResponseEntity<String>(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (FileStorageException e) {
            String errorMsg = errorMessageRest.getErrorMsg(e.getMessage());
            return new ResponseEntity<String>(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        VelocityEngine engine = null;
        try {
            engine = restVelocityFactory.getVelocityEngine(request);
            engine.init();
            Template template =  engine.getTemplate("velocitytemplates/textfile.vm");
            VelocityContext context = new VelocityContext(restVelocityFactory.getVelocityContext());
            context.put("textid", txtFile.getTextId());
            context.put("refid", txtFile.getRefId());
            context.put("filename", txtFile.getFileName());
            context.put("wsid", txtFile.getWorkspaceId());
            context.put("projid", txtFile.getProjectId());
            context.put("texturi",textURI+"/"+"dummy");
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_XML);
            System.out.println(request);
            return new ResponseEntity<String>(writer.toString(), httpHeaders, HttpStatus.CREATED);
        } catch (FileNotFoundException e) {
            throw new RestException(404, e);
        } catch (IOException e) {
            throw new RestException(404, e);
        } catch (Exception e) {
            throw new RestException(500, e);
        }
        
      
    }
}
