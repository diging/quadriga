package edu.asu.spring.quadriga.domain.implementation;


import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import edu.asu.spring.quadriga.domain.ICommunities;
import edu.asu.spring.quadriga.domain.ICommunity;

/**
 * The class representation of the communities list got from Dspace repostiory.
 * 
 * @author Ram Kumar Kumaresan
 * 
 */
@XmlRootElement(name="communities_collection")
public class Communities implements ICommunities{


	private List<ICommunity> communities;

	@XmlElementRefs({@XmlElementRef(type=Community.class)}) 
	@Override
	public List<ICommunity> getCommunities() {
		return communities;
	}

	@Override
	public void setCommunities(List<ICommunity> communities) {
		this.communities = communities;
	}
}
