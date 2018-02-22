package edu.asu.spring.quadriga.web.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import edu.asu.spring.quadriga.conceptpower.IConcept;
import edu.asu.spring.quadriga.conceptpower.IConceptpowerCache;
import edu.asu.spring.quadriga.domain.enums.ETextAccessibility;
import edu.asu.spring.quadriga.domain.network.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.network.impl.CreationEvent;
import edu.asu.spring.quadriga.domain.network.impl.ElementEventsType;
import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.qstore.IMarshallingService;
import edu.asu.spring.quadriga.qstore.IQStoreConnector;
import edu.asu.spring.quadriga.service.network.IJsonCreator;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.INetworkTransformationManager;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;
import edu.asu.spring.quadriga.service.textfile.ITextFileManager;

@Controller
public class TextConceptSearchController {

    @Autowired
    private IConceptpowerCache cpCache;

    @Autowired
    private IQStoreConnector qStoreConnector;

    @Autowired
    private IMarshallingService marshallingService;

    @Autowired
    private ITextFileManager textFileManager;

    @Autowired
    private INetworkTransformationManager transformationManager;

    @Autowired
    private INetworkManager networkManager;
    
    @Autowired
    private IJsonCreator jsonCreator;

    @RequestMapping(value = "search/texts")
    public String search(@RequestParam(value = "conceptid", defaultValue = "") String[] conceptIds, Model model)
            throws Exception {

        List<String> conceptUriSearchList = new ArrayList<String>();
        List<IConcept> concepts = new ArrayList<IConcept>();
        List<ITextFile> texts = new ArrayList<ITextFile>();
        List<String> handles = new ArrayList<String>();
        System.out.println("ConceptURIs:");
        for (String conceptUri : conceptIds) {
            System.out.println(conceptUri);
            if (!conceptUri.isEmpty()) {
                IConcept concept = cpCache.getConceptByUri(conceptUri);
                if (concept != null) {
                    concept.setId(conceptUri);
                }

                concepts.add(concept);
                conceptUriSearchList.add(conceptUri);

            }
        }
        
    
        String results = qStoreConnector.findStatementsWithConcepts(conceptUriSearchList);
        System.out.println("Results:");
        System.out.println(results);
        
        Set<String> references = new HashSet<String>();
        List<String> eventIds = new ArrayList<String>();
        List<CreationEvent> eventList = null;
        if (results != null && !results.isEmpty()) {
            ElementEventsType events = marshallingService.unMarshalXmlToElementEventsType(results);
            eventList = events.getRelationEventOrAppellationEvent();
            System.out.println("EventList Size: "+eventList.size());
            for (CreationEvent event : eventList) {
                references.add(event.getSourceReference());
                eventIds.add(event.getId());
               
            }
        }
        
        System.out.println("Event Ids:");
        for(String eventId: eventIds){
            System.out.println(eventId);
        }
        
        for (String sourceRef : references) {
            ITextFile txtFile = textFileManager.getTextFileByUri(sourceRef);
            if (txtFile == null || txtFile.getAccessibility() == ETextAccessibility.PRIVATE) {
                handles.add(sourceRef);
            } else {
                if (txtFile.getAccessibility() == ETextAccessibility.PUBLIC) {
                    texts.add(txtFile);
                    textFileManager.loadFile(txtFile);
                    txtFile.setSnippetLength(40);
                }
            }
        }

        ITransformedNetwork transformedNetwork = null;
        
        if (conceptUriSearchList.size() >= 1 && eventIds.size() >= 1) {
            List<INetworkNodeInfo> networkNodeList = networkManager.getNetworkNodes(eventIds);
            List<String> finalEventIdList = new ArrayList<String>();
            networkNodeList.forEach(networkNode -> finalEventIdList.add(networkNode.getId()));
            System.out.println("Network Node Info Ids:");
            for(INetworkNodeInfo networkNode : networkNodeList){
                System.out.println(networkNode.getId());
            }
            List<CreationEvent> finalEventlist = eventList.stream().filter(e -> finalEventIdList.contains(e.getId())).collect(Collectors.toList());
            System.out.println("Creation Event Ids:");
            for(CreationEvent event : finalEventlist){
                System.out.println(event.getId());
            }
            //transformedNetwork = transformationManager.getTransformedNetworkUsingNetworkNodesAndConcepts(networkNodeList, conceptUriSearchList);
            transformedNetwork = transformationManager.getTransformedNetworkUsingCreationEventsAndConcepts(finalEventlist, conceptUriSearchList);
        }

        String json = null;
        if (transformedNetwork != null) {
            json = jsonCreator.getJson(transformedNetwork.getNodes(), transformedNetwork.getLinks());
        }
        if (transformedNetwork == null || transformedNetwork.getNodes().size() == 0) {
            model.addAttribute("isNetworkEmpty", true);
        }

        model.addAttribute("jsonstring", json);
        model.addAttribute("references", handles);
        model.addAttribute("texts", texts);
        model.addAttribute("concepts", concepts);
        return "search/texts";
    }

}