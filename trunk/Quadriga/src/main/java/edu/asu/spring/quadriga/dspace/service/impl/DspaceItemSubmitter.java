package edu.asu.spring.quadriga.dspace.service.impl;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import edu.asu.spring.quadriga.dspace.service.IDspaceItemSubmitter;

/**
 * The class representation of the Submitter details got from Dspace repostiory.
 * 
 * @author Ram Kumar Kumaresan
 */
@XmlRootElement(name="submitter")
public class DspaceItemSubmitter implements IDspaceItemSubmitter{

	private String id;
	private String firstname;
	private String lastname;
	private String fullname;
	private int type;
	
	
	@Override
	public String getId() {
		return id;
	}


	@Override
	public void setId(String id) {
		this.id = id;
	}


	@Override
	public String getFirstname() {
		return firstname;
	}


	@Override
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	@Override
	public String getLastname() {
		return lastname;
	}


	@Override
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	@Override
	public String getFullname() {
		return fullname;
	}


	@Override
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}


	@Override
	public int getType() {
		return type;
	}


	@Override
	public void setType(int type) {
		this.type = type;
	}


	public static class Adapter extends XmlAdapter<DspaceItemSubmitter, IDspaceItemSubmitter>
	{
		@Override
		public IDspaceItemSubmitter unmarshal(DspaceItemSubmitter v) throws Exception {
			return v;
		}

		@Override
		public DspaceItemSubmitter marshal(IDspaceItemSubmitter v) throws Exception {
			return (DspaceItemSubmitter)v;
		}		
	}
	
}
