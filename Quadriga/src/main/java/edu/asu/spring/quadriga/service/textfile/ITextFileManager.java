package edu.asu.spring.quadriga.service.textfile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface ITextFileManager {

    /**
     * Save the Text file in the database and Local File System.
     * 
     * @param txtfile
     *            Text File object from web controller to handle text
     *            operations.
     * @return Returns true if file save is successful else returns false.
     * @throws QuadrigaStorageException
     * @throws IOException
     */
    boolean saveTextFile(ITextFile txtfile) throws QuadrigaStorageException, FileStorageException;

    /**
     * Retrieve Textfile Domain Objects based on Workspace Id.
     * 
     * @param wsId
     *            Associated workspace id to retrieve TextFiles.
     * @return Returns a list of TextFile objects to be displayed on workspace
     *         details page.
     */
    List<ITextFile> retrieveTextFiles(String wsId) throws QuadrigaStorageException;

    /**
     * Retrieve the text file content as a string.
     * 
     * @param txtId
     *            Text ID of the file to be retrieved.
     * @return Returns the contents of the file as a string.
     * @throws FileNotFoundException
     * @throws IOException
     * @throws FileStorageException
     */
    String retrieveTextFileContent(String txtId) throws FileStorageException;

    public abstract ITextFile getTextFileByUri(String uri) throws QuadrigaStorageException;

    void loadFile(ITextFile txtFile) throws FileStorageException;

    ITextFile getTextFile(String textId) throws QuadrigaStorageException;

    /**
     * saves or updates the txtFile in the database.
     * 
     * @param txtFile
     *            Text File object from web controller to handle text
     *            operations.
     * @return
     */
    void saveTextFileToDB(ITextFile txtFile);

    /**
     * If the accessibility of textFile is public, it's set to private.
     * otherwise it is set to public.
     * 
     * @param textFile
     *            Text File object from web controller to handle text
     *            operations.
     * @return textFile with updated accessibility
     */
    ITextFile updateTextFileAccessibility(ITextFile textFile);

}
