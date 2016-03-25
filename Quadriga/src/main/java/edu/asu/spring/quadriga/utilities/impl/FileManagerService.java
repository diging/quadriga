package edu.asu.spring.quadriga.utilities.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.exceptions.FileStorageException;
import edu.asu.spring.quadriga.utilities.IFileManager;

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
    public boolean saveFiletoDir(String dirName, String fileName, byte[] fileContent) throws FileStorageException {
        
        
        File f = new File(dirName+fileName);
        try {
            FileWriter fw  = new FileWriter(f);
            
        } catch (IOException fse) {
            // TODO Auto-generated catch block
            fse.printStackTrace();
        }
        
        
        return false;
    }

    

}
