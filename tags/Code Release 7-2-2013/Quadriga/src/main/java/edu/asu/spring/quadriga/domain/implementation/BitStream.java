package edu.asu.spring.quadriga.domain.implementation;

import edu.asu.spring.quadriga.domain.IBitStream;

/**
 * The class representation of the ByteStream got from Dspace repostiory.
 * 
 * @author Ram Kumar Kumaresan
 */
public class BitStream implements IBitStream{

	private String id;
	private String name;
	private String size;
	private String mimeType;
	
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getMimeType() {
		return mimeType;
	}

	@Override
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

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

}
