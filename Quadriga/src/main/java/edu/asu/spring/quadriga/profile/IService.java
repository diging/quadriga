package edu.asu.spring.quadriga.profile;

import java.util.List;

/**
 * this interface is implemented by all the services by user in profile
 * 
 * methods:
 * search()	 this method is used by all the service classes which implementes this interface for searching the 
 * 			 content for the particular service
 * 
 * getters and setters for the attributes like serviceId, serviceName of the service class 
 * 
 * constants:
 * POS			part of speech for conceptpower service
 * STARTINDEX	starting index in the viaf service API
 * 
 * @author rohit pendbhaje
 *
 */
public interface IService {
	
	public abstract void setServiceId(String id);
	
	public abstract String getServiceId();
	
	public abstract void setName(String name);
	
	public abstract String getName();
	
	public abstract List<ISearchResult> search(String word);
	
	public static final String POS = "noun";
	
	public static final String STARTINDEX = "1";

}
