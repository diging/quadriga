package edu.asu.spring.quadriga.dspace.service;

import java.util.List;

import edu.asu.spring.quadriga.domain.ICommunity;

public interface IDspaceManager {

	public abstract List<ICommunity> getAllCommunities(String sUserName, String sPassword);

	public abstract List<IDspaceCollection> getAllCollections(String sUserName, String sPassword,String sCommunityTitle);

	public abstract String getCollectionName(String sUserName, String sPassword,String sCollectionId);

}
