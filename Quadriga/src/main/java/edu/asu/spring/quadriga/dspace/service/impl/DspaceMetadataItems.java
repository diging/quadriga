package edu.asu.spring.quadriga.dspace.service.impl;

import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import edu.asu.spring.quadriga.dspace.service.IDspaceMetadataItemEntity;
import edu.asu.spring.quadriga.dspace.service.IDspaceMetadataItems;

/**
 * This class is used by JAXB to unmarshall the xml stream from Dspace.
 * Wrapper class for the list of items.
 * 
 * @author Ram Kumar Kumaresan
 *
 */
@XmlRootElement(name="items")
public class DspaceMetadataItems implements IDspaceMetadataItems {

	//List of item entities that the bitstream is a part of.
	private List<IDspaceMetadataItemEntity> itementities;

	@XmlElementRefs({@XmlElementRef(type=DspaceMetadataItemEntity.class)})
	@Override
	public List<IDspaceMetadataItemEntity> getItementities() {
		return itementities;
	}

	@Override
	public void setItementities(List<IDspaceMetadataItemEntity> itementities) {
		this.itementities = itementities;
	}

	public static class Adapter extends XmlAdapter<DspaceMetadataItems, IDspaceMetadataItems>
	{

		@Override
		public IDspaceMetadataItems unmarshal(DspaceMetadataItems v) throws Exception {
			return v;
		}

		@Override
		public DspaceMetadataItems marshal(IDspaceMetadataItems v) throws Exception {
			return (DspaceMetadataItems)v;
		}
	}
	
}
