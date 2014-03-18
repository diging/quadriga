package edu.asu.spring.quadriga.profile;

import java.util.Map;

/**
 * this interface is used to register all the service classes which implements IService interface
 * 
 * methods:
 * 
 * getServiceObject()		returns service object associated with serviceId
 * getServiceNameIdMap()	returns Id, name map as key, value pair
 * 
 * @author rohit pendbhaje
 *
 */
public interface IServiceRegistry {
	
	public abstract IService getServiceObject(String serviceId);
	
	public abstract Map<String,String> getServiceNameIdMap();

}
