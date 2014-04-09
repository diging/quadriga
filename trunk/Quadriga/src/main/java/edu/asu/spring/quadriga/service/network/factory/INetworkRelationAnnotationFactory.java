package edu.asu.spring.quadriga.service.network.factory;

import org.apache.commons.lang.NotImplementedException;

import edu.asu.spring.quadriga.domain.implementation.NetworkRelationAnnotation;

public interface INetworkRelationAnnotationFactory {
	/**
	 * Factory method for creating {@link NetworkRelationAnnotation} objects.
	 * @return
	 */
	public abstract INetworkRelationAnnotationFactory createUserObject();

	/**
	 * Method for cloning a {@link INetworkNodeAnnotationFactory} object. Note that this will produce a shallow clone, meaning that the NetworkRelationAnnotation
	 * will simply be put into a new list for the clone, but the NetworkRelationAnnotation objects themselves will be the same.
	 * @param networkRelationAnnotation the network Relation Annotation object to be cloned.
	 * @return a clone of the given networkRelationAnnotation object that contains the exact same information as the original object.
	 * @throws NotImplementedException
	 */
	public abstract  INetworkRelationAnnotationFactory cloneUserObject(INetworkRelationAnnotationFactory networkRelationAnnotation);

}
