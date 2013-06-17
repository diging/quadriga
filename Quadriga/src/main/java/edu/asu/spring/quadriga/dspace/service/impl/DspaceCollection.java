package edu.asu.spring.quadriga.dspace.service.impl;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import edu.asu.spring.quadriga.domain.IItem;
import edu.asu.spring.quadriga.dspace.service.IDspaceCollection;
import edu.asu.spring.quadriga.dspace.service.IDspaceCommunity;

/**
 * The class representation of the Collection got from Dspace repostiory.
 * 
 * @author Ram Kumar Kumaresan
 */

@XmlRootElement(name="collections")
public class DspaceCollection implements IDspaceCollection{

	private String id;
	private String name;
	private String shortDescription;
	private String entityReference;
	private String handle;
	private String countItems;
	
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
	public String getShortDescription() {
		return shortDescription;
	}
	
	@Override
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	
	@Override
	public String getEntityReference() {
		return entityReference;
	}
	
	@Override
	public void setEntityReference(String entityReference) {
		this.entityReference = entityReference;
	}
	
	@Override
	public String getHandle() {
		return handle;
	}
	
	@Override
	public void setHandle(String handle) {
		this.handle = handle;
	}
	
	@Override
	public String getCountItems() {
		return countItems;
	}
	
	@Override
	public void setCountItems(String countItems) {
		this.countItems = countItems;
	}	
	
	public static class Adapter extends XmlAdapter<DspaceCollection, IDspaceCollection>
	{

		@Override
		public IDspaceCollection unmarshal(DspaceCollection v) throws Exception {
			return v;
		}

		@Override
		public DspaceCollection marshal(IDspaceCollection v) throws Exception {
			return (DspaceCollection)v;
		}
		
	}
	
}
