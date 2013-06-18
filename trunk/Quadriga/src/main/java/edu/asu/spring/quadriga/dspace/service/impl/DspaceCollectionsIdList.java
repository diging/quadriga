package edu.asu.spring.quadriga.dspace.service.impl;


import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import edu.asu.spring.quadriga.dspace.service.IDspaceCollectionEntityId;
import edu.asu.spring.quadriga.dspace.service.IDspaceCollectionsIdList;

/**
 * The class representation of the collections list got from Dspace repostiory.
 * 
 * @author Ram Kumar Kumaresan
 * 
 */
@XmlRootElement(name="collections")
public class DspaceCollectionsIdList implements IDspaceCollectionsIdList{

	private List<IDspaceCollectionEntityId> collectionid;
	
	@XmlElementRefs({@XmlElementRef(type=DspaceCollectionEntityId.class)})
	@Override
	public List<IDspaceCollectionEntityId> getCollectionid() {
		return collectionid;
	}

	@Override
	public void setCollectionid(List<IDspaceCollectionEntityId> collectionid) {
		this.collectionid = collectionid;
	}
	
	public static class Adapter extends XmlAdapter<DspaceCollectionsIdList, IDspaceCollectionsIdList>
	{
		@Override
		public IDspaceCollectionsIdList unmarshal(DspaceCollectionsIdList v) throws Exception {
			return v;
		}

		@Override
		public DspaceCollectionsIdList marshal(IDspaceCollectionsIdList v) throws Exception {
			return (DspaceCollectionsIdList)v;
		}
	}
}
