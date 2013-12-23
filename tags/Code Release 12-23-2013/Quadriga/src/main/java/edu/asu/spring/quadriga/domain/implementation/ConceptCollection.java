package edu.asu.spring.quadriga.domain.implementation;

import java.util.ArrayList;
import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IConcept;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IUser;

/**
 * @description : ConceptCollection class describing the properties 
 *                of a ConceptCollection object
 * 
 * @author      : Kiran Kumar Batna
 *
 */
public class ConceptCollection implements IConceptCollection 
{
	private String name;
	private String description;
	private String id;
	private IUser owner;
	private List<ICollaborator> collaborators;
	private List<IConcept> items = new ArrayList<IConcept>();
	
	@Override
	public String getId() {
		return id; 
	}
	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
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
	public IUser getOwner() {
		return owner;
	}
	@Override
	public void setOwner(IUser owner) {
		this.owner = owner;
	}
	@Override
	public List<ICollaborator> getCollaborators() {
		return collaborators;
	}
	@Override
	public void setCollaborators(List<ICollaborator> collaborators) {
		this.collaborators = collaborators;
	}
	@Override
	public List<IConcept> getItems() {
		return items;
	}
	
	@Override
	public void setItems(List<IConcept> concepts)
	{
		this.items = concepts;
	}
	
	@Override
	public void addItem(IConcept concept) {
		if(items.indexOf(concept)<0){
		items.add(concept);
		}
	}
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
		ConceptCollection other = (ConceptCollection) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	} 
	

}
