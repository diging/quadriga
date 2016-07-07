package edu.asu.spring.quadriga.web.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.asu.spring.quadriga.conceptpower.IConceptpowerConnector;
import edu.asu.spring.quadriga.domain.enums.ETextAccessibility;
import edu.asu.spring.quadriga.domain.impl.ConceptpowerReply;
import edu.asu.spring.quadriga.domain.impl.ConceptpowerReply.ConceptEntry;
import edu.asu.spring.quadriga.domain.impl.networks.CreationEvent;
import edu.asu.spring.quadriga.domain.impl.networks.ElementEventsType;
import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.qstore.IMarshallingService;
import edu.asu.spring.quadriga.qstore.IQStoreConnector;
import edu.asu.spring.quadriga.service.textfile.ITextFileManager;

@Controller
public class TextConceptSearchController {
    
    @Autowired
    private IConceptpowerConnector conceptpowerConnector;
    
    @Autowired
    private IQStoreConnector qStoreConnector;
    
    @Autowired
    private IMarshallingService marshallingService;
    
    @Autowired
    private ITextFileManager textFileManager;

    @RequestMapping(value = "search/texts")
    public String prepareSearchPage(@RequestParam(defaultValue="") String conceptId, Model model) throws Exception {
        
        if (!conceptId.isEmpty()) {
            ConceptpowerReply reply = conceptpowerConnector.getById(conceptId);
            List<ConceptEntry> entries = reply.getConceptEntry();
            
            ConceptEntry entry = null;
            if (entries != null && !entries.isEmpty()) {
                entry = entries.get(0);
            }
            
            if (conceptId.startsWith("http://")) {
                int lastIdx = conceptId.lastIndexOf("/");
                conceptId = conceptId.substring(lastIdx+1);
            }
            String results = qStoreConnector.searchNodesByConcept(conceptId);
            ElementEventsType events = marshallingService.unMarshalXmlToElementEventsType(results);

            List<CreationEvent> eventList = events.getRelationEventOrAppellationEvent();

            Set<String> references = new HashSet<String>();
            List<ITextFile> texts = new ArrayList<ITextFile>();
            List<String> textlessReferences = new ArrayList<String>();
            
            for (CreationEvent event : eventList) {
                String sourceRef = event.getSourceReference();
                
                // if we haven't seen the reference yet
                if (references.add(sourceRef)) {
                    ITextFile txtFile = textFileManager.getTextFileByUri(sourceRef);
                    if (txtFile == null) {
                        textlessReferences.add(sourceRef);
                    } else {
                        if (txtFile.getAccessibility() == ETextAccessibility.PUBLIC) {
                            texts.add(txtFile);
                            textFileManager.loadFile(txtFile);
                            txtFile.setSnippetLength(40);
                        }
                    }
                    
                }
            }
            
            model.addAttribute("references", textlessReferences);
            model.addAttribute("concept", entry);
            model.addAttribute("texts", texts);
        }
        
        return "search/texts";
    }
}
