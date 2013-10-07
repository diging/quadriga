package edu.asu.spring.quadriga.profile;

import java.util.List;

public interface IService {
	
	public abstract void setId(String id);
	
	public abstract String getId();
	
	public abstract void setName(String name);
	
	public abstract String getName();
	
	public abstract ISearchResult search(String word);

}
