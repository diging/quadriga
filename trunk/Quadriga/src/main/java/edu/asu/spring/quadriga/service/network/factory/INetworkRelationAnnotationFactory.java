package edu.asu.spring.quadriga.service.network.factory;


import edu.asu.spring.quadriga.domain.impl.networks.NetworkRelationAnnotation;
import edu.asu.spring.quadriga.domain.network.INetworkRelationAnnotation;

public interface INetworkRelationAnnotationFactory {
	/**
	 * Factory method for creating {@link NetworkRelationAnnotation} objects.
	 * @return
	 */
	public abstract INetworkRelationAnnotation createNetworkRelationAnnotationObject();

	
}
