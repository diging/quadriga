package edu.asu.spring.quadriga.service.textfile.impl;

import java.io.File;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.service.textfile.ITextFileService;

@Service
public class TextFileService implements ITextFileService{

    @Override
    public boolean saveTextFile() {
        
        File file = new File("/Users/nischalsamji/testdir");
        file.mkdir();
        
        return true;
    }

    
    
}
