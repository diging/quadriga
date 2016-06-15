package edu.asu.spring.quadriga.service.network.factory.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.impl.networks.NetworkRelationAnnotation;
import edu.asu.spring.quadriga.domain.network.INetworkRelationAnnotation;
import edu.asu.spring.quadriga.service.network.factory.INetworkRelationAnnotationFactory;

@Service
public class NetworkRelationAnnotationFactory implements
		INetworkRelationAnnotationFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public INetworkRelationAnnotation createNetworkRelationAnnotationObject() {
		return new NetworkRelationAnnotation();
	}

	

}
