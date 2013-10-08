package edu.asu.spring.quadriga.profile.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.profile.IService;
import edu.asu.spring.quadriga.profile.IServiceRegistry;

@Service
public class ServiceRegistry implements IServiceRegistry {

	@Autowired
	private SearchResult searchResult;
	
	@Autowired
	private ApplicationContext ctx;
	
	@Autowired
	private IService serviceA;
	
	private Map<String, IService> serviceMap;
	
	private Map<String, IService> newServiceMap;
		
	
	public ServiceRegistry(){
		
		System.out.println(" -------------in serviceregistry constructor");
		
	}
	
	@PostConstruct
	public void init(){
		
		newServiceMap = new HashMap<String, IService>();

		serviceMap = ctx.getBeansOfType(IService.class);
		
		Iterator<?> serviceIter = serviceMap.entrySet().iterator();
		
		while(serviceIter.hasNext()){
			
			@SuppressWarnings("rawtypes")
			Map.Entry mEntry = (Map.Entry) serviceIter.next();
			IService serviceObject = (IService) mEntry.getValue();
			newServiceMap.put(serviceObject.getId(), serviceObject);
			
		}
		
//		Iterator<?> newServiceIter = newServiceMap.entrySet().iterator();
//		
//		while(newServiceIter.hasNext()){
//			
//			Map.Entry mEntry = (Map.Entry) newServiceIter.next();
//			
//			//IService serviceobject = (IService) mEntry.getValue();
//			System.out.println(mEntry.getKey()+":"+mEntry.getValue());
//			
//		}
		
		//Iterator<IService> iterator =  
		
		
		//IService servicea = service.get(serviceA);
		
		//serviceA.setName("serviceA");
	
	}
	
	@Override
	public IService getServiceObject(String serviceId) {
		
		
		
		return serviceMap.get(serviceId);
	}

	@Override
	public List<String> getServiceIds() {
			
		List<String> serviceIdList = new ArrayList<String>();
		Set<String> serviceIds= newServiceMap.keySet();
		
		Iterator<String> serviceIdIter = serviceIds.iterator();
		
		while(serviceIdIter.hasNext()){
			
			serviceIdList.add(serviceIdIter.next());
		}
		
		return serviceIdList;
	}

	@Override
	public List<String> getServiceNames() {

		List<String> serviceNames = new ArrayList<String>();
		Iterator<?> serviceIter = newServiceMap.entrySet().iterator();
		
		while(serviceIter.hasNext()){
			
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry)serviceIter.next();
			
			IService serviceObj = (IService) entry.getValue();
			serviceNames.add(serviceObj.getName());
		}
		
		return serviceNames;
	}

	@Override
	public List<IService> getServiceObjectList() {
		
		List<IService> serviceObjList = new ArrayList<IService>();
		Iterator<?> serviceIter = newServiceMap.entrySet().iterator();
		
		while(serviceIter.hasNext()){
			
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry)serviceIter.next();
			
			IService serviceObj = (IService) entry.getValue();
			serviceObjList.add(serviceObj);
		}
		
		return serviceObjList;
	} 
	
	
	

}
