package edu.asu.spring.quadriga.profile;

import java.util.List;

public interface IService {
	
	public abstract void setServiceId(String id);
	
	public abstract String getServiceId();
	
	public abstract void setName(String name);
	
	public abstract String getName();
	
	public abstract List<ISearchResult> search(String word);
	
	public static final String pos = "noun";
	
	public static final String startIndex = "1";

}
