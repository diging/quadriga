package edu.asu.spring.quadriga.dspace.service;

/**
 * The interface that provides access to the class representation of the items containing bitstreams got from Dspace repostiory.
 * 
 * @author Ram Kumar Kumaresan
 * 
 */
public interface IDspaceItems {

	public abstract IDspaceBitStream getBitstreams();

	public abstract void setBitstreams(IDspaceBitStream bitstreams);

}