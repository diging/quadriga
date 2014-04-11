package edu.asu.spring.quadriga.service.network.factory.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.implementation.NetworkNodeAnnotation;
import edu.asu.spring.quadriga.domain.network.INetworkNodeAnnotation;
import edu.asu.spring.quadriga.service.network.factory.INetworkNodeAnnotationFactory;

@Service
public class NetworkNodeAnnotationFactory implements INetworkNodeAnnotationFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public INetworkNodeAnnotation createUserObject() {
		// TODO Auto-generated method stub
		return new NetworkNodeAnnotation();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public INetworkNodeAnnotation cloneUserObject(
			INetworkNodeAnnotation networkNodeAnnotation) {
		// TODO Auto-generated method stub
		return null;
	}

}
