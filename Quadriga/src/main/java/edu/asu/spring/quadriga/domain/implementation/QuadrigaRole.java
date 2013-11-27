package edu.asu.spring.quadriga.domain.implementation;

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
	private String description;

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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		
		if(obj == null)
			return false;
		
		if(this == obj)
			return true;
		
		if(this.getClass() != obj.getClass())
			return false;
		
		
		IQuadrigaRole role = (QuadrigaRole) obj;
		//Check values of DBid
		if(this.DBid == null)
		{
			if(role.getDBid() != null)
			{
				return false;
			}				
		}
		else if(!this.DBid.equals(role.getDBid()))
		{
			//One of the value is null and the other is not null (or) the values do not match
			return false;
		}

		//Check values of id
		if(this.id == null)
		{
			if(role.getId() != null)
			{
				return false;
			}
		}
		else if(!this.getId().equals(role.getId()))
		{
			//One of the value is null and the other is not null (or) the values do not match
			return false;
		}

		//Check if name is not null
		if(this.name == null)
		{
			if(role.getName() != null)
			{
				return false;
			}
		}
		else if(!this.name.equals(role.getName()))
		{
			//One of the value is null and the other is not null (or) the values do not match
			return false;
		}

		//Check if description is not null
		if(this.description == null)
		{
			if(role.getDescription() != null)
			{
				return false;
			}
		}
		else if(!this.description.equals(role.getDescription()))
		{
			//One of the value is null and the other is not null (or) the values do not match
			return false;
		}
		return true;
	}
}
