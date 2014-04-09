package edu.asu.spring.quadriga.service.network.factory.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.service.network.factory.INetworkNodeAnnotationFactory;

@Service
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
