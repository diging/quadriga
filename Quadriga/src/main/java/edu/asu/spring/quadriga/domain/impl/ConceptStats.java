package edu.asu.spring.quadriga.domain.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IConceptStats;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollectionConcepts;

@Service
public class ConceptStats implements IConceptStats
{

	private String conceptId;
	private String description;
	private String lemma;
	private Integer count;
	
	public ConceptStats(String conceptId, String description, String lemma,
	        Integer count) {
		this.conceptId = conceptId;
		this.description = description;
		this.lemma = lemma;
		this.count = count;
	}

	public ConceptStats() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Integer getCount() {
		return count;
	}

	@Override
	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String getConceptId() {
		return conceptId;
	}

	@Override
	public void setConceptId(String conceptId) {
		this.conceptId = conceptId;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getLemma() {
		return lemma;
	}

	@Override
	public void setLemma(String lemma) {
		this.lemma = lemma;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConceptStats other = (ConceptStats) obj;
		if (conceptId == null) {
			if (other.conceptId != null)
				return false;
		} else if (!conceptId.equals(other.conceptId))
			return false;
		return true;
	}

	@Override
	public List<IConceptStats> getSortedList(List<IConceptStats> csList) {
		Collections.sort(csList, new Comparator<IConceptStats>() {
			public int compare(IConceptStats o1, IConceptStats o2) {
				if (o1.getCount() == o2.getCount())
					return 0;
				return o1.getCount() < o2.getCount() ? -1 : 1;
			}
		});
		return csList;
	}
		
}
