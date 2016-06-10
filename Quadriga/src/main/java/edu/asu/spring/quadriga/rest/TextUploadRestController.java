package edu.asu.spring.quadriga.rest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.RestException;
import edu.asu.spring.quadriga.exceptions.TextFileParseException;
import edu.asu.spring.quadriga.service.IRestMessage;
import edu.asu.spring.quadriga.service.textfile.ITextFileManager;
import edu.asu.spring.quadriga.utilities.ITextXMLParser;

/**
 * Controller for receiving Textfiles from the Rest end point
 * 
 * @author Nischal Samji
 * 
 */
@Controller
public class TextUploadRestController {

    @Autowired
    private ITextXMLParser txtXMLParser;

    @Autowired
    private ITextFileManager tfManager;

    @Autowired
    private IRestVelocityFactory restVelocityFactory;

    @Autowired
    private IRestMessage errorMessageRest;
    
    private static final Logger logger = LoggerFactory.getLogger(TextUploadRestController.class);

    /**
     * Controller method for handling Rest requests for Text Uploads
     * @param wsId
     *            WorkspaceId for the Textfile as a path variable.
     * @param projId
     *            Project for the Textfile as a path variable.
     * @param response
     *            Generic HTTP Response Object for returning the XML response
     * @param request
     *            Generic HTTP Request Object for RECEIVING the XML response
     * @param xml
     *            XML content as a string
     * @return Returns a Response Entity that contains with the information
     *         about the stored Textfile
     * @throws RestException
     */
    @RequestMapping(value = "rest/project/{projectid}/workspace/{workspaceid}/text", method = RequestMethod.POST, produces = "application/xml")
    public ResponseEntity<String> uploadText(@PathVariable("workspaceid") String wsId,
            @PathVariable("projectid") String projId, HttpServletResponse response, HttpServletRequest request,
            @RequestBody String xml) throws RestException {
        ITextFile txtFile = null;
        try {
            txtFile = txtXMLParser.parseTextXML(xml, wsId, projId);
        } catch (TextFileParseException tfe) {
            logger.error("Error in Text Rest Controller:", tfe);
            String errorMsg = errorMessageRest.getErrorMsg(tfe.getMessage());
            return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);
        }
        try {
            tfManager.saveTextFile(txtFile);
        } catch (QuadrigaStorageException e) {
            logger.error("Error in Text Rest Controller:", e);
            String errorMsg = errorMessageRest.getErrorMsg(e.getMessage());
            return new ResponseEntity<String>(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (FileStorageException e) {
            logger.error("Error in Text Rest Controller:", e);
            String errorMsg = errorMessageRest.getErrorMsg(e.getMessage());
            return new ResponseEntity<String>(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        VelocityEngine engine = null;
        try {
            engine = restVelocityFactory.getVelocityEngine();
            engine.init();
            Template template = engine.getTemplate("velocitytemplates/textfile.vm");
            VelocityContext context = new VelocityContext();
            context.put("url", ServletUriComponentsBuilder.fromContextPath(request).toUriString());
            context.put("textid", txtFile.getTextId());
            context.put("refid", txtFile.getRefId());
            context.put("filename", txtFile.getFileName());
            context.put("wsid", txtFile.getWorkspaceId());
            context.put("projid", txtFile.getProjectId());
            context.put("texturi", txtFile.getTextFileURI());
            context.put("accessibility",txtFile.getAccessibility());
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_XML);
            return new ResponseEntity<String>(writer.toString(), httpHeaders, HttpStatus.CREATED);
        } catch (FileNotFoundException e) {
            logger.error("Error in Text Rest Controller:", e);
            throw new RestException(500, e);
        } catch (IOException e) {
            logger.error("Error in Text Rest Controller:", e);
            throw new RestException(500, e);
        } catch (Exception e) {
            logger.error("Error in Text Rest Controller:", e);
            throw new RestException(500, e);
        }

    }
}
