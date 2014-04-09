package edu.asu.spring.quadriga.service.network.factory.impl;

import edu.asu.spring.quadriga.service.network.factory.INetworkNodeAnnotationFactory;

public class NetworkNodeAnnotationFactory implements INetworkNodeAnnotationFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public INetworkNodeAnnotationFactory createUserObject() {
		// TODO Auto-generated method stub
		return new NetworkNodeAnnotationFactory();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public INetworkNodeAnnotationFactory cloneUserObject(
			INetworkNodeAnnotationFactory networkNodeAnnotation) {
		// TODO Auto-generated method stub
		return null;
	}

}
