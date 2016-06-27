package edu.asu.spring.quadriga.service.impl.transformation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.workspace.ITransformationFile;
import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.service.transformation.ITransformationSaveService;
import edu.asu.spring.quadriga.utilities.IFileSaveUtility;

/**
 * This service contains method to save transformation files to local file storage
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
        
        if( transformationFileManager.saveFiletoDir(transformationFile.getPatternTitle(), transformationFile.getPatternFileName(), transformationFile.getPatternFileContent()) &&
            transformationFileManager.saveFiletoDir(transformationFile.getMappingTitle(), transformationFile.getMappingFileName(), transformationFile.getMappingFileContent()) ) {
            return true;
        } else
            return false;
    }
}
