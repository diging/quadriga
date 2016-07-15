package edu.asu.spring.quadriga.web.transformation;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.network.tranform.ITransformation;
import edu.asu.spring.quadriga.exceptions.QuadrigaGeneratorException;
import edu.asu.spring.quadriga.service.network.transform.impl.Generator;
import edu.asu.spring.quadriga.service.network.transform.impl.MatchGraphs;
import edu.asu.spring.quadriga.service.network.transform.impl.TransformNode;

/**
 * 
 * generation of transformed network is done as async task
 * 
 * @author yoganandakishore
 *
 */

@Service
public class TransformationProcessor {

    @Autowired
    private Generator generator;

    @Autowired
    private MatchGraphs matchGraphs;

    /**
     * Asynchronously run the task of generating transformed network results and
     * generating a string from those results
     * 
     * @param transformations
     * @param networkIdList
     * @return future
     * @throws QuadrigaGeneratorException
     */
    @Async
    public Future<String> runTransformationAsync(List<ITransformation> transformations, List<String> networkIdList)
            throws QuadrigaGeneratorException {

        List<List<TransformNode>> results = matchGraphs.matchGraphs(transformations, networkIdList);
        String graphString = generator.generateText(results, null, true);
        return new AsyncResult<>(graphString);
    }
}
