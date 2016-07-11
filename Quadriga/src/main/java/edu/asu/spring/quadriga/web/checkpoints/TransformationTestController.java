package edu.asu.spring.quadriga.web.checkpoints;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import edu.asu.spring.quadriga.domain.network.tranform.ITransformation;
import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaGeneratorException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.transform.impl.Generator;
import edu.asu.spring.quadriga.service.network.transform.impl.MatchGraphs;
import edu.asu.spring.quadriga.service.network.transform.impl.TransformNode;
import edu.asu.spring.quadriga.service.transformation.ITransformationManager;

public class TransformationTestController {

    private static final Logger logger = LoggerFactory.getLogger(TransformationTestController.class);

    @Autowired
    private MatchGraphs matchGraphs;

    @Autowired
    private ITransformationManager transformationManager;

    @Autowired
    private Generator generator;

    /**
     * Gets transformation-request,networks from the User and returns
     * transformed graph Extensions: Obtain Mapping and Pattern files from the
     * user.
     * 
     * @param model
     * @param principal
     * @return
     * @throws QuadrigaStorageException
     * @throws FileStorageException
     */

    // @RequestMapping(value = "checks/transformation", method =
    // RequestMethod.POST)
    public String transformTest(@RequestParam("project") String project,
            @RequestParam("transformation") String transformationID,
            @RequestParam("sanitized_network_id") String networkID, Model model)
            throws QuadrigaStorageException, IOException, QuadrigaGeneratorException, FileStorageException {

        String directoryName = transformationID;
        // ITransformationFile transformFile =
        // transformationManager.retrieveTransformationFilePaths(directoryName);

        // ITransformation transform = new Transformation();
        // transform.setPatternFilePath(transformFile.getAbsolutePatternFilePath());
        // transform.setTransformationFilePath(transformFile.getAbsoluteMappingFilePath());

        List<ITransformation> transformations = new ArrayList<ITransformation>();
        transformations.add(null);

        List<String> networkIdList = Arrays.asList(networkID.split("\\s*,\\s*"));
        List<String> networkIds = new ArrayList<String>();
        for (int i = 1; i < networkIdList.size(); i++) {
            networkIds.add(networkIdList.get(i));
        }

        List<List<TransformNode>> results = matchGraphs.matchGraphs(transformations, networkIds);
        logger.info("Check results for transformations: " + results);
        String graphString = generator.generateText(results, null, true);
        model.addAttribute("graph", StringEscapeUtils.escapeHtml(graphString));

        return "check";
    }

}
