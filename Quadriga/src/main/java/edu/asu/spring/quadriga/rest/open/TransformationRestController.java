package edu.asu.spring.quadriga.rest.open;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaGeneratorException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.rest.open.model.TransformationRequest;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.transformation.ITransformationSupervisor;
import edu.asu.spring.quadriga.service.transformation.impl.TransformationResult;
import edu.asu.spring.quadriga.service.transformation.impl.TransformationResult.TransformationStatus;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.web.network.INetworkStatus;

@RestController
@PropertySource("classpath:settings.properties")
public class TransformationRestController {

    private final static String PROJECT_ID_PLACEHOLDER = "{projectId}";
    private final static String PROCESS_ID_PLACEHOLDER = "{processId}";
    private final static String POLL_URL = "/public/" + PROJECT_ID_PLACEHOLDER + "/transformation/"
            + PROCESS_ID_PLACEHOLDER;

    
    @Autowired
    private Environment env;
    
    @Autowired
    private IRetrieveProjectManager projectManager;

    @Autowired
    private ITransformationSupervisor supervisor;

    @Autowired
    private INetworkManager networkManager;
    
    private String quadrigaUrl;
    
    
    @PostConstruct
    public void init() {
        quadrigaUrl = env.getProperty("quadriga.baseurl");
        if (!quadrigaUrl.endsWith("/")) {
            quadrigaUrl = quadrigaUrl + "/";
        }
    }

    @RequestMapping(value = "/public/{projectId}/transformation", method = RequestMethod.POST)
    public ResponseEntity<String> runTransformations(@PathVariable("projectId") String projectId,
            @RequestBody TransformationRequest request)
            throws QuadrigaStorageException, FileStorageException, IOException, QuadrigaGeneratorException {
        IProject project = projectManager.getProjectDetails(projectId);

        if (project == null) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }

        if (project.getProjectAccess() != EProjectAccessibility.PUBLIC) {
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        }

        List<INetwork> networks = networkManager.getNetworksInProject(projectId, INetworkStatus.APPROVED);
        List<String> networkIdList = new ArrayList<>();
        networks.forEach(n -> networkIdList.add(n.getNetworkId()));

        String processId = supervisor.startTransformation(request.getPattern(), request.getTransformation(),
                networkIdList);
        return new ResponseEntity<String>(createMessage(processId, projectId, TransformationStatus.IN_PROGRESS.name(), null),
                HttpStatus.OK);
    }

    @RequestMapping(value = POLL_URL)
    public ResponseEntity<String> getTransformationResult(@PathVariable("projectId") String projectId,
            @PathVariable("processId") String processId) throws IOException {
        TransformationResult result = supervisor.getResult(processId);
        if (result.getStatus() == TransformationStatus.UNKNOWWN) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        if (result.getStatus() == TransformationStatus.EXPIRED) {
            return new ResponseEntity<String>(createMessage(processId, projectId, result.getStatus().name(), null),
                    HttpStatus.GONE);
        }
        if (result.getStatus() == TransformationStatus.IN_PROGRESS) {
            return new ResponseEntity<String>(createMessage(processId, projectId, result.getStatus().name(), null),
                    HttpStatus.OK);
        }
        return new ResponseEntity<String>(createMessage(processId, projectId, result.getStatus().name(), result.getResult()), HttpStatus.OK);
    }

    private String createMessage(String processId, String projectId, String status, String graph)
            throws IOException {
        Map<String, Object> response = new HashMap<>();
        response.put("id", processId);
        response.put("status", status);
        
        response.put("resultUrl", quadrigaUrl +
                POLL_URL.replace(PROJECT_ID_PLACEHOLDER, projectId).replace(PROCESS_ID_PLACEHOLDER, processId));
        if (graph != null) {
           response.put("graph", new ObjectMapper().readValue(graph, TypeFactory.defaultInstance().constructMapType(Map.class, String.class, Object.class)));
        }
        return new ObjectMapper().writeValueAsString(response);
    }
}
