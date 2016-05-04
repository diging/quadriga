package edu.asu.spring.quadriga.dspace.service.impl;

import javax.xml.bind.annotation.XmlRootElement;

import edu.asu.spring.quadriga.dspace.service.IDspaceMetadataCollectionEntity;

/**
 * This class is used by JAXB to unmarshall the xml stream from Dspace.
 * Class will contain the collection id.
 * 
 * @author Ram Kumar Kumaresan
 *
 */
@XmlRootElement(name="collectionentityid")
public class DspaceMetadataCollectionEntity implements IDspaceMetadataCollectionEntity {

	private String id;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}
	
}
