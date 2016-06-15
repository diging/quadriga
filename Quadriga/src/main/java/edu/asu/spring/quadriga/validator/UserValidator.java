package edu.asu.spring.quadriga.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.domain.impl.User;

/**
 * This method validates if any user is selected
 * @author kiran batna
 *
 */
@Service
public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return arg0.isAssignableFrom(User.class);
	}

	@Override
	public void validate(Object obj, Errors err) {
		ValidationUtils.rejectIfEmptyOrWhitespace(err,"userName","user_userName_selection.required");
		
		User user = (User)obj;
		
		String userName = user.getUserName();
		
		if(err.getFieldError("userName") == null)
		{
			validateUserName(userName,err);
		}

	}
	
	/**
	 * This method validates if the user name is empty
	 * @param userName
	 * @param err
	 * @author kiran batna
	 */
	public void validateUserName(String userName,Errors err)
	{
		if(userName.equals(null))
		{
			err.rejectValue("userName", "user_userName_selection.required");
		}
	}

}
