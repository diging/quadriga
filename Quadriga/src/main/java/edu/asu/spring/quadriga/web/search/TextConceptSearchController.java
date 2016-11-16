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

import edu.asu.spring.quadriga.conceptpower.IConceptpowerConnector;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.enums.ETextAccessibility;
import edu.asu.spring.quadriga.domain.impl.ConceptpowerReply;
import edu.asu.spring.quadriga.domain.impl.ConceptpowerReply.ConceptEntry;
import edu.asu.spring.quadriga.domain.impl.networks.CreationEvent;
import edu.asu.spring.quadriga.domain.impl.networks.ElementEventsType;
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
    public String search(@RequestParam(defaultValue = "") String conceptId, Model model) throws Exception {

        String conceptUri = conceptId;

        if (!conceptId.isEmpty()) {
            ConceptpowerReply reply = conceptpowerConnector.getById(conceptId);
            List<ConceptEntry> entries = reply.getConceptEntry();

            ConceptEntry entry = null;
            if (entries != null && !entries.isEmpty()) {
                entry = entries.get(0);
                entry.setId(conceptUri);
            }

            if (conceptId.startsWith("http://")) {
                int lastIdx = conceptId.lastIndexOf("/");
                conceptId = conceptId.substring(lastIdx + 1);
            }

            String results = qStoreConnector.searchNodesByConcept(conceptId);

            if (results != null && !results.isEmpty()) {
                ElementEventsType events = marshallingService.unMarshalXmlToElementEventsType(results);

                List<CreationEvent> eventList = events.getRelationEventOrAppellationEvent();

                if (entry.getWordnetId() != null && !entry.getWordnetId().isEmpty()) {
                    results = qStoreConnector.searchNodesByConcept(entry.getWordnetId());
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
            
            model.addAttribute("concept", entry);
        }

        return "search/texts";
    }

}
