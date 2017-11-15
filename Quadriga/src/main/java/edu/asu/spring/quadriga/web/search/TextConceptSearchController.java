package edu.asu.spring.quadriga.web.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.Sets;

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
    public String search(@RequestParam(value = "conceptid", defaultValue = "") String[] conceptIds, Model model)
            throws Exception {

        List<String> conceptUriSearchList = new ArrayList<String>();
        List<IConcept> concepts = new ArrayList<IConcept>();
        List<Set<String>> referencesList = new ArrayList<Set<String>>();

        List<ITextFile> texts = new ArrayList<ITextFile>();
        List<String> handles = new ArrayList<String>();
        
        for (String conceptUri : conceptIds) {
           // System.out.println("Concept URI: "+conceptUri);
            if (!conceptUri.isEmpty()) {

                IConcept concept = cpCache.getConceptByUri(conceptUri);
                if (concept != null) {
                    concept.setId(conceptUri);
                }
               
                concepts.add(concept);
                conceptUriSearchList.add(conceptUri);

            }
        }
        String results = null;
        if(conceptUriSearchList.size() == 1){
            results = qStoreConnector.searchNodesByConcept(conceptUriSearchList.get(0));
        }
        else if(conceptUriSearchList.size() == 2){
           results =  qStoreConnector.loadNetworkWithConceptsBelongingToSameStatements(conceptUriSearchList);
        }
        
        if (results != null && !results.isEmpty()) {
            ElementEventsType events = marshallingService.unMarshalXmlToElementEventsType(results);
            List<CreationEvent> eventList = events.getRelationEventOrAppellationEvent();
            Set<String> references = new HashSet<String>();
            for (CreationEvent event : eventList) {
                String sourceRef = event.getSourceReference();
                System.out.print("Id: "+event.getId()+" , RefId: "+event.getRefId()+" , Source Reference: "+event.getSourceReference());
                references.add(sourceRef);
            }
            referencesList.add(references);
        }
        
        
        if (referencesList.size() >= 1) {
            Set<String> resultSet = referencesList.get(0);
            for (int i = 1; i < referencesList.size(); i++) {
                resultSet = Sets.intersection(resultSet, referencesList.get(i));
            }
            Iterator<String> it = resultSet.iterator();
            while (it.hasNext()) {
                String sourceRef = it.next();
                //System.out.println("sourceRef: "+sourceRef);
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
        System.out.println("handles size: "+handles.size()+" , texts: "+texts.size()+ " , concepts.size(): "+concepts.size());
        model.addAttribute("jsonstring", json);
        model.addAttribute("references", handles);
        model.addAttribute("texts", texts);
        model.addAttribute("concepts", concepts);
        return "search/texts";
    }

}