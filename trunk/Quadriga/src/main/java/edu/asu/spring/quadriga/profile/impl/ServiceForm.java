package edu.asu.spring.quadriga.profile.impl;

import java.util.List;

public class ServiceForm extends ServiceBackBean {
	
	List<ServiceBackBean> serviceList;

	public List<ServiceBackBean> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<ServiceBackBean> serviceList) {
		this.serviceList = serviceList;
	} 

}
