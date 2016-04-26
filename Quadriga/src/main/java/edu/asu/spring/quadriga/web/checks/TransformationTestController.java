package edu.asu.spring.quadriga.web.checks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.impl.networks.Transformation;
import edu.asu.spring.quadriga.domain.network.tranform.ITransformation;
import edu.asu.spring.quadriga.exceptions.QuadrigaGeneratorException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.transform.impl.Generator;
import edu.asu.spring.quadriga.service.network.transform.impl.MatchGraphs;
import edu.asu.spring.quadriga.service.network.transform.impl.TransformNode;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

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
    
    @Autowired
    private Generator generator;
    
    /**
	 * Gets transformation-request,networks from the User and returns transformed graph 
	 * Extensions: Obtain Mapping and Pattern files from the user.
	 * @param model
	 * @param principal
	 * @return
	 * @throws QuadrigaStorageException
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
			RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR,
			RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN,
			RoleNames.ROLE_QUADRIGA_ADMIN }) })
    @RequestMapping(value = "checks/transformation", method=RequestMethod.POST)
    public String transformTest(@RequestParam("project") String project, 
    							@RequestParam("transformation") String transformation,
    							@RequestParam("sanitized_network_id") String networkID) throws QuadrigaStorageException, IOException, QuadrigaGeneratorException {
        
        Resource patternRes = new ClassPathResource("transformation/Pe_Pe_engagesWith.graphml");
        Resource transformationRes = new ClassPathResource("transformation/triple_engagesWith.graphml");
        ITransformation transform = new Transformation();
        transform.setPatternFilePath(patternRes.getFile().getAbsolutePath());
        transform.setTransformationFilePath(transformationRes.getFile().getAbsolutePath());
        List<ITransformation> transformations = new ArrayList<ITransformation>();
        transformations.add(transform);        
        List<String> networkIdList = Arrays.asList(networkID.split("\\s*,\\s*"));
        List<String> networkIds = new ArrayList<String>(); 
        for(int i=1;i <	networkIdList.size();i++){
        	networkIds.add(networkIdList.get(i));
        }

        List<List<TransformNode>> results = matchGraphs.matchGraphs(transformations, networkIds);
        logger.info("Check results for transformations: " + results);
        logger.info(generator.generateText(results, null, true));
        
        System.out.println("In TransformationTestController transformTest method");
        return "check";
    }
}
