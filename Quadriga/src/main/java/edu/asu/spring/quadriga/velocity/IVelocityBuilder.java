package edu.asu.spring.quadriga.velocity;

import java.util.Map;

import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

public interface IVelocityBuilder {

    String getRenderedTemplate(String templateName, Map<String, Object> contextProperties)
            throws ResourceNotFoundException, ParseErrorException, Exception;

}