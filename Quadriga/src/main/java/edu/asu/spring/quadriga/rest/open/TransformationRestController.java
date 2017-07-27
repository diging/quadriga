package edu.asu.spring.quadriga.rest.open;

import java.io.IOException;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaGeneratorException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.rest.open.model.TransformationRequest;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.transformation.ITransformationProcessor;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.web.network.INetworkStatus;

@RestController
public class TransformationRestController {
    
    @Autowired
    private IRetrieveProjectManager projectManager;
    
    @Autowired
    private ITransformationProcessor transformationProcessor;
    
    @Autowired
    private INetworkManager networkManager;

    @RequestMapping(value = "/public/{projectId}/transformation", method = RequestMethod.POST)
    public ResponseEntity<String> runTransformations(@PathVariable("projectId") String projectId, @RequestBody TransformationRequest request) throws QuadrigaStorageException, FileStorageException, IOException, QuadrigaGeneratorException {
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
        
        String result = transformationProcessor.runTransformation(request.getPattern(), request.getTransformation(), networkIdList);
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
}
