package edu.asu.spring.quadriga.service.textfile;

public interface IFileManager {
    public boolean createDirectoryIfNotExists(String dirPath);
    public boolean saveFiletoDir(String dirName, String fileName, byte[] fileContent);
}
