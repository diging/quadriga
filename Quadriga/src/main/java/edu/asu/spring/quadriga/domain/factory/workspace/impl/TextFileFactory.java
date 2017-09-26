package edu.asu.spring.quadriga.domain.factory.workspace.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factory.workspace.ITextFileFactory;
import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.domain.workspace.impl.TextFile;

/**
 * Implementation class for TextFile Interface. Creates TextFile Beans
 * 
 * @author Nischal Samji
 *
 */
@Service
@PropertySource(value = "classpath:/settings.properties")
public class TextFileFactory implements ITextFileFactory {

    @Autowired
    private Environment env;

    @Override
    public ITextFile createTextFileObject() {
        String uriPrefix = env.getProperty("textfiles.uri");
        
        ITextFile textFile = new TextFile();
        textFile.setTextFileURIPrefix(uriPrefix);
        
        return textFile;
    }

}
