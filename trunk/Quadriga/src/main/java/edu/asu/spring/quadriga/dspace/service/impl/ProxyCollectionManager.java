package edu.asu.spring.quadriga.dspace.service.impl;

import java.util.concurrent.Callable;

import edu.asu.spring.quadriga.domain.ICollection;

public class ProxyCollectionManager implements Callable<ICollection> {

	String sCollectionId;
	
	public ProxyCollectionManager(String sCollectionId)
	{
		this.sCollectionId = sCollectionId;
	}
	
	@Override
	public ICollection call() throws Exception {
		
		//TODO Make rest call to get collection object 
		
		return null;
	}	

}
