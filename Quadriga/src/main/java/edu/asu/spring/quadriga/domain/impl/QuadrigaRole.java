package edu.asu.spring.quadriga.domain.impl;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;

/**
 * @description : QuadrigaRole class describing the properties 
 *                of a QuadrigaRole object
 * 
 * @author      : Kiran Kumar Batna
 * @author 		: Ram Kumar Kumaresan
 */
public class QuadrigaRole implements IQuadrigaRole 
{
	private String DBid;
	private String id;
	private String name;
	private String displayName;
	private String description;
	private boolean selectable;

	@Override
	public String getDBid() {
		return DBid;
	}

	@Override
	public void setDBid(String dBid) {
		DBid = dBid;
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
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public void setDisplayName(String displayName) 
	{
		this.displayName = displayName;
		
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((DBid == null) ? 0 : DBid.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((displayName == null) ? 0 : displayName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		QuadrigaRole other = (QuadrigaRole) obj;
		if (DBid == null) {
			if (other.DBid != null)
				return false;
		} else if (!DBid.equals(other.DBid))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (displayName == null) {
			if (other.displayName != null)
				return false;
		} else if (!displayName.equals(other.displayName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

    @Override
    public boolean isSelectable() {
        return selectable;
    }

    @Override
    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }


}
