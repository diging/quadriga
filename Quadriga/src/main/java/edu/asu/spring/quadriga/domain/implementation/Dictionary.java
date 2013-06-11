package edu.asu.spring.quadriga.domain.implementation;

import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IUser;

/**
 * Dictionary class describing the properties 
 *                of a Dictionary object
 * 
 * @author      : Kiran Kumar Batna
 *
 */
public class Dictionary implements IDictionary 
{
	private String name;
	private String description;
	private String id;
	private IUser owner;
	private List<ICollaborator> collaborators;
	
	/**
	 * getter for variable name 
	 * 
	 * @return 	Return name
	 */
	
	@Override
	public String getName() {
		return name;
	}
	/**
	 * setter for variable name 
	 * @param name
	 * @return 	void
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * getter for variable description 
	 * 
	 * @return 	Return description
	 */
	@Override
	public String getDescription() {
		return description;
	}
	/**
	 * setter for variable description 
	 * @param description
	 * @return 	void
	 */
	@Override
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * getter for variable id 
	 * 
	 * @return 	Return id
	 */
	
	@Override
	public String getId() {
		return id;
	}
	
	/**
	 * setter for variable id 
	 * @param id
	 * @return 	void
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * getter for variable owner 
	 * 
	 * @return 	Return owner
	 */
	
	@Override
	public IUser getOwner() {
		return owner;
	}
	/**
	 * setter for variable owner 
	 * @param IUser
	 * @return 	void
	 */
	@Override
	public void setOwner(IUser owner) {
		this.owner = owner;
	}
	/**
	 * getter for variable collaborator 
	 * 
	 * @return 	Return collaborator
	 */
	@Override
	public List<ICollaborator> getCollaborators() {
		return collaborators;
	}
	
	/**
	 * setter for variable collaborator 
	 * @param ICollaborator
	 * @return 	void
	 */
	
	@Override
	public void setCollaborators(List<ICollaborator> ICollaborator) {
		this.collaborators = ICollaborator;
	}
}
