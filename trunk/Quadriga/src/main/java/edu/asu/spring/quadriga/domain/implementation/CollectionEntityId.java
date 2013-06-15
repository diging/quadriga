package edu.asu.spring.quadriga.domain.implementation;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import edu.asu.spring.quadriga.domain.ICollectionEntityId;
import edu.asu.spring.quadriga.domain.ICollectionsIdList;

@XmlRootElement(name="collectionentityid")
public class CollectionEntityId implements ICollectionEntityId {

	private String id;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}
	
	public static class Adapter extends XmlAdapter<CollectionEntityId, ICollectionEntityId>
	{
		@Override
		public ICollectionEntityId unmarshal(CollectionEntityId v) throws Exception {
			return v;
		}

		@Override
		public CollectionEntityId marshal(ICollectionEntityId v) throws Exception {
			return (CollectionEntityId)v;
		}
	}
	
}
