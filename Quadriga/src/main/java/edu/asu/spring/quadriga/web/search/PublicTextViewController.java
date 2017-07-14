package edu.asu.spring.quadriga.web.search;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.asu.spring.quadriga.conceptpower.IConceptpowerConnector;
import edu.asu.spring.quadriga.domain.enums.ETextAccessibility;
import edu.asu.spring.quadriga.domain.impl.ConceptpowerReply;
import edu.asu.spring.quadriga.domain.impl.ConceptpowerReply.ConceptEntry;
import edu.asu.spring.quadriga.domain.network.impl.CreationEvent;
import edu.asu.spring.quadriga.domain.network.impl.ElementEventsType;
import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.qstore.IMarshallingService;
import edu.asu.spring.quadriga.qstore.IQStoreConnector;
import edu.asu.spring.quadriga.service.textfile.ITextFileManager;
import edu.asu.spring.quadriga.web.util.TextHelper;

@Controller
public class PublicTextViewController {
    
    @Autowired
    private ITextFileManager tfManager;
    
    @Autowired
    private IQStoreConnector qstoreConnector;
    
    @Autowired
    private IMarshallingService marshallingService;
    
    @Autowired
    private IConceptpowerConnector conceptpowerConnector;
    

    
    @Autowired
    private TextHelper textHelper;

    private static final Logger logger = LoggerFactory.getLogger(PublicTextViewController.class);

    @RequestMapping(value = "public/text/view", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> viewTextfile(@RequestParam("txtid") String txtId, @RequestParam(value = "conceptUri", defaultValue="") String conceptUri, HttpServletResponse response,
            HttpServletRequest request) throws QuadrigaException, JAXBException {   
        try {
            ITextFile textFile = tfManager.getTextFile(txtId);
            if (textFile.getAccessibility() == ETextAccessibility.PUBLIC) {
                tfManager.loadFile(textFile);
                String contents = textFile.getFileContent();
                
                if (!conceptUri.isEmpty()) {
                    ConceptpowerReply reply = conceptpowerConnector.getById(conceptUri);
                    List<ConceptEntry> entries = reply.getConceptEntry();
                    
                    ConceptEntry entry = null;
                    if (entries != null && !entries.isEmpty()) {
                        entry = entries.get(0);
                    }
                    
                    // get appellations with original text URI
                    String result = qstoreConnector.getAppellationEventsByConceptAndText(conceptUri, textFile.getRefId());
                    ElementEventsType events = marshallingService.unMarshalXmlToElementEventsType(result);
                    List<CreationEvent> creationEvents = events.getRelationEventOrAppellationEvent();
                    
                    // get appellations with Quadriga text URI
                    result = qstoreConnector.getAppellationEventsByConceptAndText(conceptUri, textFile.getTextFileURI());
                    events = marshallingService.unMarshalXmlToElementEventsType(result);
                    creationEvents.addAll(events.getRelationEventOrAppellationEvent());
                    
                    if (entry != null && entry.getWordnetId() != null && !entry.getWordnetId().isEmpty()) {
                        int slashIdx = conceptUri.lastIndexOf("/");
                        String base = conceptUri.substring(0, slashIdx);
                        String wordnetUri = base + "/" + entry.getWordnetId();
                        
                        result = qstoreConnector.getAppellationEventsByConceptAndText(wordnetUri, textFile.getRefId());
                        events = marshallingService.unMarshalXmlToElementEventsType(result);
                        creationEvents.addAll(events.getRelationEventOrAppellationEvent());
                        
                        result = qstoreConnector.getAppellationEventsByConceptAndText(wordnetUri, textFile.getTextFileURI());
                        events = marshallingService.unMarshalXmlToElementEventsType(result);
                        creationEvents.addAll(events.getRelationEventOrAppellationEvent());
                    }
                    
                    contents = textHelper.highlightAppellationEvents(contents, creationEvents);
                }
                
                return textHelper.getResponse(contents, response); 
            }
            
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);       
        } catch (FileStorageException e) {
            logger.error(e.getMessage());
            String respMessage = "Error while retrieving the file content";
            return new ResponseEntity<String>(respMessage, HttpStatus.NOT_FOUND);
        }

    }

}
