package edu.asu.spring.quadriga.domain.implementation;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;

/**
 * @description : QuadrigaRole class describing the properties 
 *                of a QuadrigaRole object
 * 
 * @author      : Kiran
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
	
	

}
