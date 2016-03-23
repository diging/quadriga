package edu.asu.spring.quadriga.service.textfile.impl;

import java.io.File;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.service.textfile.IFileManager;

@Service
public class FileManagerService implements IFileManager {

    @Override
    public boolean createDirectoryIfNotExists(String dirPath) {
        
        String saveDir = "testlocation";
        String filePath = saveDir + "/"+ dirPath;
        File dirFile = new File(filePath);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        return true;
    }

    @Override
    public boolean saveFiletoDir(String dirName, String fileName, byte[] fileContent) {
        
        return false;
    }

    

}
