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

	private String textFileLocation;
	
	
    public String getTextFileLocation() {
		return textFileLocation;
	}

	public void setTextFileLocation(String textFileLocation) {
		this.textFileLocation = textFileLocation;
	}

	@Override
    public boolean createDirectoryIfNotExists(String dirPath) {
        
       
        File dirFile = new File(dirPath);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        return true;
    }

    @Override
    public boolean saveFiletoDir(String dirName, String fileName, byte[] fileContent) throws IOException, FileStorageException, FileNotFoundException {
        
        createDirectoryIfNotExists(textFileLocation+"/"+dirName);
        File f = new File(textFileLocation+"/"+dirName+fileName);
        if(!f.exists()){
        	f.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(f);
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
