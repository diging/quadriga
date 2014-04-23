package edu.asu.spring.quadriga.dspace.service;


public interface IDspaceItemSubmitter {

	public abstract void setType(int type);

	public abstract int getType();

	public abstract void setFullname(String fullname);

	public abstract String getFullname();

	public abstract void setLastname(String lastname);

	public abstract String getLastname();

	public abstract void setFirstname(String firstname);

	public abstract String getFirstname();

	public abstract void setId(String id);

	public abstract String getId();

	
}