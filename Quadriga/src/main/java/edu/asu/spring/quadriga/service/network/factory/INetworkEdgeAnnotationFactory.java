package edu.asu.spring.quadriga.service.network.factory;


import edu.asu.spring.quadriga.domain.impl.networks.NetworkNodeAnnotation;
import edu.asu.spring.quadriga.domain.network.INetworkEdgeAnnotation;

public interface INetworkEdgeAnnotationFactory {
	/**
	 * Factory method for creating {@link NetworkNodeAnnotation} objects.
	 * @return
	 */
	public abstract INetworkEdgeAnnotation createNetworkEdgeAnnotationObject();

	

}
