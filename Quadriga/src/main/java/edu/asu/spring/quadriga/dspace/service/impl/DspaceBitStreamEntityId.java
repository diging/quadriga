package edu.asu.spring.quadriga.dspace.service.impl;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import edu.asu.spring.quadriga.dspace.service.IDspaceBitStreamEntityId;

/**
 * The class representation of the bitstream got from the Dspace repository
 * 
 * @author Ram Kumar Kumaresan
 */
@XmlRootElement(name="bitstreamentityid")
public class DspaceBitStreamEntityId implements IDspaceBitStreamEntityId {
	
	private String id;
	private String name;
	private String size;
	private String mimeType;

	@Override
	public String getName() {
		return name;
	}


	@Override
	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String getSize() {
		return size;
	}


	@Override
	public void setSize(String size) {
		this.size = size;
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
	public String getId() {
		return id;
	}


	@Override
	public void setId(String id) {
		this.id = id;
	}
	
	public static class Adapter extends XmlAdapter<DspaceBitStreamEntityId, IDspaceBitStreamEntityId>
	{
		@Override
		public IDspaceBitStreamEntityId unmarshal(DspaceBitStreamEntityId v) throws Exception {
			return v;
		}

		@Override
		public DspaceBitStreamEntityId marshal(IDspaceBitStreamEntityId v) throws Exception {
			return (DspaceBitStreamEntityId)v;
		}		
	}

}
