package edu.asu.spring.quadriga.dspace.service.impl;


import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;

import edu.asu.spring.quadriga.dspace.service.IDspaceCommunity;
import edu.asu.spring.quadriga.dspace.service.IDspacecCommunities;

/**
 * The class representation of the communities list got from Dspace repostiory.
 * 
 * @author Ram Kumar Kumaresan
 * 
 */
@XmlRootElement(name="communities_collection")
public class DspaceCommunities implements IDspacecCommunities{


	private List<IDspaceCommunity> communities;

	@XmlElementRefs({@XmlElementRef(type=DspaceCommunity.class)}) 
	@Override
	public List<IDspaceCommunity> getCommunities() {
		return communities;
	}

	@Override
	public void setCommunities(List<IDspaceCommunity> communities) {
		this.communities = communities;
	}
}
