package edu.asu.spring.quadriga.domain.implementation;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;

/**
 * @description : QuadrigaRole class describing the properties 
 *                of a QuadrigaRole object
 * 
 * @author      : Kiran Kumar Batna
 *
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
	public boolean compareQuadrigaRole(IQuadrigaRole role)
	{
		if(this.DBid.equalsIgnoreCase(role.getDBid()) && this.id.equalsIgnoreCase(role.getId()) 
				&& this.description.equalsIgnoreCase(role.getDescription()) && this.name.equalsIgnoreCase(role.getName()))
		{
			return true;
		}
		return false;
	}
	

}
