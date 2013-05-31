package edu.asu.spring.quadriga.domain.implementation;

import edu.asu.spring.quadriga.domain.IByteStream;

public class ByteStream implements IByteStream{

	private String name;
	private String size;
	private String format;
	
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;		
	}

	@Override
	public String getSize() {
		return this.size;
	}

	@Override
	public void setSize(String size) {
		this.size = size;
	}

	@Override
	public String getFormat() {
		return this.format;
	}

	@Override
	public void setFormat(String format) {
		this.format = format;
	}

}
