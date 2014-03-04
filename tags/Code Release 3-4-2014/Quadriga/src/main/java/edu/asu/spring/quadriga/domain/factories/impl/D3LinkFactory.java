package edu.asu.spring.quadriga.domain.factories.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ID3Link;
import edu.asu.spring.quadriga.domain.factories.ID3LinkFactory;
import edu.asu.spring.quadriga.domain.implementation.D3Link;

@Service
public class D3LinkFactory implements ID3LinkFactory  {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ID3Link createD3LinkObject() {
		return new D3Link();
	}
}
