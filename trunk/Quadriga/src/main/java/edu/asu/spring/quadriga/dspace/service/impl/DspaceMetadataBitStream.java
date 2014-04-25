package edu.asu.spring.quadriga.dspace.service.impl;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import edu.asu.spring.quadriga.dspace.service.IDspaceMetadataBitStream;
import edu.asu.spring.quadriga.dspace.service.IDspaceMetadataBundles;

/**
 * This class is used by JAXB to unmarshall the xml stream from Dspace.
 * It will store the details specific to a bitstream.
 * 
 * @author Ram Kumar Kumaresan
 */
@XmlRootElement(name="bitstream")
public class DspaceMetadataBitStream implements IDspaceMetadataBitStream{

	private String id;
	private String name;
	private String size;	
	private String checkSum;
//	private IDspaceMetadataBundles bundles;

	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.dspace.service.impl.IDspaceMetadataBitstream#getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.dspace.service.impl.IDspaceMetadataBitstream#setId(java.lang.String)
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.dspace.service.impl.IDspaceMetadataBitstream#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.dspace.service.impl.IDspaceMetadataBitstream#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.dspace.service.impl.IDspaceMetadataBitstream#getSize()
	 */
	@Override
	public String getSize() {
		return size;
	}

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.dspace.service.impl.IDspaceMetadataBitstream#setSize(java.lang.String)
	 */
	@Override
	public void setSize(String size) {
		this.size = size;
	}

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.dspace.service.impl.IDspaceMetadataBitstream#getCheckSum()
	 */
	@Override
	public String getCheckSum() {
		return checkSum;
	}

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.dspace.service.impl.IDspaceMetadataBitstream#setCheckSum(java.lang.String)
	 */
	@Override
	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}

//	@XmlElementRefs({@XmlElementRef(type=DspaceMetadataBundles.class)})
//	@Override
//	public IDspaceMetadataBundles getBundles() {
//		return bundles;
//	}
//
//	@Override
//	public void setBundles(IDspaceMetadataBundles bundles) {
//		this.bundles = bundles;
//	}
	
	
	public static class Adapter extends XmlAdapter<DspaceMetadataBitStream, IDspaceMetadataBitStream>
	{

		@Override
		public IDspaceMetadataBitStream unmarshal(DspaceMetadataBitStream v) throws Exception {
			return v;
		}

		@Override
		public DspaceMetadataBitStream marshal(IDspaceMetadataBitStream v) throws Exception {
			return (DspaceMetadataBitStream)v;
		}
		
	}
	
}
