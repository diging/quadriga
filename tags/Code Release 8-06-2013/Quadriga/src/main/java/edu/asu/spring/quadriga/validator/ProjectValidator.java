package edu.asu.spring.quadriga.validator;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.db.workbench.IDBConnectionProjectAccessManager;
import edu.asu.spring.quadriga.domain.implementation.Project;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
@Service
public class ProjectValidator implements Validator {

	@Autowired
	@Qualifier("DBConnectionProjectAccessManagerBean")
	IDBConnectionProjectAccessManager dbConnect;
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectValidator.class);
	
	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return arg0.isAssignableFrom(Project.class);
	}

	@Override
	public void validate(Object obj, Errors err) 
	{
		//validate all the input parameters
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "name", "project_name.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "description", "project_description.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "projectAccess", "project_projectAccess.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "unixName", "project_unixname.required");
		
		Project project = (Project)obj;

		String projUnixName = project.getUnixName();

		if(err.getFieldError("unixName")==null)
		{
		//validate the regular expression
		validateUnixNameExp(projUnixName,err);
		}
		
		
		if(err.getFieldError("unixName")==null)
		{
			try
			{
				validateUnixName(projUnixName,err);
			}
			catch(QuadrigaStorageException e)
			{
				logger.error("Error", e);
			}
		}
		
		
	}
	
	public void validateUnixNameExp(String unixName,Errors err)
	{
		String regex = "^[a-zA-Z0-9-_.+!*'()]*$";
		Pattern pattern = Pattern.compile(regex);
		
		if(!pattern.matcher(unixName).matches())
		{
			err.rejectValue("unixName","project_UnixName.expression");
		}
	}

	public void validateUnixName(String unixName,Errors err) throws QuadrigaStorageException
	{
		boolean isDuplicate;
		
		//Verifying if the Unix name already exists
		isDuplicate = dbConnect.chkDuplicateProjUnixName(unixName);
		if(isDuplicate)
		{
			err.rejectValue("unixName","projectUnixName.unique");
		}
	}
}
