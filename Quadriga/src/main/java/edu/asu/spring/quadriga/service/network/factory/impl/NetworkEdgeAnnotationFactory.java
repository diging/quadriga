package edu.asu.spring.quadriga.service.network.factory.impl;

import edu.asu.spring.quadriga.service.network.factory.INetworkEdgeAnnotationFactory;

public class NetworkEdgeAnnotationFactory implements INetworkEdgeAnnotationFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public INetworkEdgeAnnotationFactory createUserObject() {
		// TODO Auto-generated method stub
		return new NetworkEdgeAnnotationFactory();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public INetworkEdgeAnnotationFactory cloneUserObject(
			INetworkEdgeAnnotationFactory networkEdgeAnnotation) {
		// TODO Auto-generated method stub
		return null;
	}

}
