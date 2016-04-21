package edu.asu.spring.quadriga.dspace.service;


/**
 * The interface that provides access to the class representation of the Item got from Dspace repostiory.
 * 
 * @author Ram Kumar Kumaresan
 */
public interface IDspaceItem {

	public abstract String getId();

	public abstract void setId(String id);

	public abstract String getName();

	public abstract void setName(String name);

	public abstract String getHandle();

	public abstract void setHandle(String handle);

	public abstract void setBitstreams(IDspaceBitStream bitstreams);

	public abstract IDspaceBitStream getBitstreams();
	
}