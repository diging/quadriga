package edu.asu.spring.quadriga.dspace.service;

import java.util.List;

/**
 * The interface that provides access to the class representation of the bitstream id got from Dspace repostiory.
 * 
 * @author Ram Kumar Kumaresan
 * 
 */
public interface IDspaceBitStream {

	public abstract List<IDspaceBitStreamEntityId> getBitstreamentityid();

	public abstract void setBitstreamentityid(List<IDspaceBitStreamEntityId> bitstreams);

}