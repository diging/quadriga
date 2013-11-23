/**
 * 
 */
package edu.asu.spring.quadriga.domain.implementation;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IConcept;

/**
 * uses to create a concept and also fill in a object upon downloading from the concept power
 * @author satyaswaroop boddu
 *
 */
@Service
public class Concept implements IConcept {

	/**
	 * Created equals and hashcode methods to compare two concepts based on URI i.e is id.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Concept other = (Concept) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	private String id;
	private String pos;
	private String description;
	
	private String lemma;
	
	public String getLemma() {
		return lemma;
	}

	public void setLemma(String lemma) {
		this.lemma = lemma;
	}

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.IConcept#getName()
	 */
	@Override
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.IConcept#getPOS()
	 */
	@Override
	public String getPos() {
		return pos;
	}

	
	

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.IConcept#getDiscription()
	 */
	@Override
	public String getDescription() {
		return description;
	}

	
	@Override
	public void setId(String name) {
		this.id=name;
	}

	@Override
	public void setPos(String pos) {
		this.pos=pos;
	}

	@Override
	public void setDescription(String description) {
		this.description=description;
	}

	

	
}
