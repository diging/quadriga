package edu.asu.spring.quadriga.profile.impl;

import java.util.List;

/**
 * this class holds list of Service Backing Beans objects
 * 
 * @author rohit pendbhaje
 *
 */
public class ServiceForm  {
	
	List<ServiceBackBean> serviceList;

	public List<ServiceBackBean> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<ServiceBackBean> serviceList) {
		this.serviceList = serviceList;
	}

	

}
