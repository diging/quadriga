package edu.asu.spring.quadriga.utilities.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    public boolean saveFiletoDir(String dirName, String fileName, byte[] fileContent) throws IOException, FileStorageException, FileNotFoundException {
        
        
        FileOutputStream fos = new FileOutputStream(dirName+fileName);
        try{
        fos.write(fileContent);
        fos.close();
        return true;
        }
        catch(Exception e){
            return false;
        }
        
       }

    

}
