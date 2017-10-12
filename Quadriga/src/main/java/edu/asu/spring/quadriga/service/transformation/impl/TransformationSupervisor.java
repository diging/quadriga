package edu.asu.spring.quadriga.service.transformation.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaGeneratorException;
import edu.asu.spring.quadriga.service.network.transform.impl.Generator;
import edu.asu.spring.quadriga.service.transformation.ITransformationProcessor;
import edu.asu.spring.quadriga.service.transformation.ITransformationResultCache;
import edu.asu.spring.quadriga.service.transformation.ITransformationSupervisor;
import edu.asu.spring.quadriga.service.transformation.exception.ResultExpiredException;
import edu.asu.spring.quadriga.service.transformation.impl.TransformationResult.TransformationStatus;

@Service
public class TransformationSupervisor implements ITransformationSupervisor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final String TRANSFORMATION_FAILURE = "{ 'error': '500' }";

    @Autowired
    private ITransformationProcessor processor;

    @Autowired
    private ITransformationResultCache resultCache;

    @Autowired
    private Generator generator;

    private List<String> currentIds;

    @PostConstruct
    public void init() {
        currentIds = Collections.synchronizedList(new ArrayList<String>());
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.asu.spring.quadriga.service.transformation.impl.
     * ITransformationSupervisor#startTransformation(java.lang.String,
     * java.lang.String, java.util.List)
     */
    @Override
    public String startTransformation(String pattern, String transformation, List<String> networkIdList)
            throws FileStorageException, IOException, QuadrigaGeneratorException {
        String id = null;
        while (id == null) {
            id = generateId();
            if (currentIds.contains(id)) {
                try {
                    resultCache.get(id);
                } catch (ResultExpiredException e) {
                    // in this case we can reuse the id, we just have to delete
                    // the
                    // previous entry
                    resultCache.remove(id);
                    break;
                }
                id = null;
            }
        }

        currentIds.add(id);

        String finalId = id;
        ListenableFuture<String> transformationFuture = processor.runTransformationAsync(pattern, transformation,
                networkIdList);
        transformationFuture.addCallback(new ListenableFutureCallback<String>() {

            @Override
            public void onSuccess(String arg0) {
                resultCache.add(finalId, arg0);
            }

            @Override
            public void onFailure(Throwable arg0) {
                try {
                    logger.error("Thread finished with exception.", arg0);
                    resultCache.add(finalId, generator.generateError("500", arg0.getMessage()));
                } catch (QuadrigaGeneratorException e) {
                    logger.error("Could not generate error message.", e);
                    resultCache.add(finalId, TRANSFORMATION_FAILURE);
                }
            }
        });

        return id;
    }

    @Override
    public TransformationResult getResult(String id) {
        TransformationResult transResult = new TransformationResult(id);
        if (!currentIds.contains(id)) {
            transResult.setStatus(TransformationStatus.UNKNOWWN);
            return transResult;
        }
        try {
            transResult.setResult(resultCache.get(id));
            if (transResult.getResult() == null) {
                transResult.setStatus(TransformationStatus.IN_PROGRESS);
            } else if (transResult.getResult().equals(TRANSFORMATION_FAILURE)) {
                transResult.setStatus(TransformationStatus.FAILED);
            } else {
                transResult.setStatus(TransformationStatus.SUCCESS);
            }
        } catch (ResultExpiredException e) {
            logger.info("Result has expired.", e);
            transResult.setStatus(TransformationStatus.EXPIRED);
        }
        
        return transResult;
    }

    private String generateId() {
        char[] chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            builder.append(chars[random.nextInt(62)]);
        }

        return "CTR" + builder.toString();
    }
}
