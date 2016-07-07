package edu.asu.spring.quadriga.web.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.asu.spring.quadriga.domain.impl.networks.CreationEvent;
import edu.asu.spring.quadriga.domain.impl.networks.ElementEventsType;
import edu.asu.spring.quadriga.qstore.IMarshallingService;
import edu.asu.spring.quadriga.qstore.IQStoreConnector;

@Controller
public class TextConceptSearchController {
    
    @Autowired
    private IQStoreConnector qStoreConnector;
    
    @Autowired
    private IMarshallingService marshallingService;

    @RequestMapping(value = "search/texts")
    public String prepareSearchPage(@RequestParam(defaultValue="") String conceptId, Model model) throws Exception {
        
        if (!conceptId.isEmpty()) {
            if (conceptId.startsWith("http://")) {
                int lastIdx = conceptId.lastIndexOf("/");
                conceptId = conceptId.substring(lastIdx+1);
            }
            String results = qStoreConnector.searchNodesByConcept(conceptId);
            ElementEventsType events = marshallingService.unMarshalXmlToElementEventsType(results);

            List<CreationEvent> eventList = events.getRelationEventOrAppellationEvent();

            List<String> references = new ArrayList<String>();
            for (CreationEvent event : eventList) {
                String sourceRef = event.getSourceReference();
                references.add(sourceRef);
            }
            model.addAttribute("references", references);
        }
        
        return "search/texts";
    }
}
