package edu.asu.spring.quadriga.dspace.service.impl;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import edu.asu.spring.quadriga.dspace.service.IDspaceMetadataBundleEntity;
import edu.asu.spring.quadriga.dspace.service.IDspaceMetadataItems;

/**
 * This class is used by JAXB to unmarshall the xml stream from Dspace.
 * It will store the list of items.
 * 
 * @author Ram Kumar Kumaresan
 */
@XmlRootElement(name="bundleentity")
public class DspaceMetadataBundleEntity implements IDspaceMetadataBundleEntity {
	
	private IDspaceMetadataItems items;
	private String name;

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.dspace.service.impl.IDspaceMetadataBundleEntity#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.dspace.service.impl.IDspaceMetadataBundleEntity#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	@XmlElementRefs({@XmlElementRef(type=DspaceMetadataItems.class)})
	@Override
	public IDspaceMetadataItems getItems() {
		return items;
	}

	@Override
	public void setItems(IDspaceMetadataItems items) {
		this.items = items;
	}
	
	public static class Adapter extends XmlAdapter<DspaceMetadataBundleEntity, IDspaceMetadataBundleEntity>
	{

		@Override
		public IDspaceMetadataBundleEntity unmarshal(DspaceMetadataBundleEntity v) throws Exception {
			return v;
		}

		@Override
		public DspaceMetadataBundleEntity marshal(IDspaceMetadataBundleEntity v) throws Exception {
			return (DspaceMetadataBundleEntity)v;
		}
	}
	
}
