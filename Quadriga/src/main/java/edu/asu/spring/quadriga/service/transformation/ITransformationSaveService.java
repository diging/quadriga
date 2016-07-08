package edu.asu.spring.quadriga.service.transformation;

import java.io.IOException;

import edu.asu.spring.quadriga.domain.workspace.ITransformationFile;
import edu.asu.spring.quadriga.exceptions.FileStorageException;

/**
 * Interface for methods of saving transformation files
 *
 * @author yoganandakishore
 *
 */

public interface ITransformationSaveService {

    /**
     * Tries to store the pattern file content and transformation file content
     * of transformationFile object as two separate files in local file system.
     * 
     * @param transformationFile
     *            Transformation File Object which contains content for pattern
     *            file and transformation file to be saved to local file system
     * @return Returns true if both files are successfully saved.
     * @throws IOException
     * @throws FileStorageException
     */
    boolean saveFileToLocal(ITransformationFile transformationFile) throws FileStorageException;

    /**
     * 
     * @return location where transformations are stored
     */
    public String getTransformFileLocation();

}
