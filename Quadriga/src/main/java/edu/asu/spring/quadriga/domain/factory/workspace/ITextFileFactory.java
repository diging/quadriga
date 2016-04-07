package edu.asu.spring.quadriga.domain.factory.workspace;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.workspace.ITextFile;

/**
 * @author Nischal Samji Generates Text File Objects
 *
 */
@Service
public interface ITextFileFactory {

    /**
     * @return Returns a TextFile Object for Web Backing
     */
    public ITextFile createTextFileObject();

}
