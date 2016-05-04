package edu.asu.spring.quadriga.service.textfile;

import java.io.IOException;

import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.exceptions.FileStorageException;

/**
 * Interface for File Save Service methods
 * 
 * @author Nischal Samji
 *
 */
public interface IFileSaveService {

    /**
     * @param txtFile
     *            Text File Object to be saved
     * @return Returns true if file is successfully saved.
     * @throws IOException
     * @throws FileStorageException
     */
    boolean saveFileToLocal(ITextFile txtFile) throws FileStorageException;

    /**
     * @param filename
     *            Name of the file to be retrieved.
     * @param txtId
     *            ID of the Textfile in Quadriga, also the directory.
     * @return returns content of the file as a string.
     * @throws FileStorageException
     */
    String retrieveFileFromLocal(String filename, String txtId) throws FileStorageException;
}
