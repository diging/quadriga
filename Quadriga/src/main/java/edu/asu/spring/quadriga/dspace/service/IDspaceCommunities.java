package edu.asu.spring.quadriga.dspace.service;

import java.util.List;

/**
 * The interface that represents the list of communities fetched from, Dspace
 * 
 * @author Ram Kumar Kumaresan
 *
 */
public interface IDspaceCommunities {

	public abstract List<IDspaceCommunity> getCommunities();

	public abstract void setCommunities(List<IDspaceCommunity> communities);

}
