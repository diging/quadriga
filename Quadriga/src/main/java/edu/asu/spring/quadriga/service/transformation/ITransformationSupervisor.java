package edu.asu.spring.quadriga.service.transformation;

import java.io.IOException;
import java.util.List;

import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaGeneratorException;
import edu.asu.spring.quadriga.service.transformation.impl.TransformationResult;

public interface ITransformationSupervisor {

    String startTransformation(String pattern, String transformation, List<String> networkIdList)
            throws FileStorageException, IOException, QuadrigaGeneratorException;

    TransformationResult getResult(String id);

}