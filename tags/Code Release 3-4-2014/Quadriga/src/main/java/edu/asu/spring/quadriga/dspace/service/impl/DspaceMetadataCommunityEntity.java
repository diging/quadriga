package edu.asu.spring.quadriga.dspace.service.impl;

import javax.xml.bind.annotation.XmlRootElement;

import edu.asu.spring.quadriga.dspace.service.IDspaceMetadataCommunityEntity;

/**
 * This class is used by JAXB to unmarshall the xml stream from Dspace.
 * Class contains the id of community.
 * 
 * @author Ram Kumar Kumaresan
 *
 */
@XmlRootElement(name="communityentityid")
public class DspaceMetadataCommunityEntity implements IDspaceMetadataCommunityEntity {

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
