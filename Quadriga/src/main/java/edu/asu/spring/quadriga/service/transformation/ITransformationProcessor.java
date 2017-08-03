package edu.asu.spring.quadriga.service.transformation;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.util.concurrent.ListenableFuture;

import edu.asu.spring.quadriga.domain.network.tranform.ITransformation;
import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaGeneratorException;

public interface ITransformationProcessor {

    /**
     * Asynchronously run the task of generating transformed network results and
     * generating a string from those results
     * 
     * @param transformations
     * @param networkIdList
     * @return future
     * @throws QuadrigaGeneratorException
     */
    Future<String> runTransformationAsync(List<ITransformation> transformations, List<String> networkIdList)
            throws QuadrigaGeneratorException;

    ListenableFuture<String> runTransformationAsync(String pattern, String transformation, List<String> networkIdList)
            throws FileStorageException, IOException, QuadrigaGeneratorException;

}