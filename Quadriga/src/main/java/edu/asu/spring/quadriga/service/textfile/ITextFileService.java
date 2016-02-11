package edu.asu.spring.quadriga.service.textfile;

import java.io.IOException;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface ITextFileService {
    
    boolean saveTextFile(ITextFile txtfile) throws QuadrigaStorageException,
			IOException;
        

}
