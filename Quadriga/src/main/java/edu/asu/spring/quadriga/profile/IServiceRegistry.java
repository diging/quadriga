package edu.asu.spring.quadriga.profile;

public interface IServiceRegistry {
	
	public abstract IService getServiceObject(String serviceId);

}
