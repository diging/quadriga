package edu.asu.spring.quadriga.web.transformation;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
import edu.asu.spring.quadriga.service.transformation.ITransformationManager;

@Controller
@Scope("session")
public class TransformationController {

    private String result;

    private Future<String> future;

    @Autowired
    private ITransformationManager transformationManager;

    @Autowired
    private TransformationProcessor transformProcessor;

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

    @RequestMapping(value = "auth/transformation/start", method = RequestMethod.POST)
    public String generateTransform(@RequestParam("project") String project,
            @RequestParam("transformation") String transformationIds,
            @RequestParam("sanitized_network_id") String networkIds, Model model)
            throws QuadrigaStorageException, IOException, QuadrigaGeneratorException, FileStorageException {

        List<ITransformation> transformations = transformationManager.getTransformations(transformationIds);
        List<String> networkIdList = Arrays.asList(networkIds.split("\\s*,\\s*"));

        try {
            List<Future<String>> futureEvents = new ArrayList<>();
            future = transformProcessor.runTransformationAsync(transformations, networkIdList);
            futureEvents.add(future);
        } catch (Exception e) {
            System.out.println(" ");
        }

        return "status";
    }

    /**
     * Checks the status of future event, if it's done directs to result page
     * and if it's not done directs to status page
     * 
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @RequestMapping(value = "auth/transformation/start", method = RequestMethod.GET)
    public String showStatus() throws InterruptedException, ExecutionException {

        if (future.isDone()) {
            result = future.get();

            if (result == null)
                return "failure";

            return "success";
        }
        return "status";
    }

    /**
     * response is built with generated graphstring and downloaded as a file
     * 
     * @param response
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @RequestMapping(value = "auth/transformation/result/download")
    public void downloadTransformation(HttpServletResponse response) throws IOException {

        InputStream inputStream = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
        response.setContentLength(result.length());
        response.setHeader("Content-Disposition", String.format("attachment; filename=" + "TransformationResult"));
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }
}
