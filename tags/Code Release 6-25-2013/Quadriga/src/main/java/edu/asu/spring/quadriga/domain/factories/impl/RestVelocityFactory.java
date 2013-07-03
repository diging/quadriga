package edu.asu.spring.quadriga.domain.factories.impl;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.VelocityContext;
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

	private VelocityContext context;
	/* 
	 * Creates the factory and sets the class resource paths
	 */
	@Override
	public VelocityEngine getVelocityEngine(HttpServletRequest req) throws FileNotFoundException, IOException {
		VelocityEngine engine = new VelocityEngine();
		engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		engine.setProperty("classpath.resource.loader.class",
				ClasspathResourceLoader.class.getName());
		context = new VelocityContext();
		context.put("url", "http://"+req.getServerName()+":"+req.getServerPort()+req.getContextPath());
		return engine;
	}

	@Override
	public VelocityContext getVelocityContext() {
		// TODO Auto-generated method stub
		return context;
	}
}
