package edu.asu.spring.quadriga.domain;

import java.util.List;

import edu.asu.spring.quadriga.domain.implementation.Collection;

public interface ICollections {

	public abstract List<ICollection> getCollections();

	public abstract void setCollections(List<ICollection> collections);

}
