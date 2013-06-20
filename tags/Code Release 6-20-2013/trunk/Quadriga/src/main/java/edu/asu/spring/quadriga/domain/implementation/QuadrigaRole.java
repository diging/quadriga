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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean compareQuadrigaRole(IQuadrigaRole role)
	{
		if(role !=null && role instanceof IQuadrigaRole)
		{
			//Check if both DBid are not null
			if(this.DBid != null && role.getDBid() != null)
			{
				if(!(this.DBid.equals(role.getDBid())))
				{
					return false;
				}				
			}
			else if(!(this.DBid == null && role.getDBid() == null))
			{
				//One of the value is null and the other is not null
				return false;
			}

			//Check if id is not null
			if(this.id != null && role.getId() != null)
			{
				if(!(this.id.equals(role.getId())))
				{
					return false;
				}
			}
			else if(!(this.id == null && role.getId() == null))
			{
				//One of the value is null and the other is not null
				return false;
			}

			//Check if name is not null
			if(this.name != null && role.getName() != null)
			{
				if(!(this.name.equals(role.getName())))
				{
					return false;
				}
			}
			else if(!(this.name== null && role.getName() == null))
			{
				//One of the value is null and the other is not null
				return false;
			}

			//Check if description is not null
			if(this.description != null && role.getDescription() != null)
			{
				if(!(this.description.equals(role.getDescription())))
				{
					return false;
				}
			}
			else if(!(this.description == null && role.getDescription() == null))
			{
				//One of the value is null and the other is not null
				return false;
			}
			return true;
		}		
		return false;
	}


}
