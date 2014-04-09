package edu.asu.spring.quadriga.service.network.factory.impl;

import edu.asu.spring.quadriga.service.network.factory.INetworkRelationAnnotationFactory;

public class NetworkRelationAnnotationFactory implements
		INetworkRelationAnnotationFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public INetworkRelationAnnotationFactory createUserObject() {
		return new NetworkRelationAnnotationFactory();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public INetworkRelationAnnotationFactory cloneUserObject(
			INetworkRelationAnnotationFactory networkRelationAnnotation) {
		return null;
	}

}
