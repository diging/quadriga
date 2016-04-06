package edu.asu.spring.quadriga.domain.factory.impl.passthroughproject;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factory.passthroughproject.IPassThroughProjectFactory;
import edu.asu.spring.quadriga.domain.impl.passthroughproject.PassThroughProject;
import edu.asu.spring.quadriga.domain.passthroughproject.IPassThroughProject;

@Service
public class PassThroughProjectFactory implements IPassThroughProjectFactory {

    @Override
    public IPassThroughProject createPassThroughProjectObject() {
        return new PassThroughProject();
    }
}
