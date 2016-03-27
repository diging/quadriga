package edu.asu.spring.quadriga.dspace.service.impl;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import edu.asu.spring.quadriga.dspace.service.IDspaceMetadataBundleEntity;
import edu.asu.spring.quadriga.dspace.service.IDspaceMetadataBundles;

/**
 * This class is used by JAXB to unmarshall the xml stream from Dspace.
 * A wrapper class for the list of bitstreams.
 * 
 * @author Ram Kumar Kumaresan
 */
@XmlRootElement(name="bundles")
public class DspaceMetadataBundles implements IDspaceMetadataBundles {
	
	private IDspaceMetadataBundleEntity bundleEntity;

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.dspace.service.impl.IDspaceMetadataBundles#getBundleEntity()
	 */
	@Override
	@XmlElementRefs({@XmlElementRef(type=DspaceMetadataBundleEntity.class)})
	public IDspaceMetadataBundleEntity getBundleEntity() {
		return bundleEntity;
	}

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.dspace.service.impl.IDspaceMetadataBundles#setBundleEntity(edu.asu.spring.quadriga.dspace.service.impl.DspaceMetadataBundleEntity)
	 */
	@Override
	public void setBundleEntity(IDspaceMetadataBundleEntity bundleEntity) {
		this.bundleEntity = bundleEntity;
	}
	
	public static class Adapter extends XmlAdapter<DspaceMetadataBundles, IDspaceMetadataBundles>
	{

		@Override
		public IDspaceMetadataBundles unmarshal(DspaceMetadataBundles v) throws Exception {
			return v;
		}

		@Override
		public DspaceMetadataBundles marshal(IDspaceMetadataBundles v) throws Exception {
			return (DspaceMetadataBundles)v;
		}
		
	}

	
}
