package edu.asu.spring.quadriga.domain.factory.passthroughproject.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factory.passthroughproject.IPassThroughProjectFactory;
import edu.asu.spring.quadriga.domain.impl.passthroughproject.PassThroughProject;
import edu.asu.spring.quadriga.domain.passthroughproject.IPassThroughProject;

/**
 * Factory class for creating {@link PassThroughProject}
 * 
 */
@Service
public class PassThroughProjectFactory implements IPassThroughProjectFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public IPassThroughProject createPassThroughProjectObject() {
        return new PassThroughProject();
    }
}
