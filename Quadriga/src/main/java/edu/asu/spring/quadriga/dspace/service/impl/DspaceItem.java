package edu.asu.spring.quadriga.dspace.service.impl;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import edu.asu.spring.quadriga.dspace.service.IDspaceBitStream;
import edu.asu.spring.quadriga.dspace.service.IDspaceItem;

/**
 * The class representation of the Collection got from Dspace repostiory.
 * 
 * @author Ram Kumar Kumaresan
 */


@XmlRootElement(name="itementity")
public class DspaceItem implements IDspaceItem{

	private String id;
	private String name;
	private String handle;
	private IDspaceBitStream bitstreams;
	
	
	@XmlElementRefs({@XmlElementRef(type=DspaceBitStream.class)})
	@Override
	public IDspaceBitStream getBitstreams() {
		return bitstreams;
	}

	@Override
	public void setBitstreams(IDspaceBitStream bitstreams) {
		this.bitstreams = bitstreams;
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
