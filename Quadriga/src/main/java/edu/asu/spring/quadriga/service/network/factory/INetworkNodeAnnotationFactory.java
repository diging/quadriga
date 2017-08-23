package edu.asu.spring.quadriga.service.network.factory;


import edu.asu.spring.quadriga.domain.network.INetworkNodeAnnotation;
import edu.asu.spring.quadriga.domain.network.impl.NetworkNodeAnnotation;

public interface INetworkNodeAnnotationFactory {
	
	/**
	 * Factory method for creating {@link NetworkNodeAnnotation} objects.
	 * @return
	 */
	public abstract INetworkNodeAnnotation createNetworkNodeAnnotationObject();

	
}
