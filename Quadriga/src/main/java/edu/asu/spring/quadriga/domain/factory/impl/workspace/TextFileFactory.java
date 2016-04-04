package edu.asu.spring.quadriga.domain.factory.impl.workspace;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factory.workspace.ITextFileFactory;
import edu.asu.spring.quadriga.domain.impl.workspace.TextFile;
import edu.asu.spring.quadriga.domain.workspace.ITextFile;

/**
 * @author Nischal Samji Implementation class for TextFile Interface. Creates
 *         TextFile Beans
 *
 */
@Service
public class TextFileFactory implements ITextFileFactory {

    @Override
    public ITextFile createTextFileObject() {
        return new TextFile();
    }

}
