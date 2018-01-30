package edu.asu.spring.quadriga.web.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import edu.asu.spring.quadriga.conceptpower.IConcept;
import edu.asu.spring.quadriga.conceptpower.IConceptpowerCache;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.enums.ETextAccessibility;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.network.impl.CreationEvent;
import edu.asu.spring.quadriga.domain.network.impl.ElementEventsType;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.qstore.IMarshallingService;
import edu.asu.spring.quadriga.qstore.IQStoreConnector;
import edu.asu.spring.quadriga.service.network.IJsonCreator;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.INetworkTransformationManager;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;
import edu.asu.spring.quadriga.service.textfile.ITextFileManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.web.network.INetworkStatus;

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
    private IRetrieveProjectManager projectManager;

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

        for (String conceptUri : conceptIds) {
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
        Set<String> references = new HashSet<String>();
        List<String> eventIds = new ArrayList<String>();
        if (results != null && !results.isEmpty()) {
            ElementEventsType events = marshallingService.unMarshalXmlToElementEventsType(results);
            List<CreationEvent> eventList = events.getRelationEventOrAppellationEvent();
            for (CreationEvent event : eventList) {
                references.add(event.getSourceReference());
                System.out.println(event.getId());
                eventIds.add(event.getId());
               
            }
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

        List<INetwork> networkList = networkManager.getNetworksWithStatements(eventIds);
     
        /*        
        List<String> projectIds = new ArrayList<String>();
        ITransformedNetwork transformedNetwork = null;
        if (conceptUriSearchList.size() >= 1) {
            List<IProject> projects = projectManager.getProjectListByAccessibility(EProjectAccessibility.PUBLIC);
            projects.forEach(p -> projectIds.add(p.getProjectId()));
            transformedNetwork = transformationManager.getSearchTransformedNetworkMultipleProjects(projectIds,
                    conceptUriSearchList, INetworkStatus.APPROVED);
        }
        */
        ITransformedNetwork transformedNetwork = null;
        if (conceptUriSearchList.size() >= 1) {
            transformedNetwork = transformationManager.getTransformedNetworkusingNetworkList(networkList, conceptUriSearchList);
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