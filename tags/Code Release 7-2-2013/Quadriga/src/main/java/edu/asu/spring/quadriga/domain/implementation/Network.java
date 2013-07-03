package edu.asu.spring.quadriga.domain.implementation;

import java.util.Date;
import java.util.List;

import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.enums.ENetworkAccessibility;

/**
 * @description : Network class describing the properties 
 *                of a Network object
 * 
 * @author      : Kiran Kumar Batna
 *
 */
public class Network implements INetwork 
{
	private IUser creator;
	private Date creationTime;
	private String textUrl;
	private List<String> appellationIds;
	private List<String> relationIds;
	private ENetworkAccessibility networksAccess;
	
	@Override
	public List<String> getAppellationIds() {
		return appellationIds;
	}
	@Override
	public void setAppellationIds(List<String> appellationIds) {
		this.appellationIds = appellationIds;
	}
	@Override
	public List<String> getRelationIds() {
		return relationIds;
	}
	@Override
	public void setRelationIds(List<String> relationIds) {
		this.relationIds = relationIds;
	}
	@Override
	public ENetworkAccessibility getNetworksAccess() {
		return networksAccess;
	}
	@Override
	public void setNetworksAccess(ENetworkAccessibility networksAccess) {
		this.networksAccess = networksAccess;
	}
	
	@Override
	public IUser getCreator() {
		return creator;
	}
	@Override
	public void setCreator(IUser creator) {
		this.creator = creator;
	}

	
	@Override
	public Date getCreationTime() {
		return creationTime;
	}
	@Override
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	@Override
	public String getTextUrl() {
		return textUrl;
	}
	@Override
	public void setTextUrl(String textUrl) {
		this.textUrl = textUrl;
	}
	
	

}
