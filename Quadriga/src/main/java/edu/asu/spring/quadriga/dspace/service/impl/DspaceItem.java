package edu.asu.spring.quadriga.dspace.service.impl;

import javax.xml.bind.annotation.*;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import edu.asu.spring.quadriga.dspace.service.IDspaceItem;

/**
 * The class representation of the Collection got from Dspace repostiory.
 * 
 * @author Ram Kumar Kumaresan
 */


@XmlRootElement(name="itementity")
@XmlAccessorType(XmlAccessType.FIELD)
public class DspaceItem implements IDspaceItem{

	private String id;
	private String name;
	private String handle;
	
//	@XmlPath("items/itementity/bitstreams/bitstreamentityid/id/text()")
//    private List<String> byteids; 
	
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
	public String getHandle() {
		return handle;
	}
	
	@Override
	public void setHandle(String handle) {
		this.handle = handle;
	}
	
	public static class Adapter extends XmlAdapter<DspaceItem, IDspaceItem>
	{
		@Override
		public IDspaceItem unmarshal(DspaceItem v) throws Exception {
			return v;
		}

		@Override
		public DspaceItem marshal(IDspaceItem v) throws Exception {
			return (DspaceItem)v;
		}		
	}
	
}
