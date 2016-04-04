package edu.asu.spring.quadriga.service.textfile;

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
    
    List<ITextFile> retrieveTextFiles(String wsId);

}
