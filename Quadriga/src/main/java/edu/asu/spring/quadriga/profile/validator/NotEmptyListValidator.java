package edu.asu.spring.quadriga.profile.validator;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import edu.asu.spring.quadriga.web.profile.impl.SearchResultBackBean;

public class NotEmptyListValidator implements ConstraintValidator<NotEmptyList, List<SearchResultBackBean>> {

	@Override
	public void initialize(NotEmptyList arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(List<SearchResultBackBean> arg0,
			ConstraintValidatorContext arg1) {
		
		if(arg0.size() != 0 || arg0.isEmpty() != false)
		{
			return true;
		}
		
		return false;
	}
	
	

}
