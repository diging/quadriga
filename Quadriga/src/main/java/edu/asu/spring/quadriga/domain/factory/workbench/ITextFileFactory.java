package edu.asu.spring.quadriga.domain.factory.workbench;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.workspace.ITextFile;


/**
 * @author Nischal Samji
 * Generates Text File Objects
 *
 */
@Service
public interface ITextFileFactory {

    /**
     * @return
     * 		returns a TextFile Object
     */
    public abstract ITextFile createTextFileObject();
        
}
