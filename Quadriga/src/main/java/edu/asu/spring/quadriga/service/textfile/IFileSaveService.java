package edu.asu.spring.quadriga.service.textfile;

import java.io.IOException;

import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.exceptions.FileStorageException;

/**
 * @author Nischal Samji
 * 		Interface for File Save Service methods 
 *
 */
public interface IFileSaveService {

    /**
     * @param txtFile
     * 			Text File Object to be saved
     * @return
     * 			Returns true if file is succesfully saved.
     * @throws IOException
     * @throws FileStorageException
     */
    boolean saveFileToLocal(ITextFile txtFile) throws IOException, FileStorageException;
}
