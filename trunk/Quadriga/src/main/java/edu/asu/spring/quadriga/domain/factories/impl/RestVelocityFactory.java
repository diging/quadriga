package edu.asu.spring.quadriga.domain.factories.impl;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.stereotype.Service;
import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;

/**
 * 
 * @author Lohith Dwaraka
 * Factory for veloctiy engine object
 */
@Service
public class RestVelocityFactory implements IRestVelocityFactory {

	/* 
	 * Creates the factory and sets the class resource paths
	 */
	@Override
	public VelocityEngine RestVelocityFactory() {
		VelocityEngine engine = new VelocityEngine();
		engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		engine.setProperty("classpath.resource.loader.class",
				ClasspathResourceLoader.class.getName());
		
		return engine;
	}
}
