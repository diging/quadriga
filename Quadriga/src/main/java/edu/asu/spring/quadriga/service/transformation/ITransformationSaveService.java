package edu.asu.spring.quadriga.service.transformation;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.domain.workspace.ITransformationFile;
import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.utilities.IFileSaveUtility;

public interface ITransformationSaveService{

    /**
     * @param txtFile
     *            Text File Object to be saved
     * @return Returns true if file is successfully saved.
     * @throws IOException
     * @throws FileStorageException
     */
    boolean saveFileToLocal(ITransformationFile transformationFile) throws FileStorageException;

    
}
