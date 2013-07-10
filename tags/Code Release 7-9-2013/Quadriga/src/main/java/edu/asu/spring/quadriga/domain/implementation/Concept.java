/**
 * 
 */
package edu.asu.spring.quadriga.domain.implementation;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IConcept;

/**
 * @author satyaswaroop
 *
 */
@Service
public class Concept implements IConcept {

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
		// TODO Auto-generated method stub
		return id;
	}

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.IConcept#getPOS()
	 */
	@Override
	public String getPos() {
		// TODO Auto-generated method stub
		return pos;
	}

	
	

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.IConcept#getDiscription()
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return description;
	}

	
	@Override
	public void setId(String name) {
		// TODO Auto-generated method stub
		this.id=name;
	}

	@Override
	public void setPos(String pos) {
		// TODO Auto-generated method stub
		this.pos=pos;
	}

	@Override
	public void setDescription(String description) {
		// TODO Auto-generated method stub
		this.description=description;
	}

	

	
}
