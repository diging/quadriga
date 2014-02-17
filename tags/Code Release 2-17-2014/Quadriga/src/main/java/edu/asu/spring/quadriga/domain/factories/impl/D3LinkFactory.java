package edu.asu.spring.quadriga.domain.factories.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ID3Link;
import edu.asu.spring.quadriga.domain.factories.ID3LinkFactory;
import edu.asu.spring.quadriga.domain.implementation.D3Link;

@Service
public class D3LinkFactory implements ID3LinkFactory  {

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.factories.impl.ID3LinkFactory#createD3LinkObject()
	 */
	@Override
	public ID3Link createD3LinkObject() {
		// TODO Auto-generated method stub
		return new D3Link();
		
	}
}
