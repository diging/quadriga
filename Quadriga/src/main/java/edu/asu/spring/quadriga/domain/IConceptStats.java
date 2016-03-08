package edu.asu.spring.quadriga.domain;

import java.util.List;

/**
 * Interface to implement user profile
 *
 */
public interface IConceptStats {
	
	public abstract String getConceptId();

	public abstract void setConceptId(String conceptId);

	public abstract void setDescription(String discription);

	public abstract String getDescription();

	public abstract String getLemma();

	public abstract void setLemma(String lemma);

	public abstract Integer getCount();

	public abstract void setCount(Integer count);

	public abstract List<IConceptStats> getSortedList(List<IConceptStats> csList);
	
}
