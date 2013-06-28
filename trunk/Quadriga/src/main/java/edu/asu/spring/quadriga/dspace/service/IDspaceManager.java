package edu.asu.spring.quadriga.dspace.service;

import java.util.List;

import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.domain.IItem;

public interface IDspaceManager {

	public abstract List<ICommunity> getAllCommunities(String sUserName, String sPassword);

	public abstract List<ICollection> getAllCollections(String sUserName, String sPassword,String sCommunityId);

	public abstract String getCommunityName(String sCommunityId);
	
	public abstract String getCollectionName(String sCollectionId);
	
	public abstract ICollection getCollection(String sCollectionId);
	
	public abstract List<IItem> getAllItems(String sCollectionId);

	public abstract String getCommunityId(String sCollectionId);

	public abstract List<IBitStream> getAllBitStreams(String sUserName, String sPassword, String sCollectionId, String sItemId);

	public abstract String getItemName(String sCollectionId, String sItemId);

	public abstract IBitStream getBitStreamName(String sCollectionId, String sItemId,
			String sBitStreamId);

}
