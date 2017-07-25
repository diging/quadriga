package edu.asu.spring.quadriga.rest.concepts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.print.DocFlavor.STRING;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.asu.spring.quadriga.conceptpower.IConcept;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.network.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.INetworkConceptManager;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.web.network.INetworkStatus;

@RestController
public class UsedConceptsApiController {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private IRetrieveProjectManager projectManager;
    
    @Autowired
    private INetworkManager networkManager;
    
    @Autowired
    private INetworkConceptManager networkConceptManager;

    @RequestMapping("/rest/concepts")
    public List<IConcept> getUsedConcepts(@RequestParam(value = "projects", defaultValue="") String projectIds,
            @RequestParam(value = "types", defaultValue="") String typeIds) throws QuadrigaStorageException, JAXBException {
       
        List<IProject> projectList;
        if (projectIds.trim().isEmpty()) {
            projectList = projectManager.getProjectListByAccessibility(EProjectAccessibility.PUBLIC);
        } else {
            String[] projectIdArray = projectIds.split(",");
            projectList = new ArrayList<>();
            for (String id : projectIdArray) {
                IProject project = projectManager.getProjectDetails(id);
                if (project != null && project.getProjectAccess() == EProjectAccessibility.PUBLIC) {
                    projectList.add(project);
                }
            }
        }
        
        List<String> nodeIds = new ArrayList<>();
        for (IProject project : projectList) {
            List<INetwork> networksInProject = networkManager.getNetworksInProject(project.getProjectId(), INetworkStatus.APPROVED);
            networksInProject.forEach(n -> {
                try {
                    List<INetworkNodeInfo> nodes = n.getNetworkNodes();
                    nodes.forEach(tp -> nodeIds.add(tp.getId()));
                } catch (Exception e) {
                    logger.error("Could not get nodes.", e);
                }
            });
        }
        
        final List<String> typeIdList = Arrays.asList(typeIds.split(",")).stream().map(t -> t.trim()).filter(t -> !t.isEmpty()).collect(Collectors.toList());
        
        List<IConcept> concepts = networkConceptManager.getConceptsOfStatements(nodeIds);
        // return all concepts if they shouldn't be filtered by type
        if (typeIdList.isEmpty()) {
            return concepts;
        }
        // else filter by type
        return concepts.stream().filter(c -> typeIdList.contains(c.getTypeId().trim())).collect(Collectors.toList());
    }
}
