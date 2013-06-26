package edu.asu.spring.quadriga.dspace.service.impl;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import edu.asu.spring.quadriga.dspace.service.IDspaceBitStreamEntityId;

@XmlRootElement(name="bitstreamentityid")
public class DspaceBitStreamEntityId implements IDspaceBitStreamEntityId {
	
	private String id;

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
