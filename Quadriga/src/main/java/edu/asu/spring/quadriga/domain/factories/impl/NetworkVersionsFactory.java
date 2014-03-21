package edu.asu.spring.quadriga.domain.factories.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.INetworkVersions;
import edu.asu.spring.quadriga.domain.factories.INetworkVersionsFactory;
import edu.asu.spring.quadriga.domain.implementation.NetworkVersions;

@Service
public class NetworkVersionsFactory implements INetworkVersionsFactory{

	@Override
	public INetworkVersions createNetworkVersionsObject() {
		
		return new NetworkVersions();
	}

}
