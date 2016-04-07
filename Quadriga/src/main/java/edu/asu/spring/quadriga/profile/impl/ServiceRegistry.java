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
 * this class registers all the services and creates one time objects 
 * for each service which implements IService interface
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
	
	private Map<String, IService> servicesMap;
	private Map<String,String> serviceIdNameMap;
	
	
		
/**
 * instantiates all the services under IService interface
 * 
 * @author rohit pendbhaje
 */
	@PostConstruct
	public void init(){
		
		servicesMap = new HashMap<String, IService>();
		Map<String, IService> serviceCtxMap = ctx.getBeansOfType(IService.class);
		Iterator<?> serviceIter = serviceCtxMap.entrySet().iterator();
		
		while(serviceIter.hasNext()){
			
			@SuppressWarnings("rawtypes")
			Map.Entry mEntry = (Map.Entry) serviceIter.next();
			IService serviceObject = (IService) mEntry.getValue();
			servicesMap.put(serviceObject.getServiceId(), serviceObject);	
		}
	}

/**
 * this method takes serviceId as argument and returns the service object associated with it 
 * @param serviceId  id of the service of which object is required
 * @return service object
 * @author rohit pendbhaje
 * 
 */
	
	@Override
	public IService getServiceObject(String serviceId) {
		
		return servicesMap.get(serviceId);
	}

/**
 * 
 * returns hashmap of serviceId, serviceName as key, value pair
 * @return hashmap of serviceId and serviceName
 * @author rohit pendbhaje
 * 
 */
	@Override
	public Map<String, String> getServiceIdNameMap() {

		serviceIdNameMap = new HashMap<String,String>();
		Iterator<?> iterator = servicesMap.entrySet().iterator();
		
		while(iterator.hasNext()){
			
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry)iterator.next();
			IService serviceObj = (IService)entry.getValue();
			serviceIdNameMap.put(serviceObj.getServiceId(), serviceObj.getName());	
		}
		return serviceIdNameMap;
	}
}
