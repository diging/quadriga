package edu.asu.spring.quadriga.web.checks;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

@Controller
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

    // @RequestMapping(value = "transformation/result", method =
    // RequestMethod.POST)
    public String transformTest(@RequestParam("project") String project,
            @RequestParam("transformation") String transformationId,
            @RequestParam("sanitized_network_id") String networkIds, Model model)
            throws QuadrigaStorageException, IOException, QuadrigaGeneratorException, FileStorageException {

        List<ITransformation> transformations = transformationManager.getTransformations(transformationId);

        List<String> networkIdList = Arrays.asList(networkIds.split("\\s*,\\s*"));

        List<List<TransformNode>> results = matchGraphs.matchGraphs(transformations, networkIdList);
        logger.info("Check results for transformations: " + results);
        String graphString = generator.generateText(results, null, true);

        model.addAttribute("graph", StringEscapeUtils.escapeHtml(graphString));

        return "check";
    }
}
