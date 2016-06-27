package edu.asu.spring.quadriga.service.impl.transformation;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.workspace.ITransformationFile;
import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.service.transformation.ITransformationSaveService;
import edu.asu.spring.quadriga.utilities.IFileSaveUtility;

/**
 * 
 *  @author yoganandakishore
 *
 */

@Service
public class TransformationSaveService implements ITransformationSaveService{

    @Qualifier("transformfileSaveUtil")
    @Autowired
    private IFileSaveUtility transformationFileManager;

    @Override
    public boolean saveFileToLocal(ITransformationFile transformationFile) throws FileStorageException {
        
        byte[] patternFileContentBytes;
        byte[] mappingFileContentBytes;
        
        try {
            patternFileContentBytes = transformationFile.getPatternFileContent().getBytes("UTF-8");
            mappingFileContentBytes = transformationFile.getMappingFileContent().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new FileStorageException(e);
        }
        if( transformationFileManager.saveFiletoDir(transformationFile.getPatternTitle(), transformationFile.getPatternFileName(), patternFileContentBytes) &&
            transformationFileManager.saveFiletoDir(transformationFile.getMappingTitle(), transformationFile.getMappingFileName(), mappingFileContentBytes) ) {
            return true;
        } else
            return false;
    }
}
