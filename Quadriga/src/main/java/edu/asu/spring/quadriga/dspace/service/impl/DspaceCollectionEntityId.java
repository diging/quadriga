package edu.asu.spring.quadriga.dspace.service.impl;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import edu.asu.spring.quadriga.dspace.service.IDspaceCollectionEntityId;

/**
 * The class used to store the collection id associated with the community  
 * 
 * @author Ram Kumar Kumaresan
 * 
 */
@XmlRootElement(name="collectionentityid")
public class DspaceCollectionEntityId implements IDspaceCollectionEntityId {

	private String id;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}
	
	public static class Adapter extends XmlAdapter<DspaceCollectionEntityId, IDspaceCollectionEntityId>
	{
		@Override
		public IDspaceCollectionEntityId unmarshal(DspaceCollectionEntityId v) throws Exception {
			return v;
		}

		@Override
		public DspaceCollectionEntityId marshal(IDspaceCollectionEntityId v) throws Exception {
			return (DspaceCollectionEntityId)v;
		}
	}
	
}
