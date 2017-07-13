package edu.asu.spring.quadriga.service.network.factory.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.network.INetworkEdgeAnnotation;
import edu.asu.spring.quadriga.domain.network.impl.NetworkEdgeAnnotation;
import edu.asu.spring.quadriga.service.network.factory.INetworkEdgeAnnotationFactory;

@Service
public class NetworkEdgeAnnotationFactory implements INetworkEdgeAnnotationFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public INetworkEdgeAnnotation createNetworkEdgeAnnotationObject() {
		return new NetworkEdgeAnnotation();
	}

	

}
