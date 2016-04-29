package edu.asu.spring.quadriga.dspace.service;

/**
 * The interface that provides access to the class representation of the bitstream got from Dspace repostiory.
 * 
 * @author Ram Kumar Kumaresan
 * 
 */
public interface IDspaceBitStreamEntityId {

	public abstract String getId();

	public abstract void setId(String id);

	public abstract String getName();

	public abstract void setName(String name);

	public abstract String getSize();

	public abstract void setSize(String size);

	public abstract String getMimeType();

	public abstract void setMimeType(String mimeType);

}