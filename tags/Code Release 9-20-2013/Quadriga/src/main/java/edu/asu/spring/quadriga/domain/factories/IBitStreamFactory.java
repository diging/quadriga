package edu.asu.spring.quadriga.domain.factories;

import edu.asu.spring.quadriga.domain.IBitStream;

/**
 * Factory interface for Bitstream factories.
 * 
 * @author Ram Kumar Kumaresan
 */
public interface IBitStreamFactory {

	/**
	 * Create a bitstream object with all fields empty.
	 * 
	 * @return An empty bitstream object
	 */
	public abstract IBitStream createBitStreamObject();

}