package edu.asu.spring.quadriga.service.network.factory.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.INetworkEdgeAnnotation;
import edu.asu.spring.quadriga.domain.implementation.NetworkEdgeAnnotation;
import edu.asu.spring.quadriga.service.network.factory.INetworkEdgeAnnotationFactory;

@Service
public class NetworkEdgeAnnotationFactory implements INetworkEdgeAnnotationFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public INetworkEdgeAnnotation createUserObject() {
		// TODO Auto-generated method stub
		return new NetworkEdgeAnnotation();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public INetworkEdgeAnnotation cloneUserObject(
			INetworkEdgeAnnotation networkEdgeAnnotation) {
		// TODO Auto-generated method stub
		return null;
	}

}
