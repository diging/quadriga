package edu.asu.spring.quadriga.service.network.factory;

import org.apache.commons.lang.NotImplementedException;

import edu.asu.spring.quadriga.domain.implementation.NetworkNodeAnnotation;
import edu.asu.spring.quadriga.domain.network.INetworkNodeAnnotation;

public interface INetworkNodeAnnotationFactory {
	
	/**
	 * Factory method for creating {@link NetworkNodeAnnotation} objects.
	 * @return
	 */
	public abstract INetworkNodeAnnotation createUserObject();

	/**
	 * Method for cloning a {@link INetworkNodeAnnotationFactory} object. Note that this will produce a shallow clone, meaning that the NetworkNodeAnnotation
	 * will simply be put into a new list for the clone, but the NetworkNodeAnnotation objects themselves will be the same.
	 * @param networkNodeAnnotation the networkNodeAnnotation object to be cloned.
	 * @return a clone of the given networkNodeAnnotation object that contains the exact same information as the original object.
	 * @throws NotImplementedException
	 */
	public abstract  INetworkNodeAnnotation cloneUserObject(INetworkNodeAnnotation networkNodeAnnotation);

}
