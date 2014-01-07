package edu.asu.spring.quadriga.db.sql.profile;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.profile.ISearchResult;
import edu.asu.spring.quadriga.profile.ISearchResultFactory;

@Service
public class DBConnectionTempProfileManager {
	
	
	
	@Autowired
	private ISearchResultFactory searchResultFactory;

	public List<ISearchResult> showProfile(){
		List<ISearchResult> list = new ArrayList<ISearchResult>();
				
		ISearchResult searchResult1 = searchResultFactory.getSearchResultObject();
		searchResult1.setName("Viaf");
		searchResult1.setId("http://viaf.org/viaf/19675893");
		
		ISearchResult searchResult2 = searchResultFactory.getSearchResultObject();
		searchResult2.setName("Concept Power");
		searchResult2.setId("http://www.digitalhps.org/concepts/CONcdf33117-27c5-4f41-ada5-f8cbf3d7afea");
		
		list.add(searchResult1);
		list.add(searchResult2);
		
		return list;
	}
}
