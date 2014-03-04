package edu.asu.spring.quadriga.profile.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.profile.IService;
import edu.asu.spring.quadriga.profile.IServiceRegistry;

/***
 * @description this class registers all the services and creates one time objects 
 * 				for each service implemented by IService interface
 * 
 * @author rohit pendbhaje
 *
 */
@Service
public class ServiceRegistry implements IServiceRegistry {

	@Autowired
	private SearchResult searchResult;
	
	@Autowired
	private ApplicationContext ctx;

	
	private Map<String, IService> serviceMap;
	
	private Map<String, IService> newServiceMap;
	
	private Map<String,String> serviceIdNameMap;
		
/**
 * instantiates all the services under IService interface
 * 
 * @author rohit pendbhaje
 */
	@PostConstruct
	public void init(){
		
		newServiceMap = new HashMap<String, IService>();

		serviceMap = ctx.getBeansOfType(IService.class);
		
		Iterator<?> serviceIter = serviceMap.entrySet().iterator();
		
		while(serviceIter.hasNext()){
			
			@SuppressWarnings("rawtypes")
			Map.Entry mEntry = (Map.Entry) serviceIter.next();
			IService serviceObject = (IService) mEntry.getValue();
			newServiceMap.put(serviceObject.getServiceId(), serviceObject);
			
		}
		
	}

/**
 * used for returning service object
 * @param serviceId  id of the service of which object is required
 * @return service object
 * @author rohit pendbhaje
 * 
 */
	
	@Override
	public IService getServiceObject(String serviceId) {
		
		return newServiceMap.get(serviceId);
	}

/**
 * 
 * creates hashmap of name, id as key, value pair
 * @return map of id and name
 * @author rohit pendbhaje
 * 
 */
	@Override
	public Map<String, String> getServiceNameIdMap() {

		serviceIdNameMap = new HashMap<String,String>();
		Iterator<?> iterator = newServiceMap.entrySet().iterator();
		
		while(iterator.hasNext()){
			
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry)iterator.next();
			IService serviceObj = (IService)entry.getValue();
			serviceIdNameMap.put(serviceObj.getServiceId(), serviceObj.getName());
				
		}
		
		return serviceIdNameMap;
	}

	public Map<String, String> getServiceIdNameMap() {
		return serviceIdNameMap;
	}

	public void setServiceIdNameMap(Map<String, String> serviceIdNameMap) {
		this.serviceIdNameMap = serviceIdNameMap;
	} 

}
