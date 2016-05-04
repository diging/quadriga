package edu.asu.spring.quadriga.profile.validator;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.web.profile.impl.SearchResultBackBean;
import edu.asu.spring.quadriga.web.profile.impl.SearchResultBackBeanForm;

@Service
public class ProfileAddValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		
		return arg0.isAssignableFrom(SearchResultBackBeanForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {

		SearchResultBackBeanForm searchResultBackBeanForm = (SearchResultBackBeanForm) target;
		
		List<SearchResultBackBean> searchResultList = searchResultBackBeanForm.getSearchResultList();
		
		boolean isAllNull = true;
		
		for(int i=0;i<searchResultList.size();i++)
		{
				String recordId = searchResultList.get(i).getId();
				if(recordId != null)
				{
					isAllNull = false;
				}
		}
		
		if(isAllNull == true)
		{
			for(int i=0;i<searchResultList.size();i++)
			{
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "searchResultList["+i+"].id", "please select record in the table");
			}
		}

		
	}
	

}
