package edu.asu.spring.quadriga.utilities;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.asu.spring.quadriga.exceptions.FileStorageException;

public interface IFileManager {
        
    /**
     * @param dirName
     * 		Path of the directory for the file.
     * @param fileName
     * 		Filename of the file to be stored.
     * @param fileContent
     * 		Content of the file as a byte array.
     * @return
     * 		Return true if the file is saved else returns false.
     * @throws FileStorageException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public boolean saveFiletoDir(String dirName, String fileName, byte[] fileContent) throws FileStorageException, FileNotFoundException, IOException;
}
