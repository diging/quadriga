package edu.asu.spring.quadriga.domain.implementation;

import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IUser;

/**
 * @description : ConceptCollection class describing the properties 
 *                of a ConceptCollection object
 * 
 * @author      : Kiran
 *
 */
public class ConceptCollection implements IConceptCollection 
{
	private String name;
	private String description;
	private String id;
	private IUser owner;
	private List<ICollaborator> collaborators;
	
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
	public String getId() {
		return id;
	}
	@Override
	public void setId(String id) {
		this.id = id;
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

}
