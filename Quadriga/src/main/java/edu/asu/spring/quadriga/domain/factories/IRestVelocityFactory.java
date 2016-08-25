package edu.asu.spring.quadriga.domain.factories;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.velocity.app.VelocityEngine;
/**
 * Creates {@link VelocityEngine} objects with predefined settings.
 * @author satyaswaroop boddu
 *
 */
public interface IRestVelocityFactory {

	public abstract VelocityEngine getVelocityEngine() throws FileNotFoundException, IOException;
	public abstract VelocityEngine getVelocityRestEngine() throws FileNotFoundException,
			IOException;
	
}