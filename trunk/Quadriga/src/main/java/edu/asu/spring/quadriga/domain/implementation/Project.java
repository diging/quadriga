package edu.asu.spring.quadriga.domain.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.enums.ENetworkAccessibility;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;

/**
 * @description : Project class describing the properties 
 *                of a Project object
 * 
 * @author      : Kiran Kumar Batna
 *
 */
@Service
public class Project implements IProject 
{
	
	private String name;
	private String description;
	private String id;
	private int internalid;
	private IUser owner;
	private List<ICollaborator> collaborators;
    private EProjectAccessibility projectAccess;
    private ENetworkAccessibility networksDefaultAccess;

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.IProject#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.IProject#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.IProject#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.IProject#setDescription(java.lang.String)
	 */
	@Override
	public void setDescription(String description) {
		this.description = description;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.IProject#getId()
	 */
	@Override
	public String getId() {
		return id;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.IProject#setId(java.lang.String)
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.IProject#getInternalid()
	 */
	@Override
	public int getInternalid() {
		return internalid;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.IProject#setInternalid(java.lang.String)
	 */
	@Override
	public void setInternalid(int internalid) {
		this.internalid = internalid;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.IProject#getOwner()
	 */
	@Override
	public IUser getOwner() {
		return owner;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.IProject#setOwner(edu.asu.spring.quadriga.domain.IUser)
	 */
	@Override
	public void setOwner(IUser owner) {
		this.owner = owner;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.IProject#getCollaborators()
	 */
	@Override
	public List<ICollaborator> getCollaborators() {
		return collaborators;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.IProject#setCollaborators(java.util.List)
	 */
	@Override
	public void setCollaborators(List<ICollaborator> collaborators) {
		this.collaborators = collaborators;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.IProject#getProjectAccess()
	 */
	@Override
	public EProjectAccessibility getProjectAccess() {
		return projectAccess;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.IProject#setProjectAccess(edu.asu.spring.quadriga.domain.enums.EProjectAccessibility)
	 */
	@Override
	public void setProjectAccess(EProjectAccessibility projectAccess) {
		this.projectAccess = projectAccess;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.IProject#getNetworksDefaultAccess()
	 */
	@Override
	public ENetworkAccessibility getNetworksDefaultAccess() {
		return networksDefaultAccess;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.IProject#setNetworksDefaultAccess(edu.asu.spring.quadriga.domain.enums.EDefaultAccessibility)
	 */
	@Override
	public void setNetworksDefaultAccess(ENetworkAccessibility networksDefaultAccess) {
		this.networksDefaultAccess = networksDefaultAccess;
	}

    
    
    
}
