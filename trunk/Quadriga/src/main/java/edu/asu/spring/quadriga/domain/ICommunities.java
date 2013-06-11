package edu.asu.spring.quadriga.domain;

import java.util.List;

public interface ICommunities {

	public abstract List<ICommunity> getCommunities();

	public abstract void setCommunities(List<ICommunity> communities);

}
