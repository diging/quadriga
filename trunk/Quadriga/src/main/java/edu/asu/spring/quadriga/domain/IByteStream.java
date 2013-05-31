package edu.asu.spring.quadriga.domain;

public interface IByteStream {

	public abstract String getName();

	public abstract void setName(String name);
	
	public abstract String getSize();
	
	public abstract void setSize(String size);
	
	public abstract String getFormat();
	
	public abstract void setFormat(String format);

}
