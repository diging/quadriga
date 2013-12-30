package edu.asu.spring.quadriga.profile;

import java.util.List;
import java.util.Map;

public interface IServiceRegistry {
	
	public abstract IService getServiceObject(String serviceId);
	
	public abstract Map<String,String> getServiceNameIdMap();

}
