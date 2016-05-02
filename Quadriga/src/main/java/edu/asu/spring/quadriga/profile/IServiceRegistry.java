package edu.asu.spring.quadriga.profile;

import java.util.Map;

/**
 * this interface has methods which deals with the service class object and its attributes
 * 
 * @author rohit pendbhaje
 *
 */
public interface IServiceRegistry {
	
	/**
	 * this method provides object of service class based on the provided serviceid
	 * 
	 * @param serviceId	 service id of the class for which user needs object
	 * @return           object of the service class
	 */
	
	public abstract IService getServiceObject(String serviceId);
	
	/**
	 * this method creates hashmap in which service name as key and service id as value for all services
	 * under IService interface
	 * 
	 * @return	map of serviceName and serviceId
	 */
	public abstract Map<String,String> getServiceIdNameMap();

}
