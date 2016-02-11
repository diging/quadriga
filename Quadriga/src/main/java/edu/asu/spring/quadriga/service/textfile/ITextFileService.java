package edu.asu.spring.quadriga.service.textfile;

import java.io.IOException;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface ITextFileService {
    
    public boolean saveTextFile(String prjId, String wsId, String fileName, String fileContent) throws QuadrigaStorageException, IOException;
        

}
