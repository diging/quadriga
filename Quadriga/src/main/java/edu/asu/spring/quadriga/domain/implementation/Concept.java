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

	private String name;
	private String pos;
	private String description;
	private String id;
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.IConcept#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
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
	public void setName(String name) {
		// TODO Auto-generated method stub
		this.name=name;
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

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setID(String id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

}
