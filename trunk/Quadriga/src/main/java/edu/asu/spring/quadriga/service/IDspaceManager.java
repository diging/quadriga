package edu.asu.spring.quadriga.service;

import java.util.List;

import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;

public interface IDspaceManager {

	public abstract List<ICommunity> getAllCommunities();

	public abstract List<ICollection> getAllCollections(String sCommunityTitle);

}
