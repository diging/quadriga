package edu.asu.spring.quadriga.profile.impl;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Service;

@Service
public class SearchResultBackBeanForm {
	
	
	private List<SearchResultBackBean> searchResultList;

	public List<SearchResultBackBean> getSearchResultList() {
		return searchResultList;
	}

	public void setSearchResultList(List<SearchResultBackBean> searchResultList) {
		this.searchResultList = searchResultList;
	}
}
