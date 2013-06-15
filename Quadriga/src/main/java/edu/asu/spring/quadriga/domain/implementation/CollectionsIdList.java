package edu.asu.spring.quadriga.domain.implementation;


import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICollectionEntityId;
import edu.asu.spring.quadriga.domain.ICollectionsIdList;

/**
 * The class representation of the collections list got from Dspace repostiory.
 * 
 * @author Ram Kumar Kumaresan
 * 
 */
@XmlRootElement(name="collections")
public class CollectionsIdList implements ICollectionsIdList{

	private List<ICollectionEntityId> collectionid;
	
	@XmlElementRefs({@XmlElementRef(type=CollectionEntityId.class)})
	@Override
	public List<ICollectionEntityId> getCollectionid() {
		return collectionid;
	}

	@Override
	public void setCollectionid(List<ICollectionEntityId> collectionid) {
		this.collectionid = collectionid;
	}
	
	public static class Adapter extends XmlAdapter<CollectionsIdList, ICollectionsIdList>
	{
		@Override
		public ICollectionsIdList unmarshal(CollectionsIdList v) throws Exception {
			return v;
		}

		@Override
		public CollectionsIdList marshal(ICollectionsIdList v) throws Exception {
			return (CollectionsIdList)v;
		}
	}
}
