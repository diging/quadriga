package edu.asu.spring.quadriga.utilities;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.asu.spring.quadriga.exceptions.FileStorageException;

public interface IFileManager {
    public boolean createDirectoryIfNotExists(String dirPath);
    public boolean saveFiletoDir(String dirName, String fileName, byte[] fileContent) throws FileStorageException, FileNotFoundException, IOException;
}
