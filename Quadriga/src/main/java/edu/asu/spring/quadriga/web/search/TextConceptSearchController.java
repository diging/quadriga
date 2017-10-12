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
import edu.asu.spring.quadriga.conceptpower.IConceptpowerConnector;
import edu.asu.spring.quadriga.conceptpower.model.ConceptpowerReply;
import edu.asu.spring.quadriga.conceptpower.model.ConceptpowerReply.ConceptEntry;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.enums.ETextAccessibility;
import edu.asu.spring.quadriga.domain.network.impl.CreationEvent;
import edu.asu.spring.quadriga.domain.network.impl.ElementEventsType;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.qstore.IMarshallingService;
import edu.asu.spring.quadriga.qstore.IQStoreConnector;
import edu.asu.spring.quadriga.service.network.IJsonCreator;
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
    private IConceptpowerConnector conceptpowerConnector;

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
    private IJsonCreator jsonCreator;

    @RequestMapping(value = "search/texts")
    public String search(@RequestParam(value = "conceptid1", defaultValue = "") String conceptId1,
            @RequestParam(value = "conceptid2", defaultValue = "") String conceptId2, Model model) throws Exception {
        
        ArrayList<String> searchedConcepts = new ArrayList<>();
        List<List<ITextFile>> texts = new ArrayList<List<ITextFile>>();
        List<List<String>> handles = new ArrayList<List<String>>();
        List<String> conceptUriSearchList = new ArrayList<String>();
        List<ConceptEntry> conceptEntries = new ArrayList<ConceptEntry>();
        List<IConcept> concepts = new ArrayList<IConcept>();
        searchedConcepts.add(conceptId1);
        searchedConcepts.add(conceptId2);
        for (String conceptUri : searchedConcepts) {
            if (!conceptUri.isEmpty()) {
                
                IConcept concept = cpCache.getConceptByUri(conceptUri);
                Set<String> references = new HashSet<String>();
                List<ITextFile> conceptTexts = new ArrayList<ITextFile>();
                List<String> conceptHandles = new ArrayList<String>();
                

                if (concept != null) {
                   concept.setId(conceptUri);
                }
                
                String conceptId = null;
                if (conceptUri.startsWith("http://")) {
                    int lastIdx = conceptUri.lastIndexOf("/");
                    conceptId = conceptUri.substring(lastIdx + 1);
                }

                String results = qStoreConnector.searchNodesByConcept(conceptId);
                
                
                if (results != null && !results.isEmpty()) {
                    ElementEventsType events = marshallingService.unMarshalXmlToElementEventsType(results);
                    List<CreationEvent> eventList = events.getRelationEventOrAppellationEvent();

                    if (concept.getWordnetIds() != null && !concept.getWordnetIds().isEmpty()) {
                        results = qStoreConnector.searchNodesByConcept(concept.getWordnetIds().get(0));
                        events = marshallingService.unMarshalXmlToElementEventsType(results);
                        eventList.addAll(events.getRelationEventOrAppellationEvent());
                    }

                    for (CreationEvent event : eventList) {
                        String sourceRef = event.getSourceReference();

                        // if we haven't seen the reference yet
                        if (references.add(sourceRef)) {
                            ITextFile txtFile = textFileManager.getTextFileByUri(sourceRef);
                            if (txtFile == null || txtFile.getAccessibility() == ETextAccessibility.PRIVATE) {
                                conceptHandles.add(sourceRef);
                            } else {
                                if (txtFile.getAccessibility() == ETextAccessibility.PUBLIC) {
                                    conceptTexts.add(txtFile);
                                    textFileManager.loadFile(txtFile);
                                    txtFile.setSnippetLength(40);
                                }
                            }
                        }
                    }
                   
                    
                }
                
                concepts.add(concept);
                handles.add(conceptHandles);
                texts.add(conceptTexts);
                conceptUriSearchList.add(conceptUri);

            }
        }
        List<IProject> projects = projectManager.getProjectListByAccessibility(EProjectAccessibility.PUBLIC);

        List<String> projectIds = new ArrayList<String>();
        projects.forEach(p -> projectIds.add(p.getProjectId()));

        ITransformedNetwork transformedNetwork = transformationManager
                .getSearchTransformedNetworkMultipleProjects(projectIds, conceptUriSearchList, INetworkStatus.APPROVED);

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
        model.addAttribute("concepts", conceptEntries);
        return "search/texts";
    }

}

/*

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
import edu.asu.spring.quadriga.domain.network.impl.CreationEvent;
import edu.asu.spring.quadriga.domain.network.impl.ElementEventsType;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.qstore.IMarshallingService;
import edu.asu.spring.quadriga.qstore.IQStoreConnector;
import edu.asu.spring.quadriga.service.network.IJsonCreator;
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
    private IJsonCreator jsonCreator;

    @RequestMapping(value = "search/texts")
    public String search(@RequestParam(defaultValue = "") String conceptId, Model model) throws Exception {

        String conceptUri = conceptId;

        if (!conceptId.isEmpty()) {
            IConcept concept = cpCache.getConceptByUri(conceptId);
            
            if (concept != null) {
                concept.setId(conceptUri);
            }

            if (conceptId.startsWith("http://")) {
                int lastIdx = conceptId.lastIndexOf("/");
                conceptId = conceptId.substring(lastIdx + 1);
            }

            String results = qStoreConnector.searchNodesByConcept(conceptId);

            if (results != null && !results.isEmpty()) {
                ElementEventsType events = marshallingService.unMarshalXmlToElementEventsType(results);

                List<CreationEvent> eventList = events.getRelationEventOrAppellationEvent();

                if (concept.getWordnetIds() != null && !concept.getWordnetIds().isEmpty()) {
                    results = qStoreConnector.searchNodesByConcept(concept.getWordnetIds().get(0));
                    events = marshallingService.unMarshalXmlToElementEventsType(results);
                    eventList.addAll(events.getRelationEventOrAppellationEvent());
                }

                Set<String> references = new HashSet<String>();
                List<ITextFile> texts = new ArrayList<ITextFile>();
                List<String> handles = new ArrayList<String>();

                for (CreationEvent event : eventList) {
                    String sourceRef = event.getSourceReference();

                    // if we haven't seen the reference yet
                    if (references.add(sourceRef)) {
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
                }

                model.addAttribute("references", handles);
                model.addAttribute("texts", texts);

                List<IProject> projects = projectManager.getProjectListByAccessibility(EProjectAccessibility.PUBLIC);

                List<String> projectIds = new ArrayList<String>();
                projects.forEach(p -> projectIds.add(p.getProjectId()));

                ITransformedNetwork transformedNetwork = transformationManager
                        .getSearchTransformedNetworkMultipleProjects(projectIds, conceptUri, INetworkStatus.APPROVED);

                String json = null;
                if (transformedNetwork != null) {
                    json = jsonCreator.getJson(transformedNetwork.getNodes(), transformedNetwork.getLinks());
                }

                if (transformedNetwork == null || transformedNetwork.getNodes().size() == 0) {
                    model.addAttribute("isNetworkEmpty", true);
                }

                model.addAttribute("jsonstring", json);
                
            }            
            
            model.addAttribute("concept", concept);
        }

        return "search/texts";
    }

}


*/