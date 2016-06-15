package edu.asu.spring.quadriga.domain.impl;

import edu.asu.spring.quadriga.domain.IConceptStats;

/**
 * This class represents one concept with limited properties. It provides
 * existing properties of concept in terms of concept id, description and lemma
 * of the concept. Apart from that, it also provides count of concept
 * appears in a certain text.
 *
 * @author ajaymodi
 *
 */

public class ConceptStats implements IConceptStats {

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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((conceptId == null) ? 0 : conceptId.hashCode());
        return result;
    }
    
    public void incrementCount() {
        this.count = this.getCount() + 1;
    }

}
