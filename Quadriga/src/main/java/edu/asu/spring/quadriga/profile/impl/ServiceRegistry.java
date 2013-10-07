package edu.asu.spring.quadriga.profile.impl;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.profile.IService;
import edu.asu.spring.quadriga.profile.IServiceRegistry;

@Service
public class ServiceRegistry implements IServiceRegistry {

	@Autowired
	SearchResult searchResult;
	
	@Autowired
	AbstractApplicationContext ctx;
	
	@Autowired
	IService serviceA;
	
	Map<String, IService> service;
	
	//private ApplicationContext context;
	
	
	public ServiceRegistry(){
		
		System.out.println(" -------------in serviceregistry constructor");
		
	}
	
	@PostConstruct
	public void init(){
		
		
//		System.out.println("------------in method A()");
		
//		System.out.println("---------name of service "+searchResult.getName());

		service = ctx.getBeansOfType(IService.class);
		//IService servicea = service.get(serviceA);
		
		serviceA.setName("serviceA");
	
	}
	
	@Override
	public IService getServiceObject(String serviceId) {
		
		//IService servicea = service.get(serviceId);
		
		//if(service.get(serviceId) == serviceId)
		
		//context = new ClassPathXmlApplicationContext("spring.xml");
		//ServiceA serviceA = (ServiceA)context.getBean("ServiceA");
		
		/*if(serviceId == serviceA.getId())
		{
			return serviceA;
		} */
		
		return service.get(serviceId);
	} 
	
	

}
