package edu.asu.spring.quadriga.service.profile;

import java.util.List;

import edu.asu.spring.quadriga.web.profile.impl.SearchResultBackBean;

public interface ISearchResultBackBeanFormManager {
	
	public abstract List<SearchResultBackBean> getsearchResultBackBeanList(String serviceId, String term);

}
