package edu.asu.spring.quadriga.domain.implementation;


import java.util.List;

import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;

/**
 * The class representation of the communities list got from Dspace repostiory.
 * 
 * @author Ram Kumar Kumaresan
 * 
 */
public class Communities{


	private List<Community> communities;

	public List<Community> getCommunities() {
		return communities;
	}

	public void setCommunities(List<Community> communities) {
		this.communities = communities;
	}
	


}
