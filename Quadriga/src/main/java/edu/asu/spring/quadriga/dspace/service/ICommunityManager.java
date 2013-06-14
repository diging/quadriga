package edu.asu.spring.quadriga.dspace.service;

import java.util.List;

import edu.asu.spring.quadriga.domain.ICommunity;

public interface ICommunityManager {

	public abstract List<ICommunity> getAllCommunities(String sUserName, String sPassword);
}