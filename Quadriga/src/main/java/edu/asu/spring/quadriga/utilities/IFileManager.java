package edu.asu.spring.quadriga.utilities;

import edu.asu.spring.quadriga.exceptions.FileStorageException;

public interface IFileManager {
    public boolean createDirectoryIfNotExists(String dirPath);
    public boolean saveFiletoDir(String dirName, String fileName, byte[] fileContent) throws FileStorageException;
}
