package edu.asu.spring.quadriga.dspace.service.impl;

import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import edu.asu.spring.quadriga.dspace.service.IDspaceMetadataCommunity;
import edu.asu.spring.quadriga.dspace.service.IDspaceMetadataCommunityEntity;

/**
 * This class is used by JAXB to unmarshall the xml stream from Dspace.
 * Wrapper class for the list of communities.
 * 
 * @author Ram Kumar Kumaresan
 *
 */
@XmlRootElement(name="communities")
public class DspaceMetadataCommunity implements IDspaceMetadataCommunity {
	
	private List<IDspaceMetadataCommunityEntity> communityEntitites;

	@XmlElementRefs({@XmlElementRef(type=DspaceMetadataCommunityEntity.class)})
	@Override
	public List<IDspaceMetadataCommunityEntity> getCommunityEntitites() {
		return communityEntitites;
	}

	@Override
	public void setCommunityEntitites(List<IDspaceMetadataCommunityEntity> communityEntitites) {
		this.communityEntitites = communityEntitites;
	}
	
	public static class Adapter extends XmlAdapter<DspaceMetadataCommunity, IDspaceMetadataCommunity>
	{
		@Override
		public IDspaceMetadataCommunity unmarshal(DspaceMetadataCommunity v) throws Exception {
			return v;
		}

		@Override
		public DspaceMetadataCommunity marshal(IDspaceMetadataCommunity v) throws Exception {
			return (DspaceMetadataCommunity)v;
		}
	}
}
