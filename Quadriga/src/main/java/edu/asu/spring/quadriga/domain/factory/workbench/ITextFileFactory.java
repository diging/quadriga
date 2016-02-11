package edu.asu.spring.quadriga.domain.factory.workbench;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.workspace.ITextFile;


@Service
public interface ITextFileFactory {

    public abstract ITextFile  createTextFileObject();
        
}
