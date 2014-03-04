package edu.asu.spring.quadriga.dspace.service.impl;

import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import edu.asu.spring.quadriga.dspace.service.IDspaceMetadataCollection;
import edu.asu.spring.quadriga.dspace.service.IDspaceMetadataCollectionEntity;

/**
 * This class is used by JAXB to unmarshall the xml stream from Dspace.
 * A wrapper class for the list of collections.
 * 
 * @author Ram Kumar Kumaresan
 *
 */
@XmlRootElement(name="collections")
public class DspaceMetadataCollection implements IDspaceMetadataCollection {

	private List<IDspaceMetadataCollectionEntity> collectionEntitites;

	@XmlElementRefs({@XmlElementRef(type=DspaceMetadataCollectionEntity.class)})
	@Override
	public List<IDspaceMetadataCollectionEntity> getCollectionEntitites() {
		return collectionEntitites;
	}

	@Override
	public void setCollectionEntitites(List<IDspaceMetadataCollectionEntity> collectionEntitites) {
		this.collectionEntitites = collectionEntitites;
	}
	
	public static class Adapter extends XmlAdapter<DspaceMetadataCollection, IDspaceMetadataCollection>
	{
		@Override
		public IDspaceMetadataCollection unmarshal(DspaceMetadataCollection v) throws Exception {
			return v;
		}

		@Override
		public DspaceMetadataCollection marshal(IDspaceMetadataCollection v) throws Exception {
			return (DspaceMetadataCollection)v;
		}
	}
	
}
