package edu.asu.spring.quadriga.service.network.factory.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.network.INetworkNodeAnnotation;
import edu.asu.spring.quadriga.domain.network.impl.NetworkNodeAnnotation;
import edu.asu.spring.quadriga.service.network.factory.INetworkNodeAnnotationFactory;

@Service
public class NetworkNodeAnnotationFactory implements INetworkNodeAnnotationFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public INetworkNodeAnnotation createNetworkNodeAnnotationObject() {
		
		return new NetworkNodeAnnotation();
	}

	
}
