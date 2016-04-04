package edu.asu.spring.quadriga.service.textfile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface ITextFileManager {

    /**
     * @param txtfile
     *            Text File object from web controller to handle text
     *            operations.
     * @return Returns true if file save is successful else returns false.
     * @throws QuadrigaStorageException
     * @throws IOException
     */
    boolean saveTextFile(ITextFile txtfile) throws QuadrigaStorageException, FileStorageException, IOException;
    
    /**
     * Retrieve Textfile Domain Objects based on Workspace Id.
     * @param wsId
     *        Associated workspace id to retrieve TextFiles.
     * @return
     *        Returns a list of TextFile objects to be displayed on workspace details page. 
     */
    List<ITextFile> retrieveTextFiles(String wsId);
    
    String retrieveTextFileContent(String txtId) throws FileNotFoundException, IOException; 

}
