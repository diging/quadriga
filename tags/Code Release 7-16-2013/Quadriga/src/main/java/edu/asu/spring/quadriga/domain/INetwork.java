package edu.asu.spring.quadriga.domain;

import java.util.Date;
import java.util.List;

import edu.asu.spring.quadriga.domain.enums.ENetworkAccessibility;

/**
 * @description   : interface to implement Network class.
 * 
 * @author        : Kiran Kumar Batna
 *
 */
public interface INetwork 
{

	public abstract void setTextUrl(String textUrl);

	public abstract String getTextUrl();

	public abstract void setCreationTime(Date creationTime);

	public abstract Date getCreationTime();

	public abstract void setCreator(IUser creator);

	public abstract IUser getCreator();

	public abstract void setNetworksAccess(ENetworkAccessibility networksAccess);

	public abstract ENetworkAccessibility getNetworksAccess();

	public abstract void setRelationIds(List<String> relationIds);

	public abstract List<String> getRelationIds();

	public abstract void setAppellationIds(List<String> appellationIds);

	public abstract List<String> getAppellationIds();


}
