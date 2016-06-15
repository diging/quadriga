package edu.asu.spring.quadriga.domain.factories.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;

/**
 * 
 * @author Lohith Dwaraka
 * @author satyaswaroop boddu
 * Factory for veloctiy engine object
 */
@Service
public class RestVelocityFactory implements IRestVelocityFactory {

	
	/* 
	 * Creates the factory and sets the class resource paths
	 */
	@Override
	public VelocityEngine getVelocityEngine() throws FileNotFoundException, IOException {
		VelocityEngine engine = new VelocityEngine();
		engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		engine.setProperty("classpath.resource.loader.class",
				ClasspathResourceLoader.class.getName());
		engine.setProperty( RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
				"org.apache.velocity.runtime.log.Log4JLogChute"
				);
		engine.setProperty("runtime.log.logsystem.log4j.logger","velocity");
		Properties props = new Properties();
		props.put("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
		props.put("runtime.log.logsystem.log4j.category", "runtime.log.logsystem.log4j.category");
		props.put("runtime.log.logsystem.log4j.logger", "velocity");
		
		return engine;
	}
	
	@Override
	public VelocityEngine getVelocityRestEngine() throws FileNotFoundException, IOException {
		VelocityEngine engine = new VelocityEngine();
		engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		engine.setProperty("classpath.resource.loader.class",
				ClasspathResourceLoader.class.getName());
		engine.setProperty( RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
				"org.apache.velocity.runtime.log.Log4JLogChute"
				);
		engine.setProperty("runtime.log.logsystem.log4j.logger","velocity");
		Properties props = new Properties();
		props.put("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
		props.put("runtime.log.logsystem.log4j.category", "runtime.log.logsystem.log4j.category");
		props.put("runtime.log.logsystem.log4j.logger", "velocity");
		
		return engine;
	}

	
}
