package edu.asu.spring.quadriga.service.network.factory;

import org.apache.commons.lang.NotImplementedException;

import edu.asu.spring.quadriga.domain.implementation.NetworkNodeAnnotation;
import edu.asu.spring.quadriga.domain.network.INetworkEdgeAnnotation;

public interface INetworkEdgeAnnotationFactory {
	/**
	 * Factory method for creating {@link NetworkNodeAnnotation} objects.
	 * @return
	 */
	public abstract INetworkEdgeAnnotation createUserObject();

	/**
	 * Method for cloning a {@link INetworkEdgeAnnotationFactory} object. Note that this will produce a shallow clone, meaning that the NetworkEdgeAnnotation
	 * will simply be put into a new list for the clone, but the NetworkNodeAnnotation objects themselves will be the same.
	 * @param networkEdgeAnnotation the networkEdgeAnnotation object to be cloned.
	 * @return a clone of the given networkEdgeAnnotation object that contains the exact same information as the original object.
	 * @throws NotImplementedException
	 */
	public abstract  INetworkEdgeAnnotation cloneUserObject(INetworkEdgeAnnotation networkEdgeAnnotation);

}
