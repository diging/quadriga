package edu.asu.spring.quadriga.dspace.service;

import java.util.List;

public interface IDspaceMetadataCommunity {

	public abstract List<IDspaceMetadataCommunityEntity> getCommunityEntitites();

	public abstract  void setCommunityEntitites(List<IDspaceMetadataCommunityEntity> communityEntitites);


}