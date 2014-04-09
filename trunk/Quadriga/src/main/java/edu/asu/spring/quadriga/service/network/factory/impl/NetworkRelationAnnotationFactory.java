package edu.asu.spring.quadriga.service.network.factory.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.INetworkRelationAnnotation;
import edu.asu.spring.quadriga.domain.implementation.NetworkRelationAnnotation;
import edu.asu.spring.quadriga.service.network.factory.INetworkRelationAnnotationFactory;

@Service
public class NetworkRelationAnnotationFactory implements
		INetworkRelationAnnotationFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public INetworkRelationAnnotation createUserObject() {
		return new NetworkRelationAnnotation();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public INetworkRelationAnnotation cloneUserObject(
			INetworkRelationAnnotation networkRelationAnnotation) {
		return null;
	}

}
