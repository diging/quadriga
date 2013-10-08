package edu.asu.spring.quadriga.profile;

import java.util.List;
import java.util.Set;

public interface IServiceRegistry {
	
	public abstract IService getServiceObject(String serviceId);
	
	public abstract List<IService> getServiceObjectList();
	
	public abstract List<String> getServiceIds();
	
	public abstract List<String> getServiceNames();

}
