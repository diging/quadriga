package edu.asu.spring.quadriga.domain;

public interface INetworkVersions {

	public abstract String getNetworkname();

	public abstract void setNetworkname(String networkname);

	public abstract int getVersionnumber();

	public abstract void setVersionnumber(int versionnumber);

	public abstract String getStatus();

	public abstract void setStatus(String status);

	public abstract String getAssigneduser();

	public abstract void setAssigneduser(String assigneduser);

}