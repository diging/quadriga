package edu.asu.spring.quadriga.utilities;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.asu.spring.quadriga.exceptions.FileStorageException;

/**
 * @author Nischal Samji Interface for file Save and retrieve operations
 *
 */
public interface IFileSaveUtility {

    /**
     * @param dirName
     *            Path of the directory for the file.
     * @param fileName
     *            Filename of the file to be stored.
     * @param fileContent
     *            Content of the file as a byte array.
     * @return Return true if the file is saved else returns false.
     * @throws FileStorageException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public boolean saveFiletoDir(String dirName, String fileName, byte[] fileContent)
            throws FileStorageException, FileNotFoundException, IOException;

    /**
     * @param fileName
     *            Name of the File whose content is to be read.
     * @param dirName
     *            Name of the Directory containing the File
     * @return Returns file content as a string.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public String readFileContent(String fileName, String dirName) throws FileStorageException, IOException;
}
