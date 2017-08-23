package edu.asu.spring.quadriga.validator;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.dictionary.impl.DictionaryCollaborator;
import edu.asu.spring.quadriga.domain.impl.Collaborator;
/**
 * This class checks if the collaborator user name and roles are empty
 * @author kiran batna
 *
 */
@Service
public class CollaboratorValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		
		boolean check = arg0.isAssignableFrom(Collaborator.class); 
		
		return check ;
	}


	private static final Logger logger = LoggerFactory
			.getLogger(CollaboratorValidator.class);

	
	@Override
	public void validate(Object obj, Errors err) 
	{
		String userName;
		List<IQuadrigaRole> role;
		
		Collaborator collaborator = (Collaborator)obj;
		
		if(obj instanceof DictionaryCollaborator){
			logger.info("instance of dicitonary ");
		}else{
			logger.info(obj.getClass()+"");
		}
		userName = collaborator.getUserObj().getUserName();
		role = collaborator.getCollaboratorRoles();
		
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "userObj", "collaborator_user.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "collaboratorRoles", "collaborator_roles.required");
		if(err.getFieldError("userObj")==null)
		{

		   validateUserName(userName,err);
		}
		
		if(err.getFieldError("collaboratorRoles")==null)
		{
		validateCollaboratorRoles(role,err);
		}
	}
	
	/**
	 * This method checks if username is empty
	 * @param UserName
	 * @param err
	 * @author kiran batna
	 */
	public void validateUserName(String UserName,Errors err)
	{

		if(UserName == null){
			err.rejectValue("userObj", "collaborator_user_selection.required");
		}
	}
	
	/**
	 * This methods validates if the roles are empty
	 * @param roles
	 * @param err
	 * @author kiran batna
	 */
	public void validateCollaboratorRoles(List<IQuadrigaRole> roles,Errors err)
	{

		if(roles == null){

			err.rejectValue("collaboratorRoles", "collaborator_roles_selection.required");}
	}

}
