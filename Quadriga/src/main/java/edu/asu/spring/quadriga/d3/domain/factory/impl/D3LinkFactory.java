package edu.asu.spring.quadriga.d3.domain.factory.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.d3.domain.ID3Link;
import edu.asu.spring.quadriga.d3.domain.factory.ID3LinkFactory;
import edu.asu.spring.quadriga.d3.domain.impl.D3Link;

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
