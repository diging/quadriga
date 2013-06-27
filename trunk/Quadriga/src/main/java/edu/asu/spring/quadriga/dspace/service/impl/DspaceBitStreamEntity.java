package edu.asu.spring.quadriga.dspace.service.impl;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import edu.asu.spring.quadriga.dspace.service.IDspaceBitStreamEntityId;

@XmlRootElement(name="bitstreamentity")
public class DspaceBitStreamEntity implements IDspaceBitStreamEntityId {
	
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
	
	public static class Adapter extends XmlAdapter<DspaceBitStreamEntity, IDspaceBitStreamEntityId>
	{
		@Override
		public IDspaceBitStreamEntityId unmarshal(DspaceBitStreamEntity v) throws Exception {
			return v;
		}

		@Override
		public DspaceBitStreamEntity marshal(IDspaceBitStreamEntityId v) throws Exception {
			return (DspaceBitStreamEntity)v;
		}		
	}

}
