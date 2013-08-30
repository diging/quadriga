package edu.asu.spring.quadriga.domain.factories.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.factories.IBitStreamFactory;
import edu.asu.spring.quadriga.domain.implementation.BitStream;

/**
 * Factory class for creating {@link @BitStream}
 * 
 * @author Ram Kumar Kumaresan
 *
 */
@Service
public class BitStreamFactory implements IBitStreamFactory {
	
	/**
	 * {@inheritDoc} 
	 */
	@Override
	public IBitStream createBitStreamObject()
	{
		return new BitStream();
	}

}
