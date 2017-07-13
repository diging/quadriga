package edu.asu.spring.quadriga.service.network.factory;


import edu.asu.spring.quadriga.domain.network.INetworkRelationAnnotation;
import edu.asu.spring.quadriga.domain.network.impl.NetworkRelationAnnotation;

public interface INetworkRelationAnnotationFactory {
	/**
	 * Factory method for creating {@link NetworkRelationAnnotation} objects.
	 * @return
	 */
	public abstract INetworkRelationAnnotation createNetworkRelationAnnotationObject();

	
}
