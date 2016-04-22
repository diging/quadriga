package edu.asu.spring.quadriga.web.checks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.asu.spring.quadriga.domain.impl.networks.Transformation;
import edu.asu.spring.quadriga.domain.network.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.network.tranform.ITransformation;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.transform.impl.MatchGraphs;
import edu.asu.spring.quadriga.service.network.transform.impl.TransformNode;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.web.workbench.AddCollaboratorController;

@Controller
public class TransformationTestController {
    
    private static final Logger logger = LoggerFactory
            .getLogger(TransformationTestController.class);

    
    @Autowired
    private MatchGraphs matchGraphs;
    
    @Autowired
    private IRetrieveProjectManager projectManager;
    
    @Autowired
    private INetworkManager networkManager;

    @RequestMapping(value = "checks/transformation")
    public String transformTest() throws QuadrigaStorageException, IOException {
        
        Resource patternRes = new ClassPathResource("transformation/Pe_Pe_engagesWith.graphml");
        Resource transformationRes = new ClassPathResource("transformation/triple_engagesWith.graphml");
        ITransformation transform = new Transformation();
        transform.setPatternFilePath(patternRes.getFile().getAbsolutePath());
        transform.setTransformationFilePath(transformationRes.getFile().getAbsolutePath());
        
        List<ITransformation> transformations = new ArrayList<ITransformation>();
        transformations.add(transform);
        
        List<String> networkIds = new ArrayList<String>();
        networkIds.add("ff9d92ae-761b-4e12-907d-94efed0796c2");
        
        List<List<TransformNode>> results = matchGraphs.matchGraphs(transformations, networkIds);
        logger.info("Check results for transformations: " + results);
        
        return "check";
    }
}
