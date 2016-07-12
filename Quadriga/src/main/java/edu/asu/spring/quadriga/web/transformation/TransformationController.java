package edu.asu.spring.quadriga.web.transformation;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
public class TransformationController {

    private static final Logger logger = LoggerFactory.getLogger(TransformationController.class);

    private static String result;

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

    @RequestMapping(value = "auth/transformation/result", method = RequestMethod.POST)
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

        result = graphString;

        if (result == null) {
            return "failure_transform";
        } else {
            result = graphString;
            return "success_transform";
        }

    }

    /**
     * Generates the file that is downloaded
     * 
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "auth/transformation/result/download")
    public void generateDownloadFile(HttpServletResponse response) throws IOException {

        InputStream inputStream = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
        response.setContentLength(result.length());
        response.setHeader("Content-Disposition", String.format("attachment; filename=" + "TransformationResult"));
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }
}
