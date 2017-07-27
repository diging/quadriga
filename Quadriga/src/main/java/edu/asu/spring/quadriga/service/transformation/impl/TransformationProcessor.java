package edu.asu.spring.quadriga.service.transformation.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.network.impl.Transformation;
import edu.asu.spring.quadriga.domain.network.tranform.ITransformation;
import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaGeneratorException;
import edu.asu.spring.quadriga.service.network.transform.impl.Generator;
import edu.asu.spring.quadriga.service.network.transform.impl.MatchGraphs;
import edu.asu.spring.quadriga.service.network.transform.impl.TransformNode;
import edu.asu.spring.quadriga.service.transformation.ITransformationProcessor;
import edu.asu.spring.quadriga.utilities.IFileSaveUtility;

/**
 * @author yoganandakishore
 * 
 *         This class creates a task to generate transformed network
 *         asynchronously
 * 
 */

@Service
public class TransformationProcessor implements ITransformationProcessor {

    @Autowired
    private Generator generator;

    @Autowired
    private MatchGraphs matchGraphs;
    
    @Qualifier("tempSaveUtil")
    @Autowired
    private IFileSaveUtility fileSaveUtility;

    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.service.transformation.impl.ITransformationProcessor#runTransformationAsync(java.util.List, java.util.List)
     */
    @Override
    @Async
    public Future<String> runTransformationAsync(List<ITransformation> transformations, List<String> networkIdList)
            throws QuadrigaGeneratorException {

        List<List<TransformNode>> results = matchGraphs.matchGraphs(transformations, networkIdList);
        String graphString = generator.generateText(results, null, true);
        return new AsyncResult<>(graphString);
    }
    
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.service.transformation.impl.ITransformationProcessor#runTransformation(java.lang.String, java.lang.String, java.util.List)
     */
    @Override
    public String runTransformation(String pattern, String transformation, List<String> networkIdList) throws FileStorageException, IOException, QuadrigaGeneratorException {
        String patternFileName = null;
        String transformationFileName = null;
        
        // generate not yet existing filenames
        // FIXME: make thread-safe
        while (patternFileName == null){
            patternFileName = generateId("PAT");
            if (fileSaveUtility.readFileContent(patternFileName, null) != null) {
                patternFileName = null;
            }
        }
        while (transformationFileName == null){
            transformationFileName = generateId("TRA");
            if (fileSaveUtility.readFileContent(transformationFileName, null) != null) {
                transformationFileName = null;
            }
        }
        
        ITransformation transformationObj = new Transformation();
        transformationObj.setPatternFilePath(fileSaveUtility.saveFileInRoot(patternFileName, pattern));
        transformationObj.setTransformationFilePath(fileSaveUtility.saveFileInRoot(transformationFileName, transformation));
        
        List<ITransformation> transformations = Arrays.asList(new ITransformation[] { transformationObj });
        List<List<TransformNode>> results = matchGraphs.matchGraphs(transformations, networkIdList);
        
        return generator.generateText(results, "transformation/json-networks.vm", true);
    }
    
    private String generateId(String prefix) {
        char[] chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            builder.append(chars[random.nextInt(62)]);
        }

        return prefix + builder.toString();
    }
}
