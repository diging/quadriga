package edu.asu.spring.quadriga.domain;

/**
 * The interface that provides access to the class representation of the ByteStream got from Dspace repostiory.
 * 
 * @author Ram Kumar Kumaresan
 */
public interface IBitStream {

	public abstract String getName();

	public abstract void setName(String name);
	
	public abstract String getSize();
	
	public abstract void setSize(String size);
	
	public abstract String getFormat();
	
	public abstract void setFormat(String format);

}
